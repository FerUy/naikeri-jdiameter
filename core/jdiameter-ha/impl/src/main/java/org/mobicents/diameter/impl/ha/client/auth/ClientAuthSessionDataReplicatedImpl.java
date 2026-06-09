package org.mobicents.diameter.impl.ha.client.auth;

import java.io.Serializable;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.auth.ClientAuthSession;
import org.jdiameter.client.impl.app.auth.IClientAuthSessionData;
import org.jdiameter.common.api.app.auth.ClientAuthSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientAuthSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IClientAuthSessionData {

  private static final String STATE = "STATE";
  private static final String DESTINATION_HOST = "DESTINATION_HOST";
  private static final String DESTINATION_REALM = "DESTINATION_REALM";
  private static final String STATELESS = "STATELESS";
  private static final String TS_TIMERID = "TS_TIMERID";

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   */
  public ClientAuthSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ClientAuthSession.class);
      setClientAuthSessionState(ClientAuthSessionState.IDLE);
    }
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   */
  public ClientAuthSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster
    );
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setClientAuthSessionState(org.jdiameter.common.api.app.auth.
   * ClientAuthSessionState)
   */
  @Override
  public void setClientAuthSessionState(ClientAuthSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getClientAuthSessionState()
   */
  @Override
  public ClientAuthSessionState getClientAuthSessionState() {
    if (exists()) {
      return (ClientAuthSessionState) getNodeValue(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#isStateless()
   */
  @Override
  public boolean isStateless() {
    if (exists()) {
      return toPrimitive((Boolean) getNodeValue(STATELESS), true);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setStateless(boolean)
   */
  @Override
  public void setStateless(boolean b) {
    if (exists()) {
      putNodeValue(STATELESS, b);
    }
    else {
      throw new IllegalStateException();
    }

  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    if (exists()) {
      return (String) getNodeValue(DESTINATION_HOST);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String host) {
    if (exists()) {
      putNodeValue(DESTINATION_HOST, host);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    if (exists()) {
      return (String) getNodeValue(DESTINATION_REALM);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String realm) {
    if (exists()) {
      putNodeValue(DESTINATION_REALM, realm);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tid) {
    if (exists()) {
      putNodeValue(TS_TIMERID, tid);
    }
    else {
      throw new IllegalStateException();
    }
  }

}
