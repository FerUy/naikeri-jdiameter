package org.jdiameter.server.impl.app.gx;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.gx.ServerGxSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerGxSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerGxSessionData {

  protected boolean stateless = true;
  protected ServerGxSessionState state = ServerGxSessionState.IDLE;
  protected Serializable tccTimerId;

  /**
   *
   */
  public ServerGxSessionDataLocalImpl() {

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
  public ServerGxSessionState getServerGxSessionState() {
    return state;
  }

  @Override
  public void setServerGxSessionState(ServerGxSessionState state) {
    this.state = state;
  }

  @Override
  public Serializable getTccTimerId() {
    return tccTimerId;
  }

  @Override
  public void setTccTimerId(Serializable tccTimerId) {
    this.tccTimerId = tccTimerId;
  }

}
