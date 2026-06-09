package org.jdiameter.common.api.app.slh;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ISLhMessageFactory {

  LCSRoutingInfoRequest createLCSRoutingInfoRequest(Request request);

  LCSRoutingInfoAnswer createLCSRoutingInfoAnswer(Answer answer);

  /**
    * Returns the Application-Id that this message factory is related to
    *
    * @return the Application-Id value
    */
  long getApplicationId();

}