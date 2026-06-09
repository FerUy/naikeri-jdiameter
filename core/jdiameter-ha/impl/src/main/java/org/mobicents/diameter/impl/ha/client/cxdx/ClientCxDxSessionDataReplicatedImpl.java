package org.mobicents.diameter.impl.ha.client.cxdx;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.cxdx.ClientCxDxSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.app.cxdx.IClientCxDxSessionData;
import org.jdiameter.common.api.app.cxdx.CxDxSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.cxdx.CxDxSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientCxDxSessionDataReplicatedImpl extends CxDxSessionDataReplicatedImpl implements IClientCxDxSessionData {

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ClientCxDxSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster, container);

    if (super.create()) {
      setAppSessionIface(this, ClientCxDxSession.class);
      setCxDxSessionState(CxDxSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ClientCxDxSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster, container
    );
  }

}
