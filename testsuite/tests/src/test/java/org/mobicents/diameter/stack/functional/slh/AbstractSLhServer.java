package org.mobicents.diameter.stack.functional.slh;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.api.slh.ServerSLhSessionListener;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.jdiameter.common.impl.app.slh.LCSRoutingInfoAnswerImpl;
import org.jdiameter.common.impl.app.slh.SLhSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public abstract class AbstractSLhServer extends TBase implements ServerSLhSessionListener {

  // NOTE: implementing NetworkReqListener since its required for stack to
  // know we support it... ech.

  protected ServerSLhSession serverSLhSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777291));
      SLhSessionFactoryImpl slhSessionFactory = new SLhSessionFactoryImpl(this.sessionFactory);
      (sessionFactory).registerAppFacory(ServerSLhSession.class, slhSessionFactory);
      (sessionFactory).registerAppFacory(ClientSLhSession.class, slhSessionFactory);
      slhSessionFactory.setServerSessionListener(this);
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

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
    RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  public void doLCSRoutingInfoRequestEvent(ServerSLhSession session, LCSRoutingInfoRequest request) throws InternalException, IllegalDiameterStateException,
    RouteException, OverloadException {
    fail("Received \"RIR\" event, request[" + request + "], on session[" + session + "]", null);
  }

  // -------- conf

  public String getSessionId() {
    return this.serverSLhSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverSLhSession = stack.getSession(sessionId, ServerSLhSession.class);
  }

  public ServerSLhSession getSession() {
    return this.serverSLhSession;
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

  /** Attributes for LCS-Routing-Info-Answer (RIA) **/

  protected abstract String getUserName();
  protected abstract byte[] getMSISDN();
  protected abstract byte[] getLMSI();
  protected abstract byte[] getSGSNNumber();
  protected abstract String getSGSNName();
  protected abstract String getSGSNRealm();
  protected abstract String getMMEName();
  protected abstract String getMMERealm();
  protected abstract byte[] getMSCNumber();
  protected abstract String get3GPPAAAServerName();
  protected abstract long getLCSCapabilitiesSets();
  protected abstract InetAddress getGMLCAddress();
  protected abstract byte[] getAdditionalSGSNNumber();
  protected abstract String getAdditionalSGSNName();
  protected abstract String getAdditionalSGSNRealm();
  protected abstract String getAdditionalMMEName();
  protected abstract String getAdditionalMMERealm();
  protected abstract byte[] getAdditionalMSCNumber();
  protected abstract String getAdditional3GPPAAAServerName();
  protected abstract long getAdditionalLCSCapabilitiesSets();
  protected abstract InetAddress getAdditionalGMLCAddress();
  protected abstract InetAddress getPPRAddress();
  protected abstract long getRIAFLags();

  public LCSRoutingInfoAnswer createRIA(LCSRoutingInfoRequest rir, long resultCode) throws Exception {

/*
  3GPP TS 29.173 v18.0.0 § 6.2.4

  The LCS-Routing-Info-Answer (RIA) command, indicated by the Command-Code field set to 8388622
  and the 'R' bit cleared in the Command Flags field, is sent from HSS to GMLC.

  Message Format:
  < LCS-Routing-Info-Answer > ::= < Diameter Header: 8388622, PXY, 16777291 >
		                   < Session-Id >
		                   [ Vendor-Specific-Application-Id ]
		                   [ Result-Code ]
		                   [ Experimental-Result ]
		                   { Auth-Session-State }
		                   { Origin-Host }
		                   { Origin-Realm }
		                  *[ Supported-Features ]
		                   [ User-Name ]
		                   [ MSISDN ]
		                   [ LMSI ]
		                   [ Serving-Node ]
		                  *[ Additional-Serving-Node ]
		                   [ GMLC-Address ]
		                   [ PPR-Address ]
		                   [ RIA-Flags ]
		                  *[ AVP ]
		                   [ Failed-AVP ]
		                  *[ Proxy-Info ]
		                  *[ Route-Record ]
 */
    LCSRoutingInfoAnswer ria = new LCSRoutingInfoAnswerImpl((Request) rir.getMessage(), resultCode);

    AvpSet reqSet = rir.getMessage().getAvps();
    AvpSet set = ria.getMessage().getAvps();
    set.removeAvp(Avp.DESTINATION_HOST);
    set.removeAvp(Avp.DESTINATION_REALM);
    set.addAvp(reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // { Vendor-Specific-Application-Id }
    if (set.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = set.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }
    // [ Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (set.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      set.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // [ User-Name ]
    if (getUserName() != null)
      set.addAvp(Avp.USER_NAME, getUserName(), 10415, true, false, false);

    // [ MSISDN ]
    if (getMSISDN() != null)
      set.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ LMSI ]
    if (getLMSI() != null)
      set.addAvp(Avp.LMSI, getLMSI(), 10415, true, false);

    /*
      Serving-Node ::= <AVP header: 2401 10415>
                           [ SGSN-Number ]
                           [ MME-Name ]
                           [ SGSN-Name ]
                           [ SGSN-Realm ]
                           [ MME-Realm ]
                           [ MSC-Number ]
                           [ 3GPP-AAA-Server-Name ]
                           [ LCS-Capabilities-Sets ]
                           [ GMLC-Address ]
                          *[AVP]
    */
    AvpSet servingNode = set.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
    if (getSGSNNumber() != null)
      servingNode.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, false, false);
    if (getSGSNName() != null)
      servingNode.addAvp(Avp.SGSN_NAME, getSGSNName(), 10415, false, false, false);
    if (getSGSNRealm() != null)
      servingNode.addAvp(Avp.SGSN_REALM, getSGSNRealm(), 10415, false, false, false);
    if (getMMEName() != null)
      servingNode.addAvp(Avp.MME_NAME, getMMEName(), 10415, false, false, false);
    if (getMMERealm() != null)
      servingNode.addAvp(Avp.MME_REALM, getMMERealm(), 10415, false, false, false);
    if (getMSCNumber() != null)
      servingNode.addAvp(Avp.MSC_NUMBER, getMSCNumber(), 10415, false, false);
    if (get3GPPAAAServerName() != null)
      servingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, get3GPPAAAServerName(), 10415, false, false, false);
    if (getLCSCapabilitiesSets() != -1)
      servingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getLCSCapabilitiesSets(), 10415, false, false, true);
    if (getGMLCAddress() != null)
      servingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, true, false);

    /*
      Additional-Serving-Node ::= <AVP header: 2406 10415>
                           [ SGSN-Number ]
                           [ MME-Name ]
                           [ SGSN-Name ]
                           [ SGSN-Realm ]
                           [ MME-Realm ]
                           [ MSC-Number ]
                           [ 3GPP-AAA-Server-Name ]
                           [ LCS-Capabilities-Sets ]
                           [ GMLC-Address ]
                          *[AVP]
    */
    AvpSet additionalServingNode = set.addGroupedAvp(Avp.ADDITIONAL_SERVING_NODE, 10415, true, false);
    if (getAdditionalSGSNNumber() != null)
      additionalServingNode.addAvp(Avp.SGSN_NUMBER, getAdditionalSGSNNumber(), 10415, false, false);
    if (getAdditionalSGSNName() != null)
      additionalServingNode.addAvp(Avp.SGSN_NAME, getAdditionalSGSNName(), 10415, false, false, false);
    if (getAdditionalSGSNRealm() != null)
      additionalServingNode.addAvp(Avp.SGSN_REALM, getAdditionalSGSNRealm(), 10415, false, false, false);
    if (getAdditionalMMEName() != null)
      additionalServingNode.addAvp(Avp.MME_NAME, getAdditionalMMEName(), 10415, false, false, false);
    if (getAdditionalMMERealm() != null)
      additionalServingNode.addAvp(Avp.MME_REALM, getAdditionalMMERealm(), 10415, false, false, false);
    if (getAdditionalMSCNumber() != null)
      additionalServingNode.addAvp(Avp.MSC_NUMBER, getAdditionalMSCNumber(), 10415, false, false);
    if (getAdditional3GPPAAAServerName() != null)
      additionalServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, getAdditional3GPPAAAServerName(), 10415, false, false, false);
    if (getAdditionalLCSCapabilitiesSets() != -1)
      additionalServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getAdditionalLCSCapabilitiesSets(), 10415, false, false, true);
    if (getAdditionalGMLCAddress() != null)
      additionalServingNode.addAvp(Avp.GMLC_ADDRESS, getAdditionalGMLCAddress(), 10415, false, false);

    // [ GMLC-Address ]
    if (getGMLCAddress() != null)
      set.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, true, false);

    // [ PPR-Address ]
    if (getPPRAddress() != null)
      set.addAvp(Avp.PPR_ADDRESS, getPPRAddress(), 10415, true, false);

    //[ RIA-Flags ]
    if (getRIAFLags() != -1)
      set.addAvp(Avp.RIA_FLAGS, getRIAFLags(), 10415, true, false, true);

    return ria;
  }

}