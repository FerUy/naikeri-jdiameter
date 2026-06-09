package org.jdiameter.common.impl.app.s13;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.s13.IS13SessionData;
import org.jdiameter.common.api.app.s13.S13SessionState;

public class S13LocalSessionDataImpl extends AppSessionDataLocalImpl implements IS13SessionData {

  protected S13SessionState state = S13SessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  @Override
  public void setS13SessionState(S13SessionState state) {
    this.state = state;
  }

  @Override
  public S13SessionState getS13SessionState() {
    return this.state;
  }

  @Override
  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

  @Override
  public void setTsTimerId(Serializable tid) {
    this.tsTimerId = tid;
  }

  @Override
  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  @Override
  public Request getBuffer() {
    return this.buffer;
  }
}
