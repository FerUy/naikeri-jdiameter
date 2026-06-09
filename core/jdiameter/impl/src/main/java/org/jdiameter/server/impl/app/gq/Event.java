package org.jdiameter.server.impl.app.gq;

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
    RECEIVE_AUTH_REQUEST,
    SEND_AUTH_ANSWER,
    RECEIVE_STR_REQUEST,
    SEND_STR_ANSWER,
    SEND_ASR_REQUEST,
    SEND_ASR_FAILURE,
    RECEIVE_ASR_ANSWER,
    SEND_RAR_REQUEST,
    SEND_RAR_FAILURE,
    RECEIVE_RAR_ANSWER,
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
