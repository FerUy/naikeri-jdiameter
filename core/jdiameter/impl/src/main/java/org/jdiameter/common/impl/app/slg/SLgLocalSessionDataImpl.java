package org.jdiameter.common.impl.app.slg;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.slg.ISLgSessionData;
import org.jdiameter.common.api.app.slg.SLgSessionState;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class SLgLocalSessionDataImpl extends AppSessionDataLocalImpl implements ISLgSessionData {

  protected SLgSessionState state = SLgSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  public void setSLgSessionState(SLgSessionState state) {
    this.state = state;
  }

  public SLgSessionState getSLgSessionState() {
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