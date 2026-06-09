package org.jdiameter.common.api.app.ro;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.auth.events.ReAuthAnswer;
import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.api.ro.events.RoCreditControlAnswer;
import org.jdiameter.api.ro.events.RoCreditControlRequest;

/**
 * Diameter Ro Application Message Factory
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IRoMessageFactory {

  ReAuthRequest createReAuthRequest(Request request);

  ReAuthAnswer createReAuthAnswer(Answer answer);

  RoCreditControlRequest createCreditControlRequest(Request request);

  RoCreditControlAnswer createCreditControlAnswer(Answer answer);

  long[] getApplicationIds();

}
