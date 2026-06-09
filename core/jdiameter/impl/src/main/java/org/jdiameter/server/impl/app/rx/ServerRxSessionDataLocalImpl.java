package org.jdiameter.server.impl.app.rx;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.rx.ServerRxSessionState;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ServerRxSessionDataLocalImpl extends AppSessionDataLocalImpl implements IServerRxSessionData {

  protected boolean stateless = true;
  protected ServerRxSessionState state = ServerRxSessionState.IDLE;

  /**
   *
   */
  public ServerRxSessionDataLocalImpl() {

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
  public ServerRxSessionState getServerRxSessionState() {
    return state;
  }

  @Override
  public void setServerRxSessionState(ServerRxSessionState state) {
    this.state = state;
  }

}
