package org.jdiameter.common.api.timer;

import java.io.Serializable;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ITimerFacility {

  Serializable schedule(String sessionId, String timerName, long miliseconds) throws IllegalArgumentException;

  void cancel(Serializable id);

}
