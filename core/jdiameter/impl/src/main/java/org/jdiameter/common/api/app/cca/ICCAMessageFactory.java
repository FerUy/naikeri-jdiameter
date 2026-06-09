package org.jdiameter.common.api.app.cca;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.auth.events.ReAuthAnswer;
import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.api.cca.events.JCreditControlAnswer;
import org.jdiameter.api.cca.events.JCreditControlRequest;

/**
 * Diameter Credit-Control Application Message Factory
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ICCAMessageFactory {

  ReAuthRequest createReAuthRequest(Request request);

  ReAuthAnswer createReAuthAnswer(Answer answer);

  JCreditControlRequest createCreditControlRequest(Request request);

  JCreditControlAnswer createCreditControlAnswer(Answer answer);

  long[] getApplicationIds();

}
