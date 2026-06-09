package org.jdiameter.server.impl.app.rx;

import org.jdiameter.common.api.app.rx.IRxSessionData;
import org.jdiameter.common.api.app.rx.ServerRxSessionState;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IServerRxSessionData extends IRxSessionData {

  boolean isStateless();

  void setStateless(boolean stateless);

  ServerRxSessionState getServerRxSessionState();

  void setServerRxSessionState(ServerRxSessionState state);

}
