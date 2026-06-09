package org.mobicents.diameter.impl.ha.client.s6c;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.s6c.IClientS6cSessionData;
import org.jdiameter.common.api.app.s6c.S6cSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.s6c.S6cSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientS6cSessionDataReplicatedImpl extends S6cSessionDataReplicatedImpl implements IClientS6cSessionData {

  /**
   * @param nodeFqnWrapper    Node FQN Wrapper
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ClientS6cSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientS6cSession.class);
      setS6cSessionState(S6cSessionState.IDLE);
    }
  }

  /**
   * @param sessionId         Diameter Session Id
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ClientS6cSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}
