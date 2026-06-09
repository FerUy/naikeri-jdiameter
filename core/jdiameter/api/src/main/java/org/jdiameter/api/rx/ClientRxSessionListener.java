package org.jdiameter.api.rx;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rx.events.RxAAAnswer;
import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.api.rx.events.RxAbortSessionRequest;
import org.jdiameter.api.rx.events.RxReAuthRequest;
import org.jdiameter.api.rx.events.RxSessionTermAnswer;
import org.jdiameter.api.rx.events.RxSessionTermRequest;

/**
 * This interface defines the possible actions for the different states in the client
 * Rx Interface state machine.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ClientRxSessionListener {

  void doAAAnswer(ClientRxSession session, RxAARequest request, RxAAAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doReAuthRequest(ClientRxSession session, RxReAuthRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doSessionTermAnswer(ClientRxSession session, RxSessionTermRequest request, RxSessionTermAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doAbortSessionRequest(ClientRxSession session, RxAbortSessionRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
