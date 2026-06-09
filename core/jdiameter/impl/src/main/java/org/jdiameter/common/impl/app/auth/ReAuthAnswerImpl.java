package org.jdiameter.common.impl.app.auth;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.auth.events.ReAuthAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ReAuthAnswerImpl extends AppAnswerEventImpl implements ReAuthAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public ReAuthAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public ReAuthAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public ReAuthAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public ReAuthAnswerImpl(Request request) {
    super(request);
  }

}
