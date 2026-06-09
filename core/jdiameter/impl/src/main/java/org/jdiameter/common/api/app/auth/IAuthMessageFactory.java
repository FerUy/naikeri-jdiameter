package org.jdiameter.common.api.app.auth;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IAuthMessageFactory {

  ApplicationId getApplicationId();

  int getAuthMessageCommandCode();

  AppRequestEvent createAuthRequest(Request request);

  AppAnswerEvent createAuthAnswer(Answer answer);

}
