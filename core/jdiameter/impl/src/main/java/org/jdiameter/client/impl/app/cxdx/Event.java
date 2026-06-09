package org.jdiameter.client.impl.app.cxdx;

import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateEvent;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class Event implements StateEvent {

  enum Type {
    SEND_MESSAGE,
    RECEIVE_RTR,
    RECEIVE_PPR,
    TIMEOUT_EXPIRES,
    RECEIVE_UAA,
    RECEIVE_SAA,
    RECEIVE_LIA,
    RECEIVE_MAA;
  }

  AppEvent request;
  AppEvent answer;
  Type type;

  Event(Type type, AppEvent request, AppEvent answer) {
    this.type = type;
    this.answer = answer;
    this.request = request;
  }

  @Override
  public <E> E encodeType(Class<E> eClass) {
    return eClass == Type.class ? (E) type : null;
  }

  @Override
  public Enum getType() {
    return type;
  }

  public AppEvent getRequest() {
    return request;
  }

  public AppEvent getAnswer() {
    return answer;
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public Object getData() {
    return request != null ? request : answer;
  }

  @Override
  public void setData(Object data) {
    // TODO: Replace Request/Answer with this
  }

}
