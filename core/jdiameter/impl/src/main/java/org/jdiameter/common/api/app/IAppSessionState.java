package org.jdiameter.common.api.app;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAppSessionState<T> {

  int getValue();

  T fromInt(int val) throws IllegalArgumentException;
}
