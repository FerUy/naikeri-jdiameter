package org.jdiameter.common.impl.app.gx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.gx.events.GxReAuthAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public class GxReAuthAnswerImpl extends AppAnswerEventImpl implements GxReAuthAnswer {

  private static final long serialVersionUID = 1L;
  protected static final Logger logger = LoggerFactory.getLogger(GxReAuthAnswerImpl.class);

  public GxReAuthAnswerImpl(Request message, long resultCode) {
    super(message.createAnswer(resultCode));
  }

  public GxReAuthAnswerImpl(Answer message) {
    super(message);
  }
}
