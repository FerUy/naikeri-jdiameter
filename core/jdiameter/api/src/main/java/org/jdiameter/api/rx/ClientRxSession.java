package org.jdiameter.api.rx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.api.rx.events.RxAbortSessionAnswer;
import org.jdiameter.api.rx.events.RxReAuthAnswer;
import org.jdiameter.api.rx.events.RxSessionTermRequest;

/**
 * Basic class for Rx Client Interface specific session.
 * Listener must be injected from constructor of implementation class.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ClientRxSession extends AppSession, StateMachine {

  void sendAARequest(final RxAARequest request) throws InternalException, IllegalDiameterStateException, RouteException,
  OverloadException;

  void sendSessionTermRequest(final RxSessionTermRequest request) throws InternalException, IllegalDiameterStateException, RouteException,
  OverloadException;

  void sendReAuthAnswer(final RxReAuthAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void sendAbortSessionAnswer(final RxAbortSessionAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
