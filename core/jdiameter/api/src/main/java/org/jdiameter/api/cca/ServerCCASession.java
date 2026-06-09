package org.jdiameter.api.cca;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.api.cca.events.JCreditControlAnswer;

/**
 * Basic class for server credit-control application specific session
 * Listener must be injected from constructor of implementation class
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ServerCCASession extends CCASession {

  /**
   * Send credit-control answer to client
   * @param answer Credit-Control-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendCreditControlAnswer(JCreditControlAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send re-authentication request to client
   * @param request Re-Auth-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendReAuthRequest(ReAuthRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
