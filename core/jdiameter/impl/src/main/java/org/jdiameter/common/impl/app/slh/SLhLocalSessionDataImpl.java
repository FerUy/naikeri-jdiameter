package org.jdiameter.common.impl.app.slh;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.slh.ISLhSessionData;
import org.jdiameter.common.api.app.slh.SLhSessionState;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class SLhLocalSessionDataImpl extends AppSessionDataLocalImpl implements ISLhSessionData {

  protected SLhSessionState state = SLhSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  public void setSLhSessionState(SLhSessionState state) {
    this.state = state;
  }

  public SLhSessionState getSLhSessionState() {
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