package org.mobicents.diameter.impl.ha.common.slg;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slg.ClientSLgSession;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.slg.ISLgSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.slg.ClientSLgSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.slg.ServerSLgSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class SLgReplicatedSessionDataFactory implements IAppSessionDataFactory<ISLgSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public SLgReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
    super();
    this.replicatedSessionDataSource = (ReplicatedSessionDatasource) replicatedSessionDataSource;
    this.mobicentsCluster = this.replicatedSessionDataSource.getMobicentsCluster();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  public ISLgSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientSLgSession.class)) {
      ClientSLgSessionDataReplicatedImpl data = new ClientSLgSessionDataReplicatedImpl(sessionId, this.mobicentsCluster,
          this.replicatedSessionDataSource.getContainer());
      return data;
    } else if (clazz.equals(ServerSLgSession.class)) {
      ServerSLgSessionDataReplicatedImpl data = new ServerSLgSessionDataReplicatedImpl(sessionId, this.mobicentsCluster,
          this.replicatedSessionDataSource.getContainer());
      return data;
    }
    throw new IllegalArgumentException();
  }

}
