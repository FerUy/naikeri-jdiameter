package org.jdiameter.common.impl.app.auth;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.auth.events.SessionTermAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class SessionTermAnswerImpl extends AppAnswerEventImpl implements SessionTermAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public SessionTermAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public SessionTermAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public SessionTermAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public SessionTermAnswerImpl(Request request) {
    super(request);
  }

}
