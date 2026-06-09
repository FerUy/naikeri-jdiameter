package org.jdiameter.common.api.app.rf;

import java.util.concurrent.ScheduledFuture;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.rf.ServerRfSession;

/**
 * Diameter Accounting Server Additional listener
 * Actions for FSM
 *
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerRfActionContext {

  void sessionTimerStarted(ServerRfSession appSession, ScheduledFuture timer) throws InternalException;

  void sessionTimeoutElapses(ServerRfSession appSession) throws InternalException;

  void sessionTimerCanceled(ServerRfSession appSession, ScheduledFuture timer) throws InternalException;
}
