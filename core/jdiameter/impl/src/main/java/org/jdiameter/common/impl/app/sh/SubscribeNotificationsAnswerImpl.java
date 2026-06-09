package org.jdiameter.common.impl.app.sh;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.sh.events.SubscribeNotificationsAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class SubscribeNotificationsAnswerImpl extends AppAnswerEventImpl implements SubscribeNotificationsAnswer {

  private static final long serialVersionUID = 1L;

  /**
   * @param answer
   */
  public SubscribeNotificationsAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public SubscribeNotificationsAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public SubscribeNotificationsAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public SubscribeNotificationsAnswerImpl(Request request) {
    super(request);
  }

}
