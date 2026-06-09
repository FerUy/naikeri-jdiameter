package org.jdiameter.server.impl.app.acc;

import org.jdiameter.api.acc.events.AccountRequest;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateEvent;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class Event implements StateEvent {

  enum Type {
    RECEIVED_EVENT_RECORD,
    RECEIVED_START_RECORD,
    RECEIVED_INTERIM_RECORD,
    RECEIVED_STOP_RECORD
  }

  Type type;
  AppEvent data;

  Event(Type type) {
    this.type = type;
  }

  Event(AccountRequest accountRequest) throws Exception {
    data = accountRequest;
    int type = accountRequest.getAccountingRecordType();
    switch (type) {
      case 1:
        this.type = Type.RECEIVED_EVENT_RECORD;
        break;
      case 2:
        this.type = Type.RECEIVED_START_RECORD;
        break;
      case 3:
        this.type = Type.RECEIVED_INTERIM_RECORD;
        break;
      case 4:
        this.type = Type.RECEIVED_STOP_RECORD;
        break;
      default:
        throw new Exception("Unknown type " + type);
    }
  }

  @Override
  public <E> E encodeType(Class<E> eClass) {
    return eClass == Type.class ? (E) type : null;
  }

  @Override
  public Enum getType() {
    return type;
  }

  @Override
  public void setData(Object o) {
    data = (AppEvent) o;
  }

  @Override
  public Object getData() {
    return data;
  }

  @Override
  public int compareTo(Object other) {
    return equals(other) ? 0 : -1;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    return this == other;
  }
}
