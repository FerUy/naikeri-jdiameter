package org.mobicents.diameter.impl.ha.client.slg;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.slg.ClientSLgSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.slg.IClientSLgSessionData;
import org.jdiameter.common.api.app.slg.SLgSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.slg.SLgSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ClientSLgSessionDataReplicatedImpl extends SLgSessionDataReplicatedImpl implements IClientSLgSessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ClientSLgSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientSLgSession.class);
      setSLgSessionState(SLgSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ClientSLgSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}
