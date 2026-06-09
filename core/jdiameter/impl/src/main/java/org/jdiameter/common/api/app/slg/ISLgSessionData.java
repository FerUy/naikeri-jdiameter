package org.jdiameter.common.api.app.slg;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.IAppSessionData;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface ISLgSessionData extends IAppSessionData {

  void setSLgSessionState(SLgSessionState state);

  SLgSessionState getSLgSessionState();

  Serializable getTsTimerId();

  void setTsTimerId(Serializable tid);

  void setBuffer(Request buffer);

  Request getBuffer();

}
