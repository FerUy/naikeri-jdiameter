package org.jdiameter.common.api.app.gx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.gx.events.GxCreditControlAnswer;
import org.jdiameter.api.gx.events.GxCreditControlRequest;
import org.jdiameter.api.gx.events.GxReAuthAnswer;
import org.jdiameter.api.gx.events.GxReAuthRequest;

/**
 * Diameter Gx Application Message Factory
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface IGxMessageFactory {

  GxReAuthRequest createGxReAuthRequest(Request request);

  GxReAuthAnswer createGxReAuthAnswer(Answer answer);

  GxCreditControlRequest createCreditControlRequest(Request request);

  GxCreditControlAnswer createCreditControlAnswer(Answer answer);

  long[] getApplicationIds();

}
