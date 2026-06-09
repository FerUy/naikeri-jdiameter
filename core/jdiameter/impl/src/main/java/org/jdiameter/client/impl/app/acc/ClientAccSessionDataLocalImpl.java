package org.jdiameter.client.impl.app.acc;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.acc.ClientAccSessionState;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientAccSessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientAccSessionData {

  protected ClientAccSessionState state = ClientAccSessionState.IDLE;
  protected Request buffer;
  protected String destRealm;
  protected String destHost;
  protected Serializable tid;

  /**
   *
   */
  public ClientAccSessionDataLocalImpl() {
  }

  @Override
  public void setClientAccSessionState(ClientAccSessionState state) {
    this.state = state;
  }

  @Override
  public ClientAccSessionState getClientAccSessionState() {
    return this.state;
  }

  @Override
  public void setInterimTimerId(Serializable tid) {
    this.tid = tid;
  }

  @Override
  public Serializable getInterimTimerId() {
    return this.tid;
  }

  @Override
  public void setDestinationHost(String destHost) {
    this.destHost = destHost;
  }

  @Override
  public String getDestinationHost() {
    return this.destHost;
  }

  @Override
  public void setDestinationRealm(String destRealm) {
    this.destRealm = destRealm;
  }

  @Override
  public String getDestinationRealm() {
    return this.destRealm;
  }

  @Override
  public void setBuffer(Request event) {
    this.buffer = event;
  }

  @Override
  public Request getBuffer() {
    return this.buffer;
  }

}
