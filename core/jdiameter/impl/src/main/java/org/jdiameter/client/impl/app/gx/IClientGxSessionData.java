package org.jdiameter.client.impl.app.gx;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.gx.ClientGxSessionState;
import org.jdiameter.common.api.app.gx.IGxSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientGxSessionData extends IGxSessionData {

  boolean isEventBased();

  void setEventBased(boolean b);

  boolean isRequestTypeSet();

  void setRequestTypeSet(boolean b);

  ClientGxSessionState getClientGxSessionState();

  void setClientGxSessionState(ClientGxSessionState state);

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
