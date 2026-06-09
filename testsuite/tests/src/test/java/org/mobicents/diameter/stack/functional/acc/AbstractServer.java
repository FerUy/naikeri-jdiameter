package org.mobicents.diameter.stack.functional.acc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.api.acc.ClientAccSession;
import org.jdiameter.api.acc.ServerAccSession;
import org.jdiameter.api.acc.ServerAccSessionListener;
import org.jdiameter.common.api.app.acc.IServerAccActionContext;
import org.jdiameter.common.api.app.acc.ServerAccSessionState;
import org.jdiameter.common.impl.app.acc.AccSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.StateChange;
import org.mobicents.diameter.stack.functional.TBase;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class AbstractServer extends TBase implements ServerAccSessionListener, IServerAccActionContext {

  // NOTE: implementing NetworkReqListener since its required for stack to
  // know we support it... ech.

  protected static final int ACC_REQUEST_TYPE_INITIAL = 2;
  protected static final int ACC_REQUEST_TYPE_INTERIM = 3;
  protected static final int ACC_REQUEST_TYPE_TERMINATE = 4;
  protected static final int ACC_REQUEST_TYPE_EVENT = 1;

  protected ServerAccSession serverAccSession;
  protected int ccRequestNumber = 0;

  protected List<StateChange<ServerAccSessionState>> stateChanges = new ArrayList<StateChange<ServerAccSessionState>>(); // state changes

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAccAppId(0, 300));
      AccSessionFactoryImpl creditControlSessionFactory = new AccSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerAccSession.class, creditControlSessionFactory);
      sessionFactory.registerAppFacory(ClientAccSession.class, creditControlSessionFactory);

      creditControlSessionFactory.setStateListener(this);
      creditControlSessionFactory.setServerSessionListener(this);
      creditControlSessionFactory.setServerContextListener(this);
    }
    finally {
      try {
        configStream.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // ----------- delegate methods so

  public void start() throws IllegalDiameterStateException, InternalException {
    stack.start();
  }

  public void start(Mode mode, long timeOut, TimeUnit timeUnit) throws IllegalDiameterStateException, InternalException {
    stack.start(mode, timeOut, timeUnit);
  }

  public void stop(long timeOut, TimeUnit timeUnit, int disconnectCause) throws IllegalDiameterStateException, InternalException {
    stack.stop(timeOut, timeUnit, disconnectCause);
  }

  public void stop(int disconnectCause) {
    stack.stop(disconnectCause);
  }

  // ----------- ctx
  @Override
  public void sessionTimerStarted(ServerAccSession appSession, ScheduledFuture timer) throws InternalException {
    // NOP
  }

  @Override
  public void sessionTimeoutElapses(ServerAccSession appSession) throws InternalException {
    // NOP
  }

  @Override
  public void sessionTimerCanceled(ServerAccSession appSession, ScheduledFuture timer) throws InternalException {
    // NOP
  }

  public String getSessionId() {
    return this.serverAccSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverAccSession = stack.getSession(sessionId, ServerAccSession.class);
  }

  public ServerAccSession getSession() {
    return this.serverAccSession;
  }

  public List<StateChange<ServerAccSessionState>> getStateChanges() {
    return stateChanges;
  }
}
