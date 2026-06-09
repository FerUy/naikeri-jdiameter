package org.jdiameter.server.impl.app.ro;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.ro.ServerRoSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerRoSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerRoSessionData {

  protected boolean stateless = true;
  protected ServerRoSessionState state = ServerRoSessionState.IDLE;
  protected Serializable tccTimerId;

  /**
   *
   */
  public ServerRoSessionDataLocalImpl() {

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
  public ServerRoSessionState getServerRoSessionState() {
    return state;
  }

  @Override
  public void setServerRoSessionState(ServerRoSessionState state) {
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
