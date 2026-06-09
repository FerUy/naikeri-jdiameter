package org.jdiameter.api.s6a;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6a.events.JAuthenticationInformationAnswer;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationRequest;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JNotifyAnswer;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUEAnswer;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetRequest;
import org.jdiameter.api.s6a.events.JUpdateLocationAnswer;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;

/**
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ClientS6aSessionListener {

  void doAuthenticationInformationAnswerEvent(ClientS6aSession session, JAuthenticationInformationRequest rir, JAuthenticationInformationAnswer ria)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doUpdateLocationAnswerEvent(ClientS6aSession session, JUpdateLocationRequest ulr, JUpdateLocationAnswer ula)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doCancelLocationRequestEvent(ClientS6aSession session, JCancelLocationRequest clr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doInsertSubscriberDataRequestEvent(ClientS6aSession session, JInsertSubscriberDataRequest idr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doDeleteSubscriberDataRequestEvent(ClientS6aSession session, JDeleteSubscriberDataRequest dsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doPurgeUEAnswerEvent(ClientS6aSession session, JPurgeUERequest pur, JPurgeUEAnswer pua)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doResetRequestEvent(ClientS6aSession session, JResetRequest rsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doNotifyAnswerEvent(ClientS6aSession session, JNotifyRequest nor, JNotifyAnswer noa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
