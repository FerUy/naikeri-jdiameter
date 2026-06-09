package org.jdiameter.common.api.app.s6c;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface IS6cSessionData extends IAppSessionData {

  void setS6cSessionState(S6cSessionState state);

  S6cSessionState getS6cSessionState();

  Serializable getTsTimerId();

  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);

  Request getBuffer();

}
