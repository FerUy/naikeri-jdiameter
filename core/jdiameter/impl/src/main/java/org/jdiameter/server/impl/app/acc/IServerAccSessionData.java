package org.jdiameter.server.impl.app.acc;

import java.io.Serializable;

import org.jdiameter.common.api.app.acc.IAccSessionData;
import org.jdiameter.common.api.app.acc.ServerAccSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerAccSessionData extends IAccSessionData {

  void setServerAccSessionState(ServerAccSessionState value);
  ServerAccSessionState getServerAccSessionState();

  void setStateless(boolean value);
  boolean isStateless();

  /**
   * Seconds value, its taken from either request or answer. Contained in Acct-Interim-Interval AVP
   * @param value
   */
  void setTsTimeout(long value);
  long getTsTimeout();

  void setTsTimerId(Serializable value);
  Serializable getTsTimerId();

}
