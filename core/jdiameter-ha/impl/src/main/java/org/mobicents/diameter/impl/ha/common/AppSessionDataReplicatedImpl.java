package org.mobicents.diameter.impl.ha.common;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.common.api.app.IAppSessionData;
import org.restcomm.cache.MobicentsCache;
import org.restcomm.cluster.MobicentsCluster;
import org.restcomm.cluster.cache.ClusteredCacheData;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AppSessionDataReplicatedImpl extends ClusteredCacheData implements IAppSessionData {

  protected static final String SID = "SID";
  protected static final String APID = "APID";
  protected static final String SIFACE = "SIFACE";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public AppSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);
  }

  public AppSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster
    );
  }

  public static void setAppSessionIface(ClusteredCacheData ccd, Class<? extends AppSession> iface) {
    ccd.getMobicentsCache().putCacheNodeValue(ccd.getNodeFqnWrapper(), SIFACE, iface);
  }

  public static Class<? extends AppSession> getAppSessionIface(MobicentsCache mcCache, String sessionId) {
    @SuppressWarnings("unchecked")
    Class<AppSession> value = (Class<AppSession>) mcCache.getCacheNodeValue(
            FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
            SIFACE
    );
    return value;
  }

  @Override
  public String getSessionId() {
    return (String) super.getNodeFqnLastElement();
  }

  @Override
  public void setApplicationId(ApplicationId applicationId) {
    if (exists()) {
      putNodeValue(APID, applicationId);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public ApplicationId getApplicationId() {
    if (exists()) {
      return (ApplicationId) getNodeValue(APID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  // Some util methods for handling primitives

  protected boolean toPrimitive(Boolean b, boolean _default) {
    return b == null ? _default : b;
  }

  protected int toPrimitive(Integer i) {
    return i == null ? NON_INITIALIZED : i;
  }

  protected long toPrimitive(Long l) {
    return l == null ? NON_INITIALIZED : l;
  }

}
