package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JCancelLocationAnswerImpl extends AppAnswerEventImpl implements JCancelLocationAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JCancelLocationAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JCancelLocationAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
