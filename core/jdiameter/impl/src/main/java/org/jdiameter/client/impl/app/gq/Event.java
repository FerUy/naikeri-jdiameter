package org.jdiameter.client.impl.app.gq;

import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateEvent;

/**
 *
 * @author <a href="mailto:webdev@web-ukraine.info"> Yulian Oifa </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class Event implements StateEvent {

  enum Type {
    SEND_AUTH_REQUEST,
    SEND_AUTH_ANSWER,
    SEND_SESSION_TERMINATION_REQUEST,
    SEND_SESSION_ABORT_ANSWER,
    RECEIVE_AUTH_ANSWER,
    RECEIVE_FAILED_AUTH_ANSWER,
    RECEIVE_ABORT_SESSION_REQUEST,
    RECEIVE_SESSION_TERINATION_ANSWER,
    TIMEOUT_EXPIRES
  }

  Type type;
  AppEvent data;

  Event(Type type, AppEvent data) {
    this.type = type;
    this.data = data;
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
