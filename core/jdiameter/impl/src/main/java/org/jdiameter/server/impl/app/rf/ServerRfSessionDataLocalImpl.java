package org.jdiameter.server.impl.app.rf;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.rf.ServerRfSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerRfSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerRfSessionData {

  protected boolean stateless = true;
  protected ServerRfSessionState state = ServerRfSessionState.IDLE;
  protected Serializable tsTimerId;
  protected long tsTimeout = NON_INITIALIZED;

  /**
   *
   */
  public ServerRfSessionDataLocalImpl() {

  }

  @Override
  public boolean isStateless() {
    return stateless;
  }

  @Override
  public void setStateless(boolean stateless) {
    this.stateless = stateless;
  }

  @Override
  public ServerRfSessionState getServerRfSessionState() {
    return state;
  }

  @Override
  public void setServerRfSessionState(ServerRfSessionState state) {
    this.state = state;
  }

  @Override
  public Serializable getTsTimerId() {
    return tsTimerId;
  }

  @Override
  public void setTsTimerId(Serializable tsTimerId) {
    this.tsTimerId = tsTimerId;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.rf.IServerRfSessionData#getTsTimeout()
   */
  @Override
  public long getTsTimeout() {
    return this.tsTimeout;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.server.impl.app.rf.IServerRfSessionData#setTsTimeout(long)
   */
  @Override
  public void setTsTimeout(long tsTimeout) {
    this.tsTimeout = tsTimeout;
  }

}
