package org.jdiameter.common.api.app.cxdx;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ICxDxSessionData extends IAppSessionData {

  void setCxDxSessionState(CxDxSessionState state);
  CxDxSessionState getCxDxSessionState();

  Serializable getTsTimerId();
  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);
  Request getBuffer();

}
