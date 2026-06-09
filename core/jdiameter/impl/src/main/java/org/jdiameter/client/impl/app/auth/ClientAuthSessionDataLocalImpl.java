package org.jdiameter.client.impl.app.auth;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.auth.ClientAuthSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientAuthSessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientAuthSessionData {

  protected ClientAuthSessionState state = ClientAuthSessionState.IDLE;
  protected boolean stateless = true;
  protected String destinationHost;
  protected String destinationRealm;
  protected Serializable tsTimerId;

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setClientAuthSessionState(org.jdiameter.common.api.app.auth.ClientAuthSessionState)
   */
  @Override
  public void setClientAuthSessionState(ClientAuthSessionState state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getClientAuthSessionState()
   */
  @Override
  public ClientAuthSessionState getClientAuthSessionState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#isStateless()
   */
  @Override
  public boolean isStateless() {
    return this.stateless;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setStateless(boolean)
   */
  @Override
  public void setStateless(boolean b) {
    this.stateless = b;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    return this.destinationHost;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String host) {
    this.destinationHost = host;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    return this.destinationRealm;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String realm) {
    this.destinationRealm = realm;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.client.impl.app.auth.IClientAuthSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tid) {
    this.tsTimerId = tid;
  }

}
