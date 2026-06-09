package org.jdiameter.client.impl.app.ro;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.ro.ClientRoSessionState;
import org.jdiameter.common.api.app.ro.IRoSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientRoSessionData extends IRoSessionData {

  boolean isEventBased();

  void setEventBased(boolean b);

  boolean isRequestTypeSet();

  void setRequestTypeSet(boolean b);

  ClientRoSessionState getClientRoSessionState();

  void setClientRoSessionState(ClientRoSessionState state);

  Serializable getTxTimerId();

  void setTxTimerId(Serializable txTimerId);

  Request getTxTimerRequest();

  void setTxTimerRequest(Request txTimerRequest);

  Request getBuffer();

  void setBuffer(Request buffer);

  int getGatheredRequestedAction();

  void setGatheredRequestedAction(int gatheredRequestedAction);

  int getGatheredCCFH();

  void setGatheredCCFH(int gatheredCCFH);

  int getGatheredDDFH();

  void setGatheredDDFH(int gatheredDDFH);
}
