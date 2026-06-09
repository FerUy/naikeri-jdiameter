package org.jdiameter.common.impl.app.auth;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.Request;
import org.jdiameter.api.auth.events.AbortSessionAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AbortSessionAnswerImpl extends AppAnswerEventImpl implements AbortSessionAnswer {

  private static final long serialVersionUID = 1L;

  public AbortSessionAnswerImpl(Request request, int authRequestType, long resultCode) {
    super(request.createAnswer(resultCode));
    try {
      getMessage().getAvps().addAvp(Avp.AUTH_REQUEST_TYPE, authRequestType);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * @param answer
   */
  public AbortSessionAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public AbortSessionAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public AbortSessionAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public AbortSessionAnswerImpl(Request request) {
    super(request);
  }

}
