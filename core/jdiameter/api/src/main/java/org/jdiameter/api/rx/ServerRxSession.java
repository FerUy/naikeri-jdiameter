package org.jdiameter.api.rx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.rx.events.RxAAAnswer;
import org.jdiameter.api.rx.events.RxAbortSessionRequest;
import org.jdiameter.api.rx.events.RxReAuthRequest;
import org.jdiameter.api.rx.events.RxSessionTermAnswer;

/**
 * Basic class for Rx Server Interface specific session.
 * Listener must be injected from constructor of implementation class
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ServerRxSession extends AppSession, StateMachine {

  void sendAAAnswer(RxAAAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void sendSessionTermAnswer(RxSessionTermAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void sendReAuthRequest(RxReAuthRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void sendAbortSessionRequest(RxAbortSessionRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
