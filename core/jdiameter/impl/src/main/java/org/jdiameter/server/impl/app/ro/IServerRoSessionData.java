package org.jdiameter.server.impl.app.ro;

import java.io.Serializable;

import org.jdiameter.common.api.app.ro.IRoSessionData;
import org.jdiameter.common.api.app.ro.ServerRoSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerRoSessionData extends IRoSessionData {

  boolean isStateless();

  void setStateless(boolean stateless);

  ServerRoSessionState getServerRoSessionState();

  void setServerRoSessionState(ServerRoSessionState state);

  void setTccTimerId(Serializable tccTimerId);

  Serializable getTccTimerId();

}
