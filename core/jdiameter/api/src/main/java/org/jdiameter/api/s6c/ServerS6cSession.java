package org.jdiameter.api.s6c;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ServerS6cSession extends AppSession, StateMachine {

  /**
   * Send Send-Routing-Info-for-SM-Answer to client
   *
   * @param sendRoutingInfoForSMAnswer Send-Routing-Info-for-SM-Answer event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendSendRoutingInfoForSMAnswer(SendRoutingInfoForSMAnswer sendRoutingInfoForSMAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Report-SM-Delivery-Status-Answer to client
   *
   * @param reportSMDeliveryStatusAnswer Report-SM-Delivery-Status-Answer event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendReportSMDeliveryStatusAnswer(ReportSMDeliveryStatusAnswer reportSMDeliveryStatusAnswer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Alert-Service-Centre-Request to client
   *
   * @param alertServiceCentreRequest Alert-Service-Centre-Request event instance
   * @throws InternalException             The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException                The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException             The OverloadException signals that destination host is overloaded.
   */
  void sendAlertServiceCentreRequest(AlertServiceCentreRequest alertServiceCentreRequest)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
