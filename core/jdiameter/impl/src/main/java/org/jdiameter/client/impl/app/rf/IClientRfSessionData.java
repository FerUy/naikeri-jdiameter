package org.jdiameter.client.impl.app.rf;

import java.io.Serializable;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.rf.ClientRfSessionState;
import org.jdiameter.common.api.app.rf.IRfSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientRfSessionData extends IRfSessionData {

  void setClientRfSessionState(ClientRfSessionState state);
  ClientRfSessionState getClientRfSessionState();

  void setBuffer(Request event);
  Request getBuffer();

  @Override
  ApplicationId getApplicationId();
  @Override
  void setApplicationId(ApplicationId appId);

  Serializable getTsTimerId();
  void setTsTimerId(Serializable tid);

  String getDestinationHost();
  void setDestinationHost(String destinationHost);

  String getDestinationRealm();
  void setDestinationRealm(String destinationRealm);

}
