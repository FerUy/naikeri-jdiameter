package org.jdiameter.server.impl.app.auth;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.auth.ServerAuthSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerAuthSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerAuthSessionData {

  protected ServerAuthSessionState state = ServerAuthSessionState.IDLE;
  protected Serializable tsTimerId;
  protected long tsTimeout = NON_INITIALIZED;
  protected boolean stateless = true;

  /**
   *
   */
  public ServerAuthSessionDataLocalImpl() {

  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#getServerAuthSessionState()
   */
  @Override
  public ServerAuthSessionState getServerAuthSessionState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#setServerAuthSessionState(org.jdiameter.common.api.app.auth.ServerAuthSessionState)
   */
  @Override
  public void setServerAuthSessionState(ServerAuthSessionState state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#isStateless()
   */
  @Override
  public boolean isStateless() {
    return this.stateless;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#setStateless(boolean)
   */
  @Override
  public void setStateless(boolean stateless) {
    this.stateless = stateless;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#setTsTimeout(long)
   */
  @Override
  public void setTsTimeout(long tsTimeout) {
    this.tsTimeout = tsTimeout;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#getTsTimeout()
   */
  @Override
  public long getTsTimeout() {
    return this.tsTimeout;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tsTimerId) {
    this.tsTimerId = tsTimerId;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.auth.IServerAuthSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

}
