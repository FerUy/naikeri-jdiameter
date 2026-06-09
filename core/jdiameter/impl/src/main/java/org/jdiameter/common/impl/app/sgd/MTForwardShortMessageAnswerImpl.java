package org.jdiameter.common.impl.app.sgd;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class MTForwardShortMessageAnswerImpl extends AppAnswerEventImpl implements MTForwardShortMessageAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(MTForwardShortMessageAnswerImpl.class);

  /**
   *
   * @param answer SGd TFA
   */
  public MTForwardShortMessageAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request    SGd TFR
   * @param resultCode Diameter result code of SGd TFA
   */
  public MTForwardShortMessageAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
