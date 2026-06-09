package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.cxdx.events.JMultimediaAuthAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JMultimediaAuthAnswerImpl extends AppAnswerEventImpl implements JMultimediaAuthAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public JMultimediaAuthAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public JMultimediaAuthAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public JMultimediaAuthAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public JMultimediaAuthAnswerImpl(Request request) {
    super(request);
  }

}
