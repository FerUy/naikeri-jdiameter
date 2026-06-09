package org.jdiameter.common.api.app.ro;

import java.util.concurrent.ScheduledFuture;

import org.jdiameter.api.Request;
import org.jdiameter.api.ro.ServerRoSession;

/**
 * Diameter Ro Application Server Additional listener
 * Actions for FSM
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerRoSessionContext {

  void sessionSupervisionTimerExpired(ServerRoSession session);

  /**
   * This is called always when Tcc starts
   * @param session
   * @param future
   */
  void sessionSupervisionTimerStarted(ServerRoSession session, ScheduledFuture future);

  void sessionSupervisionTimerReStarted(ServerRoSession session, ScheduledFuture future);

  void sessionSupervisionTimerStopped(ServerRoSession session, ScheduledFuture future);

  /**
   * Returns seconds value representing default validity time, App session uses 2x for Tcc timer
   * @return
   */
  long getDefaultValidityTime();

  void timeoutExpired(Request request);

}
