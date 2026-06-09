package org.jdiameter.api.s6c;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ClientS6cSessionListener {

  void doSendRoutingInfoForSMAnswerEvent(ClientS6cSession session, SendRoutingInfoForSMRequest srr, SendRoutingInfoForSMAnswer sra)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doReportSMDeliveryStatusAnswerEvent(ClientS6cSession session, ReportSMDeliveryStatusRequest rdr, ReportSMDeliveryStatusAnswer rda)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doAlertServiceCentreRequestEvent(ClientS6cSession session, AlertServiceCentreRequest alr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
