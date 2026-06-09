package org.jdiameter.common.impl.app.sgd;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class MOForwardShortMessageAnswerImpl extends AppAnswerEventImpl implements MOForwardShortMessageAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(MOForwardShortMessageAnswerImpl.class);

  /**
   *
   * @param answer SGd OFA
   */
  public MOForwardShortMessageAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request    SGd OFR
   * @param resultCode Diameter result code of SGd OFA
   */
  public MOForwardShortMessageAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
