package org.mobicents.diameter.impl.ha.client.sh;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.sh.ClientShSession;
import org.jdiameter.client.impl.app.sh.IShClientSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ShClientSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IShClientSessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ShClientSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ClientShSession.class);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ShClientSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster
    );
  }

}
