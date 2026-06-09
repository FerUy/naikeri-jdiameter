package org.mobicents.diameter.impl.ha.server.rf;

import java.io.Serializable;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.rf.ServerRfSession;
import org.jdiameter.common.api.app.rf.ServerRfSessionState;
import org.jdiameter.server.impl.app.rf.IServerRfSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerRfSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IServerRfSessionData {

  private static final String TS_TIMERID = "TCCID";
  private static final String STATELESS = "STATELESS";
  private static final String STATE = "STATE";
  private static final String TS_TIMEOUT = "TS_TIMEOUT";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ServerRfSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerRfSession.class);
      setServerRfSessionState(ServerRfSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ServerRfSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
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
  public ServerRfSessionState getServerRfSessionState() {
    if (exists()) {
      return (ServerRfSessionState) getNodeValue(STATE);
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
  public void setServerRfSessionState(ServerRfSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#setTccTimerId (java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tccTimerId) {
    if (exists()) {
      putNodeValue(TS_TIMERID, tccTimerId);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData#getTccTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.rf.IServerRfSessionData#getTsTimeout()
   */
  @Override
  public long getTsTimeout() {
    if (exists()) {
      return toPrimitive((Long) getNodeValue(TS_TIMEOUT));
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.rf.IServerRfSessionData#setTsTimeout(long)
   */
  @Override
  public void setTsTimeout(long l) {
    if (exists()) {
      putNodeValue(TS_TIMEOUT, l);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
