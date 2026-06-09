package org.jdiameter.common.api.app.rf;

import org.jdiameter.common.api.app.IAppSessionState;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum ClientRfSessionState implements IAppSessionState<ClientRfSessionState> {

  IDLE(0),
  OPEN(1),
  PENDING_EVENT(2),
  PENDING_START (3) ,
  PENDING_INTERIM(4),
  PENDING_CLOSE(5),
  PENDING_BUFFERED(6);

  private final int value;

  ClientRfSessionState(int val) {
    value = val;
  }

  @Override
  public final int getValue() {
    return value;
  }

  @Override
  public final ClientRfSessionState fromInt(int val) throws IllegalArgumentException {
    switch (val) {
      case 0:
        return IDLE;
      case 1:
        return OPEN;
      case 2:
        return PENDING_EVENT;
      case 3:
        return PENDING_START;
      case 4:
        return PENDING_INTERIM;
      case 5:
        return PENDING_CLOSE;
      case 6:
        return PENDING_BUFFERED;
      default:
        throw new IllegalArgumentException();
    }
  }
}