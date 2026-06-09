package org.jdiameter.server.impl.app.auth;

import java.io.Serializable;

import org.jdiameter.common.api.app.auth.IAuthSessionData;
import org.jdiameter.common.api.app.auth.ServerAuthSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerAuthSessionData extends IAuthSessionData {

  ServerAuthSessionState getServerAuthSessionState();
  void setServerAuthSessionState(ServerAuthSessionState state);

  boolean isStateless();
  void setStateless(boolean b);

  void setTsTimeout(long l);
  long getTsTimeout();

  void setTsTimerId(Serializable tid);
  Serializable getTsTimerId();

}
