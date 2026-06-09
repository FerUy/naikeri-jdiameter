package org.jdiameter.server.impl.app.cca;

import java.io.Serializable;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.cca.ServerCCASessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ServerCCASessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerCCASessionData {

  protected boolean stateless = true;
  protected ServerCCASessionState state = ServerCCASessionState.IDLE;
  protected Serializable tccTimerId;

  /**
   *
   */
  public ServerCCASessionDataLocalImpl() {

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
  public ServerCCASessionState getServerCCASessionState() {
    return state;
  }

  @Override
  public void setServerCCASessionState(ServerCCASessionState state) {
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
