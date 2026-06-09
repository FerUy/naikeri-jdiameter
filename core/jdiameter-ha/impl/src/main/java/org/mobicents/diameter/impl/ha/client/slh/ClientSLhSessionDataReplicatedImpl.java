package org.mobicents.diameter.impl.ha.client.slh;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.slh.IClientSLhSessionData;
import org.jdiameter.common.api.app.slh.SLhSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.slh.SLhSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ClientSLhSessionDataReplicatedImpl extends SLhSessionDataReplicatedImpl implements IClientSLhSessionData {

  /**
    * @param nodeFqnWrapper
    * @param mobicentsCluster
    * @param container
    */
  public ClientSLhSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientSLhSession.class);
      setSLhSessionState(SLhSessionState.IDLE);
    }
  }

  /**
    * @param sessionId
    * @param mobicentsCluster
    * @param container
    */
  public ClientSLhSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}