package org.mobicents.diameter.impl.ha.server.s13;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.s13.ServerS13Session;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.common.api.app.s13.S13SessionState;
import org.jdiameter.server.impl.app.s13.IServerS13SessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.s13.S13SessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerS13SessionDataReplicatedImpl extends S13SessionDataReplicatedImpl implements IServerS13SessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ServerS13SessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ServerS13Session.class);
      setS13SessionState(S13SessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ServerS13SessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster, container
    );
  }

}
