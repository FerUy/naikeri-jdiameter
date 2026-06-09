package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JInsertSubscriberDataAnswerImpl extends AppAnswerEventImpl implements JInsertSubscriberDataAnswer {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param answer
   */
  public JInsertSubscriberDataAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   *
   * @param request
   * @param resultCode
   */
  public JInsertSubscriberDataAnswerImpl(Request request, long resultCode) {
    super(request.createAnswer(resultCode));
  }

}
