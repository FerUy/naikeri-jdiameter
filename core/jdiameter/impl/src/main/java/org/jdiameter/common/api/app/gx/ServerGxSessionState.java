package org.jdiameter.common.api.app.gx;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 * Diameter Gx Server states
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public enum ServerGxSessionState implements IAppSessionState<ServerGxSessionState> {

  IDLE(0),
  OPEN(1);

  private int stateRepresentation = -1;

  ServerGxSessionState(int v) {
    this.stateRepresentation = v;
  }

  @Override
  public ServerGxSessionState fromInt(int v) throws IllegalArgumentException {
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
