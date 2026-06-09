package org.jdiameter.common.impl.app.slg;

import org.jdiameter.api.Avp;
import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class ProvideLocationAnswerImpl extends AppRequestEventImpl implements ProvideLocationAnswer {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(ProvideLocationRequestImpl.class);

  /**
   *
   * @param answer
   */
  public ProvideLocationAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public ProvideLocationAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

  public Avp getResultCodeAvp() throws AvpDataException {
    return null;
  }

}