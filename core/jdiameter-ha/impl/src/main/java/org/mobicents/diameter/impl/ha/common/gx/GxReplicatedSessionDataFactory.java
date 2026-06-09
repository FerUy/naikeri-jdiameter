package org.mobicents.diameter.impl.ha.common.gx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.gx.ClientGxSession;
import org.jdiameter.api.gx.ServerGxSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.gx.IGxSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.gx.ClientGxSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.gx.ServerGxSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class GxReplicatedSessionDataFactory implements IAppSessionDataFactory<IGxSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public GxReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
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
  public IGxSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientGxSession.class)) {
      ClientGxSessionDataReplicatedImpl data =
          new ClientGxSessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
      return data;
    }
    else if (clazz.equals(ServerGxSession.class)) {
      ServerGxSessionDataReplicatedImpl data = new ServerGxSessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    throw new IllegalArgumentException();
  }

}
