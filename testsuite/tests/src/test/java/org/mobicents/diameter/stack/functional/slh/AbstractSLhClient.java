package org.mobicents.diameter.stack.functional.slh;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.api.slh.ClientSLhSessionListener;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.jdiameter.common.impl.app.slh.LCSRoutingInfoRequestImpl;
import org.jdiameter.common.impl.app.slh.SLhSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public abstract class AbstractSLhClient extends TBase implements ClientSLhSessionListener {

  // NOTE: implementing NetworkReqListener since it's required for stack to know we support it... ech.

  protected ClientSLhSession clientSLhSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777291));
      SLhSessionFactoryImpl slhSessionFactory = new SLhSessionFactoryImpl(this.sessionFactory);
      (sessionFactory).registerAppFacory(ServerSLhSession.class, slhSessionFactory);
      (sessionFactory).registerAppFacory(ClientSLhSession.class, slhSessionFactory);

      slhSessionFactory.setClientSessionListener(this);

      this.clientSLhSession = (this.sessionFactory).getNewAppSession(this.sessionFactory.getSessionId("xx-SLh-TESTxx"), getApplicationId(),
          ClientSLhSession.class, (Object) null);
    } finally {
      try {
        configStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // ----------- delegate methods so

  public void start() throws IllegalDiameterStateException, InternalException {
    stack.start();
  }

  public void start(Mode mode, long timeOut, TimeUnit timeUnit) throws IllegalDiameterStateException, InternalException {
    stack.start(mode, timeOut, timeUnit);
  }

  public void stop(long timeOut, TimeUnit timeUnit, int disconnectCause) throws IllegalDiameterStateException, InternalException {
    stack.stop(timeOut, timeUnit, disconnectCause);
  }

  public void stop(int disconnectCause) {
        stack.stop(disconnectCause);
    }

  // ------- def methods, to fail :)

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  public void doLCSRoutingInfoAnswerEvent(ClientSLhSession session, LCSRoutingInfoRequest request, LCSRoutingInfoAnswer answer) throws InternalException,
      IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"RIA\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // ----------- conf

  public String getSessionId() {
        return this.clientSLhSession.getSessionId();
    }

  public ClientSLhSession getSession() {
        return this.clientSLhSession;
    }

  /*
   3GPP TS 29.173 v18.0.0 § 5.2.1

   5.2.1	Send Routing Information for LCS
   5.2.1.1	General
   This procedure is used between the GMLC and the HSS.  The procedure is invoked by the GMLC and is used:
     - To retrieve routing information for LCS for a specified user from the HSS.

   5.2.1.2 Detailed Behaviour of the HSS
   Upon reception of the Send Routing Info for LCS request, the HSS shall, in the following order:
    1. Check whether the requesting GMLC belongs to a network authorized to request UE location information. If not, Experimental-Result shall be set to DIAMETER_ERROR_UNAUTHORIZED_REQUESTING_NETWORK in the Send Routing Information for LCS Response.
    2. Check that the User Identity for whom data is asked exists in HSS. If not, Experimental-Result shall be set to DIAMETER_ERROR_USER_UNKNOWN in the Send Routing Information for LCS Response.
     2a. If both IMSI and MSISDN are present in the request, check whether they identify the same User. If not, the HSS Result-Code shall be set to DIAMETER_CONTRADICTING_AVPS in the Send Routing Information for LCS Response.
    3. Check that there is at least one serving node associated with the targeted user. If not, Experimental-Result shall be set to DIAMETER_ERROR_ABSENT_USER in the Send Routing Information for LCS Response.

   If there is an error in any of the above steps then the HSS shall stop processing and shall return the error code specified
   in the respective step (see 3GPP TS 29.329 and 3GPP TS 29.229 [8] for an explanation of the error codes).
   If the HSS cannot fulfil the received request for reasons not stated in the above steps, e.g. due to a database error or empty mandatory data elements,
   it shall stop processing the request and set Result-Code to DIAMETER_UNABLE_TO_COMPLY.
   Otherwise, the requested operation shall take place and the HSS shall return the Result-Code AVP set to DIAMETER_SUCCESS.
   The HSS returns one or several of the network addresses of the current MME, SGSN, 3GPP AAA server and/or VMSC/MSC server,
   the LCS capabilities of the serving nodes if available, the V-GMLC address associated with the serving nodes,
   if available, and whichever of the IMSI and MSISDN that was not provided in the Send Routing Info for LCS request.
   If MSISDN was not provided in the Send Routing Info for LCS request and the subscription is MSISDN-less,
   the HSS shall return the dummy MSISDN value (see 3GPP TS 23.003 [10]). If both MSISDN and IMSI were provided
   in the Send Routing Info for LCS request, the HSS returns either MSISDN or IMSI or both. The HSS returns the address of the H-GMLC.
   The HSS also provides the address of the PPR, if available.

   The HSS shall include the Diameter Identity of the SGSN (i.e. SGSN-Name and SGSN-Realm), within the Serving-Node AVP
   or within an Additional-Serving-Node AVP, if and only if the HSS has received an indication that the Lgd interface
   is supported by the SGSN (see 3GPP TS 29.272).

   The HSS shall include the SGSN Number, within the Serving-Node AVP or Additional Serving-Node AVP,
   except if the HSS has received an indication that the Lg interface is not supported by the SGSN (see 3GPP TS 29.272).

   If the UE is served by the MME and SGSN parts of the same combined MME/SGSN
   (see 3GPP TS 29.272 subclause 5.2.2.1.1 for how the HSS determines if the UE is served by the combined MME/SGSN)
   and if this combined MME/SGSN has indicated the support for optimized LCS procedure
   (via the Supported-Feature AVP as defined in 3GPP TS 29.272 subclause 7.3.10)
   and if HSS supports this optimized LCS procedure,
   then the HSS shall set the "Combined-MME/SGSN-Supporting-Optimized-LCS-Proc" bit of the RIA-Flags.

   Regarding the LCS capabilities of the serving nodes, if the HSS registered an SGSN via the S6d reference point
   (i.e., the registered serving node is an S4-SGSN), the HSS shall set the LCS-Capabilities-Set value to indicate
   support of Capability Set 5 (i.e., LCS release 7 or later version).
   If the HSS registered an MME, the HSS shall not indicate any LCS capability value to the GMLC
   (i.e., the LCS-Capabilities-Set AVP shall be absent over SLh when the serving node is an MME);
   in this case, the GMLC shall assume that the MME supports LCS Capability Set 5.
  */

  /** Attributes for LCS-Routing-Info-Request (RIR) **/
  protected abstract String getUserName();
  protected abstract byte[] getMSISDN();
  protected abstract byte[] getGMLCNumber();

  /*
   3GPP TS 29.173 v18.0.0 § 6.2.3

   The LCS-Routing-Info-Request (RIR) command, indicated by the Command-Code field set to 8388622
   and the "R" bit set in the Command Flags field, is sent from GMLC to HSS.
   Message Format:
   < LCS-Routing-Info-Request > ::= < Diameter Header: 8388622, REQ, PXY, 16777291 >
                             < Session-Id >
                             [ Vendor-Specific-Application-Id ]
                             { Auth-Session-State }
                             { Origin-Host }
                             { Origin-Realm }
                             [ Destination-Host ]
                             { Destination-Realm }
                             [ User-Name ]
                             [ MSISDN ]
                             [ GMLC-Number ]
                            *[ Supported-Features ]
                            *[ Proxy-Info ]
                            *[ Route-Record ]
                            *[ AVP ]
  */
  protected LCSRoutingInfoRequest createRIR(ClientSLhSession slhSession) throws Exception {
    // < LCS-Routing-Info-Request > ::= < Diameter Header: 8388622, REQ, PXY, 16777291 >
    LCSRoutingInfoRequest rir = new LCSRoutingInfoRequestImpl(slhSession.getSessions().get(0).
        createRequest(LCSRoutingInfoRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = rir.getMessage().getAvps();

    if (reqSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = reqSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // { Auth-Session-State }
    if (reqSet.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      reqSet.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // { Origin-Host }
    reqSet.removeAvp(Avp.ORIGIN_HOST);
    reqSet.addAvp(Avp.ORIGIN_HOST, getClientURI(), true);

    // [ User-Name ]
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 10415, true, false, false);

    // [ MSISDN ]
    if (getMSISDN() != null)
      reqSet.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ GMLC-Number ]
    if (getGMLCNumber() != null)
      reqSet.addAvp(Avp.GMLC_NUMBER, getGMLCNumber(), 10415, false, false);

    return rir;
  }

}