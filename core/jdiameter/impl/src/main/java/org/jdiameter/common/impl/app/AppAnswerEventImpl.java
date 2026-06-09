package org.jdiameter.common.impl.app;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AppAnswerEventImpl extends AppEventImpl implements AppAnswerEvent {

  private static final long serialVersionUID = 1L;

  public AppAnswerEventImpl(Request request, long resultCode) {
    this(request.createAnswer(resultCode));
  }

  public AppAnswerEventImpl(Request request, long vendorId, long resultCode) {
    this(request.createAnswer(vendorId, resultCode));
  }

  public AppAnswerEventImpl(Request request) {
    this(request.createAnswer());
  }

  public AppAnswerEventImpl(Answer answer) {
    super(answer);
  }

  @Override
  public Avp getResultCodeAvp() throws AvpDataException {
    Avp resultCodeAvp = message.getAvps().getAvp(Avp.RESULT_CODE);
    if (resultCodeAvp != null) {
      return resultCodeAvp;
    }
    resultCodeAvp = message.getAvps().getAvp(Avp.EXPERIMENTAL_RESULT);
    if (resultCodeAvp != null) {
      return resultCodeAvp.getGrouped().getAvp(Avp.EXPERIMENTAL_RESULT_CODE);
    }
    return null;
  }
}
