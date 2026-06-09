package org.jdiameter.client.impl.app.sgd;

import org.jdiameter.api.Answer;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateEvent;
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.api.sgd.ClientSGdSessionListener;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.impl.app.sgd.Event.Type;
import org.jdiameter.common.api.app.sgd.ISGdMessageFactory;
import org.jdiameter.common.api.app.sgd.SGdSessionState;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.jdiameter.common.impl.app.sgd.SGdSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SGdClientSessionImpl extends SGdSession implements ClientSGdSession, EventListener<Request, Answer>, NetworkReqListener {

  private static final Logger logger = LoggerFactory.getLogger(SGdClientSessionImpl.class);

  private transient ClientSGdSessionListener listener;

  protected long appId;
  protected IClientSGdSessionData sessionData;

  public SGdClientSessionImpl(IClientSGdSessionData sessionData, ISGdMessageFactory fct, ISessionFactory sf,
                              ClientSGdSessionListener lst) {
    super(sf, sessionData);
    if (lst == null) {
      throw new IllegalArgumentException("Listener can not be null");
    }
    if (fct.getApplicationId() < 0) {
      throw new IllegalArgumentException("ApplicationId can not be less than zero");
    }

    this.appId = fct.getApplicationId();
    this.listener = lst;
    super.messageFactory = fct;
    this.sessionData = sessionData;
  }

  @SuppressWarnings("unchecked")
  public <E> E getState(Class<E> stateType) {
    return stateType == SGdSessionState.class ? (E) this.sessionData.getSGdSessionState() : null;
  }

  public Answer processRequest(Request request) {
    RequestDelivery rd = new RequestDelivery();
    rd.session = this;
    rd.request = request;
    super.scheduler.execute(rd);
    return null;
  }

  public void sendMTForwardShortMessageRequest(MTForwardShortMessageRequest mtForwardShortMessageRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, mtForwardShortMessageRequest, null);
  }

  public void sendMOForwardShortMessageAnswer(MOForwardShortMessageAnswer moForwardShortMessageAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, null, moForwardShortMessageAnswer);
  }

  public void receivedSuccessMessage(Request request, Answer answer) {
    AnswerDelivery rd = new AnswerDelivery();
    rd.session = this;
    rd.request = request;
    rd.answer = answer;
    super.scheduler.execute(rd);
  }

  public void timeoutExpired(Request request) {
    try {
      handleEvent(new Event(Event.Type.TIMEOUT_EXPIRES, new AppRequestEventImpl(request), null));
    } catch (Exception e) {
      logger.debug("Failed to process timeout message", e);
    }
  }

  protected void send(Event.Type type, AppEvent request, AppEvent answer) throws InternalException {
    try {
      if (type != null) {
        handleEvent(new Event(type, request, answer));
      }
    } catch (Exception e) {
      throw new InternalException(e);
    }
  }

  public boolean handleEvent(StateEvent event) throws InternalException, OverloadException {
    try {
      sendAndStateLock.lock();
      if (!super.session.isValid()) {
        // FIXME: throw new InternalException("Generic session is not valid.");
        return false;
      }
      final SGdSessionState state = this.sessionData.getSGdSessionState();
      SGdSessionState newState;
      Event localEvent = (Event) event;
      Event.Type eventType = (Type) event.getType();
      switch (state) {

        case IDLE:
          switch (eventType) {

            case SEND_MESSAGE:
              newState = SGdSessionState.MESSAGE_SENT_RECEIVED;
              super.session.send(((AppEvent) event.getData()).getMessage(), this);
              setState(newState); // FIXME: is this ok to be here?
              break;

            case RECEIVE_OFR:
              this.sessionData.setBuffer((Request) ((AppEvent) event.getData()).getMessage());
              super.cancelMsgTimer();
              super.startMsgTimer();
              newState = SGdSessionState.MESSAGE_SENT_RECEIVED;
              setState(newState);
              listener.doMOForwardShortMessageRequestEvent(this, (MOForwardShortMessageRequest) event.getData());
              break;

            default:
              logger.error("Invalid Event Type {} for SGd Client Session at state {}.", eventType,
                  sessionData.getSGdSessionState());
              break;
          }
          break;

        case MESSAGE_SENT_RECEIVED:
          switch (eventType) {
            case TIMEOUT_EXPIRES:
              newState = SGdSessionState.TIMEDOUT;
              setState(newState);
              break;

            case SEND_MESSAGE:
              try {
                super.session.send(((AppEvent) event.getData()).getMessage(), this);
              } finally {
                newState = SGdSessionState.TERMINATED;
                setState(newState);
              }
              break;

            case RECEIVE_TFA:
              //newState = SGdSessionState.TERMINATED;
              newState = SGdSessionState.IDLE;
              setState(newState);
              listener.doMTForwardShortMessageAnswerEvent(this, (MTForwardShortMessageRequest) localEvent.getRequest(),
                  (MTForwardShortMessageAnswer) localEvent.getAnswer());
              break;

            default:
              throw new InternalException("Unexpected/Unknown message received: " + event.getData());
          }
          break;

        case TERMINATED:
          throw new InternalException("Cant receive message in state TERMINATED. Command: " + event.getData());

        case TIMEDOUT:
          throw new InternalException("Cant receive message in state TIMEDOUT. Command: " + event.getData());

        default:
          logger.error("SGd Client FSM in wrong state: {}", state);
          break;
      }
    } catch (Exception e) {
      throw new InternalException(e);
    } finally {
      sendAndStateLock.unlock();
    }
    return true;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void setState(SGdSessionState newState) {
    SGdSessionState oldState = this.sessionData.getSGdSessionState();
    this.sessionData.setSGdSessionState(newState);

    for (StateChangeListener i : stateListeners) {
      i.stateChanged(this, oldState, newState);
    }
    if (newState == SGdSessionState.TERMINATED || newState == SGdSessionState.TIMEDOUT) {
      super.cancelMsgTimer();
      this.release();
    }
  }

  public void onTimer(String timerName) {
    if (timerName.equals(SGdSession.TIMER_NAME_MSG_TIMEOUT)) {
      try {
        sendAndStateLock.lock();
        try {
          handleEvent(
              new Event(Event.Type.TIMEOUT_EXPIRES, new AppRequestEventImpl(this.sessionData.getBuffer()), null));
        } catch (Exception e) {
          logger.debug("Failure handling Timeout event.");
        }
        this.sessionData.setBuffer(null);
        this.sessionData.setTsTimerId(null);
      } finally {
        sendAndStateLock.unlock();
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (int) (appId ^ (appId >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    SGdClientSessionImpl other = (SGdClientSessionImpl) obj;
    return appId == other.appId;
  }

  @Override
  public void release() {
    if (isValid()) {
      try {
        sendAndStateLock.lock();
        super.release();
      } catch (Exception e) {
        logger.debug("Failed to release session", e);
      } finally {
        sendAndStateLock.unlock();
      }
    } else {
      logger.debug("Trying to release an already invalid session, with Session ID '{}'", getSessionId());
    }
  }

  private class RequestDelivery implements Runnable {
    ClientSGdSession session;
    Request request;

    public void run() {
      try {
        if (request.getCommandCode() == MOForwardShortMessageRequest.code) {
          handleEvent(
              new Event(Type.RECEIVE_OFR, messageFactory.createMOForwardShortMessageRequest(request), null));
        } else {
          listener.doOtherEvent(session, new AppRequestEventImpl(request), null);
        }
      } catch (Exception e) {
        logger.debug("Failed to process request message", e);
      }
    }
  }

  private class AnswerDelivery implements Runnable {
    ClientSGdSession session;
    Answer answer;
    Request request;

    public void run() {
      try {
        if (answer.getCommandCode() == MTForwardShortMessageAnswer.code) {
          handleEvent(new Event(Type.RECEIVE_TFA, messageFactory.createMTForwardShortMessageRequest(request),
              messageFactory.createMTForwardShortMessageAnswer(answer)));
        } else {
          listener.doOtherEvent(session, new AppRequestEventImpl(request), new AppAnswerEventImpl(answer));
        }
      } catch (Exception e) {
        logger.debug("Failed to process success message", e);
      }
    }
  }
}
