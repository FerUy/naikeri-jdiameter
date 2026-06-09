package org.jdiameter.common.api.app.s13;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

public interface IS13SessionData extends IAppSessionData {

  void setS13SessionState(S13SessionState state);
  S13SessionState getS13SessionState();

  Serializable getTsTimerId();
  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);
  Request getBuffer();
}
