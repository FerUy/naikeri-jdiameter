package org.jdiameter.api.gx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.gx.events.GxCreditControlRequest;
import org.jdiameter.api.gx.events.GxReAuthAnswer;
import org.jdiameter.api.gx.events.GxReAuthRequest;

/**
 * This interface defines the possible actions for the different states in the server
 * Credit-Control Application state machine.
 *
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface ServerGxSessionListener {

  /**
   * Notifies this ServerRoSessionListener that the ServerRoSession has received a CCR message.
   *
   * @param session parent application session (FSM)
   * @param request request object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doCreditControlRequest(ServerGxSession session, GxCreditControlRequest request) throws InternalException, IllegalDiameterStateException,
  RouteException, OverloadException;

  /**
   * Notifies this ServerRoSessionListener that the ServerRoSession has received a RAA message.
   *
   * @param session parent application session (FSM)
   * @param request request object
   * @param answer answer object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doGxReAuthAnswer(ServerGxSession session, GxReAuthRequest request, GxReAuthAnswer answer) throws InternalException, IllegalDiameterStateException,
  RouteException, OverloadException;

  /**
   * Notifies this ServerRoSessionListener that the ServerRoSession has received not Ro message,
   * now it can be even RAA.
   *
   * @param session parent application session (FSM)
   * @param request request object
   * @param answer answer object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
  RouteException, OverloadException;
}
