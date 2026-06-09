package org.jdiameter.api.sgd;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ClientSGdSessionListener {

  void doMTForwardShortMessageAnswerEvent(ClientSGdSession session, MTForwardShortMessageRequest tfr, MTForwardShortMessageAnswer tfa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doMOForwardShortMessageRequestEvent(ClientSGdSession session, MOForwardShortMessageRequest ofr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
