package org.jdiameter.server.impl.app.gx;

import java.io.Serializable;

import org.jdiameter.common.api.app.gx.IGxSessionData;
import org.jdiameter.common.api.app.gx.ServerGxSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerGxSessionData extends IGxSessionData {

  boolean isStateless();

  void setStateless(boolean stateless);

  ServerGxSessionState getServerGxSessionState();

  void setServerGxSessionState(ServerGxSessionState state);

  void setTccTimerId(Serializable tccTimerId);

  Serializable getTccTimerId();

}
