package org.jdiameter.api.s6c;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ClientS6cSession extends AppSession, StateMachine {

  /**
   * Send Send-Routing-Info-for-SM-Request to server
   *
   * @param sendRoutingInfoForSMRequest    Send-Routing-Info-for-SM-Request event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendSendRoutingInfoForSMRequest(SendRoutingInfoForSMRequest sendRoutingInfoForSMRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Report-SM-Delivery-Status-Request to server
   *
   * @param reportSMDeliveryStatusRequest  Report-SM-Delivery-Status-Request event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendReportSMDeliveryStatusRequest(ReportSMDeliveryStatusRequest reportSMDeliveryStatusRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Alert-Service-Centre-Answer to server
   *
   * @param alertServiceCentreAnswer       Alert-Service-Centre-Answer event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendAlertServiceCentreAnswer(AlertServiceCentreAnswer alertServiceCentreAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
