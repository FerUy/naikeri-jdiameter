package org.jdiameter.client.impl.app.rf;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.rf.ClientRfSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientRfSessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientRfSessionData {

  protected boolean isEventBased = true;
  protected boolean requestTypeSet = false;
  protected ClientRfSessionState state = ClientRfSessionState.IDLE;
  protected Serializable tsTimerId;

  protected Request buffer;
  protected String destinationHost;
  protected String destinationRealm;

  /**
   *
   */
  public ClientRfSessionDataLocalImpl() {
  }

  @Override
  public ClientRfSessionState getClientRfSessionState() {
    return state;
  }

  @Override
  public void setClientRfSessionState(ClientRfSessionState state) {
    this.state = state;
  }

  @Override
  public Serializable getTsTimerId() {
    return tsTimerId;
  }

  @Override
  public void setTsTimerId(Serializable txTimerId) {
    this.tsTimerId = txTimerId;
  }

  @Override
  public Request getBuffer() {
    return buffer;
  }

  @Override
  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    return this.destinationHost;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String destinationHost) {
    this.destinationHost = destinationHost;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    return this.destinationRealm;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String destinationRealm) {
    this.destinationRealm = destinationRealm;
  }

}
