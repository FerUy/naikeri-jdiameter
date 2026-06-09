package org.jdiameter.client.impl.app.rx;

import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.rx.ClientRxSessionState;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ClientRxSessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientRxSessionData {

  protected boolean isEventBased = true;
  protected boolean requestTypeSet = false;
  protected ClientRxSessionState state = ClientRxSessionState.IDLE;

  /**
   *
   */
  public ClientRxSessionDataLocalImpl() {
  }

  @Override
  public boolean isEventBased() {
    return isEventBased;
  }

  @Override
  public void setEventBased(boolean isEventBased) {
    this.isEventBased = isEventBased;
  }

  @Override
  public boolean isRequestTypeSet() {
    return requestTypeSet;
  }

  @Override
  public void setRequestTypeSet(boolean requestTypeSet) {
    this.requestTypeSet = requestTypeSet;
  }

  @Override
  public ClientRxSessionState getClientRxSessionState() {
    return state;
  }

  @Override
  public void setClientRxSessionState(ClientRxSessionState state) {
    this.state = state;
  }

}
