package org.mobicents.diameter.impl.ha.common.slh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.slh.ISLhSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.slh.ClientSLhSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.slh.ServerSLhSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class SLhReplicatedSessionDataFactory implements IAppSessionDataFactory<ISLhSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
    * @param replicatedSessionDataSource
    */
  public SLhReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
    // super();
    this.replicatedSessionDataSource = (ReplicatedSessionDatasource) replicatedSessionDataSource;
    this.mobicentsCluster = this.replicatedSessionDataSource.getMobicentsCluster();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  public ISLhSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientSLhSession.class)) {
      ClientSLhSessionDataReplicatedImpl data = new ClientSLhSessionDataReplicatedImpl(sessionId, this.mobicentsCluster,
          this.replicatedSessionDataSource.getContainer());
      return data;
    } else if (clazz.equals(ServerSLhSession.class)) {
      ServerSLhSessionDataReplicatedImpl data = new ServerSLhSessionDataReplicatedImpl(sessionId, this.mobicentsCluster,
          this.replicatedSessionDataSource.getContainer());
      return data;
    }
    throw new IllegalArgumentException();
  }

}