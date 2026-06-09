package org.jdiameter.common.impl.app.rx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.rx.events.RxAAAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RxAAAnswerImpl extends AppAnswerEventImpl implements RxAAAnswer {

  private static final long serialVersionUID = 1L;
  protected static final Logger logger = LoggerFactory.getLogger(RxAAAnswerImpl.class);

  public RxAAAnswerImpl(Request message, long resultCode) {
    super(message.createAnswer(resultCode));
  }

  public RxAAAnswerImpl(Answer message) {
    super(message);
  }
}
