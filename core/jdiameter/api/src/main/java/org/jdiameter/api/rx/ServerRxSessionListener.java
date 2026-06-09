package org.jdiameter.api.rx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.api.rx.events.RxAbortSessionAnswer;
//import org.jdiameter.api.auth.events.AbortSessionAnswer;
//import org.jdiameter.api.auth.events.AbortSessionRequest;
import org.jdiameter.api.rx.events.RxAbortSessionRequest;
import org.jdiameter.api.rx.events.RxReAuthAnswer;
//import org.jdiameter.api.auth.events.ReAuthAnswer;
//import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.api.rx.events.RxReAuthRequest;
//import org.jdiameter.api.auth.events.SessionTermRequest;
import org.jdiameter.api.rx.events.RxSessionTermRequest;

/**
 * This interface defines the possible actions for the different states in the server Rx
 * Interface state machine.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ServerRxSessionListener {

  void doAARequest(ServerRxSession session, RxAARequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doSessionTermRequest(ServerRxSession session, RxSessionTermRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doReAuthAnswer(ServerRxSession session, RxReAuthRequest request, RxReAuthAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doAbortSessionAnswer(ServerRxSession session, RxAbortSessionRequest request, RxAbortSessionAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
