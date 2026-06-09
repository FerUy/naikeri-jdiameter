package org.jdiameter.api.s6a;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.api.s6a.events.JCancelLocationRequest;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.api.s6a.events.JResetRequest;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface ServerS6aSessionListener {

  void doAuthenticationInformationRequestEvent(ServerS6aSession session, JAuthenticationInformationRequest air)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doUpdateLocationRequestEvent(ServerS6aSession session, JUpdateLocationRequest ulr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doCancelLocationAnswerEvent(ServerS6aSession session, JCancelLocationRequest clr, JCancelLocationAnswer cla)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doInsertSubscriberDataAnswerEvent(ServerS6aSession session, JInsertSubscriberDataRequest idr, JInsertSubscriberDataAnswer ida)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doDeleteSubscriberDataAnswerEvent(ServerS6aSession session, JDeleteSubscriberDataRequest dsr, JDeleteSubscriberDataAnswer dsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doPurgeUERequestEvent(ServerS6aSession session, JPurgeUERequest pur)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doResetAnswerEvent(ServerS6aSession session, JResetRequest rsr, JResetAnswer rsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doNotifyRequestEvent(ServerS6aSession session, JNotifyRequest nor)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
