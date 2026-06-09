package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.cxdx.events.JRegistrationTerminationAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JRegistrationTerminationAnswerImpl extends AppAnswerEventImpl implements JRegistrationTerminationAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public JRegistrationTerminationAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public JRegistrationTerminationAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public JRegistrationTerminationAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public JRegistrationTerminationAnswerImpl(Request request) {
    super(request);
  }

}
