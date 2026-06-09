package org.jdiameter.server.impl.app.rf;

import java.io.Serializable;

import org.jdiameter.common.api.app.rf.IRfSessionData;
import org.jdiameter.common.api.app.rf.ServerRfSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerRfSessionData extends IRfSessionData {

  ServerRfSessionState getServerRfSessionState();
  void setServerRfSessionState(ServerRfSessionState state);

  void setTsTimerId(Serializable tsTimerId);
  Serializable getTsTimerId();

  long getTsTimeout();
  void setTsTimeout(long tsTimeout);

  boolean isStateless();
  void setStateless(boolean stateless);

}
