package org.mobicents.diameter.impl.ha.server.sh;

 import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.server.impl.app.sh.IShServerSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ShServerSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IShServerSessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ShServerSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ServerShSession.class);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ShServerSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster
    );
  }

}
