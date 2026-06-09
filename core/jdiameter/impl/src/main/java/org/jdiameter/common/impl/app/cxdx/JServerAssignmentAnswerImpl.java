package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.cxdx.events.JServerAssignmentAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JServerAssignmentAnswerImpl extends AppAnswerEventImpl implements JServerAssignmentAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public JServerAssignmentAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public JServerAssignmentAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public JServerAssignmentAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public JServerAssignmentAnswerImpl(Request request) {
    super(request);
  }

}
