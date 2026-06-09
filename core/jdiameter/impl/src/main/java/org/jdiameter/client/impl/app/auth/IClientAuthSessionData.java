package org.jdiameter.client.impl.app.auth;

import java.io.Serializable;

import org.jdiameter.common.api.app.auth.ClientAuthSessionState;
import org.jdiameter.common.api.app.auth.IAuthSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientAuthSessionData extends IAuthSessionData {

  void setClientAuthSessionState(ClientAuthSessionState state);
  ClientAuthSessionState getClientAuthSessionState();

  boolean isStateless();
  void setStateless(boolean b);

  String getDestinationHost();
  void setDestinationHost(String host);

  String getDestinationRealm();
  void setDestinationRealm(String realm);

  Serializable getTsTimerId();
  void setTsTimerId(Serializable realm);

}
