package org.jdiameter.server.impl.app.acc;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.acc.ServerAccSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerAccSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerAccSessionData {

  protected ServerAccSessionState state = ServerAccSessionState.IDLE;
  protected boolean stateles = true;
  protected long tsTimeout = NON_INITIALIZED;
  protected Serializable tsTimerId;

  /**
   *
   */
  public ServerAccSessionDataLocalImpl() {
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.acc.IServerAccSessionData#
   * setServerAccSessionState
   * (org.jdiameter.common.api.app.acc.ServerAccSessionState)
   */
  @Override
  public void setServerAccSessionState(ServerAccSessionState value) {
    this.state = value;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.acc.IServerAccSessionData#
   * getServerAccSessionState()
   */
  @Override
  public ServerAccSessionState getServerAccSessionState() {
    return this.state;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.impl.app.acc.IServerAccSessionData#setStateles(boolean
   * )
   */
  @Override
  public void setStateless(boolean value) {
    this.stateles = value;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.impl.app.acc.IServerAccSessionData#isStateles()
   */
  @Override
  public boolean isStateless() {
    return this.stateles;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.impl.app.acc.IServerAccSessionData#setTsTimeout(
   * long)
   */
  @Override
  public void setTsTimeout(long value) {
    this.tsTimeout = value;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.impl.app.acc.IServerAccSessionData#getTsTimeout()
   */
  @Override
  public long getTsTimeout() {
    return this.tsTimeout;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.impl.app.acc.IServerAccSessionData#setTsTimerId(
   * java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable value) {
    this.tsTimerId = value;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.impl.app.acc.IServerAccSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

}
