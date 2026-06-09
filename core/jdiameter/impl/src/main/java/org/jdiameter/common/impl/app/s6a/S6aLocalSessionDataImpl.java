package org.jdiameter.common.impl.app.s6a;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.s6a.IS6aSessionData;
import org.jdiameter.common.api.app.s6a.S6aSessionState;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public class S6aLocalSessionDataImpl extends AppSessionDataLocalImpl implements IS6aSessionData {

  protected S6aSessionState state = S6aSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  @Override
  public void setS6aSessionState(S6aSessionState state) {
    this.state = state;
  }

  @Override
  public S6aSessionState getS6aSessionState() {
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
