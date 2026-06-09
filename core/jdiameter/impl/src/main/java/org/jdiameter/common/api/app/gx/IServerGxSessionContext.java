package org.jdiameter.common.api.app.gx;

import java.util.concurrent.ScheduledFuture;

import org.jdiameter.api.Request;
import org.jdiameter.api.gx.ServerGxSession;

/**
 * Diameter Gx Application Server Additional listener
 * Actions for FSM
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerGxSessionContext {

  void sessionSupervisionTimerExpired(ServerGxSession session);

  /**
   * This is called always when Tcc starts
   * @param session
   * @param future
   */
  void sessionSupervisionTimerStarted(ServerGxSession session, ScheduledFuture future);

  void sessionSupervisionTimerReStarted(ServerGxSession session, ScheduledFuture future);

  void sessionSupervisionTimerStopped(ServerGxSession session, ScheduledFuture future);

  /**
   * Returns seconds value representing default validity time, App session uses 2x for Tcc timer
   * @return
   */
  long getDefaultValidityTime();

  void timeoutExpired(Request request);

}
