package org.jdiameter.api.s6a;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ClientS6aSession extends AppSession, StateMachine {

  /**
   * Send Authentication-Information-Request to server
   *
   * @param request Authentication-Information-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendAuthenticationInformationRequest(JAuthenticationInformationRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Purge-UE-Request to server
   *
   * @param request Purge-UE-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendPurgeUERequest(JPurgeUERequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Notify-Request to server
   *
   * @param request Notify-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendNotifyRequest(JNotifyRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Update-Location-Request to server
   *
   * @param request Update-Location-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendUpdateLocationRequest(JUpdateLocationRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Cancel-Location-Answer to server
   *
   * @param answer Cancel-Location-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendCancelLocationAnswer(JCancelLocationAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Insert-Subscriber-Data-Answer to server
   *
   * @param answer Insert-Subscriber-Data-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendInsertSubscriberDataAnswer(JInsertSubscriberDataAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Delete-Subscriber-Data-Answer to server
   *
   * @param answer Delete-Subscriber-Data-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendDeleteSubscriberDataAnswer(JDeleteSubscriberDataAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Reset-Answer to server
   *
   * @param answer Reset-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendResetAnswer(JResetAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
