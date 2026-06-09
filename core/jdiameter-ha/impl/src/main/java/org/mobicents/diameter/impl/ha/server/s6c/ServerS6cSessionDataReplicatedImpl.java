package org.mobicents.diameter.impl.ha.server.s6c;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.common.api.app.s6c.S6cSessionState;
import org.jdiameter.server.impl.app.s6c.IServerS6cSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.s6c.S6cSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ServerS6cSessionDataReplicatedImpl extends S6cSessionDataReplicatedImpl implements IServerS6cSessionData {

  /**
   * @param nodeFqnWrapper    FQN Wrapper
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public ServerS6cSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ServerS6cSession.class);
      setS6cSessionState(S6cSessionState.IDLE);
    }
  }

  /**
   * @param sessionId        Diameter Session Id
   * @param mobicentsCluster Cluster
   * @param container        Container
   */
  public ServerS6cSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}
