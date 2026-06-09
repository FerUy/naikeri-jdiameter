package org.jdiameter.common.api.app.rx;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.api.rx.events.RxAAAnswer;
import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.api.rx.events.RxAbortSessionAnswer;
import org.jdiameter.api.rx.events.RxAbortSessionRequest;
import org.jdiameter.api.rx.events.RxReAuthAnswer;
import org.jdiameter.api.rx.events.RxReAuthRequest;
import org.jdiameter.api.rx.events.RxSessionTermAnswer;
import org.jdiameter.api.rx.events.RxSessionTermRequest;

/**
 * Diameter 3GPP IMS Rx Reference Point Message Factory
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 */
public interface IRxMessageFactory {

  RxReAuthRequest createReAuthRequest(Request request);

  RxReAuthAnswer createReAuthAnswer(Answer answer);

  RxSessionTermRequest createSessionTermRequest(Request request);

  RxSessionTermAnswer createSessionTermAnswer(Answer answer);

  RxAbortSessionRequest createAbortSessionRequest(Request request);

  RxAbortSessionAnswer createAbortSessionAnswer(Answer answer);

  RxAARequest createAARequest(Request request);

  RxAAAnswer createAAAnswer(Answer answer);

  long[] getApplicationIds();

}
