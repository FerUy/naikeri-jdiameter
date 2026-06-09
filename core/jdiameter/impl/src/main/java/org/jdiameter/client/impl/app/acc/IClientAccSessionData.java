package org.jdiameter.client.impl.app.acc;

import java.io.Serializable;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.acc.ClientAccSessionState;
import org.jdiameter.common.api.app.acc.IAccSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientAccSessionData extends IAccSessionData {

  void setClientAccSessionState(ClientAccSessionState state);
  ClientAccSessionState getClientAccSessionState();

  void setInterimTimerId(Serializable tid);
  Serializable getInterimTimerId();

  void setDestinationHost(String destHost);
  String getDestinationHost();

  void setDestinationRealm(String destRealm);
  String getDestinationRealm();

  void setBuffer(Request event);
  Request getBuffer();

  void setApplicationId(ApplicationId aid);
  ApplicationId getApplicationId();

}
