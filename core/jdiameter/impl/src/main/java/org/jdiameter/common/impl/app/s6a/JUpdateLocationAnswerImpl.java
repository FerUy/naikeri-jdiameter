package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JUpdateLocationAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public class JUpdateLocationAnswerImpl extends AppAnswerEventImpl implements JUpdateLocationAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JUpdateLocationAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JUpdateLocationAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
