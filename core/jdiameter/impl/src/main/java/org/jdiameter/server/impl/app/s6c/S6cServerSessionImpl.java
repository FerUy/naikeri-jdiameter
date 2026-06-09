package org.jdiameter.server.impl.app.s6c;

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
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.api.s6c.ServerS6cSessionListener;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.s6c.IS6cMessageFactory;
import org.jdiameter.common.api.app.s6c.S6cSessionState;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.jdiameter.common.impl.app.s6c.S6cSession;
import org.jdiameter.server.impl.app.s6c.Event.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cServerSessionImpl extends S6cSession implements ServerS6cSession, EventListener<Request, Answer>, NetworkReqListener {

  private static final Logger logger = LoggerFactory.getLogger(S6cServerSessionImpl.class);

  // Factories and Listeners
  // --------------------------------------------------
  private transient ServerS6cSessionListener listener;
  protected long appId;
  protected IServerS6cSessionData sessionData;

  public S6cServerSessionImpl(IServerS6cSessionData sessionData, IS6cMessageFactory fct, ISessionFactory sf,
                              ServerS6cSessionListener lst) {
    super(sf, sessionData);
    if (lst == null) {
      throw new IllegalArgumentException("Listener can not be null");
    }
    if ((this.appId = fct.getApplicationId()) < 0) {
      throw new IllegalArgumentException("ApplicationId can not be less than zero");
    }

    this.listener = lst;
    super.messageFactory = fct;
    this.sessionData = sessionData;
  }

  public void sendSendRoutingInfoForSMAnswer(SendRoutingInfoForSMAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, null, answer);
  }

  public void sendReportSMDeliveryStatusAnswer(ReportSMDeliveryStatusAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, null, answer);
  }

  public void sendAlertServiceCentreRequest(AlertServiceCentreRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, request, null);
  }

  @SuppressWarnings("unchecked")
  public <E> E getState(Class<E> stateType) {
    return stateType == S6cSessionState.class ? (E) this.sessionData.getS6cSessionState() : null;
  }

  @SuppressWarnings("unused")
  public boolean handleEvent(StateEvent event) throws InternalException, OverloadException {
    try {
      sendAndStateLock.lock();
      if (!super.session.isValid()) {
        // FIXME: throw new InternalException("Generic session is not valid.");
        return false;
      }
      final S6cSessionState state = this.sessionData.getS6cSessionState();
      S6cSessionState newState;
      Event localEvent = (Event) event;
      Event.Type eventType = (Type) event.getType();

      switch (state) {

        case IDLE:
          switch (eventType) {

            case RECEIVE_SRR:
              this.sessionData.setBuffer((Request) ((AppEvent) event.getData()).getMessage());
              super.cancelMsgTimer();
              super.startMsgTimer();
              newState = S6cSessionState.MESSAGE_SENT_RECEIVED;
              setState(newState);
              listener.doSendRoutingInfoForSMRequestEvent(this, (SendRoutingInfoForSMRequest) event.getData());
              break;

            case RECEIVE_RDR:
              this.sessionData.setBuffer((Request) ((AppEvent) event.getData()).getMessage());
              super.cancelMsgTimer();
              super.startMsgTimer();
              newState = S6cSessionState.MESSAGE_SENT_RECEIVED;
              setState(newState);
              listener.doReportSMDeliveryStatusRequestEvent(this, (ReportSMDeliveryStatusRequest) event.getData());
              break;

            case SEND_MESSAGE:
              super.session.send(((AppEvent) event.getData()).getMessage(), this);
              newState = S6cSessionState.MESSAGE_SENT_RECEIVED;
              setState(newState);
              break;

            default:
              logger.error("Wrong action in S6c Server FSM. State: IDLE, Event Type: {}", eventType);
              break;
          }
          break;

        case MESSAGE_SENT_RECEIVED:
          switch (eventType) {
            case TIMEOUT_EXPIRES:
              newState = S6cSessionState.TIMEDOUT;
              setState(newState);
              break;

            case RECEIVE_ALA:
              newState = S6cSessionState.TERMINATED;
              setState(newState);
              listener.doAlertServiceCentreAnswerEvent(this, (AlertServiceCentreRequest) localEvent.getRequest(),
                  (AlertServiceCentreAnswer) localEvent.getAnswer());
              break;

            case SEND_MESSAGE:
              try {
                super.session.send(((AppEvent) event.getData()).getMessage(), this);
              } finally {
                newState = S6cSessionState.TERMINATED;
                setState(newState);
              }
              break;

            default:
              throw new InternalException(
                  "Should not receive more messages after initial. Command: " + event.getData());
          }
          break;

        case TERMINATED:
          throw new InternalException("Cant receive message in state TERMINATED. Command: " + event.getData());

        case TIMEDOUT:
          throw new InternalException("Cant receive message in state TIMEDOUT. Command: " + event.getData());

        default:
          logger.error("S6c Server FSM in wrong state: {}", state);
          break;
      }
    } catch (Exception e) {
      throw new InternalException(e);
    } finally {
      sendAndStateLock.unlock();
    }
    return true;
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

  public Answer processRequest(Request request) {
    RequestDelivery rd = new RequestDelivery();
    rd.session = this;
    rd.request = request;
    super.scheduler.execute(rd);
    return null;
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

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void setState(S6cSessionState newState) {
    S6cSessionState oldState = this.sessionData.getS6cSessionState();
    this.sessionData.setS6cSessionState(newState);

    for (StateChangeListener i : stateListeners) {
      i.stateChanged(this, oldState, newState);
    }
    if (newState == S6cSessionState.TERMINATED || newState == S6cSessionState.TIMEDOUT) {
      super.cancelMsgTimer();
      this.release();
    }
  }

  @Override
  public void onTimer(String timerName) {
    if (timerName.equals(S6cSession.TIMER_NAME_MSG_TIMEOUT)) {
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
    ServerS6cSession session;
    Request request;

    public void run() {
      try {
        switch (request.getCommandCode()) {

          case SendRoutingInfoForSMRequest.code:
            handleEvent(
                new Event(Event.Type.RECEIVE_SRR, messageFactory.createSendRoutingInfoForSMRequest(request), null));
            break;

          case ReportSMDeliveryStatusRequest.code:
            handleEvent(
                new Event(Event.Type.RECEIVE_RDR, messageFactory.createReportSMDeliveryStatusRequest(request), null));
            break;

          default:
            listener.doOtherEvent(session, new AppRequestEventImpl(request), null);
            break;
        }
      } catch (Exception e) {
        logger.debug("Failed to process request message", e);
      }
    }
  }

  private class AnswerDelivery implements Runnable {
    ServerS6cSession session;
    Answer answer;
    Request request;

    public void run() {
      try {
        if (answer.getCommandCode() == AlertServiceCentreAnswer.code) {
          handleEvent(new Event(Type.RECEIVE_ALA, messageFactory.createAlertServiceCentreRequest(request),
              messageFactory.createAlertServiceCentreAnswer(answer)));
        } else {
          listener.doOtherEvent(session, new AppRequestEventImpl(request), new AppAnswerEventImpl(answer));
        }
      } catch (Exception e) {
        logger.debug("Failed to process success message", e);
      }
    }
  }

}
