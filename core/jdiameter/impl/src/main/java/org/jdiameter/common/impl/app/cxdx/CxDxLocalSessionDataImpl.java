package org.jdiameter.common.impl.app.cxdx;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.cxdx.CxDxSessionState;
import org.jdiameter.common.api.app.cxdx.ICxDxSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class CxDxLocalSessionDataImpl extends AppSessionDataLocalImpl implements ICxDxSessionData {

  protected CxDxSessionState state = CxDxSessionState.IDLE;
  protected Request buffer;
  protected Serializable tsTimerId;

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#setCxDxSessionState(org.jdiameter.common.api.app.cxdx.CxDxSessionState)
   */
  @Override
  public void setCxDxSessionState(CxDxSessionState state) {
    this.state = state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#getCxDxSessionState()
   */
  @Override
  public CxDxSessionState getCxDxSessionState() {
    return this.state;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    return this.tsTimerId;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tid) {
    this.tsTimerId = tid;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#setBuffer(org.jdiameter.api.Message)
   */
  @Override
  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#getBuffer()
   */
  @Override
  public Request getBuffer() {
    return this.buffer;
  }
}
