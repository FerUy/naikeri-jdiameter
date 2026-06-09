package org.jdiameter.common.api.app.cca;

import java.util.concurrent.ScheduledFuture;

import org.jdiameter.api.Request;
import org.jdiameter.api.cca.ServerCCASession;

/**
 * Diameter Credit Control Application Server Additional listener
 * Actions for FSM
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerCCASessionContext {

  void sessionSupervisionTimerExpired(ServerCCASession session);

  /**
   * This is called always when Tcc starts
   * @param session
   * @param future
   */
  void sessionSupervisionTimerStarted(ServerCCASession session, ScheduledFuture future);

  void sessionSupervisionTimerReStarted(ServerCCASession session, ScheduledFuture future);

  void sessionSupervisionTimerStopped(ServerCCASession session, ScheduledFuture future);

  /**
   * Returns seconds value representing default validity time, App session uses 2x for Tcc timer
   * @return
   */
  long getDefaultValidityTime();

  void timeoutExpired(Request request);

}
