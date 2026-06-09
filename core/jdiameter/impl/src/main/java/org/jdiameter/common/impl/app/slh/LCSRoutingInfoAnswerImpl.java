package org.jdiameter.common.impl.app.slh;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class LCSRoutingInfoAnswerImpl extends AppRequestEventImpl implements LCSRoutingInfoAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(LCSRoutingInfoAnswer.class);

  /**
    *
    * @param answer
    */
  public LCSRoutingInfoAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
    *
    * @param request
    * @param resultCode
    */
  public LCSRoutingInfoAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

  public Avp getResultCodeAvp() throws AvpDataException {
    return null;
  }

}
