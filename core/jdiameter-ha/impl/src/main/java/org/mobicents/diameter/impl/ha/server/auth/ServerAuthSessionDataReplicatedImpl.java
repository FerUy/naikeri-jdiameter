package org.mobicents.diameter.impl.ha.server.auth;

import java.io.Serializable;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.auth.ServerAuthSession;
import org.jdiameter.common.api.app.auth.ServerAuthSessionState;
import org.jdiameter.server.impl.app.auth.IServerAuthSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerAuthSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IServerAuthSessionData {

  private static final String STATELESS = "STATELESS";
  private static final String STATE = "STATE";
  private static final String TS_TIMEOUT = "TS_TIMEOUT";
  private static final String TS_TIMERID = "TS_TIMERID";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ServerAuthSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerAuthSession.class);
      setServerAuthSessionState(ServerAuthSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ServerAuthSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster
    );
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#isStateless()
   */
  @Override
  public boolean isStateless() {
    if (exists()) {
      return toPrimitive((Boolean) getNodeValue(STATELESS), true);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#setStateless( boolean)
   */
  @Override
  public void setStateless(boolean stateless) {
    if (exists()) {
      putNodeValue(STATELESS, stateless);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData# getServerCCASessionState()
   */
  @Override
  public ServerAuthSessionState getServerAuthSessionState() {
    if (exists()) {
      return (ServerAuthSessionState) getNodeValue(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData# setServerCCASessionState
   * (org.jdiameter.common.api.app.cca.ServerCCASessionState)
   */
  @Override
  public void setServerAuthSessionState(ServerAuthSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setTsTimeout(long value) {
    if (exists()) {
      putNodeValue(TS_TIMEOUT, value);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public long getTsTimeout() {
    if (exists()) {
      return toPrimitive((Long) getNodeValue(TS_TIMEOUT));
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setTsTimerId(Serializable value) {
    if (exists()) {
      putNodeValue(TS_TIMERID, value);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
