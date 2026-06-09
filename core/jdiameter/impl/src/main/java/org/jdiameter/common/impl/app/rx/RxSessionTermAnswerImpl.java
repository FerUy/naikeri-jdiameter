package org.jdiameter.common.impl.app.rx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.rx.events.RxSessionTermAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RxSessionTermAnswerImpl extends AppAnswerEventImpl implements RxSessionTermAnswer {

  private static final long serialVersionUID = 1L;
  protected static final Logger logger = LoggerFactory.getLogger(RxSessionTermAnswerImpl.class);

  public RxSessionTermAnswerImpl(Request message, long resultCode) {
    super(message.createAnswer(resultCode));
  }

  public RxSessionTermAnswerImpl(Answer message) {
    super(message);
  }
}
