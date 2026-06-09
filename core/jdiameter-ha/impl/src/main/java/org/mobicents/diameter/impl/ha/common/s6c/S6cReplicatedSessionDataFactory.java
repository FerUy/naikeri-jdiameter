package org.mobicents.diameter.impl.ha.common.s6c;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.s6c.IS6cSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.s6c.ClientS6cSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.s6c.ServerS6cSessionDataReplicatedImpl;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cReplicatedSessionDataFactory implements IAppSessionDataFactory<IS6cSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource replicated session data source
   */
  public S6cReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
    super();
    this.replicatedSessionDataSource = (ReplicatedSessionDatasource) replicatedSessionDataSource;
    this.mobicentsCluster = this.replicatedSessionDataSource.getMobicentsCluster();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  public IS6cSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientS6cSession.class)) {
      return new ClientS6cSessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
    } else if (clazz.equals(ServerS6cSession.class)) {
      return new ServerS6cSessionDataReplicatedImpl(sessionId, this.mobicentsCluster, this.replicatedSessionDataSource.getContainer());
    }
    throw new IllegalArgumentException();
  }

}
