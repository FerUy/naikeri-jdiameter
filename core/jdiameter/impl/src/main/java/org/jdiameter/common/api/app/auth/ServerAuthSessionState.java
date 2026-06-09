package org.jdiameter.common.api.app.auth;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum ServerAuthSessionState implements IAppSessionState<ServerAuthSessionState> {

  IDLE(0), OPEN(1), DISCONNECTED(2);

  private final int value;

  ServerAuthSessionState(int val) {
    value = val;
  }

  @Override
  public final int getValue() {
    return value;
  }

  @Override
  public final ServerAuthSessionState fromInt(int val) throws IllegalArgumentException {
    switch (val) {
      case 0:
        return IDLE;
      case 1:
        return OPEN;
      case 2:
        return DISCONNECTED;
      default:
        throw new IllegalArgumentException();
    }
  }
}