package org.jdiameter.common.impl.app.sgd;

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
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.api.sgd.ClientSGdSessionListener;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.api.sgd.ServerSGdSessionListener;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.impl.app.sgd.IClientSGdSessionData;
import org.jdiameter.client.impl.app.sgd.SGdClientSessionImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.sgd.ISGdMessageFactory;
import org.jdiameter.common.api.app.sgd.ISGdSessionData;
import org.jdiameter.common.api.app.sgd.ISGdSessionFactory;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.jdiameter.server.impl.app.sgd.IServerSGdSessionData;
import org.jdiameter.server.impl.app.sgd.SGdServerSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SGdSessionFactoryImpl implements ISGdSessionFactory, ServerSGdSessionListener, ClientSGdSessionListener,
    ISGdMessageFactory, StateChangeListener<AppSession> {

  private static final Logger logger = LoggerFactory.getLogger(SGdSessionFactoryImpl.class);

  protected ISessionFactory sessionFactory;

  protected ServerSGdSessionListener serverSessionListener;
  protected ClientSGdSessionListener clientSessionListener;

  protected ISGdMessageFactory messageFactory;
  protected StateChangeListener<AppSession> stateListener;
  protected ISessionDatasource iss;
  protected IAppSessionDataFactory<ISGdSessionData> sessionDataFactory;

  public SGdSessionFactoryImpl() {
  }

  public SGdSessionFactoryImpl(SessionFactory sessionFactory) {
    super();
    init(sessionFactory);
  }

  public void init(SessionFactory sessionFactory) {
    this.sessionFactory = (ISessionFactory) sessionFactory;
    this.iss = this.sessionFactory.getContainer().getAssemblerFacility().getComponentInstance(ISessionDatasource.class);
    this.sessionDataFactory = (IAppSessionDataFactory<ISGdSessionData>) this.iss.getDataFactory(ISGdSessionData.class);
  }

  /**
   * @return the serverSessionListener
   */
  public ServerSGdSessionListener getServerSessionListener() {
    return serverSessionListener != null ? serverSessionListener : this;
  }

  /**
   * @param serverSessionListener the serverSessionListener to set
   */
  public void setServerSessionListener(ServerSGdSessionListener serverSessionListener) {
    this.serverSessionListener = serverSessionListener;
  }

  /**
   * @return the serverSessionListener
   */
  public ClientSGdSessionListener getClientSessionListener() {
    return clientSessionListener != null ? clientSessionListener : this;
  }

  /**
   * @param clientSessionListener the clientSessionListener to set
   */
  public void setClientSessionListener(ClientSGdSessionListener clientSessionListener) {
    this.clientSessionListener = clientSessionListener;
  }

  /**
   * @return the messageFactory
   */
  public ISGdMessageFactory getMessageFactory() {
    return messageFactory != null ? messageFactory : this;
  }

  /**
   * @param messageFactory the messageFactory to set
   */
  public void setMessageFactory(ISGdMessageFactory messageFactory) {
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
      if (aClass == ServerSGdSession.class) {
        IServerSGdSessionData sessionData = (IServerSGdSessionData) this.sessionDataFactory
            .getAppSessionData(ServerSGdSession.class, sessionId);
        SGdServerSessionImpl serverSession = new SGdServerSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getServerSessionListener());
        serverSession.getSessions().get(0).setRequestListener(serverSession);
        appSession = serverSession;
      } else if (aClass == ClientSGdSession.class) {
        IClientSGdSessionData sessionData = (IClientSGdSessionData) this.sessionDataFactory
            .getAppSessionData(ClientSGdSession.class, sessionId);
        SGdClientSessionImpl clientSession = new SGdClientSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getClientSessionListener());
        clientSession.getSessions().get(0).setRequestListener(clientSession);
        appSession = clientSession;
      } else {
        throw new IllegalArgumentException(
            "Wrong session class: " + aClass + ". Supported[" + ServerSGdSession.class + "]");
      }
    } catch (Exception e) {
      logger.error("Failure to obtain new SGd Session.", e);
    }
    return appSession;
  }

  public AppSession getNewSession(String sessionId, Class<? extends AppSession> aClass, ApplicationId applicationId,
                                  Object[] args) {
    AppSession appSession = null;

    try {
      if (aClass == ServerSGdSession.class) {
        if (sessionId == null) {
          if (args != null && args.length > 0 && args[0] instanceof Request) {
            Request request = (Request) args[0];
            sessionId = request.getSessionId();
          } else {
            sessionId = this.sessionFactory.getSessionId();
          }
        }
        IServerSGdSessionData sessionData = (IServerSGdSessionData) this.sessionDataFactory
            .getAppSessionData(ServerSGdSession.class, sessionId);
        sessionData.setApplicationId(applicationId);
        SGdServerSessionImpl serverSession = new SGdServerSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getServerSessionListener());

        iss.addSession(serverSession);
        serverSession.getSessions().get(0).setRequestListener(serverSession);
        appSession = serverSession;
      } else if (aClass == ClientSGdSession.class) {
        if (sessionId == null) {
          if (args != null && args.length > 0 && args[0] instanceof Request) {
            Request request = (Request) args[0];
            sessionId = request.getSessionId();
          } else {
            sessionId = this.sessionFactory.getSessionId();
          }
        }
        IClientSGdSessionData sessionData = (IClientSGdSessionData) this.sessionDataFactory
            .getAppSessionData(ClientSGdSession.class, sessionId);
        sessionData.setApplicationId(applicationId);
        SGdClientSessionImpl clientSession = new SGdClientSessionImpl(sessionData, getMessageFactory(), sessionFactory,
            this.getClientSessionListener());

        iss.addSession(clientSession);
        clientSession.getSessions().get(0).setRequestListener(clientSession);
        appSession = clientSession;
      } else {
        throw new IllegalArgumentException(
            "Wrong session class: " + aClass + ". Supported[" + ServerSGdSession.class + "]");
      }
    } catch (Exception e) {
      logger.error("Failure to obtain new SGd Session.", e);
    }
    return appSession;
  }

  public void stateChanged(Enum oldState, Enum newState) {
    logger.info("Diameter SGd Session Factory :: stateChanged :: oldState[{}], newState[{}]", oldState, newState);
  }

  public long getApplicationId() {
    return 16777313;
  }

  public void stateChanged(AppSession source, Enum oldState, Enum newState) {
    logger.info("Diameter SGd Session Factory :: stateChanged :: Session, [{}], oldState[{}], newState[{}]",
        new Object[] { source, oldState, newState });
  }

  public MTForwardShortMessageRequest createMTForwardShortMessageRequest(Request request) {
    return new MTForwardShortMessageRequestImpl(request);
  }

  public MTForwardShortMessageAnswer createMTForwardShortMessageAnswer(Answer answer) {
    return new MTForwardShortMessageAnswerImpl(answer);
  }

  public void doMTForwardShortMessageRequestEvent(ServerSGdSession appSession, MTForwardShortMessageRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter SGd Session Factory :: doMTForwardShortMessageRequest :: appSession[{}], Request[{}]", appSession,
        request);
  }

  public void doMTForwardShortMessageAnswerEvent(ClientSGdSession appSession, MTForwardShortMessageRequest request, MTForwardShortMessageAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter SGd Session Factory :: doMTForwardShortMessageAnswerEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

  public MOForwardShortMessageRequest createMOForwardShortMessageRequest(Request request) {
    return new MOForwardShortMessageRequestImpl(request);
  }

  public MOForwardShortMessageAnswer createMOForwardShortMessageAnswer(Answer answer) {
    return new MOForwardShortMessageAnswerImpl(answer);
  }

  public void doMOForwardShortMessageRequestEvent(ClientSGdSession appSession, MOForwardShortMessageRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter SGd Session Factory :: doMOForwardShortMessageRequestEvent :: appSession[{}], Request[{}]", appSession, request);
  }

  public void doMOForwardShortMessageAnswerEvent(ServerSGdSession appSession, MOForwardShortMessageRequest request, MOForwardShortMessageAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    logger.info("Diameter SGd Session Factory :: doMOForwardShortMessageAnswerEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

  public void doOtherEvent(AppSession appSession, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    logger.info("Diameter SGd Session Factory :: doOtherEvent :: appSession[{}], Request[{}], Answer[{}]",
        new Object[] { appSession, request, answer });
  }

}
