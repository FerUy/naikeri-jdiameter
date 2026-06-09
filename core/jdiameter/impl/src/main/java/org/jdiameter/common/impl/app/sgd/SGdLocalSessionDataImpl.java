package org.jdiameter.common.impl.app.sgd;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.sgd.ISGdSessionData;
import org.jdiameter.common.api.app.sgd.SGdSessionState;

import java.io.Serializable;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SGdLocalSessionDataImpl extends AppSessionDataLocalImpl implements ISGdSessionData {

  protected SGdSessionState state = SGdSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  public void setSGdSessionState(SGdSessionState state) {
    this.state = state;
  }

  public SGdSessionState getSGdSessionState() {
    return this.state;
  }

  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

  public void setTsTimerId(Serializable tid) {
    this.tsTimerId = tid;
  }

  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  public Request getBuffer() {
    return this.buffer;
  }
}
