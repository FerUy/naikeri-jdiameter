package org.jdiameter.api.rf;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.rf.events.RfAccountingRequest;

/**
 * Basic class for Rf accounting application specific session
 * Listener must injection from constructor of implementation class
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ClientRfSession extends AppSession, StateMachine {

  /**
   * Send Account Request to Server
   * @param request request object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendAccountRequest(RfAccountingRequest request) throws InternalException, IllegalStateException, RouteException, OverloadException;

}
