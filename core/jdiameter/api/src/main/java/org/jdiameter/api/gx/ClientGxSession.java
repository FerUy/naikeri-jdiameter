package org.jdiameter.api.gx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.gx.events.GxCreditControlRequest;
import org.jdiameter.api.gx.events.GxReAuthAnswer;

/**
 * Basic class for Gx client credit-control application specific session.
 * Listener must be injected from constructor of implementation class.
 *
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface ClientGxSession extends AppSession, StateMachine {

  /**
   * Send credit-control request to server.
   * @param request Credit-Control-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendCreditControlRequest(final GxCreditControlRequest request) throws InternalException, IllegalDiameterStateException, RouteException,
  OverloadException;

  /**
   * Send re-authentication answer to server
   * @param answer Re-Auth-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendGxReAuthAnswer(final GxReAuthAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
