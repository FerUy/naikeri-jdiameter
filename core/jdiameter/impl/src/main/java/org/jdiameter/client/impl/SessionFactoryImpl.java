package org.jdiameter.client.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.RawSession;
import org.jdiameter.api.Session;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.api.StackState;
import org.jdiameter.client.impl.helpers.UIDGenerator;
import org.jdiameter.common.api.app.IAppSessionFactory;
import org.jdiameter.common.api.data.ISessionDatasource;

/**
 * Implementation for {@link ISessionFactory}
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SessionFactoryImpl implements ISessionFactory {

  private IContainer stack;

  @SuppressWarnings("rawtypes")
  private Map<Class, IAppSessionFactory> appFactories = new ConcurrentHashMap<Class, IAppSessionFactory>();
  private ISessionDatasource dataSource;

  protected static UIDGenerator uid = new UIDGenerator();

  public SessionFactoryImpl(IContainer stack) {
    this.stack = stack;
    this.dataSource = this.stack.getAssemblerFacility().getComponentInstance(ISessionDatasource.class);
  }

  @Override
  public String getSessionId(String custom) {
    long id = uid.nextLong();
    long high32 = (id & 0xffffffff00000000L) >> 32;
    long low32 = (id & 0xffffffffL);
    StringBuilder sb = new StringBuilder();
    sb.append(stack.getMetaData().getLocalPeer().getUri().getFQDN()).
    append(";").append(high32).append(";").append(low32);
    if (custom != null) {
      //FIXME: add checks for not allowed chars?
      sb.append(";").append(custom);
    }
    return sb.toString();
  }

  @Override
  public String getSessionId() {
    return this.getSessionId(null);
  }

  @Override
  public RawSession getNewRawSession() throws InternalException {
    if (stack.getState() == StackState.IDLE) {
      throw new InternalException("Illegal state of stack");
    }
    return new RawSessionImpl(stack);
  }

  @Override
  public Session getNewSession() throws InternalException {
    if (stack.getState() == StackState.IDLE) {
      throw new InternalException("Illegal state of stack");
    }

    // FIXME: store this! Properly handle in ISessiondata
    SessionImpl session = new SessionImpl(stack);
    this.dataSource.addSession(session);
    return session;
  }

  @Override
  public Session getNewSession(String sessionId) throws InternalException {
    if (stack.getState() == StackState.IDLE) {
      throw new InternalException("Illegal state of stack");
    }
    SessionImpl session = new SessionImpl(stack);
    if (sessionId != null && sessionId.length() > 0) {
      session.sessionId = sessionId;
    }
    // FIXME: store this! Properly handle in ISessiondata
    this.dataSource.addSession(session);
    return session;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends AppSession> T getNewAppSession(ApplicationId applicationId, Class<? extends AppSession> aClass) throws InternalException {
    return (T) getNewAppSession(null, applicationId, aClass, new Object[0]);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends AppSession> T getNewAppSession(String sessionId, ApplicationId applicationId, Class<? extends AppSession> aClass) throws InternalException {
    return (T) getNewAppSession(sessionId, applicationId, aClass, new Object[0]);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends AppSession> T getNewAppSession(String sessionId, ApplicationId applicationId, Class<? extends AppSession> aClass, Object... args)
      throws InternalException {
    T session = null;
    if (stack.getState() == StackState.IDLE) {
      throw new InternalException("Illegal state of stack");
    }
    if (appFactories.containsKey(aClass)) {
      session = (T) appFactories.get(aClass).getNewSession(sessionId, aClass, applicationId, args);
      // FIXME: add check if it exists already!
      //dataSource.addSession(session);
    }
    return session;
  }

  @Override
  public void registerAppFacory(Class<? extends AppSession> sessionClass, IAppSessionFactory factory) {
    appFactories.put(sessionClass, factory);
  }

  @Override
  public void unRegisterAppFacory(Class<? extends AppSession> sessionClass) {
    appFactories.remove(sessionClass);
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.api.ISessionFactory#getAppSessionFactory(java.lang.Class)
   */
  @Override
  public IAppSessionFactory getAppSessionFactory(Class<? extends AppSession> sessionClass) {
    return appFactories.get(sessionClass);
  }

  @Override
  public IContainer getContainer() {
    return stack;
  }

}
