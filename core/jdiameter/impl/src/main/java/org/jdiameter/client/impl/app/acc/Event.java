package org.jdiameter.client.impl.app.acc;

import org.jdiameter.api.Avp;
import org.jdiameter.api.ResultCode;
import org.jdiameter.api.acc.events.AccountAnswer;
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
    SEND_EVENT_RECORD,
    SEND_START_RECORD,
    SEND_INTERIM_RECORD,
    SEND_STOP_RECORD,
    FAILED_SEND_RECORD,
    RECEIVED_RECORD,
    FAILED_RECEIVE_RECORD
  }

  Type type;
  AppEvent data;

  Event(Type type) {
    this.type = type;
  }

  Event(AccountAnswer accountAnswer) throws Exception {
    int resCode = ResultCode.SUCCESS;
    try {
      resCode = accountAnswer.getMessage().getAvps().getAvp(Avp.RESULT_CODE).getInteger32();
    }
    catch (Exception exc) {
    }
    type =  (resCode == ResultCode.SUCCESS || (resCode / 1000 == 4)) ? Type.RECEIVED_RECORD : Type.FAILED_RECEIVE_RECORD;
    data = accountAnswer;
  }

  Event(AccountRequest accountRequest) throws Exception {
    data = accountRequest;
    int type = accountRequest.getAccountingRecordType();
    switch (type) {
      case 1:
        this.type = Type.SEND_EVENT_RECORD;
        break;
      case 2:
        this.type = Type.SEND_START_RECORD;
        break;
      case 3:
        this.type = Type.SEND_INTERIM_RECORD;
        break;
      case 4:
        this.type = Type.SEND_STOP_RECORD;
        break;
      default:
        throw new Exception("Unknown type " + type);
    }
  }

  Event(Type type, AccountRequest accountRequest) throws Exception {
    this.type = type;
    this.data = accountRequest;
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
  public int compareTo(Object o) {
    return 0;
  }
}