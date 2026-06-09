package org.jdiameter.common.api.app.acc;

import java.util.concurrent.ScheduledFuture;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.acc.ServerAccSession;

/**
 * Diameter Accounting Server Additional listener
 * Actions for FSM
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerAccActionContext {

  void sessionTimerStarted(ServerAccSession appSession, ScheduledFuture timer) throws InternalException;

  void sessionTimeoutElapses(ServerAccSession appSession) throws InternalException;

  void sessionTimerCanceled(ServerAccSession appSession, ScheduledFuture timer) throws InternalException;
}
