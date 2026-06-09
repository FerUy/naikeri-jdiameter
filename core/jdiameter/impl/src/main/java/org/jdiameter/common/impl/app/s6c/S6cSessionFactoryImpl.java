package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.SessionFactory;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.ClientS6cSessionListener;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.api.s6c.ServerS6cSessionListener;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.impl.app.s6c.IClientS6cSessionData;
import org.jdiameter.client.impl.app.s6c.S6cClientSessionImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.s6c.IS6cMessageFactory;
import org.jdiameter.common.api.app.s6c.IS6cSessionData;
import org.jdiameter.common.api.app.s6c.IS6cSessionFactory;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.jdiameter.server.impl.app.s6c.IServerS6cSessionData;
import org.jdiameter.server.impl.app.s6c.S6cServerSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cSessionFactoryImpl implements IS6cSessionFactory, ServerS6cSessionListener, ClientS6cSessionListener,
    IS6cMessageFactory, StateChangeListener<AppSession> {

  private static final Logger logger = LoggerFactory.getLogger(S6cSessionFactoryImpl.class);

  protected ISessionFactory sessionFactory;

  protected ServerS6cSessionListener serverSessionListener;
  protected ClientS6cSessionListener clientSessionListener;

  protected IS6cMessageFactory messageFactory;
  protected StateChangeListener<AppSession> stateListener;
  protected ISessionDatasource iss;
  protected IAppSessionDataFactory<IS6cSessionData> sessionDataFactory;

  public S6cSessionFactoryImpl() {
  }

  public S6cSessionFactoryImpl(SessionFactory sessionFactory) {
    super();
    init(sessionFactory);
  }

  public void init(SessionFactory sessionFactory) {
    this.sessionFactory = (ISessionFactory) sessionFactory;
    this.iss = this.sessionFactory.getContainer().getAssemblerFacility().getComponentInstance(ISessionDatasource.class);
    this.sessionDataFactory = (IAppSessionDataFactory<IS6cSessionData>) this.iss.getDataFactory(IS6cSessionData.class);
  }

  /**
   * @return the serverSessionListener
   */
  public ServerS6cSessionListener getServerSessionListener() {
    return serverSessionListener != null ? serverSessionListener : this;
  }

  /**
   * @param serverSessionListener the serverSessionListener to set
   */
  public void setServerSessionListener(ServerS6cSessionListener serverSessionListener) {
    this.serverSessionListener = serverSessionListener;
  }

  /**
   * @return the serverSessionListener
   */
  public ClientS6cSessionListener getClientSessionListener() {
    return clientSessionListener != null ? clientSessionListener : this;
  }

  /**
   * @param clientSessionListener the clientSessionListener to set
   */
  public void setClientSessionListener(ClientS6cSessionListener clientSessionListener) {
    this.clientSessionListener = clientSessionListener;
  }

  /**
   * @return the messageFactory
   */
  public IS6cMessageFactory getMessageFactory() {
    return messageFactory != null ? messageFactory : this;
  }

  /**
   * @param messageFactory the messageFactory to set
   */
  public void setMessageFactory(IS6cMessageFactory messageFactory) {
    this.messageFactory = messageFactory;
  }

  /**
   * @return the stateListener
   */
  public StateChangeListener<AppSession> getStateListener() {
    return stateListener != null ? stateListener : this;
  }

  /**
   * @param stateListener the stateListener to set
   */
  public void setStateListener(StateChangeListener<AppSession> stateListener) {
    this.stateListener = stateListener;
  }

  public AppSession getSession(String sessionId, Class<? extends AppSession> aClass) {
    if (sessionId == null) {
      throw new IllegalArgumentException("SessionId must not be null");
    }
    if (!this.iss.exists(sessionId)) {
      return null;
    }
    AppSession appSession = null;
    try {
      if (aClass == ServerS6cSession.class) {
        IServerS6cSessionData sessionData = (IServerS6cSessionData) this.sessionDataFactory
            .getAppSessionData(ServerS6cSession.class, sessionId);
        S6cServerSessionImpl serverSession = new S6cServerSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getServerSessionListener());
        serverSession.getSessions().get(0).setRequestListener(serverSession);
        appSession = serverSession;
      } else if (aClass == ClientS6cSession.class) {
        IClientS6cSessionData sessionData = (IClientS6cSessionData) this.sessionDataFactory
            .getAppSessionData(ClientS6cSession.class, sessionId);
        S6cClientSessionImpl clientSession = new S6cClientSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getClientSessionListener());
        clientSession.getSessions().get(0).setRequestListener(clientSession);
        appSession = clientSession;
      } else {
        throw new IllegalArgumentException(
            "Wrong session class: " + aClass + ". Supported[" + ServerS6cSession.class + "]");
      }
    } catch (Exception e) {
      logger.error("Failure to obtain new S6c Session.", e);
    }
    return appSession;
  }

  public AppSession getNewSession(String sessionId, Class<? extends AppSession> aClass, ApplicationId applicationId,
                                  Object[] args) {
    AppSession appSession = null;

    try {
      if (aClass == ServerS6cSession.class) {
        if (sessionId == null) {
          if (args != null && args.length > 0 && args[0] instanceof Request) {
            Request request = (Request) args[0];
            sessionId = request.getSessionId();
          } else {
            sessionId = this.sessionFactory.getSessionId();
          }
        }
        IServerS6cSessionData sessionData = (IServerS6cSessionData) this.sessionDataFactory
            .getAppSessionData(ServerS6cSession.class, sessionId);
        sessionData.setApplicationId(applicationId);
        S6cServerSessionImpl serverSession = new S6cServerSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getServerSessionListener());

        iss.addSession(serverSession);
        serverSession.getSessions().get(0).setRequestListener(serverSession);
        appSession = serverSession;
      } else if (aClass == ClientS6cSession.class) {
        if (sessionId == null) {
          if (args != null && args.length > 0 && args[0] instanceof Request) {
            Request request = (Request) args[0];
            sessionId = request.getSessionId();
          } else {
            sessionId = this.sessionFactory.getSessionId();
          }
        }
        IClientS6cSessionData sessionData = (IClientS6cSessionData) this.sessionDataFactory
            .getAppSessionData(ClientS6cSession.class, sessionId);
        sessionData.setApplicationId(applicationId);
        S6cClientSessionImpl clientSession = new S6cClientSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getClientSessionListener());

        iss.addSession(clientSession);
        clientSession.getSessions().get(0).setRequestListener(clientSession);
        appSession = clientSession;
      } else {
        throw new IllegalArgumentException(
            "Wrong session class: " + aClass + ". Supported[" + ServerS6cSession.class + "]");
      }
    } catch (Exception e) {
      logger.error("Failure to obtain new S6c Session.", e);
    }
    return appSession;
  }

  public void stateChanged(Enum oldState, Enum newState) {
    logger.info("Diameter S6c Session Factory :: stateChanged :: oldState[{}], newState[{}]", oldState, newState);
  }

  public long getApplicationId() {
    return 16777312;
  }

  public void stateChanged(AppSession source, Enum oldState, Enum newState) {
    logger.info("Diameter S6c Session Factory :: stateChanged :: Session, [{}], oldState[{}], newState[{}]",
        new Object[] { source, oldState, newState });
  }

  public SendRoutingInfoForSMRequest createSendRoutingInfoForSMRequest(Request request) {
    return new SendRoutingInfoForSMRequestImpl(request);
  }

  public SendRoutingInfoForSMAnswer createSendRoutingInfoForSMAnswer(Answer answer) {
    return new SendRoutingInfoForSMAnswerImpl(answer);
  }

  public void doSendRoutingInfoForSMRequestEvent(ServerS6cSession appSession, SendRoutingInfoForSMRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter S6c Session Factory :: doSendRoutingInfoForSMRequest :: appSession[{}], Request[{}]", appSession,
        request);
  }

  public void doSendRoutingInfoForSMAnswerEvent(ClientS6cSession appSession, SendRoutingInfoForSMRequest request,
                                                SendRoutingInfoForSMAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter S6c Session Factory :: doSendRoutingInfoForSMAnswerEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

  public ReportSMDeliveryStatusRequest createReportSMDeliveryStatusRequest(Request request) {
    return new ReportSMDeliveryStatusRequestImpl(request);
  }

  public ReportSMDeliveryStatusAnswer createReportSMDeliveryStatusAnswer(Answer answer) {
    return new ReportSMDeliveryStatusAnswerImpl(answer);
  }

  public void doReportSMDeliveryStatusRequestEvent(ServerS6cSession appSession, ReportSMDeliveryStatusRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter S6c Session Factory :: doReportSMDeliveryStatusRequestEvent :: appSession[{}], Request[{}]", appSession,
        request);
  }

  public void doReportSMDeliveryStatusAnswerEvent(ClientS6cSession appSession, ReportSMDeliveryStatusRequest request,
                                                  ReportSMDeliveryStatusAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter S6c Session Factory :: doReportSMDeliveryStatusAnswerEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

  public AlertServiceCentreRequest createAlertServiceCentreRequest(Request request) {
    return new AlertServiceCentreRequestImpl(request);
  }

  public AlertServiceCentreAnswer createAlertServiceCentreAnswer(Answer answer) {
    return new AlertServiceCentreAnswerImpl(answer);
  }

  public void doAlertServiceCentreRequestEvent(ClientS6cSession appSession, AlertServiceCentreRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    logger.info("Diameter S6c Session Factory :: doAlertServiceCentreRequestEvent :: appSession[{}], Request[{}]", appSession,
        request);
  }

  public void doAlertServiceCentreAnswerEvent(ServerS6cSession appSession, AlertServiceCentreRequest request,
                                              AlertServiceCentreAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter S6c Session Factory :: doAlertServiceCentreAnswerEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

  public void doOtherEvent(AppSession appSession, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    logger.info("Diameter S6c Session Factory :: doOtherEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

}
