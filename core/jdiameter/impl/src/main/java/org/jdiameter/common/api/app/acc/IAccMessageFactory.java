package org.jdiameter.common.api.app.acc;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Request;
import org.jdiameter.api.acc.events.AccountAnswer;
import org.jdiameter.api.acc.events.AccountRequest;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IAccMessageFactory {

  ApplicationId getApplicationId();

  int getAccMessageCommandCode();

  AccountRequest createAccRequest(Request request);

  AccountAnswer createAccAnswer(Answer answer);

}
