package org.jdiameter.client.impl.app.s6c;

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
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.ClientS6cSessionListener;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.impl.app.s6c.Event.Type;
import org.jdiameter.common.api.app.s6c.IS6cMessageFactory;
import org.jdiameter.common.api.app.s6c.S6cSessionState;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.jdiameter.common.impl.app.s6c.S6cSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cClientSessionImpl extends S6cSession implements ClientS6cSession, EventListener<Request, Answer>, NetworkReqListener {

  private static final Logger logger = LoggerFactory.getLogger(S6cClientSessionImpl.class);

  private transient ClientS6cSessionListener listener;

  protected long appId = -1;
  protected IClientS6cSessionData sessionData;

  public S6cClientSessionImpl(IClientS6cSessionData sessionData, IS6cMessageFactory fct, ISessionFactory sf,
                              ClientS6cSessionListener lst) {
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
    return stateType == S6cSessionState.class ? (E) this.sessionData.getS6cSessionState() : null;
  }

  public Answer processRequest(Request request) {
    RequestDelivery rd = new RequestDelivery();
    rd.session = this;
    rd.request = request;
    super.scheduler.execute(rd);
    return null;
  }

  public void sendSendRoutingInfoForSMRequest(SendRoutingInfoForSMRequest sendRoutingInfoForSMRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, sendRoutingInfoForSMRequest, null);
  }

  public void sendReportSMDeliveryStatusRequest(ReportSMDeliveryStatusRequest reportSMDeliveryStatusRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, reportSMDeliveryStatusRequest, null);
  }


  public void sendAlertServiceCentreAnswer(AlertServiceCentreAnswer alertServiceCentreAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    send(Event.Type.SEND_MESSAGE, null, alertServiceCentreAnswer);
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
      final S6cSessionState state = this.sessionData.getS6cSessionState();
      S6cSessionState newState;
      Event localEvent = (Event) event;
      Event.Type eventType = (Type) event.getType();
      switch (state) {

        case IDLE:
          switch (eventType) {

            case SEND_MESSAGE:
              newState = S6cSessionState.MESSAGE_SENT_RECEIVED;
              super.session.send(((AppEvent) event.getData()).getMessage(), this);
              setState(newState); // FIXME: is this ok to be here?
              break;

            case RECEIVE_ALR:
              this.sessionData.setBuffer((Request) ((AppEvent) event.getData()).getMessage());
              super.cancelMsgTimer();
              super.startMsgTimer();
              newState = S6cSessionState.MESSAGE_SENT_RECEIVED;
              setState(newState);
              listener.doAlertServiceCentreRequestEvent(this, (AlertServiceCentreRequest) event.getData());
              break;

            default:
              logger.error("Invalid Event Type {} for S6c Client Session at state {}.", eventType,
                  sessionData.getS6cSessionState());
              break;
          }
          break;

        case MESSAGE_SENT_RECEIVED:
          switch (eventType) {
            case TIMEOUT_EXPIRES:
              newState = S6cSessionState.TIMEDOUT;
              setState(newState);
              break;

            case SEND_MESSAGE:
              try {
                super.session.send(((AppEvent) event.getData()).getMessage(), this);
              } finally {
                newState = S6cSessionState.TERMINATED;
                setState(newState);
              }
              break;

            case RECEIVE_SRA:
              //newState = S6cSessionState.TERMINATED;
              newState = S6cSessionState.IDLE;
              setState(newState);
              listener.doSendRoutingInfoForSMAnswerEvent(this, (SendRoutingInfoForSMRequest) localEvent.getRequest(),
                  (SendRoutingInfoForSMAnswer) localEvent.getAnswer());
              break;

            case RECEIVE_RDA:
              //newState = S6cSessionState.TERMINATED;
              newState = S6cSessionState.IDLE;
              setState(newState);
              listener.doReportSMDeliveryStatusAnswerEvent(this, (ReportSMDeliveryStatusRequest) localEvent.getRequest(),
                  (ReportSMDeliveryStatusAnswer) localEvent.getAnswer());
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
          logger.error("S6c Client FSM in wrong state: {}", state);
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

    S6cClientSessionImpl other = (S6cClientSessionImpl) obj;
    if (appId != other.appId) {
      return false;
    }
    return true;
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
    ClientS6cSession session;
    Request request;

    public void run() {
      try {
        if (request.getCommandCode() == AlertServiceCentreRequest.code) {
          handleEvent(
              new Event(Type.RECEIVE_ALR, messageFactory.createAlertServiceCentreRequest(request), null));
        } else {
          listener.doOtherEvent(session, new AppRequestEventImpl(request), null);
        }
      } catch (Exception e) {
        logger.debug("Failed to process request message", e);
      }
    }
  }

  private class AnswerDelivery implements Runnable {
    ClientS6cSession session;
    Answer answer;
    Request request;

    public void run() {
      try {
        switch (answer.getCommandCode()) {

          case SendRoutingInfoForSMAnswer.code:
            handleEvent(new Event(Event.Type.RECEIVE_SRA, messageFactory.createSendRoutingInfoForSMRequest(request),
                messageFactory.createSendRoutingInfoForSMAnswer(answer)));
            break;

          case ReportSMDeliveryStatusAnswer.code:
            handleEvent(new Event(Event.Type.RECEIVE_RDA, messageFactory.createReportSMDeliveryStatusRequest(request),
                messageFactory.createReportSMDeliveryStatusAnswer(answer)));
            break;

          default:
            listener.doOtherEvent(session, new AppRequestEventImpl(request), new AppAnswerEventImpl(answer));
            break;
        }
      } catch (Exception e) {
        logger.debug("Failed to process success message", e);
      }
    }
  }

}
