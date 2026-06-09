package org.jdiameter.common.api.app.s6a;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface IS6aSessionData extends IAppSessionData {

  void setS6aSessionState(S6aSessionState state);
  S6aSessionState getS6aSessionState();

  Serializable getTsTimerId();
  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);
  Request getBuffer();

}
