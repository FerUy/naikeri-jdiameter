package org.mobicents.diameter.stack.functional.cxdx.base;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.cxdx.ClientCxDxSession;
import org.jdiameter.api.cxdx.events.JUserAuthorizationAnswer;
import org.jdiameter.api.cxdx.events.JUserAuthorizationRequest;
import org.jdiameter.common.impl.app.cxdx.JUserAuthorizationRequestImpl;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.cxdx.AbstractClient;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ClientUAR extends AbstractClient {

  protected boolean receivedUserAuthorization;
  protected boolean sentUserAuthorization;

  /**
   *
   */
  public ClientUAR() {
  }

  public void sendUserAuthorization() throws Exception {
    JUserAuthorizationRequest request = new JUserAuthorizationRequestImpl(super.createRequest(this.clientCxDxSession, JUserAuthorizationRequest.code));
    AvpSet reqSet = request.getMessage().getAvps();
    // < User-Authorization-Request> ::= < Diameter Header: 300, REQ, PXY, 16777216 >
    // < Session-Id >
    // { Vendor-Specific-Application-Id }
    // { Auth-Session-State }
    // { Origin-Host }
    // { Origin-Realm }
    // [ Destination-Host ]
    // { Destination-Realm }
    // { User-Name }
    reqSet.addAvp(Avp.USER_NAME, "ala", false);
    // *[ Supported-Features ]
    // { Public-Identity }
    reqSet.addAvp(Avp.PUBLIC_IDENTITY, "tralalalal user", getApplicationId().getVendorId(), true, false, false);
    // [MSISDN]
    // *[AVP]
    // { Visited-Network-Identifier }
    reqSet.addAvp(Avp.VISITED_NETWORK_ID, "ala", getApplicationId().getVendorId(), true, false, false);
    // [ User-Authorization-Type ]
    // [ UAR-Flags ]
    // *[ AVP ]
    // *[ Proxy-Info ]
    // *[ Route-Record ]
    this.clientCxDxSession.sendUserAuthorizationRequest(request);
    Utils.printMessage(log, super.stack.getDictionary(), request.getMessage(), true);
    this.sentUserAuthorization = true;
  }

  @Override
  public void doUserAuthorizationAnswer(ClientCxDxSession session, JUserAuthorizationRequest request, JUserAuthorizationAnswer answer) throws InternalException,
  IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedUserAuthorization) {
      fail("Received UAA more than once", null);
      return;
    }
    this.receivedUserAuthorization = true;
  }

  public boolean isReceivedUserAuthorization() {
    return receivedUserAuthorization;
  }

  public boolean isSentUserAuthorization() {
    return sentUserAuthorization;
  }

}
