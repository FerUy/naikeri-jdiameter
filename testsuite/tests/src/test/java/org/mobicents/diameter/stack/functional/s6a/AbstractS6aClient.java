package org.mobicents.diameter.stack.functional.s6a;

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
import org.jdiameter.api.s6a.ClientS6aSession;
import org.jdiameter.api.s6a.ClientS6aSessionListener;
import org.jdiameter.api.s6a.ServerS6aSession;
import org.jdiameter.api.s6a.events.JAuthenticationInformationAnswer;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.api.s6a.events.JCancelLocationRequest;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JNotifyAnswer;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUEAnswer;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.api.s6a.events.JResetRequest;
import org.jdiameter.api.s6a.events.JUpdateLocationAnswer;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;
import org.jdiameter.common.impl.app.s6a.JAuthenticationInformationRequestImpl;
import org.jdiameter.common.impl.app.s6a.JCancelLocationAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JDeleteSubscriberDataAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JInsertSubscriberDataAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JNotifyRequestImpl;
import org.jdiameter.common.impl.app.s6a.JPurgeUERequestImpl;
import org.jdiameter.common.impl.app.s6a.JResetAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JUpdateLocationRequestImpl;
import org.jdiameter.common.impl.app.s6a.S6aSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.net.InetAddress;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractS6aClient extends TBase implements ClientS6aSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ClientS6aSession clientS6aSession;
  protected ServerS6aSession serverS6aSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777251));
      S6aSessionFactoryImpl s6aSessionFactory = new S6aSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerS6aSession.class, s6aSessionFactory);
      sessionFactory.registerAppFacory(ClientS6aSession.class, s6aSessionFactory);

      s6aSessionFactory.setClientSessionListener(this);

      this.clientS6aSession = (this.sessionFactory).getNewAppSession(this.sessionFactory.getSessionId("xx-S6a-TESTxx"),
          getApplicationId(), ClientS6aSession.class, (Object) null);
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
  public void doAuthenticationInformationAnswerEvent(ClientS6aSession session, JAuthenticationInformationRequest air, JAuthenticationInformationAnswer aia)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"AIA\" event, request[" + air + "], answer[" + aia + "], on session[" + session + "]", null);
  }

  public void doUpdateLocationAnswerEvent(ClientS6aSession session, JUpdateLocationRequest ulr, JUpdateLocationAnswer ula)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"ULA\" event, request[" + ulr + "], answer[" + ula + "], on session[" + session + "]", null);
  }

  public void doCancelLocationRequestEvent(ClientS6aSession session, JCancelLocationRequest clr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"CLR\" event, request[" + clr + "], on session[" + session + "]", null);
  }

  public void doInsertSubscriberDataRequestEvent(ClientS6aSession session, JInsertSubscriberDataRequest idr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"IDR\" event, request[" + idr + "], on session[" + session + "]", null);
  }

  public void doDeleteSubscriberDataRequestEvent(ClientS6aSession session, JDeleteSubscriberDataRequest dsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"DSR\" event, request[" + dsr + "], on session[" + session + "]", null);
  }

  public void doPurgeUEAnswerEvent(ClientS6aSession session, JPurgeUERequest pur, JPurgeUEAnswer pua)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"PUA\" event, request[" + pur + "], answer[" + pua + "], on session[" + session + "]", null);
  }

  public void doResetRequestEvent(ClientS6aSession session, JResetRequest rsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"RSR\" event, request[" + rsr + "], on session[" + session + "]", null);
  }

  public void doNotifyAnswerEvent(ClientS6aSession session, JNotifyRequest nor, JNotifyAnswer noa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"NOA\" event, request[" + nor + "], answer[" + noa + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // ----------- conf parts
  public String getSessionId() {
    return this.clientS6aSession.getSessionId();
  }

  public ClientS6aSession getSession() {
    return this.clientS6aSession;
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverS6aSession = stack.getSession(sessionId, ServerS6aSession.class);
  }

  // Attributes for Authentication-Information-Request (AIR), Update-Location-Request (ULR),
  // Cancel-Location-Answer (CLA), Insert-Subscriber-Data-Answer (IDA), Delete-Subscriber-Data-Answer (DSA),
  // Purge-UE-Request (PUR), Reset-Answer (RSA), Notify-Request (NOR)

  // { User-Name }
  protected abstract String getUserName();

  // [ OC-Supported-Features ]
  protected abstract long getOCFeatureVector();
  protected abstract String getSourceID();
  protected abstract long getOCPeerAlgo();

  // *[ Supported-Features ]
  protected abstract long getVendorId();
  protected abstract long getFeatureListID();
  protected abstract long getFeatureList();

  // [ Requested-EUTRAN-Authentication-Info ]
  protected abstract long getEUTRANNumberOfRequestedVectors();
  protected abstract long getEUTRANImmediateResponsePreferred();
  protected abstract byte[] getEUTRANReSynchronizationInfo();

  // [ Requested-UTRAN-GERAN-Authentication-Info ]
  protected abstract long getNumberOfRequestedVectors();
  protected abstract long getImmediateResponsePreferred();
  protected abstract byte[] getReSynchronizationInfo();

  // { Visited-PLMN-Id } / [ Equivalent-PLMN-List ] / [ Adjacent-PLMNs ]
  protected abstract byte[] getVisitedPLMNId();

  // [ AIR-Flags ]
  protected abstract long getAirFlags();

  // [ Terminal-Information ]
  protected abstract String getIMEI();
  protected abstract String get3gpp2MEID();
  protected abstract String getSoftwareVersion();

  // { RAT-Type }
  protected abstract int getRatType();

  // { ULR-Flags }
  protected abstract long getUlrFlags();

  // [ UE-SRVCC-Capability ]
  protected abstract int getUeSRVCCCapability();

  // [ SGSN-Number ]
  protected abstract byte[] getSgsnNumber();

  // [ Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions ]
  protected abstract int getHomogeneousSupportOfIMSVoiceOverPsSessions();

  // [ GMLC-Address ]
  protected abstract InetAddress getGMLCAddress();

  // *[ Active-APN ]
  protected abstract long getContextIdentifier();
  protected abstract String getServiceSelection();
  protected abstract InetAddress getMIPHomeAgentAddress();
  protected abstract String getMIPHomeAgentHostDestRealm();
  protected abstract String getMIPHomeAgentHostDestHost();
  protected abstract byte[] getVisitedNetworkIdentifier();
  protected abstract String getSpecificApnServiceSelection();
  protected abstract InetAddress getSpecificApnMIPHomeAgentAddress();
  protected abstract String getSpecificApnMIPHomeAgentHostDestRealm();
  protected abstract String getSpecificApnMIPHomeAgentHostDestHost();
  protected abstract byte[] getSpecificApnVisitedNetworkIdentifier();

  // [ MME-Number-for-MT-SMS ]
  protected abstract byte[] getMMENumberForMtSMS();

  // [ SMS-Register-Request ]
  protected abstract int getSMSRegisterRequest();

  // [ SGs-MME-Identity ]
  protected abstract String getSGsMMEIdentity();

  // [ Coupled-Node-Diameter-ID ]
  protected abstract String getCoupledNodeDiameterId();

  // [ Supported-Services ]
  protected abstract long getSupportedMonitoringEvents();

  // [ SF-ULR-Timestamp ]
  protected abstract Time getSfUlrTimestamp();

  // [ SF-Provisional-Indication ]
  protected abstract int getSfProvisionalIndication();

  // [ IMS-Voice-Over-PS-Sessions-Supported ]
  protected abstract int getIMSVoiceOverPSSessionsSupported();

  // [ Last-UE-Activity-Time ]
  protected abstract Time getLastUEActivityTime();

  // [ IDA-Flags ]
  protected abstract long getIDAFlags();

  // [ EPS-User-State ]
  protected abstract int getMMEUserState();
  protected abstract int getSGSNUserState();

  // [ EPS-Location-Information ]
  protected abstract byte[] getEUtranCellGlobalIdentity();
  protected abstract byte[] getTrackingAreaIdentity();
  protected abstract byte[] getGeographicalInformation();
  protected abstract byte[] getGeodeticInformation();
  protected abstract int getCurrentLocationRetrieved();
  protected abstract long getAgeOfLocationInformation();
  protected abstract long getCSGId();
  protected abstract int getCSGAccessMode();
  protected abstract int getCSGMembershipIndication();
  protected abstract byte[] getENodeBId();
  protected abstract byte[] getExtendedENodeBId();
  protected abstract byte[] getCellGlobalIdentity();
  protected abstract byte[] getLocationAreaIdentity();
  protected abstract byte[] getServiceAreaIdentity();
  protected abstract byte[] getRoutingAreaIdentity();

  // [ Local-Time-Zone ]
  protected abstract String getTimeZone();
  protected abstract int getDaylightSavingTime();

  // *[ Monitoring-Event-Config-Status ]
  protected abstract long getServiceResultCode();
  protected abstract long getNodeType();
  protected abstract long getSCEFReferenceID();
  protected abstract long getSCEFReferenceIDExt();
  protected abstract String getSCEFId();

  // [ DSA-Flags ]
  protected abstract long getDSAFlags();

  // [ PUR-Flags ]
  protected abstract long getPURFlags();

  // [ Alert-Reason ]
  protected abstract int getAlertReason();

  // [ NOR-Flags ]
  protected abstract long getNORFlags();

  // [ Maximum-UE-Availability-Time ]
  protected abstract Time getMaximumUEAvailabilityTime();

  // [ Emergency-Services ]
  protected abstract long getEmergencyServices();

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.5

    The Authentication-Information-Request (AIR) command, indicated by the Command-Code field set to 318
    and the 'R' bit set in the Command Flags field, is sent from MME or SGSN to HSS.

    Message Format
    < Authentication-Information-Request > ::= < Diameter Header: 318, REQ, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ Destination-Host ]
                                        { Destination-Realm }
                                        { User-Name }
                                        [ OC-Supported-Features ]
                                       *[ Supported-Features ]
                                        [ Requested-EUTRAN-Authentication-Info ]
                                        [ Requested-UTRAN-GERAN-Authentication-Info ]
                                        { Visited-PLMN-Id }
                                        [ AIR-Flags ]
                                       *[ AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JAuthenticationInformationRequest createAIR(ClientS6aSession clientS6aSession) throws Exception {
    JAuthenticationInformationRequest air = new JAuthenticationInformationRequestImpl(clientS6aSession.getSessions().get(0).
        createRequest(JAuthenticationInformationRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = air.getMessage().getAvps();

    // [ Vendor-Specific-Application-Id ]
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
    // { Origin-Realm }

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, serverHost, true);
    // { Destination-Realm }
    // getServerRealmName()

    // { User-Name }
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ Requested-EUTRAN-Authentication-Info ]
    AvpSet requestedEutranAuthenticationInfo = reqSet.addGroupedAvp(Avp.REQUESTED_EUTRAN_AUTHENTICATION_INFO, 10415, true, false);
    if (getEUTRANNumberOfRequestedVectors() > -1)
      requestedEutranAuthenticationInfo.addAvp(Avp.NUMBER_OF_REQUESTED_VECTORS, getEUTRANNumberOfRequestedVectors(), 10415, true, false, true);
    if (getEUTRANImmediateResponsePreferred() > -1)
      requestedEutranAuthenticationInfo.addAvp(Avp.IMMEDIATE_RESPONSE_PREFERRED, getEUTRANImmediateResponsePreferred(), 10415, true, false, true);
    if (getEUTRANReSynchronizationInfo() != null)
      requestedEutranAuthenticationInfo.addAvp(Avp.RE_SYNCHRONIZATION_INFO, getEUTRANReSynchronizationInfo(), 10415, true, false);

    // [ Requested-UTRAN-GERAN-Authentication-Info ]
    AvpSet requestedUtranGeranAuthenticationInfo = reqSet.addGroupedAvp(Avp.REQUESTED_UTRAN_GERAN_AUTHENTICATION_INFO, 10415, true, false);
    if (getNumberOfRequestedVectors() > -1)
      requestedUtranGeranAuthenticationInfo.addAvp(Avp.NUMBER_OF_REQUESTED_VECTORS, getNumberOfRequestedVectors(), 10415, true, false, true);
    if (getImmediateResponsePreferred() > -1)
      requestedUtranGeranAuthenticationInfo.addAvp(Avp.IMMEDIATE_RESPONSE_PREFERRED, getImmediateResponsePreferred(), 10415, true, false, true);
    if (getReSynchronizationInfo() != null)
      requestedUtranGeranAuthenticationInfo.addAvp(Avp.RE_SYNCHRONIZATION_INFO, getReSynchronizationInfo(), 10415, true, false);

    // { Visited-PLMN-Id }
    if (getVisitedPLMNId() != null)
      reqSet.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);

    // [ AIR-Flags ]
    if (getAirFlags() > -1)
      reqSet.addAvp(Avp.AIR_FLAGS, getAirFlags(), 10415, false, false, true);

    return air;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.3

    The Update-Location-Request (ULR) command, indicated by the Command-Code field set to 316
    and the "R" bit set in the Command Flags field, is sent from MME or SGSN to HSS.

    Message Format
    < Update-Location-Request > ::= < Diameter Header: 316, REQ, PXY, 16777251 >
                             < Session-Id >
                             [ DRMP ]
                             [ Vendor-Specific-Application-Id ]
                             { Auth-Session-State }
                             { Origin-Host }
                             { Origin-Realm }
                             [ Destination-Host ]
                             { Destination-Realm }
                             { User-Name }
                             [ OC-Supported-Features ]
                            *[ Supported-Features ]
                             [ Terminal-Information ]
                             { RAT-Type }
                             { ULR-Flags }
                             [ UE-SRVCC-Capability ]
                             { Visited-PLMN-Id }
                             [ SGSN-Number ]
                             [ Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions ]
                             [ GMLC-Address ]
                            *[ Active-APN ]
                             [ Equivalent-PLMN-List ]
                             [ MME-Number-for-MT-SMS ]
                             [ SMS-Register-Request ]
                             [ SGs-MME-Identity ]
                             [ Coupled-Node-Diameter-ID ]
                             [ Adjacent-PLMNs ]
                             [ Supported-Services ]
                             [ SF-ULR-Timestamp ]
                             [ SF-Provisional-Indication ]
                             *[ AVP ]
                             *[ Proxy-Info ]
                             *[ Route-Record ]
   */
  protected JUpdateLocationRequest createULR(ClientS6aSession clientS6aSession) throws Exception {
    JUpdateLocationRequest ulr = new JUpdateLocationRequestImpl(clientS6aSession.getSessions().get(0).
        createRequest(JUpdateLocationRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = ulr.getMessage().getAvps();

    // [ Vendor-Specific-Application-Id ]
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
    // { Origin-Realm }

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, serverHost, true);
    // { Destination-Realm }
    // getServerRealmName()

    // { User-Name }
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ Terminal-Information ]
    AvpSet terminalInformation = reqSet.addGroupedAvp(Avp.TERMINAL_INFORMATION, 10415, true, false);
    if (getIMEI() != null)
      terminalInformation.addAvp(Avp.TGPP_IMEI, getIMEI(), 10415, false, false, false);
    if (get3gpp2MEID() != null)
      terminalInformation.addAvp(Avp.TGPP2_MEID, get3gpp2MEID(), 10415, false, false, true);
    if (getSoftwareVersion() != null)
      terminalInformation.addAvp(Avp.SOFTWARE_VERSION, getSoftwareVersion(), 10415, false, false, false);

    // { RAT-Type }
    if (getRatType() > -1)
      reqSet.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);

    // { ULR-Flags }
    if (getUlrFlags() > -1)
      reqSet.addAvp(Avp.ULR_FLAGS, getUlrFlags(),10415, true, false, true);

    // [ UE-SRVCC-Capability ]
    if (getUeSRVCCCapability() > -1)
      reqSet.addAvp(Avp.UE_SRVCC_CAPABILITY, getUeSRVCCCapability(), 10415, false, false);

    // { Visited-PLMN-Id }
    if (getVisitedPLMNId() != null)
      reqSet.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);

    // [ SGSN-Number ]
    if (getSgsnNumber() != null)
      reqSet.addAvp(Avp.SGSN_NUMBER, getSgsnNumber(), 10415, false, false);

    // [ Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions ]
    if (getHomogeneousSupportOfIMSVoiceOverPsSessions() > -1)
      reqSet.addAvp(Avp.HOMOGENEOUS_SUPPORT_OF_IMS_VOICE_OVER_PS_SESSIONS, getHomogeneousSupportOfIMSVoiceOverPsSessions(), 10415, false, false);

    // [ GMLC-Address ]
    if (getGMLCAddress() != null)
      reqSet.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, true, false);

    // *[ Active-APN ]
    AvpSet activeApn = reqSet.addGroupedAvp(Avp.ACTIVE_APN, 10415, true, false);
    if (getContextIdentifier() > -1)
      activeApn.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getServiceSelection() != null)
      activeApn.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet mip6AgentInfo = activeApn.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getVisitedNetworkIdentifier() != null)
      activeApn.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);
    AvpSet specificApnInfo = activeApn.addGroupedAvp(Avp.SPECIFIC_APN_INFO, 10415, true, false);
    if (getSpecificApnServiceSelection() != null)
      specificApnInfo.addAvp(Avp.SERVICE_SELECTION, getSpecificApnServiceSelection(), 0, true, false, false);
    AvpSet specificApnMip6AgentInfo = specificApnInfo.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getSpecificApnMIPHomeAgentAddress() != null)
      specificApnMip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getSpecificApnMIPHomeAgentAddress(), 0, true, false);
    AvpSet specificApnMipHomeAgentHost = specificApnMip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getSpecificApnMIPHomeAgentHostDestRealm() != null)
      specificApnMipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getSpecificApnMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getSpecificApnMIPHomeAgentHostDestHost() != null)
      specificApnMipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getSpecificApnMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getSpecificApnVisitedNetworkIdentifier() != null)
      specificApnInfo.addAvp(Avp.VISITED_NETWORK_ID, getSpecificApnVisitedNetworkIdentifier(), 10415, true, false);

    // [ Equivalent-PLMN-List ]
    AvpSet equivalentPLMNList = reqSet.addGroupedAvp(Avp.EQUIVALENT_PLMN_LIST, 10415, true, false);
    if (getVisitedPLMNId() != null)
      equivalentPLMNList.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);

    // [ MME-Number-for-MT-SMS ]
    if (getMMENumberForMtSMS() != null)
      reqSet.addAvp(Avp.MME_NUMBER_FOR_MT_SMS, getMMENumberForMtSMS(), 10415, false, false);

    // [ SMS-Register-Request ]
    if (getSMSRegisterRequest() > -1)
      reqSet.addAvp(Avp.SMS_REGISTER_REQUEST, getSMSRegisterRequest(), 10415, false, false);

    // [ SGs-MME-Identity ]
    if (getSGsMMEIdentity() != null)
      reqSet.addAvp(Avp.SGS_MME_IDENTITY, getSGsMMEIdentity(), 10415, false, false, false);

    // [ Coupled-Node-Diameter-ID ]
    if (getCoupledNodeDiameterId() != null)
      reqSet.addAvp(Avp.COUPLED_NODE_DIAMETER_ID, getCoupledNodeDiameterId(), 10415, false, false, false);

    // [ Adjacent-PLMNs ]
    AvpSet adjacentPLMNs = reqSet.addGroupedAvp(Avp.ADJACENT_PLMNS, 10415, false, false);
    if (getVisitedPLMNId() != null)
      adjacentPLMNs.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);

    // [ Supported-Services ]
    AvpSet supportedServices = reqSet.addGroupedAvp(Avp.SUPPORTED_SERVICES, 10415, true, false);
    if (getSupportedMonitoringEvents() > -1)
      supportedServices.addAvp(Avp.SUPPORTED_MONITORING_EVENTS, getSupportedMonitoringEvents(), 10415, true, false);

    // [ SF-ULR-Timestamp ]
    if (getSfUlrTimestamp() != null)
      reqSet.addAvp(Avp.SF_ULR_TIMESTAMP, getSfUlrTimestamp(), 10415, false, false);

    // [ SF-Provisional-Indication ]
    if (getSfProvisionalIndication() > -1)
      reqSet.addAvp(Avp.SF_PROVISIONAL_INDICATION, getSfProvisionalIndication(), 10415, false, false, true);

    return ulr;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.8

    The Cancel-Location-Answer (CLA) command, indicated by the Command-Code field set to 317
    and the 'R' bit cleared in the Command Flags field, is sent from MME or SGSN to HSS.

    Message Format
    < Cancel-Location-Answer> ::= < Diameter Header: 317, PXY, 16777251 >
                             < Session-Id >
                             [ DRMP ]
                             [ Vendor-Specific-Application-Id ]
                            *[ Supported-Features ]
                             [ Result-Code ]
                             [ Experimental-Result ]
                             { Auth-Session-State }
                             { Origin-Host }
                             { Origin-Realm }
                             *[ AVP ]
                             [ Failed-AVP ]
                             *[ Proxy-Info ]
                             *[ Route-Record ]
   */
  protected JCancelLocationAnswer createCLA(JCancelLocationRequest clr, long resultCode) throws Exception {
    // < Cancel-Location-Answer> ::= < Diameter Header: 317, PXY, 16777251 >
    JCancelLocationAnswer cla = new JCancelLocationAnswerImpl((Request) clr.getMessage(), resultCode);

    AvpSet reqSet = clr.getMessage().getAvps();
    AvpSet avpSet = cla.getMessage().getAvps();
    avpSet.removeAvp(Avp.DESTINATION_HOST);
    avpSet.removeAvp(Avp.DESTINATION_REALM);
    avpSet.addAvp(reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // { Vendor-Specific-Application-Id }
    if (avpSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = avpSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // [ Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (avpSet.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      avpSet.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    return cla;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.10

    The Insert-Subscriber-Data-Answer (IDA) command, indicated by the Command-Code field set to 319
    and the 'R' bit cleared in the Command Flags field, is sent from MME or SGSN to HSS or CSS.

    Message Format when used over the S6a or S6d application:
    < Insert-Subscriber-Data-Answer > ::= < Diameter Header: 319, PXY, 16777251 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                  *[ Supported-Features ]
                                   [ Result-Code ]
                                   [ Experimental-Result ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ IMS-Voice-Over-PS-Sessions-Supported ]
                                   [ Last-UE-Activity-Time ]
                                   [ RAT-Type ]
                                   [ IDA-Flags ]
                                   [ EPS-User-State ]
                                   [ EPS-Location-Information ]
                                   [ Local-Time-Zone ]
                                   [ Supported-Services ]
                                  *[ Monitoring-Event-Report ]
                                  *[ Monitoring-Event-Config-Status ]
                                  *[ AVP ]
                                   [ Failed-AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected JInsertSubscriberDataAnswer createIDA(JInsertSubscriberDataRequest idr, long resultCode) throws Exception {
    // < Insert-Subscriber-Data-Answer > ::= < Diameter Header: 319, PXY, 16777251 >
    JInsertSubscriberDataAnswer ida = new JInsertSubscriberDataAnswerImpl((Request) idr.getMessage(), resultCode);

    AvpSet reqSet = idr.getMessage().getAvps();
    AvpSet avpSet = ida.getMessage().getAvps();
    avpSet.removeAvp(Avp.DESTINATION_HOST);
    avpSet.removeAvp(Avp.DESTINATION_REALM);
    avpSet.addAvp(reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // { Vendor-Specific-Application-Id }
    if (avpSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = avpSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // [ Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (avpSet.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      avpSet.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);


    // [ IMS-Voice-Over-PS-Sessions-Supported ]
    if (getIMSVoiceOverPSSessionsSupported() > -1)
      avpSet.addAvp(Avp.IMS_VOICE_OVER_PS_SESSIONS_SUPPORTED, getIMSVoiceOverPSSessionsSupported(), 10415, false, false);

    // [ Last-UE-Activity-Time ]
    if (getLastUEActivityTime() != null)
      avpSet.addAvp(Avp.LAST_UE_ACTIVITY_TIME, getLastUEActivityTime(), 10415, false, false);

    // [ RAT-Type ]
    if (getRatType() > -1)
      avpSet.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);

    // [ IDA-Flags ]
    if (getIDAFlags() > -1)
      avpSet.addAvp(Avp.IDA_FLAGS, getIDAFlags(), 10415, true, false, true);

    // [ EPS-User-State ]
    AvpSet epsUserState = avpSet.addGroupedAvp(Avp.EPS_USER_STATE, 10415, false, false);
    AvpSet mmeUserState = epsUserState.addGroupedAvp(Avp.MME_USER_STATE, 10415, false, false);
    if (getMMEUserState() > -1)
      mmeUserState.addAvp(Avp.USER_STATE, getMMEUserState(), 10415, false, false);
    AvpSet sgsnUserState = epsUserState.addGroupedAvp(Avp.SGSN_USER_STATE, 10415, false, false);
    if (getSGSNUserState() > -1)
      sgsnUserState.addAvp(Avp.USER_STATE, getSGSNUserState(), 10415, false, false);

    // [ EPS-Location-Information ]
    AvpSet epsLocationInformation = avpSet.addGroupedAvp(Avp.EPS_LOCATION_INFORMATION, 10415, true, false);
    AvpSet mmeLocationInformation = epsLocationInformation.addGroupedAvp(Avp.MME_LOCATION_INFORMATION, 10415, true, true);
    AvpSet sgsnLocationInformation = epsLocationInformation.addGroupedAvp(Avp.SGSN_LOCATION_INFORMATION, 10415, true, true);
    if (getEUtranCellGlobalIdentity() != null) {
      mmeLocationInformation.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    }
    if (getTrackingAreaIdentity() != null) {
      mmeLocationInformation.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    }
    if (getGeographicalInformation() != null) {
      mmeLocationInformation.addAvp(Avp.GEOGRAPHICAL_INFORMATION, getGeographicalInformation(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.GEOGRAPHICAL_INFORMATION, getGeographicalInformation(), 10415, false, true);
    }
    if (getGeodeticInformation() != null) {
      mmeLocationInformation.addAvp(Avp.GEODETIC_INFORMATION, getGeodeticInformation(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.GEODETIC_INFORMATION, getGeodeticInformation(), 10415, false, true);
    }
    if (getCurrentLocationRetrieved() != -1) {
      mmeLocationInformation.addAvp(Avp.CURRENT_LOCATION_RETRIEVED, getCurrentLocationRetrieved(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.CURRENT_LOCATION_RETRIEVED, getCurrentLocationRetrieved(), 10415, false, true);
    }
    if (getAgeOfLocationInformation() != -1) {
      mmeLocationInformation.addAvp(Avp.AGE_OF_LOCATION_INFORMATION, getAgeOfLocationInformation(), 10415, false, true, true);
      sgsnLocationInformation.addAvp(Avp.AGE_OF_LOCATION_INFORMATION, getAgeOfLocationInformation(), 10415, false, true, true);
    }
    AvpSet userCSGInformationMme = mmeLocationInformation.addGroupedAvp(Avp.USER_CSG_INFORMATION, 10415, true, true);
    AvpSet userCSGInformationSgsn = sgsnLocationInformation.addGroupedAvp(Avp.USER_CSG_INFORMATION, 10415, true, true);
    if (getCSGId() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_ID, getCSGId(), 10415, false, true, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_ID, getCSGId(), 10415, false, true, true);
    }
    if (getCSGAccessMode() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_ACCESS_MODE, getCSGAccessMode(), 10415, false, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_ACCESS_MODE, getCSGAccessMode(), 10415, false, true);
    }
    if (getCSGMembershipIndication() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_MEMBERSHIP_INDICATION, getCSGMembershipIndication(), 10415, false, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_MEMBERSHIP_INDICATION, getCSGMembershipIndication(), 10415, false, true);
    }
    if (getENodeBId() != null)
      mmeLocationInformation.addAvp(Avp.E_NODE_B_ID, getENodeBId(), 10415, false, true);
    if (getExtendedENodeBId() != null)
      mmeLocationInformation.addAvp(Avp.EXTENDED_E_NODE_B_ID, getExtendedENodeBId(), 10415, false, true);
    if (getCellGlobalIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getServiceAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);

    // [ Local-Time-Zone ]
    AvpSet localTimeZone = avpSet.addGroupedAvp(Avp.LOCAL_TIME_ZONE, 10415, false, false);
    if (getTimeZone() != null)
      localTimeZone.addAvp(Avp.TIME_ZONE, getTimeZone(), 10415, false, false, false);
    if (getDaylightSavingTime() > -1)
      localTimeZone.addAvp(Avp.DAYLIGHT_SAVING_TIME, getDaylightSavingTime(), 10415, false, false);

    // [ Supported-Services ]
    AvpSet supportedServices = avpSet.addGroupedAvp(Avp.SUPPORTED_SERVICES, 10415, true, false);
    if (getSupportedMonitoringEvents() > -1)
      supportedServices.addAvp(Avp.SUPPORTED_MONITORING_EVENTS, getSupportedMonitoringEvents(), 10415, true, false);

    // *[ Monitoring-Event-Report ]
    if (getSupportedMonitoringEvents() > -1)
      avpSet.addAvp(Avp.SUPPORTED_MONITORING_EVENTS, getSupportedMonitoringEvents(), 10415, true, false);

    // *[ Monitoring-Event-Config-Status ]
    AvpSet monitoringEventConfigStatus = avpSet.addGroupedAvp(Avp.MONITORING_EVENT_CONFIG_STATUS, 10415, true, false);
    AvpSet serviceReport = monitoringEventConfigStatus.addGroupedAvp(Avp.SERVICE_REPORT, 10415, true, false);
    AvpSet serviceResult = serviceReport.addGroupedAvp(Avp.SERVICE_RESULT, 10415, true, false);
    if (getVendorId() > -1)
      serviceResult.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getServiceResultCode() > -1)
      serviceResult.addAvp(Avp.SERVICE_RESULT_CODE, getServiceResultCode(), 10415, true, false, true);
    if (getNodeType() > -1)
      serviceReport.addAvp(Avp.NODE_TYPE, getNodeType(), 10415, true, false, true);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);

    return ida;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.12

    The Delete-Subscriber Data-Answer (DSA) command, indicated by the Command-Code field set to 320
    and the 'R' bit cleared in the Command Flags field, is sent from MME or SGSN to HSS or CSS.

    Message Format when used over the S6a or S6d application:
    < Delete-Subscriber-Data-Answer > ::= < Diameter Header: 320, PXY, 16777251 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                  *[ Supported-Features ]
                                   [ Result-Code ]
                                   [ Experimental-Result ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ DSA-Flags ]
                                  *[ AVP ]
                                   [ Failed-AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected JDeleteSubscriberDataAnswer createDSA(JDeleteSubscriberDataRequest dsr, long resultCode) throws Exception {
    // < Delete-Subscriber-Data-Answer > ::= < Diameter Header: 320, PXY, 16777251 >
    JDeleteSubscriberDataAnswer dsa = new JDeleteSubscriberDataAnswerImpl((Request) dsr.getMessage(), resultCode);

    AvpSet reqSet = dsr.getMessage().getAvps();
    AvpSet avpSet = dsa.getMessage().getAvps();
    avpSet.removeAvp(Avp.DESTINATION_HOST);
    avpSet.removeAvp(Avp.DESTINATION_REALM);
    avpSet.addAvp(reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // { Vendor-Specific-Application-Id }
    if (avpSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = avpSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // [ Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (avpSet.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      avpSet.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ DSA-Flags ]
    if (getDSAFlags() > -1)
      avpSet.addAvp(Avp.DSA_FLAGS, getDSAFlags(), 10415, true, false, true);

    return dsa;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.13

    The Purge-UE-Request (PUR) command, indicated by the Command-Code field set to 321
    and the 'R' bit set in the Command Flags field, is sent from MME or SGSN to HSS.

    Message Format
    < Purge-UE-Request > ::= < Diameter Header: 321, REQ, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ Destination-Host ]
                                        { Destination-Realm }
                                        { User-Name }
                                        [ OC-Supported-Features ]
                                        [ PUR-Flags ]
                                       *[ Supported-Features ]
                                        [ EPS-Location-Information ]
                                       *[ AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JPurgeUERequest createPUR(ClientS6aSession clientS6aSession) throws Exception {
    JPurgeUERequest pur = new JPurgeUERequestImpl(clientS6aSession.getSessions().get(0).
        createRequest(JPurgeUERequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = pur.getMessage().getAvps();

    // [ Vendor-Specific-Application-Id ]
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
    // { Origin-Realm }

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, serverHost, true);
    // { Destination-Realm }
    // getServerRealmName())

    // { User-Name }
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ PUR-Flags ]
    if (getPURFlags() > -1)
      reqSet.addAvp(Avp.PUR_FLAGS, getPURFlags(), 10415, false, false, true);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ EPS-Location-Information ]
    AvpSet epsLocationInformation = reqSet.addGroupedAvp(Avp.EPS_LOCATION_INFORMATION, 10415, true, false);
    AvpSet mmeLocationInformation = epsLocationInformation.addGroupedAvp(Avp.MME_LOCATION_INFORMATION, 10415, true, true);
    AvpSet sgsnLocationInformation = epsLocationInformation.addGroupedAvp(Avp.SGSN_LOCATION_INFORMATION, 10415, true, true);
    if (getEUtranCellGlobalIdentity() != null) {
      mmeLocationInformation.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    }
    if (getTrackingAreaIdentity() != null) {
      mmeLocationInformation.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    }
    if (getGeographicalInformation() != null) {
      mmeLocationInformation.addAvp(Avp.GEOGRAPHICAL_INFORMATION, getGeographicalInformation(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.GEOGRAPHICAL_INFORMATION, getGeographicalInformation(), 10415, false, true);
    }
    if (getGeodeticInformation() != null) {
      mmeLocationInformation.addAvp(Avp.GEODETIC_INFORMATION, getGeodeticInformation(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.GEODETIC_INFORMATION, getGeodeticInformation(), 10415, false, true);
    }
    if (getCurrentLocationRetrieved() != -1) {
      mmeLocationInformation.addAvp(Avp.CURRENT_LOCATION_RETRIEVED, getCurrentLocationRetrieved(), 10415, false, true);
      sgsnLocationInformation.addAvp(Avp.CURRENT_LOCATION_RETRIEVED, getCurrentLocationRetrieved(), 10415, false, true);
    }
    if (getAgeOfLocationInformation() != -1) {
      mmeLocationInformation.addAvp(Avp.AGE_OF_LOCATION_INFORMATION, getAgeOfLocationInformation(), 10415, false, true, true);
      sgsnLocationInformation.addAvp(Avp.AGE_OF_LOCATION_INFORMATION, getAgeOfLocationInformation(), 10415, false, true, true);
    }
    AvpSet userCSGInformationMme = mmeLocationInformation.addGroupedAvp(Avp.USER_CSG_INFORMATION, 10415, true, true);
    AvpSet userCSGInformationSgsn = sgsnLocationInformation.addGroupedAvp(Avp.USER_CSG_INFORMATION, 10415, true, true);
    if (getCSGId() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_ID, getCSGId(), 10415, false, true, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_ID, getCSGId(), 10415, false, true, true);
    }
    if (getCSGAccessMode() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_ACCESS_MODE, getCSGAccessMode(), 10415, false, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_ACCESS_MODE, getCSGAccessMode(), 10415, false, true);
    }
    if (getCSGMembershipIndication() != -1) {
      userCSGInformationMme.addAvp(Avp.CSG_MEMBERSHIP_INDICATION, getCSGMembershipIndication(), 10415, false, true);
      userCSGInformationSgsn.addAvp(Avp.CSG_MEMBERSHIP_INDICATION, getCSGMembershipIndication(), 10415, false, true);
    }
    if (getENodeBId() != null)
      mmeLocationInformation.addAvp(Avp.E_NODE_B_ID, getENodeBId(), 10415, false, true);
    if (getExtendedENodeBId() != null)
      mmeLocationInformation.addAvp(Avp.EXTENDED_E_NODE_B_ID, getExtendedENodeBId(), 10415, false, true);
    if (getCellGlobalIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getServiceAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      sgsnLocationInformation.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);

    return pur;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.16

    The Reset-Answer (RSA) command, indicated by the Command-Code field set to 322
    and the 'R' bit cleared in the Command Flags field, is sent from MME or SGSN to HSS or CSS.

    Message Format when used over the S6a or S6d application:
    < Reset-Answer > ::= < Diameter Header: 322, PXY, 16777251 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                  *[ Supported-Features ]
                                   [ Result-Code ]
                                   [ Experimental-Result ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                  *[ AVP ]
                                   [ Failed-AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected JResetAnswer createRSA(JResetRequest rsr, long resultCode) throws Exception {
    // < Reset-Answer > ::= < Diameter Header: 322, PXY, 16777251 >
    JResetAnswer rsa = new JResetAnswerImpl((Request) rsr.getMessage(), resultCode);

    AvpSet reqSet = rsr.getMessage().getAvps();
    AvpSet avpSet = rsa.getMessage().getAvps();
    avpSet.removeAvp(Avp.DESTINATION_HOST);
    avpSet.removeAvp(Avp.DESTINATION_REALM);
    avpSet.addAvp(reqSet.getAvp(Avp.AUTH_APPLICATION_ID));

    // { Vendor-Specific-Application-Id }
    if (avpSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = avpSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // [ Result-Code ]
    // [ Experimental-Result ]
    // { Auth-Session-State }
    if (avpSet.getAvp(Avp.AUTH_SESSION_STATE) == null) {
      avpSet.addAvp(Avp.AUTH_SESSION_STATE, 1);
    }

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    return rsa;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.17

    The Notify-Request (NOR) command, indicated by the Command-Code field set to 323
    and the 'R' bit set in the Command Flags field, is sent from MME or SGSN to HSS.

    Message Format
    < Notify-Request > ::= < Diameter Header: 323, REQ, PXY, 16777251 >
                                        < Session-Id >
                                        [ Vendor-Specific-Application-Id ]
                                        [ DRMP ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ Destination-Host ]
                                        { Destination-Realm }
                                        { User-Name }
                                        [ OC-Supported-Features ]
                                       *[ Supported-Features ]
                                        [ Terminal-Information ]
                                        [ MIP6-Agent-Info ]
                                        [ Visited-Network-Identifier ]
                                        [ Context-Identifier ]
                                        [ Service-Selection ]
                                        [ Alert-Reason ]
                                        [ UE-SRVCC-Capability ]
                                        [ NOR-Flags ]
                                        [ Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions ]
                                        [ Maximum-UE-Availability-Time ]
                                       *[ Monitoring-Event-Config-Status ]
                                        [ Emergency-Services ]
                                       *[ AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JNotifyRequest createNOR(ClientS6aSession clientS6aSession) throws Exception {
    JNotifyRequest nor = new JNotifyRequestImpl(clientS6aSession.getSessions().get(0).
        createRequest(JNotifyRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = nor.getMessage().getAvps();

    // [ Vendor-Specific-Application-Id ]
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
    // { Origin-Realm }

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, serverHost, true);
    // { Destination-Realm }
    // getServerRealmName())

    // { User-Name }
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ Terminal-Information ]
    AvpSet terminalInformation = reqSet.addGroupedAvp(Avp.TERMINAL_INFORMATION, 10415, true, false);
    if (getIMEI() != null)
      terminalInformation.addAvp(Avp.TGPP_IMEI, getIMEI(), 10415, false, false, false);
    if (get3gpp2MEID() != null)
      terminalInformation.addAvp(Avp.TGPP2_MEID, get3gpp2MEID(), 10415, false, false, true);
    if (getSoftwareVersion() != null)
      terminalInformation.addAvp(Avp.SOFTWARE_VERSION, getSoftwareVersion(), 10415, false, false, false);

    // [ MIP6-Agent-Info ]
    AvpSet mip6AgentInfo = reqSet.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d

    // [ Visited-Network-Identifier ]
    if (getVisitedNetworkIdentifier() != null)
      reqSet.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);

    // [ Context-Identifier ]
    if (getContextIdentifier() > -1)
      reqSet.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);

    // [ Service-Selection ]
    if (getServiceSelection() != null)
      reqSet.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);

    // [ Alert-Reason ]
    if (getAlertReason() > -1)
      reqSet.addAvp(Avp.ALERT_REASON, getAlertReason(), 10415, true, false);

    // [ UE-SRVCC-Capability ]
    if (getUeSRVCCCapability() > -1)
      reqSet.addAvp(Avp.UE_SRVCC_CAPABILITY, getUeSRVCCCapability(), 10415, false, false);

    // [ NOR-Flags ]
    if (getNORFlags() >-1)
      reqSet.addAvp(Avp.NOR_FLAGS, getNORFlags(), 10415, true, false, true);

    // [ Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions ]
    if (getHomogeneousSupportOfIMSVoiceOverPsSessions() > -1)
      reqSet.addAvp(Avp.HOMOGENEOUS_SUPPORT_OF_IMS_VOICE_OVER_PS_SESSIONS, getHomogeneousSupportOfIMSVoiceOverPsSessions(), 10415, false, false);

    // [ Maximum-UE-Availability-Time ]
    if (getMaximumUEAvailabilityTime() != null)
      reqSet.addAvp(Avp.MAXIMUM_UE_AVAILABILITY_TIME, getMaximumUEAvailabilityTime(), 10415, false, false);

    // *[ Monitoring-Event-Config-Status ]
    AvpSet monitoringEventConfigStatus = reqSet.addGroupedAvp(Avp.MONITORING_EVENT_CONFIG_STATUS, 10415, true, false);
    AvpSet serviceReport = monitoringEventConfigStatus.addGroupedAvp(Avp.SERVICE_REPORT, 10415, true, false);
    AvpSet serviceResult = serviceReport.addGroupedAvp(Avp.SERVICE_RESULT, 10415, true, false);
    if (getVendorId() > -1)
      serviceResult.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getServiceResultCode() > -1)
      serviceResult.addAvp(Avp.SERVICE_RESULT_CODE, getServiceResultCode(), 10415, true, false, true);
    if (getNodeType() > -1)
      serviceReport.addAvp(Avp.NODE_TYPE, getNodeType(), 10415, true, false, true);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfigStatus.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);

    // [ Emergency-Services ]
    if (getEmergencyServices() > -1)
      reqSet.addAvp(Avp.EMERGENCY_SERVICES, getEmergencyServices(), 10415, false, false, true);

    return nor;
  }

}
