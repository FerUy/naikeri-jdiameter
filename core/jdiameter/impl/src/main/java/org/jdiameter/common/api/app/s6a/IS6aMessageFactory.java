package org.jdiameter.common.api.app.s6a;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.s6a.events.JAuthenticationInformationAnswer;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.api.s6a.events.JCancelLocationRequest;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JNotifyAnswer;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUEAnswer;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.api.s6a.events.JResetRequest;
import org.jdiameter.api.s6a.events.JUpdateLocationAnswer;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;

/**
 * Message Factory for Diameter S6a application.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface IS6aMessageFactory {

  JUpdateLocationRequest createUpdateLocationRequest(Request request);
  JUpdateLocationAnswer createUpdateLocationAnswer(Answer answer);

  JCancelLocationRequest createCancelLocationRequest(Request request);
  JCancelLocationAnswer createCancelLocationAnswer(Answer answer);

  JAuthenticationInformationRequest createAuthenticationInformationRequest(Request request);
  JAuthenticationInformationAnswer createAuthenticationInformationAnswer(Answer answer);

  JInsertSubscriberDataRequest createInsertSubscriberDataRequest(Request request);
  JInsertSubscriberDataAnswer createInsertSubscriberDataAnswer(Answer answer);

  JDeleteSubscriberDataRequest createDeleteSubscriberDataRequest(Request request);
  JDeleteSubscriberDataAnswer createDeleteSubscriberDataAnswer(Answer answer);

  JPurgeUERequest createPurgeUERequest(Request request);
  JPurgeUEAnswer createPurgeUEAnswer(Answer answer);

  JResetRequest createResetRequest(Request request);
  JResetAnswer createResetAnswer(Answer answer);

  JNotifyRequest createNotifyRequest(Request request);
  JNotifyAnswer createNotifyAnswer(Answer answer);

  /**
   * Returns the Application-Id that this message factory is related to
   *
   * @return the Application-Id value
   */
  long getApplicationId();

}
