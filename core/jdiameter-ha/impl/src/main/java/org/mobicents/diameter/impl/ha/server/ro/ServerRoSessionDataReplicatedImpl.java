package org.mobicents.diameter.impl.ha.server.ro;

import java.io.Serializable;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.ro.ServerRoSession;
import org.jdiameter.common.api.app.ro.ServerRoSessionState;
import org.jdiameter.server.impl.app.ro.IServerRoSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerRoSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IServerRoSessionData {

  private static final String TCCID = "TCCID";
  private static final String STATELESS = "STATELESS";
  private static final String STATE = "STATE";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ServerRoSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerRoSession.class);
      setServerRoSessionState(ServerRoSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ServerRoSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
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
  public ServerRoSessionState getServerRoSessionState() {
    if (exists()) {
      return (ServerRoSessionState) getNodeValue(STATE);
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
  public void setServerRoSessionState(ServerRoSessionState state) {
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
  public void setTccTimerId(Serializable tccTimerId) {
    if (exists()) {
      putNodeValue(TCCID, tccTimerId);
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
  public Serializable getTccTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TCCID);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
