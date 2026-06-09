package org.jdiameter.client.impl.app.cca;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.cca.ClientCCASessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientCCASessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientCCASessionData {

  protected boolean isEventBased = true;
  protected boolean requestTypeSet = false;
  protected ClientCCASessionState state = ClientCCASessionState.IDLE;
  protected Serializable txTimerId;
  //protected JCreditControlRequest txTimerRequest;
  protected Request txTimerRequest;

  // Event Based Buffer
  //protected Message buffer = null;
  protected Request buffer;

  protected int gatheredRequestedAction = NON_INITIALIZED;

  protected int gatheredCCFH = NON_INITIALIZED;
  protected int gatheredDDFH = NON_INITIALIZED;

  /**
   *
   */
  public ClientCCASessionDataLocalImpl() {
  }

  @Override
  public boolean isEventBased() {
    return isEventBased;
  }

  @Override
  public void setEventBased(boolean isEventBased) {
    this.isEventBased = isEventBased;
  }

  @Override
  public boolean isRequestTypeSet() {
    return requestTypeSet;
  }

  @Override
  public void setRequestTypeSet(boolean requestTypeSet) {
    this.requestTypeSet = requestTypeSet;
  }

  @Override
  public ClientCCASessionState getClientCCASessionState() {
    return state;
  }

  @Override
  public void setClientCCASessionState(ClientCCASessionState state) {
    this.state = state;
  }

  @Override
  public Serializable getTxTimerId() {
    return txTimerId;
  }

  @Override
  public void setTxTimerId(Serializable txTimerId) {
    this.txTimerId = txTimerId;
  }

  @Override
  public Request getTxTimerRequest() {
    return txTimerRequest;
  }

  @Override
  public void setTxTimerRequest(Request txTimerRequest) {
    this.txTimerRequest = txTimerRequest;
  }

  @Override
  public Request getBuffer() {
    return buffer;
  }

  @Override
  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  @Override
  public int getGatheredRequestedAction() {
    return gatheredRequestedAction;
  }

  @Override
  public void setGatheredRequestedAction(int gatheredRequestedAction) {
    this.gatheredRequestedAction = gatheredRequestedAction;
  }

  @Override
  public int getGatheredCCFH() {
    return gatheredCCFH;
  }

  @Override
  public void setGatheredCCFH(int gatheredCCFH) {
    this.gatheredCCFH = gatheredCCFH;
  }

  @Override
  public int getGatheredDDFH() {
    return gatheredDDFH;
  }

  @Override
  public void setGatheredDDFH(int gatheredDDFH) {
    this.gatheredDDFH = gatheredDDFH;
  }

}
