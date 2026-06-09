package org.jdiameter.common.impl.app.sh;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.sh.events.UserDataAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class UserDataAnswerImpl extends AppAnswerEventImpl implements UserDataAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public UserDataAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public UserDataAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public UserDataAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public UserDataAnswerImpl(Request request) {
    super(request);
  }

}
