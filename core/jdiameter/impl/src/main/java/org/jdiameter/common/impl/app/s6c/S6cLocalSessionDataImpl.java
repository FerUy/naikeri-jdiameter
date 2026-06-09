package org.jdiameter.common.impl.app.s6c;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.s6c.IS6cSessionData;
import org.jdiameter.common.api.app.s6c.S6cSessionState;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cLocalSessionDataImpl extends AppSessionDataLocalImpl implements IS6cSessionData {

  protected S6cSessionState state = S6cSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  public void setS6cSessionState(S6cSessionState state) {
    this.state = state;
  }

  public S6cSessionState getS6cSessionState() {
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
