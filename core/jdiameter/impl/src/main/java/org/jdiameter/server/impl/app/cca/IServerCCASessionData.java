package org.jdiameter.server.impl.app.cca;

import java.io.Serializable;

import org.jdiameter.common.api.app.cca.ICCASessionData;
import org.jdiameter.common.api.app.cca.ServerCCASessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerCCASessionData extends ICCASessionData {

  boolean isStateless();

  void setStateless(boolean stateless);

  ServerCCASessionState getServerCCASessionState();

  void setServerCCASessionState(ServerCCASessionState state);

  void setTccTimerId(Serializable tccTimerId);

  Serializable getTccTimerId();

}
