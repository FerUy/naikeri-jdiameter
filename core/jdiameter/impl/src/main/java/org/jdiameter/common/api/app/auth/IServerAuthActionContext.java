package org.jdiameter.common.api.app.auth;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.auth.ServerAuthSession;

/**
 * Diameter Authorization Server Additional listener
 * Actions for FSM
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IServerAuthActionContext {

  long getAccessTimeout() throws InternalException;

  void accessTimeoutElapses(ServerAuthSession session) throws InternalException;
}