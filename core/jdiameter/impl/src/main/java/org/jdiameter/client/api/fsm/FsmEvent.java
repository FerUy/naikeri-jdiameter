package org.jdiameter.client.api.fsm;

import org.jdiameter.api.app.StateEvent;
import org.jdiameter.client.api.IMessage;

/**
 * This class extends behaviour of FSM StateEvent
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class FsmEvent implements StateEvent {

  private String key;
  private EventTypes type;
  private Object value;
  private final long createdTime = System.currentTimeMillis();

  /**
   * Create instance of class
   *
   * @param type type of event
   */
  public FsmEvent(EventTypes type) {
    this.type = type;
  }

  /**
   * Create instance of class with predefined parameters
   *
   * @param type type of event
   * @param key event key
   */
  public FsmEvent(EventTypes type, String key) {
    this(type);
    this.key = key;
  }

  /**
   * Create instance of class with predefined parameters
   *
   * @param type type of event
   * @param value attached message
   */
  public FsmEvent(EventTypes type, IMessage value) {
    this(type);
    this.value = value;
  }

  /**
   * Create instance of class with predefined parameters
   *
   * @param type type of event
   * @param value  attached message
   * @param key event key
   */
  public FsmEvent(EventTypes type, IMessage value, String key) {
    this(type, value);
    this.key = key;
  }

  /**
   * Return key value
   *
   * @return key value
   */
  public String getKey() {
    return key;
  }

  /**
   * Return attached message
   *
   * @return diameter message
   */
  public IMessage getMessage() {
    return (IMessage) getData();
  }

  /**
   * Return created time
   *
   * @return created time
   */
  public long getCreatedTime() {
    return createdTime;
  }

  @Override
  public <E> E encodeType(Class<E> eClass) {
    return (E) type;
  }

  @Override
  public Enum getType() {
    return type;
  }

  @Override
  public void setData(Object o) {
    value = o;
  }

  @Override
  public Object getData() {
    return value;
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }

  /**
   * Return string representation of instance
   *
   * @return string representation of instance
   */
  @Override
  public String toString() {
    return "Event{name:" + type.name() + ", key:" + key + ", object:" + value + "}";
  }
}
