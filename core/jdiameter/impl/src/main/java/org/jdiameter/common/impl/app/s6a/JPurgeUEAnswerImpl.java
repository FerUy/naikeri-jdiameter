package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JPurgeUEAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public class JPurgeUEAnswerImpl extends AppAnswerEventImpl implements JPurgeUEAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JPurgeUEAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JPurgeUEAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
