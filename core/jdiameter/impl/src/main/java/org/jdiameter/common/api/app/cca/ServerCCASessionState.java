package org.jdiameter.common.api.app.cca;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 * Diameter Credit-Control Application Server states
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public enum ServerCCASessionState implements IAppSessionState<ServerCCASessionState> {

  IDLE(0),
  OPEN(1);

  private int stateRepresentation = -1;

  ServerCCASessionState(int v) {
    this.stateRepresentation = v;
  }

  @Override
  public ServerCCASessionState fromInt(int v) throws IllegalArgumentException {
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
