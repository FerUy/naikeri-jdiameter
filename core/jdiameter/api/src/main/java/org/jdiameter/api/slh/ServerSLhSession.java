package org.jdiameter.api.slh;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ServerSLhSession extends AppSession, StateMachine {

  /**
    * Send LCS-Routing-Info-Answer to client
    *
    * @param answer LCS-Routing-Info-Answer event instance
    * @throws InternalException The InternalException signals that internal error is occurred.
    * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
    * @throws RouteException The NoRouteException signals that no route exist for a given realm.
    * @throws OverloadException The OverloadException signals that destination host is overloaded.
    */
  void sendLCSRoutingInfoAnswer(LCSRoutingInfoAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}