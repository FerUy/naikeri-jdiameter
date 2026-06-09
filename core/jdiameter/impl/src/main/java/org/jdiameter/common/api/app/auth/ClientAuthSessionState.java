package org.jdiameter.common.api.app.auth;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public enum ClientAuthSessionState implements IAppSessionState<ClientAuthSessionState> {

  IDLE(0), OPEN(1), PENDING(2), DISCONNECTED(3);

  private final int value;

  ClientAuthSessionState(int val) {
    value = val;
  }

  @Override
  public final int getValue() {
    return value;
  }

  @Override
  public final ClientAuthSessionState fromInt(int val) throws IllegalArgumentException {
    switch (val) {
      case 0:
        return IDLE;
      case 1:
        return OPEN;
      case 2:
        return PENDING;
      case 3:
        return DISCONNECTED;
      default:
        throw new IllegalArgumentException();
    }
  }
}