package org.jdiameter.api.s6c;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ServerS6cSessionListener {

  void doSendRoutingInfoForSMRequestEvent(ServerS6cSession session, SendRoutingInfoForSMRequest srr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doReportSMDeliveryStatusRequestEvent(ServerS6cSession session, ReportSMDeliveryStatusRequest rdr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doAlertServiceCentreAnswerEvent(ServerS6cSession session, AlertServiceCentreRequest alr, AlertServiceCentreAnswer ala)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
