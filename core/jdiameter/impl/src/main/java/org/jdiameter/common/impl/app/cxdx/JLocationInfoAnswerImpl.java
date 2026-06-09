package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.cxdx.events.JLocationInfoAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JLocationInfoAnswerImpl extends AppAnswerEventImpl implements JLocationInfoAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public JLocationInfoAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public JLocationInfoAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public JLocationInfoAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public JLocationInfoAnswerImpl(Request request) {
    super(request);
  }

}
