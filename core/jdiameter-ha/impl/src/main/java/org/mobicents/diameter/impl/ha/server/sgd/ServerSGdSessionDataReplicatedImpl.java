package org.mobicents.diameter.impl.ha.server.sgd;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.common.api.app.sgd.SGdSessionState;
import org.jdiameter.server.impl.app.sgd.IServerSGdSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.sgd.SGdSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ServerSGdSessionDataReplicatedImpl extends SGdSessionDataReplicatedImpl implements IServerSGdSessionData {

  /**
   * @param nodeFqnWrapper    FQN Wrapper
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ServerSGdSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ServerSGdSession.class);
      setSGdSessionState(SGdSessionState.IDLE);
    }
  }

  /**
   * @param sessionId        Diameter Session Id
   * @param mobicentsCluster Cluster
   * @param container        Container
   */
  public ServerSGdSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}
