package org.jdiameter.common.api.app.rf;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum ServerRfSessionState implements IAppSessionState<ServerRfSessionState> {

  IDLE(0),
  OPEN(1);

  private final int value;

  ServerRfSessionState(int val) {
    value = val;
  }

  @Override
  public final int getValue() {
    return value;
  }

  @Override
  public final ServerRfSessionState fromInt(int val) throws IllegalArgumentException {
    switch (val) {
      case 0:
        return IDLE;
      case 1:
        return OPEN;
      default:
        throw new IllegalArgumentException();
    }
  }

}
