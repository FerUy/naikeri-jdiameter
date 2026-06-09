package org.jdiameter.common.api.app.sgd;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ISGdSessionData extends IAppSessionData {

  void setSGdSessionState(SGdSessionState state);

  SGdSessionState getSGdSessionState();

  Serializable getTsTimerId();

  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);

  Request getBuffer();

}
