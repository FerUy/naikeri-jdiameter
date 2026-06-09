package org.mobicents.diameter.stack.functional.cxdx.base;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.cxdx.ClientCxDxSession;
import org.jdiameter.api.cxdx.events.JMultimediaAuthAnswer;
import org.jdiameter.api.cxdx.events.JMultimediaAuthRequest;
import org.jdiameter.common.impl.app.cxdx.JMultimediaAuthRequestImpl;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.cxdx.AbstractClient;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ClientMAR extends AbstractClient {

  protected boolean receivedMultimediaAuth;
  protected boolean sentMultimediaAuth;

  /**
   *
   */
  public ClientMAR() {
  }

  public void sendMultimediaAuth() throws Exception {

    // < Multimedia-Auth-Request > ::= < Diameter Header: 303, REQ, PXY, 16777216 >
    JMultimediaAuthRequest request = new JMultimediaAuthRequestImpl(super.createRequest(this.clientCxDxSession, JMultimediaAuthRequest.code));
    AvpSet reqSet = request.getMessage().getAvps();
    // < Session-Id >
    // { Vendor-Specific-Application-Id }
    // { Auth-Session-State }
    // { Origin-Host }
    // { Origin-Realm }
    // { Destination-Realm }
    // [ Destination-Host ]
    // { User-Name }
    reqSet.addAvp(Avp.USER_NAME, "myUsername", true, false, false);
    // [ OC-Supported-Features ]
    // *[ Supported-Features ]
    // { Public-Identity }
    reqSet.addAvp(Avp.PUBLIC_IDENTITY, "tralalalal user", getApplicationId().getVendorId(), true, false, false);
    // [MSISDN]
    // *[AVP]
    // { SIP-Auth-Data-Item }
    // seriously ....
    reqSet.addGroupedAvp(Avp.SIP_AUTH_DATA_ITEM, getApplicationId().getVendorId(), true, false);
    // { SIP-Number-Auth-Items }
    reqSet.addAvp(Avp.SIP_NUMBER_AUTH_ITEMS, 1, getApplicationId().getVendorId(), true, false, true);
    // { Server-Name }
    reqSet.addAvp(Avp.SERVER_NAME, "ala", getApplicationId().getVendorId(), true, false, false);
    // *[ AVP ]
    // *[ Proxy-Info ]
    // *[ Route-Record ]

    this.clientCxDxSession.sendMultimediaAuthRequest(request);
    Utils.printMessage(log, super.stack.getDictionary(), request.getMessage(), true);
    this.sentMultimediaAuth = true;
  }

  @Override
  public void doMultimediaAuthAnswer(ClientCxDxSession session, JMultimediaAuthRequest request, JMultimediaAuthAnswer answer) throws InternalException,
  IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedMultimediaAuth) {
      fail("Received MAA more than once", null);
      return;
    }

    this.receivedMultimediaAuth = true;
  }

  public boolean isReceivedMultimediaAuth() {
    return receivedMultimediaAuth;
  }

  public boolean isSentMultimediaAuth() {
    return sentMultimediaAuth;
  }

}
