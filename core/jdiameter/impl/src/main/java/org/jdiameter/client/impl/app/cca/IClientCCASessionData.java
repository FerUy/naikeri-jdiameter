package org.jdiameter.client.impl.app.cca;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.cca.ClientCCASessionState;
import org.jdiameter.common.api.app.cca.ICCASessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientCCASessionData extends ICCASessionData {

  boolean isEventBased();

  void setEventBased(boolean b);

  boolean isRequestTypeSet();

  void setRequestTypeSet(boolean b);

  ClientCCASessionState getClientCCASessionState();

  void setClientCCASessionState(ClientCCASessionState state);

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
