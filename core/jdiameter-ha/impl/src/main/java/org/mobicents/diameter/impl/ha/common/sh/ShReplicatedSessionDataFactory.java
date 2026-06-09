package org.mobicents.diameter.impl.ha.common.sh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sh.ClientShSession;
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.sh.IShSessionData;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.client.sh.ShClientSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.mobicents.diameter.impl.ha.server.sh.ShServerSessionDataReplicatedImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ShReplicatedSessionDataFactory implements IAppSessionDataFactory<IShSessionData> {

  private ReplicatedSessionDatasource replicatedSessionDataSource;
  private MobicentsCluster mobicentsCluster;

  /**
   * @param replicatedSessionDataSource
   */
  public ShReplicatedSessionDataFactory(ISessionDatasource replicatedSessionDataSource) { // Is this ok?
    super();
    this.replicatedSessionDataSource = (ReplicatedSessionDatasource) replicatedSessionDataSource;
    this.mobicentsCluster = this.replicatedSessionDataSource.getMobicentsCluster();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData (java.lang.Class, java.lang.String)
   */
  @Override
  public IShSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientShSession.class)) {
      ShClientSessionDataReplicatedImpl data = new ShClientSessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    else if (clazz.equals(ServerShSession.class)) {
      ShServerSessionDataReplicatedImpl data = new ShServerSessionDataReplicatedImpl(sessionId, this.mobicentsCluster);
      return data;
    }
    throw new IllegalArgumentException();
  }

}
