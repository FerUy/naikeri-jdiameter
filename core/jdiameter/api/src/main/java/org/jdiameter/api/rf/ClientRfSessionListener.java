package org.jdiameter.api.rf;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rf.events.RfAccountingAnswer;
import org.jdiameter.api.rf.events.RfAccountingRequest;

/**
 * This interface defines the possible actions that the different states in the
 * Accounting state machine.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ClientRfSessionListener {

  /**
   * Notifies this AccSessionEventListener that the ClientAccSesssion has received AccAnswer message.
   * @param appSession parent application session (FSM)
   * @param request accounting request object
   * @param answer accounting answer object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doRfAccountingAnswerEvent(ClientRfSession appSession, RfAccountingRequest request, RfAccountingAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Notifies this AccSessionEventListener that the ClientAccSesssion has received not account message.
   * @param appSession parent application session (FSM)
   * @param request request object
   * @param answer answer object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doOtherEvent(AppSession appSession, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
