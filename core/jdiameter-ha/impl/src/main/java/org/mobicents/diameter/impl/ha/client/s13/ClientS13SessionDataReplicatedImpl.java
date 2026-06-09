package org.mobicents.diameter.impl.ha.client.s13;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.s13.ClientS13Session;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.s13.IClientS13SessionData;
import org.jdiameter.common.api.app.s13.S13SessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.s13.S13SessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientS13SessionDataReplicatedImpl extends S13SessionDataReplicatedImpl implements IClientS13SessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ClientS13SessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientS13Session.class);
      setS13SessionState(S13SessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ClientS13SessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster, container
    );
  }

}
