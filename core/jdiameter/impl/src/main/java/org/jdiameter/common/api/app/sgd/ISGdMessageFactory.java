package org.jdiameter.common.api.app.sgd;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ISGdMessageFactory {

  MTForwardShortMessageRequest createMTForwardShortMessageRequest(Request request);

  MTForwardShortMessageAnswer createMTForwardShortMessageAnswer(Answer answer);

  MOForwardShortMessageRequest createMOForwardShortMessageRequest(Request request);

  MOForwardShortMessageAnswer createMOForwardShortMessageAnswer(Answer answer);

  /**
   * Returns the Application-Id that this message factory is related to
   *
   * @return the Application-Id value
   */
  long getApplicationId();
}
