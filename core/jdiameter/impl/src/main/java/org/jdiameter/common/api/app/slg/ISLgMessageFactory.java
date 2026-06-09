package org.jdiameter.common.api.app.slg;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.jdiameter.api.slg.events.ProvideLocationRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ISLgMessageFactory {

  ProvideLocationRequest createProvideLocationRequest(Request request);

  ProvideLocationAnswer createProvideLocationAnswer(Answer answer);

  LocationReportRequest createLocationReportRequest(Request request);

  LocationReportAnswer createLocationReportAnswer(Answer answer);

  /**
   * Returns the Application-Id that this message factory is related to
   *
   * @return the Application-Id value
   */
  long getApplicationId();

}
