package org.mobicents.diameter.impl.ha.server.slg;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.common.api.app.slg.SLgSessionState;
import org.jdiameter.server.impl.app.slg.IServerSLgSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.slg.SLgSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ServerSLgSessionDataReplicatedImpl extends SLgSessionDataReplicatedImpl implements IServerSLgSessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ServerSLgSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ServerSLgSession.class);
      setSLgSessionState(SLgSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ServerSLgSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
        FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId), mobicentsCluster, container);
  }

}