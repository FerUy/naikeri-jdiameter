package org.mobicents.diameter.impl.ha.common.ro;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.ro.ClientRoSession;
import org.jdiameter.api.ro.ServerRoSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.ro.IRoSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.ro.ClientRoSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.ro.ServerRoSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class RoReplicatedSessionDataFactory implements IAppSessionDataFactory<IRoSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public RoReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
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
  public IRoSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientRoSession.class)) {
      ClientRoSessionDataReplicatedImpl data =
          new ClientRoSessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
      return data;
    }
    else if (clazz.equals(ServerRoSession.class)) {
      ServerRoSessionDataReplicatedImpl data = new ServerRoSessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    throw new IllegalArgumentException();
  }

}
