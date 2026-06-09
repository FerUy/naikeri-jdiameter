package org.jdiameter.client.impl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Message;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RawSession;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.IRequest;
import org.jdiameter.client.api.ISession;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.jdiameter.common.api.timer.ITimerFacility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation for {@link ISession}
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SessionImpl extends BaseSessionImpl implements ISession {

  private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);

  private Semaphore lock = new Semaphore(1); // container lock

  SessionImpl(IContainer container) {
    setContainer(container);
    try {
      sessionId = container.getSessionFactory().getSessionId();
    }
    catch (IllegalDiameterStateException idse) {
      throw new IllegalStateException("Unable to generate Session-Id", idse);
    }
  }

  void setContainer(IContainer container) {
    try {
      lock.acquire(); // allow container change only if not releasing
      this.container = container;
      this.parser = (IMessageParser) container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
    }
    catch (InterruptedException e) {
      logger.error("failure getting lock", e);
    }
    finally {
      lock.release();
    }
  }

  @Override
  public void send(Message message, EventListener<Request, Answer> listener, Boolean useRealm)
          throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    genericSend(message,  listener, useRealm);
  }
  @Override
  public void send(Message message, EventListener<Request, Answer> listener)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    genericSend(message,  listener, true);
  }

  @Override
  public void send(Message message, EventListener<Request, Answer> listener, long timeout, TimeUnit timeUnit)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    genericSend(message, listener, timeout, timeUnit, true);
  }

  @Override
  public void setRequestListener(NetworkReqListener listener) {
    if (listener != null) {
      super.reqListener = listener;
      container.addSessionListener(sessionId, listener);
    }
  }

  @Override
  public NetworkReqListener getReqListener() {
    return super.reqListener;
  }

  @Override
  public Request createRequest(int commandCode, ApplicationId appId, String destRealm) {
    if (isValid) {
      setLastAccessTime();
      IRequest m = parser.createEmptyMessage(IRequest.class, commandCode, getAppId(appId));
      m.setNetworkRequest(false);
      m.setRequest(true);
      m.getAvps().addAvp(Avp.SESSION_ID, sessionId, true, false, false);
      appendAppId(appId, m);
      if (destRealm != null) {
        m.getAvps().addAvp(Avp.DESTINATION_REALM, destRealm, true, false, true);
      }
      MessageUtility.addOriginAvps(m, container.getMetaData());
      return m;
    }
    else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public Request createRequest(int commandCode, ApplicationId appId, String destRealm, String destHost) {
    if (isValid) {
      setLastAccessTime();
      IRequest m = parser.createEmptyMessage(IRequest.class, commandCode, getAppId(appId));
      m.setNetworkRequest(false);
      m.setRequest(true);
      m.getAvps().addAvp(Avp.SESSION_ID, sessionId, true, false, false);
      appendAppId(appId, m);
      if (destRealm != null) {
        m.getAvps().addAvp(Avp.DESTINATION_REALM, destRealm, true, false, true);
      }
      if (destHost != null) {
        m.getAvps().addAvp(Avp.DESTINATION_HOST, destHost, true, false, true);
      }
      MessageUtility.addOriginAvps(m, container.getMetaData());
      return m;
    }
    else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public Request createRequest(Request prevRequest) {
    if (isValid) {
      setLastAccessTime();
      IRequest request = parser.createEmptyMessage(Request.class, (IMessage) prevRequest);
      request.setRequest(true);
      request.setNetworkRequest(false);
      MessageUtility.addOriginAvps(request, container.getMetaData());
      return request;
    }
    else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public void release() {
    isValid = false;
    try {
      lock.acquire(); // prevent container NullPointerException

      if (container != null) {
        if (istTimerId != null) {
          container.getAssemblerFacility().getComponentInstance(ITimerFacility.class).cancel(istTimerId);
        }
        container.removeSessionListener(sessionId);
        container.getAssemblerFacility().getComponentInstance(ISessionDatasource.class).removeSession(sessionId);
      }

      container = null;
      parser = null;
      reqListener = null;
    }
    catch (InterruptedException e) {
      logger.error("failure getting lock", e);
    }
    finally {
      lock.release();
    }
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws InternalException {
    return iface == RawSession.class;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T unwrap(Class<T> iface) throws InternalException {
    return (T) (iface == RawSession.class ?  new RawSessionImpl(container) : null);
  }

}
