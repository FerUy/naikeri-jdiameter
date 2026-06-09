package org.jdiameter.common.api.app.auth;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.Message;
import org.jdiameter.api.auth.ClientAuthSession;

/**
 * Diameter Authorization Client Additional listener
 * Actions for FSM
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientAuthActionContext {

  long getAccessTimeout() throws InternalException;

  void accessTimeoutElapses(ClientAuthSession session) throws InternalException;

  void disconnectUserOrDev(ClientAuthSession session, Message request) throws InternalException;
}
