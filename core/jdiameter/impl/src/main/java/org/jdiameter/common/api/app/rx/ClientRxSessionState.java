package org.jdiameter.common.api.app.rx;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 * Diameter 3GPP IMS Rx Reference Point Client Session States
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 */
public enum ClientRxSessionState implements IAppSessionState<ClientRxSessionState> {

  IDLE(0),
  PENDING_AAR(1),
  PENDING_STR(2),
  PENDING_EVENT(3),
  PENDING_BUFFERED(4),
  OPEN(5);
  private int stateValue = -1;

  ClientRxSessionState(int stateV) {
    this.stateValue = stateV;
  }

  @Override
  public ClientRxSessionState fromInt(int v) throws IllegalArgumentException {
    switch (v) {
      case 0:
        return IDLE;
      case 1:
        return PENDING_AAR;
      case 2:
        return PENDING_STR;
      case 3:
        return PENDING_EVENT;
      case 4:
        return PENDING_BUFFERED;
      case 5:
        return OPEN;
      default:
        throw new IllegalArgumentException("Illegal value of int representation!!!!");
    }
  }

  @Override
  public int getValue() {
    return stateValue;
  }
}
