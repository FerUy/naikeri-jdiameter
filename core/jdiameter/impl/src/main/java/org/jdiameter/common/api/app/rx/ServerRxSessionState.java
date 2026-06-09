package org.jdiameter.common.api.app.rx;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 * Diameter 3GPP IMS Rx Reference Point Server Session states
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 */
public enum ServerRxSessionState implements IAppSessionState<ServerRxSessionState> {

  IDLE(0),
  OPEN(1);

  private int stateRepresentation = -1;

  ServerRxSessionState(int v) {
    this.stateRepresentation = v;
  }

  @Override
  public ServerRxSessionState fromInt(int v) throws IllegalArgumentException {
    switch (v) {
      case 0:
        return IDLE;

      case 1:
        return OPEN;

      default:
        throw new IllegalArgumentException("Illegal value of int representation!!!!");
    }
  }

  @Override
  public int getValue() {
    return stateRepresentation;
  }

}
