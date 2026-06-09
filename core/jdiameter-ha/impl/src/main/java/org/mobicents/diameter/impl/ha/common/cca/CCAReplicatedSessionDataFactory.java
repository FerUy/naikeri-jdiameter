package org.mobicents.diameter.impl.ha.common.cca;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.cca.ClientCCASession;
import org.jdiameter.api.cca.ServerCCASession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.cca.ICCASessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.cca.ClientCCASessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.cca.ServerCCASessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class CCAReplicatedSessionDataFactory implements IAppSessionDataFactory<ICCASessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public CCAReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
    super();
    this.replicatedSessionDataSource = (ReplicatedSessionDatasource) replicatedSessionDataSource;
    this.mobicentsCluster = this.replicatedSessionDataSource.getMobicentsCluster();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public ICCASessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientCCASession.class)) {
      ClientCCASessionDataReplicatedImpl data =
          new ClientCCASessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
      return data;
    }
    else if (clazz.equals(ServerCCASession.class)) {
      ServerCCASessionDataReplicatedImpl data = new ServerCCASessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    throw new IllegalArgumentException();
  }

}
