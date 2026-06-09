package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JNotifyAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JNotifyAnswerImpl extends AppAnswerEventImpl implements JNotifyAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JNotifyAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JNotifyAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
