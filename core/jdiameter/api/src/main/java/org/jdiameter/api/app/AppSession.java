package org.jdiameter.api.app;

import java.util.List;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.BaseSession;
import org.jdiameter.api.Session;

/**
 * Basic class for application specific session (Sx, Rx, Gx)
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AppSession extends BaseSession {

  /**
   * Return true if session has stateless FSM
   *
   * @return true if session has stateless FSM
   */
  boolean isStateless();

  /**
   * Return current value of applicationId of application session.
   *
   * @return applicationId of application session.
   */
  ApplicationId getSessionAppId();

  /**
   * Returns a list of child sessions
   *
   * @return List of child delivery sessions
   */
  List<Session> getSessions();

}
