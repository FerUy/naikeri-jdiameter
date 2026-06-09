package org.jdiameter.common.impl.app.slg;

import org.jdiameter.api.Avp;
import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class LocationReportAnswerImpl extends AppRequestEventImpl implements LocationReportAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(LocationReportAnswerImpl.class);

  /**
   *
   * @param answer
   */
  public LocationReportAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public LocationReportAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

  public Avp getResultCodeAvp() throws AvpDataException {
    return null;
  }

}