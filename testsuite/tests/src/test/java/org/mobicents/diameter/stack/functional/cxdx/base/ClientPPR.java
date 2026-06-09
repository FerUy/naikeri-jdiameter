package org.mobicents.diameter.stack.functional.cxdx.base;

import java.io.InputStream;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.cxdx.ClientCxDxSession;
import org.jdiameter.api.cxdx.ServerCxDxSession;
import org.jdiameter.api.cxdx.events.JPushProfileAnswer;
import org.jdiameter.api.cxdx.events.JPushProfileRequest;
import org.jdiameter.common.impl.app.cxdx.CxDxSessionFactoryImpl;
import org.jdiameter.common.impl.app.cxdx.JPushProfileAnswerImpl;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.cxdx.AbstractClient;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ClientPPR extends AbstractClient {

  protected boolean receivedPushProfile;
  protected boolean sentPushProfile;

  protected JPushProfileRequest request;

  /**
   *
   */
  public ClientPPR() {
  }

  @Override
  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777216));
      CxDxSessionFactoryImpl cxdxSessionFactory = new CxDxSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerCxDxSession.class, cxdxSessionFactory);
      sessionFactory.registerAppFacory(ClientCxDxSession.class, cxdxSessionFactory);
      cxdxSessionFactory.setClientSessionListener(this);
    }
    finally {
      try {
        configStream.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public void sendPushProfile() throws Exception {
    if (!receivedPushProfile || request == null) {
      fail("Did not receive PPR or answer already sent.", null);
      throw new Exception("Did not receive PPR or answer already sent. Request: " + this.request);
    }
    JPushProfileAnswer answer = new JPushProfileAnswerImpl((Request) this.request.getMessage(), 2001);

    AvpSet reqSet = request.getMessage().getAvps();

    AvpSet set = answer.getMessage().getAvps();
    set.removeAvp(Avp.DESTINATION_HOST);
    set.removeAvp(Avp.DESTINATION_REALM);
    set.addAvp(reqSet.getAvp(Avp.CC_REQUEST_TYPE), reqSet.getAvp(Avp.CC_REQUEST_NUMBER), reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // < Push-Profile-Answer > ::= < Diameter Header: 305, PXY, 16777216 >
    // < Session-Id >
    // { Vendor-Specific-Application-Id }
    if (set.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = set.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }
    // [Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (set.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      set.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }
    // { Origin-Host }
    // { Origin-Realm }
    // *[ Supported-Features ]
    // *[ AVP ]
    // *[ Failed-AVP ]
    // *[ Proxy-Info ]
    // *[ Route-Record ]

    this.clientCxDxSession.sendPushProfileAnswer(answer);
    Utils.printMessage(log, super.stack.getDictionary(), request.getMessage(), true);
    this.request = null;
    this.sentPushProfile = true;
  }

  @Override
  public void doPushProfileRequest(ClientCxDxSession session, JPushProfileRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException,
  OverloadException {
    if (this.receivedPushProfile) {
      fail("Received PPR more than once", null);
      return;
    }
    this.receivedPushProfile = true;
    this.request = request;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != JPushProfileAnswer.code) {
      fail("Received Request with code not used by CxDx!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientCxDxSession != null) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    }
    else {
      try {
        super.clientCxDxSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientCxDxSession.class, (Object) null);
        ((NetworkReqListener) this.clientCxDxSession).processRequest(request);
      }
      catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  public boolean isReceivedPushProfile() {
    return receivedPushProfile;
  }

  public boolean isSentPushProfile() {
    return sentPushProfile;
  }

}
