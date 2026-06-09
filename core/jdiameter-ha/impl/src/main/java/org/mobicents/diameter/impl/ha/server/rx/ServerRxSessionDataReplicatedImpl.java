package org.mobicents.diameter.impl.ha.server.rx;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.rx.ServerRxSession;
import org.jdiameter.common.api.app.rx.ServerRxSessionState;
import org.jdiameter.server.impl.app.rx.IServerRxSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerRxSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IServerRxSessionData {

  private static final String STATELESS = "STATELESS";
  private static final String STATE = "STATE";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ServerRxSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerRxSession.class);
      setServerRxSessionState(ServerRxSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ServerRxSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
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
    } else {
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
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.cca.IServerCCASessionData# getServerCCASessionState()
   */
  @Override
  public ServerRxSessionState getServerRxSessionState() {
    if (exists()) {
      return (ServerRxSessionState) getNodeValue(STATE);
    } else {
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
  public void setServerRxSessionState(ServerRxSessionState state) {

    if (exists()) {
      putNodeValue(STATE, state);
    } else {
      throw new IllegalStateException();
    }

  }
}
