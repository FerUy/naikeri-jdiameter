package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JResetAnswerImpl extends AppAnswerEventImpl implements JResetAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JResetAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JResetAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
