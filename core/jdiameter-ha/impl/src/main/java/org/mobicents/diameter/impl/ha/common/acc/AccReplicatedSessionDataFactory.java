package org.mobicents.diameter.impl.ha.common.acc;

import org.jdiameter.api.acc.ClientAccSession;
import org.jdiameter.api.acc.ServerAccSession;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.acc.IAccSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.acc.ClientAccSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.acc.ServerAccSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AccReplicatedSessionDataFactory implements IAppSessionDataFactory<IAccSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public AccReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
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
  public IAccSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientAccSession.class)) {
      ClientAccSessionDataReplicatedImpl data =
          new ClientAccSessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
      return data;
    }
    else if (clazz.equals(ServerAccSession.class)) {
      ServerAccSessionDataReplicatedImpl data = new ServerAccSessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    throw new IllegalArgumentException();
  }

}
