package org.jdiameter.api.sgd;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ServerSGdSession extends AppSession, StateMachine {

  /**
   * Send MO-Forward-Short-Message-Request to client
   *
   * @param moForwardShortMessageRequest   MO-Forward-Short-Message-Request event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendMOForwardShortMessageRequest(MOForwardShortMessageRequest moForwardShortMessageRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send MT-Forward-Short-Message-Answer to client
   *
   * @param mtForwardShortMessageAnswer    MT-Forward-Short-Message-Request event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendMTForwardShortMessageAnswer(MTForwardShortMessageAnswer mtForwardShortMessageAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
