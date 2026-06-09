package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JDeleteSubscriberDataAnswerImpl extends AppAnswerEventImpl implements JDeleteSubscriberDataAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JDeleteSubscriberDataAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JDeleteSubscriberDataAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
