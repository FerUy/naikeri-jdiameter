package org.mobicents.diameter.impl.ha.timer;

import java.io.Serializable;

import org.restcomm.timers.PeriodicScheduleStrategy;
import org.restcomm.timers.TimerTaskData;

/**
 * Diameter timer task data holder.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
final class DiameterTimerTaskData extends TimerTaskData {

  private static final long serialVersionUID = 8774218122384404225L;

  // data we need to recreate timer task
  private String sessionId;
  private String timerName;

  DiameterTimerTaskData(Serializable id, long delay, String sessionId, String timerName) {
    super(id, System.currentTimeMillis() + delay, -1, PeriodicScheduleStrategy.withFixedDelay);
    this.sessionId = sessionId;
    this.timerName = timerName;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getTimerName() {
    return timerName;
  }
}
