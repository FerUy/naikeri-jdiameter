package org.jdiameter.client.impl.annotation;

import org.jdiameter.api.annotation.Getter;
import org.jdiameter.api.annotation.Setter;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class Value<T> {

  protected T value;

  @Setter
  public Value(T value) {
    this.value = value;
  }

  @Getter
  public T get() {
    return value;
  }

  @Override
  public String toString() {
    return "Value{" + "value=" + value + "}";
  }
}