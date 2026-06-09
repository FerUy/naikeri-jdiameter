package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */

public class AlertServiceCentreAnswerImpl extends AppAnswerEventImpl implements AlertServiceCentreAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(AlertServiceCentreAnswerImpl.class);

  /**
   *
   * @param answer S6c ALA
   */
  public AlertServiceCentreAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request    S6c ALR
   * @param resultCode Diameter result code of S6c ALA
   */
  public AlertServiceCentreAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
