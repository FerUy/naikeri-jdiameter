package org.jdiameter.common.api.app.slh;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ISLhSessionData extends IAppSessionData {

  void setSLhSessionState(SLhSessionState state);

  SLhSessionState getSLhSessionState();

  Serializable getTsTimerId();

  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);

  Request getBuffer();

}