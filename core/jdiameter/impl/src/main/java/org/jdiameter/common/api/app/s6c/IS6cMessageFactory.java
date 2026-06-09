package org.jdiameter.common.api.app.s6c;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface IS6cMessageFactory {

  SendRoutingInfoForSMRequest createSendRoutingInfoForSMRequest(Request request);

  SendRoutingInfoForSMAnswer createSendRoutingInfoForSMAnswer(Answer answer);

  ReportSMDeliveryStatusRequest createReportSMDeliveryStatusRequest(Request request);

  ReportSMDeliveryStatusAnswer createReportSMDeliveryStatusAnswer(Answer answer);

  AlertServiceCentreRequest createAlertServiceCentreRequest(Request request);

  AlertServiceCentreAnswer createAlertServiceCentreAnswer(Answer answer);

  /**
   * Returns the Application-Id that this message factory is related to
   *
   * @return the Application-Id value
   */
  long getApplicationId();

}
