package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */

public class ReportSMDeliveryStatusAnswerImpl extends AppAnswerEventImpl implements ReportSMDeliveryStatusAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(ReportSMDeliveryStatusAnswerImpl.class);

  /**
   *
   * @param answer S6c RDA
   */
  public ReportSMDeliveryStatusAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request    S6c RDR
   * @param resultCode Diameter result code of S6c RDA
   */
  public ReportSMDeliveryStatusAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
