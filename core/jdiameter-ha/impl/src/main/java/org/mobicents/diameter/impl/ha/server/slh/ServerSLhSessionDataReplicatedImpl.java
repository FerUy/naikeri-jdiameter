package org.mobicents.diameter.impl.ha.server.slh;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.common.api.app.slh.SLhSessionState;
import org.jdiameter.server.impl.app.slh.IServerSLhSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.slh.SLhSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ServerSLhSessionDataReplicatedImpl extends SLhSessionDataReplicatedImpl implements IServerSLhSessionData {

  /**
    * @param nodeFqnWrapper
    * @param mobicentsCluster
    * @param container
    */
  public ServerSLhSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ServerSLhSession.class);
      setSLhSessionState(SLhSessionState.IDLE);
    }
  }

  /**
    * @param sessionId
    * @param mobicentsCluster
    * @param container
    */
  public ServerSLhSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}