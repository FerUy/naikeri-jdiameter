package org.mobicents.diameter.impl.ha.client.sgd;
import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.sgd.IClientSGdSessionData;
import org.jdiameter.common.api.app.sgd.SGdSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.sgd.SGdSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientSGdSessionDataReplicatedImpl extends SGdSessionDataReplicatedImpl implements IClientSGdSessionData {

  /**
   * @param nodeFqnWrapper    Node FQN Wrapper
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ClientSGdSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientSGdSession.class);
      setSGdSessionState(SGdSessionState.IDLE);
    }
  }

  /**
   * @param sessionId         Diameter Session Id
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ClientSGdSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}
