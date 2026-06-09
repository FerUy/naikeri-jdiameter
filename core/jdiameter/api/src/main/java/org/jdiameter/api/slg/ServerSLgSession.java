package org.jdiameter.api.slg;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
// import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ServerSLgSession extends AppSession, StateMachine {

  /**
   * Send Provide-Location-Answer to client
   *
   * @param answer Provide-Location-Answer event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendProvideLocationAnswer(ProvideLocationAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send Location-Report-Request to client
   *
   * @param request Location-Report-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void sendLocationReportRequest(LocationReportRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}