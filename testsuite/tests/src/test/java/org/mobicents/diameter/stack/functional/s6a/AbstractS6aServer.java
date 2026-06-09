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
import org.jdiameter.api.s6a.ServerS6aSession;
import org.jdiameter.api.s6a.ServerS6aSessionListener;
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
import org.jdiameter.common.impl.app.s6a.JAuthenticationInformationAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JCancelLocationRequestImpl;
import org.jdiameter.common.impl.app.s6a.JDeleteSubscriberDataRequestImpl;
import org.jdiameter.common.impl.app.s6a.JInsertSubscriberDataRequestImpl;
import org.jdiameter.common.impl.app.s6a.JNotifyAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JPurgeUEAnswerImpl;
import org.jdiameter.common.impl.app.s6a.JResetRequestImpl;
import org.jdiameter.common.impl.app.s6a.JUpdateLocationAnswerImpl;
import org.jdiameter.common.impl.app.s6a.S6aSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractS6aServer extends TBase implements ServerS6aSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ServerS6aSession serverS6aSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777251));
      S6aSessionFactoryImpl s6SessionFactory = new S6aSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerS6aSession.class, s6SessionFactory);
      sessionFactory.registerAppFacory(ClientS6aSession.class, s6SessionFactory);
      s6SessionFactory.setServerSessionListener(this);
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
  public void doAuthenticationInformationRequestEvent(ServerS6aSession session, JAuthenticationInformationRequest air)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"AIR\" event, request[" + air + "], on session[" + session + "]", null);
  }

  public void doUpdateLocationRequestEvent(ServerS6aSession session, JUpdateLocationRequest ulr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"ULR\" event, request[" + ulr + "], on session[" + session + "]", null);
  }

  public void doCancelLocationAnswerEvent(ServerS6aSession session, JCancelLocationRequest clr, JCancelLocationAnswer cla)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"CLA\" event, request[" + clr + "], answer[" + cla + "], on session[" + session + "]", null);
  }

  public void doInsertSubscriberDataAnswerEvent(ServerS6aSession session, JInsertSubscriberDataRequest idr, JInsertSubscriberDataAnswer ida)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"IDA\" event, request[" + idr + "], answer[" + ida + "], on session[" + session + "]", null);
  }

  public void doDeleteSubscriberDataAnswerEvent(ServerS6aSession session, JDeleteSubscriberDataRequest dsr, JDeleteSubscriberDataAnswer dsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"DSA\" event, request[" + dsr + "], answer[" + dsa + "], on session[" + session + "]", null);
  }

  public void doPurgeUERequestEvent(ServerS6aSession session, JPurgeUERequest pur)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"PUR\" event, request[" + pur + "], on session[" + session + "]", null);
  }

  public void doResetAnswerEvent(ServerS6aSession session, JResetRequest rsr, JResetAnswer rsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"RSA\" event, request[" + rsr + "], answer[" + rsa + "], on session[" + session + "]", null);
  }

  public void doNotifyRequestEvent(ServerS6aSession session, JNotifyRequest nor)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"NOR\" event, request[" + nor + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // -------- conf

  public String getSessionId() {
    return this.serverS6aSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverS6aSession = stack.getSession(sessionId, ServerS6aSession.class);
  }

  public ServerS6aSession getSession() {
    return this.serverS6aSession;
  }

  // Attributes for Authentication-Information-Answer (AIA), Update-Location-Request (ULR),
  // Cancel-Location-Request (CLR), Insert-Subscriber-Data-Request (IDR), Delete-Subscriber-Data-Request (DSR),
  // Purge-UE-Answer (PUA), Reset-Request (RSA), Notify-Answer (NOA)

  // [ OC-Supported-Features ]
  protected abstract long getOCFeatureVector();
  protected abstract String getSourceID();
  protected abstract long getOCPeerAlgo();

  // [ OC-OLR ]
  protected abstract long getOCSequenceNumber();
  protected abstract int getOCReportType();
  protected abstract long getOCReductionPercentage();
  protected abstract long getOCValidityDuration();

  // *[ Supported-Features ]
  protected abstract long getVendorId();
  protected abstract long getFeatureListID();
  protected abstract long getFeatureList();

  // *[ Load ]
  protected abstract int getLoadType();
  protected abstract long getLoadValue();

  // [ Authentication-Info ]
  protected abstract long getEUtranItemNumber();
  protected abstract byte[] getEUtranRAND();
  protected abstract byte[] getEUtranXRES();
  protected abstract byte[] getEUtranAUTN();
  protected abstract byte[] getEUtranKASME();
  protected abstract long getUtranItemNumber();
  protected abstract byte[] getUtranRAND();
  protected abstract byte[] getUtranXRES();
  protected abstract byte[] getUtranAUTN();
  protected abstract byte[] getConfidentialityKey();
  protected abstract byte[] getIntegrityKey();
  protected abstract long getGeranItemNumber();
  protected abstract byte[] getGeranRAND();
  protected abstract byte[] getSRES();
  protected abstract byte[] getKc();

  // [ UE-Usage-Type ]
  protected abstract long getUeUsageType();

  // [ ULA-Flags ]
  protected abstract long getULAFlags();

  // [ Subscription-Data ]
  protected abstract int getSubscriberStatus();
  protected abstract byte[] getMSISDN();
  protected abstract byte[] getAMSISDN();
  protected abstract byte[] getSTNSR();
  protected abstract int getICSIndicator();
  protected abstract int getNetworkAccessMode();
  protected abstract int getOperatorDeterminedBarring();
  protected abstract long getHPLMNODB();
  protected abstract byte[] getRegionalSubscriptionZoneCode();
  protected abstract long getAccessRestrictionData();
  protected abstract String getAPNOiReplacement();
  protected abstract byte[] getGMLCNumber();
  protected abstract byte[] getSSCode();
  protected abstract byte[] getSSStatus();
  protected abstract int getNotificationToUeUser();
  protected abstract byte[] getClientIdentity();
  protected abstract int getGMLCRestriction();
  protected abstract int getPLMNClient();
  protected abstract long getServiceTypeIdentity();
  protected abstract byte[] getTSCode();
  protected abstract String get3GPPChargingCharacteristics();
  protected abstract long getMaxRequestedBandwidthUL();
  protected abstract long getMaxRequestedBandwidthDL();
  protected abstract long getExtendedMaxRequestedBWUL();
  protected abstract long getExtendedMaxRequestedBWDL();
  protected abstract long getContextIdentifier();
  protected abstract long getAdditionalContextIdentifier();
  protected abstract int getThirdContextIdentifier();
  protected abstract int getAllAPNConfigurationsIncludedIndicator();
  protected abstract InetAddress getServedPartyIPAddress();
  protected abstract int getPDNType();
  protected abstract String getServiceSelection();
  protected abstract int getQCI();
  protected abstract long getPriorityLevel();
  protected abstract int getPreemptionCapability();
  protected abstract int getPreemptionVulnerability();
  protected abstract int getVPLMNDynamicAddressAllowed();
  protected abstract InetAddress getMIPHomeAgentAddress();
  protected abstract String getMIPHomeAgentHostDestRealm();
  protected abstract String getMIPHomeAgentHostDestHost();
  protected abstract byte[] getVisitedNetworkIdentifier();
  protected abstract int getPDNGwAllocationType();
  protected abstract String getSpecificApnServiceSelection();
  protected abstract InetAddress getSpecificApnMIPHomeAgentAddress();
  protected abstract String getSpecificApnMIPHomeAgentHostDestRealm();
  protected abstract String getSpecificApnMIPHomeAgentHostDestHost();
  protected abstract byte[] getSpecificApnVisitedNetworkIdentifier();
  protected abstract int getSIPTOPermission();
  protected abstract int getLIPAPermission();
  protected abstract long getRATFrequencySelectionPriorityID();
  protected abstract byte[] getTraceReference();
  protected abstract int getTraceDepth();
  protected abstract byte[] getTraceNETypeList();
  protected abstract byte[] getTraceInterfaceList();
  protected abstract byte[] getTraceEventList();
  protected abstract byte[] getOMCId();
  protected abstract InetAddress getTraceCollectionEntity();
  protected abstract int getJobType();
  protected abstract byte[] getCellGlobalIdentity();
  protected abstract byte[] getEUtranCellGlobalIdentity();
  protected abstract byte[] getRoutingAreaIdentity();
  protected abstract byte[] getLocationAreaIdentity();
  protected abstract byte[] getTrackingAreaIdentity();
  protected abstract byte[] getNRCellGlobalIdentity();
  protected abstract long getListOfMeasurements();
  protected abstract long getReportingTrigger();
  protected abstract int getReportingInterval();
  protected abstract int getReportingAmount();
  protected abstract long getEventThresholdRSRP();
  protected abstract long getEventThresholdRSRQ();
  protected abstract int getLoggingInterval();
  protected abstract int getLoggingDuration();
  protected abstract int getMeasurementPeriodLTE();
  protected abstract int getMeasurementPeriodUMTS();
  protected abstract int getCollectionPeriodRMMLTE();
  protected abstract int getCollectionPeriodRMMUMTS();
  protected abstract byte[] getPositioningMethod();
  protected abstract byte[] getMeasurementQuantity();
  protected abstract int getEventThresholdEvent1F();
  protected abstract int getEventThresholdEvent1I();
  protected abstract byte[] getMDTAllowedPLMNId();
  protected abstract long getMBSFNAreaID();
  protected abstract long getCarrierFrequency();
  protected abstract long getEventThresholdSINR();
  protected abstract int getCollectionPeriodRRMNR();
  protected abstract int getCollectionPeriodM6NR();
  protected abstract int getCollectionPeriodM7NR();
  protected abstract int getSensorMeasurement();
  protected abstract String getTraceReportingConsumerUri();
  protected abstract int getCompleteDataListIncludedIndicator();
  protected abstract byte[] getPDPType();
  protected abstract InetAddress getPDPAddress();
  protected abstract byte[] getQoSSubscribed();
  protected abstract byte[] getExtPDPType();
  protected abstract InetAddress getExtPDPAddress();
  protected abstract long getRestorationPriority();
  protected abstract long getSIPTOLocalNetworkPermission();
  protected abstract int getNonIPDataDeliveryMechanism();
  protected abstract String getSCEFId();
  protected abstract long getCSGId();
  protected abstract Time getExpirationDate();
  protected abstract byte[] getVisitedPLMNId();
  protected abstract int getRoamingRestrictedDueToUnsupportedFeature();
  protected abstract long getSubscribedPeriodicRAUTAUTimer();
  protected abstract long getMPSPriority();
  protected abstract int getVPLMNLIPAAllowed();
  protected abstract int getRelayNodeIndicator();
  protected abstract int getMDTUserConsent();
  protected abstract int getSubscribedVSRVCC();
  protected abstract long getProSePermission();
  protected abstract long getAuthorizedDiscoveryRange();
  protected abstract long getProSeDirectAllowed();
  protected abstract long getSubscriptionDataFlags();
  protected abstract int getDLBufferingSuggestedPacketCount();
  protected abstract long getGroupServiceId();
  protected abstract byte[] getGroupPLMNId();
  protected abstract byte[] getLocalGroupId();
  protected abstract long getSCEFReferenceID();
  protected abstract long getSCEFReferenceIDExt();
  protected abstract long getSCEFReferenceIDForDeletion();
  protected abstract long getSCEFReferenceIDForDeletionExt();
  protected abstract long getPeriodicCommunicationIndicator();
  protected abstract long getCommunicationDurationTime();
  protected abstract long getPeriodicTime();
  protected abstract long getDayOfWeekMask();
  protected abstract long getTimeOfDayStart();
  protected abstract long getTimeOfDayEnd();
  protected abstract long getStationaryIndication();
  protected abstract Time getReferenceIDValidityTime();
  protected abstract int getTrafficProfile();
  protected abstract long getBatteryIndicator();
  protected abstract String getMTCProviderID();
  protected abstract long getMonitoringType();
  protected abstract long getMaximumNumberOfReports();
  protected abstract Time getMonitoringDuration();
  protected abstract String getChargedParty();
  protected abstract long getMaximumDetectionTime();
  protected abstract long getReachabilityType();
  protected abstract long getMaximumLatency();
  protected abstract long getMaximumResponseTime();
  protected abstract long getMONTELocationType();
  protected abstract long getAccuracy();
  protected abstract long getAssociationType();
  protected abstract int getPLMNIdRequested();
  protected abstract String getExternalIdentifier();
  protected abstract long getV2xPermission();
  protected abstract long getUePc5AMBR();
  protected abstract int get5QI();
  protected abstract int getGuaranteedFlowBitrates();
  protected abstract int getMaximumFlowBitrates();
  protected abstract int getPC5Range();
  protected abstract int getPC5LinkAMBR();
  protected abstract int getRatType();
  protected abstract byte[] getEDRXCycleLengthValue();
  protected abstract long getActiveTime();
  protected abstract long getServiceGapTime();
  protected abstract long getBroadcastLocationAssistanceDataTypes();
  protected abstract long getAerialUESubscriptionInformation();
  protected abstract long getCoreNetworkRestrictions();
  protected abstract long getOperationMode();
  protected abstract byte[] getPagingTimeWindowLength();
  protected abstract long getSubscribedARPI();
  protected abstract int getIABOperationPermission();
  protected abstract long getPLMNRATUsageControl();

  // *[ Reset-ID ]
  protected abstract byte[] getResetID();

  // { User-Name }
  protected abstract String getUserName();

  // { Cancellation-Type }
  protected abstract int getCancellationType();

  // [ CLR-Flags ]
  protected abstract long getCLRFlags();

  // [ IDR-Flags]
  protected abstract long getIDRFlags();

  // { DSR-Flags }
  protected abstract long getDSRFlags();

  // { PUA-Flags }
  protected abstract long getPUAFlags();

  // *[ User-Id ]
  protected abstract String getUserId();

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.6

    The Authentication-Information-Answer (AIA) command, indicated by the Command-Code field set to 318
    and the 'R' bit cleared in the Command Flags field, is sent from HSS to MME or SGSN.

    Message Format
    < Authentication-Information-Answer > ::= < Diameter Header: 318, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        [ Result-Code ]
                                        [ Experimental-Result ]
                                        [ Error-Diagnostic ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ OC-Supported-Features ]
                                        [ OC-OLR ]
                                       *[ Load ]
                                       *[ Supported-Features ]
                                        [ Authentication-Info ]
                                        [ UE-Usage-Type ]
                                       *[ AVP ]
                                        [ Failed-AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JAuthenticationInformationAnswer createAIA(JAuthenticationInformationRequest air, long resultCode) throws Exception {
    // < Authentication-Information-Answer > ::= < Diameter Header: 318, PXY, 16777251 >
    JAuthenticationInformationAnswer aia = new JAuthenticationInformationAnswerImpl((Request) air.getMessage(), resultCode);

    AvpSet reqSet = air.getMessage().getAvps();
    AvpSet avpSet = aia.getMessage().getAvps();
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

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = avpSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = avpSet.addGroupedAvp(Avp.OC_OLR, 0, false, false);
    if (getOCSequenceNumber() > -1)
      ocOlr.addAvp(Avp.OC_SEQUENCE_NUMBER, getOCSequenceNumber(), 0, false, false);
    if (getOCReportType() > -1)
      ocOlr.addAvp(Avp.OC_REPORT_TYPE, getOCReportType(), 0, false, false);
    if (getOCReductionPercentage() > -1)
      ocOlr.addAvp(Avp.OC_REDUCTION_PERCENTAGE, getOCReductionPercentage(), 0, false, false, true);
    if (getOCValidityDuration() > -1)
      ocOlr.addAvp(Avp.OC_VALIDITY_DURATION, getOCValidityDuration(), 0, false, false, true);
    if (getSourceID() != null)
      ocOlr.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Load ]
    AvpSet load = avpSet.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ Authentication-Info ]
    AvpSet eutranVector = avpSet.addGroupedAvp(Avp.E_UTRAN_VECTOR, 10415, true, false);
    if (getEUtranItemNumber() > -1)
      eutranVector.addAvp(Avp.ITEM_NUMBER, getEUtranItemNumber(), 10415, true, false, true);
    if (getEUtranRAND() != null)
      eutranVector.addAvp(Avp.RAND, getEUtranRAND(), 10415, true, false);
    if (getEUtranXRES() != null)
      eutranVector.addAvp(Avp.XRES, getEUtranXRES(), 10415, true, false);
    if (getEUtranAUTN() != null)
      eutranVector.addAvp(Avp.AUTN, getEUtranAUTN(), 10415, true, false);
    if (getEUtranKASME() != null)
      eutranVector.addAvp(Avp.KASME, getEUtranKASME(), 10415, true, false);
    AvpSet utranVector = avpSet.addGroupedAvp(Avp.UTRAN_VECTOR, 10415, true, false);
    if (getUtranItemNumber() > -1)
      utranVector.addAvp(Avp.ITEM_NUMBER, getUtranItemNumber(), 10415, true, false, true);
    if (getUtranRAND() != null)
      utranVector.addAvp(Avp.RAND, getUtranRAND(), 10415, true, false);
    if (getUtranXRES() != null)
      utranVector.addAvp(Avp.XRES, getUtranXRES(), 10415, true, false);
    if (getUtranAUTN() != null)
      utranVector.addAvp(Avp.AUTN, getUtranAUTN(), 10415, true, false);
    if (getConfidentialityKey() != null)
      utranVector.addAvp(Avp.CONFIDENTIALITY_KEY, getConfidentialityKey(), 10415, true, false);
    if (getIntegrityKey() != null)
      utranVector.addAvp(Avp.INTEGRITY_KEY, getIntegrityKey(), 10415, true, false);
    AvpSet geranVector = avpSet.addGroupedAvp(Avp.GERAN_VECTOR, 10415, true, false);
    if (getGeranItemNumber() > -1)
      geranVector.addAvp(Avp.ITEM_NUMBER, getGeranItemNumber(), 10415, true, false, true);
    if (getGeranRAND() != null)
      geranVector.addAvp(Avp.RAND, getGeranRAND(), 10415, true, false);
    if (getSRES() != null)
      geranVector.addAvp(Avp.RAND, getSRES(), 10415, true, false);
    if (getKc() != null)
      geranVector.addAvp(Avp.RAND, getKc(), 10415, true, false);

    // [ UE-Usage-Type ]
    if (getUeUsageType() > -1)
      avpSet.addAvp(Avp.UE_USAGE_TYPE, getUeUsageType(), 10415, false, false, true);

    return aia;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.6

    The Update-Location-Answer (ULA) command, indicated by the Command-Code field set to 316
    and the 'R' bit cleared in the Command Flags field, is sent from HSS to MME or SGSN.

    Message Format
    < Update-Location-Answer >::= < Diameter Header: 316, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        [ Result-Code ]
                                        [ Experimental-Result ]
                                        [ Error-Diagnostic ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ OC-Supported-Features ]
                                        [ OC-OLR ]
                                       *[ Load ]
                                       *[ Supported-Features ]
                                        [ ULA-Flags ]
                                        [ Subscription-Data ]
                                       *[ Reset-ID ]
                                       *[ AVP ]
                                        [ Failed-AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JUpdateLocationAnswer createULA(JUpdateLocationRequest ulr, long resultCode) throws Exception {
    // < Update-Location-Answer >::= < Diameter Header: 316, PXY, 16777251 >
    JUpdateLocationAnswer ula = new JUpdateLocationAnswerImpl((Request) ulr.getMessage(), resultCode);

    AvpSet reqSet = ulr.getMessage().getAvps();
    AvpSet avpSet = ula.getMessage().getAvps();
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

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = avpSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = avpSet.addGroupedAvp(Avp.OC_OLR, 0, false, false);
    if (getOCSequenceNumber() > -1)
      ocOlr.addAvp(Avp.OC_SEQUENCE_NUMBER, getOCSequenceNumber(), 0, false, false);
    if (getOCReportType() > -1)
      ocOlr.addAvp(Avp.OC_REPORT_TYPE, getOCReportType(), 0, false, false);
    if (getOCReductionPercentage() > -1)
      ocOlr.addAvp(Avp.OC_REDUCTION_PERCENTAGE, getOCReductionPercentage(), 0, false, false, true);
    if (getOCValidityDuration() > -1)
      ocOlr.addAvp(Avp.OC_VALIDITY_DURATION, getOCValidityDuration(), 0, false, false, true);
    if (getSourceID() != null)
      ocOlr.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Load ]
    AvpSet load = avpSet.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ ULA-Flags ]
    if (getULAFlags() > -1)
      avpSet.addAvp(Avp.ULA_FLAGS, getULAFlags(), 10415, true, false, true);

    // [ Subscription-Data ]
    AvpSet subscriptionData = avpSet.addGroupedAvp(Avp.SUBSCRIPTION_DATA, 10415, true, false);
    if (getSubscriberStatus() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBER_STATUS, getSubscriberStatus(), 10415, true, false, true);
    if (getMSISDN() != null)
      subscriptionData.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    if (getAMSISDN() != null)
      subscriptionData.addAvp(Avp.A_MSISDN, getAMSISDN(), 10415, true, false);
    if (getSTNSR() != null)
      subscriptionData.addAvp(Avp.STN_SR, getSTNSR(), 10415, true, false);
    if (getICSIndicator() > -1)
      subscriptionData.addAvp(Avp.ICS_INDICATOR, getICSIndicator(), 10415, false, false);
    if (getNetworkAccessMode() > -1)
      subscriptionData.addAvp(Avp.NETWORK_ACCESS_MODE, getNetworkAccessMode(), 10415, true, false);
    if (getOperatorDeterminedBarring() > -1)
      subscriptionData.addAvp(Avp.OPERATOR_DETERMINED_BARRING, getOperatorDeterminedBarring(), 10415, true, false);
    if (getHPLMNODB() > -1)
      subscriptionData.addAvp(Avp.HPLMN_ODB, getHPLMNODB(), 10415, true, false, true);
    if (getRegionalSubscriptionZoneCode() != null)
      subscriptionData.addAvp(Avp.REGIONAL_SUBSCRIPTION_ZONE_CODE, getRegionalSubscriptionZoneCode(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      subscriptionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      subscriptionData.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    AvpSet lcsInfo = subscriptionData.addGroupedAvp(Avp.LCS_INFO, 10415, true, false);
    if (getGMLCNumber() != null)
      lcsInfo.addAvp(Avp.GMLC_NUMBER, getGMLCNumber(), 10415, true, false);
    AvpSet lcsPrivacyException = lcsInfo.addGroupedAvp(Avp.LCS_PRIVACY_EXCEPTION, 10415, true, false);
    if (getSSCode() != null)
      lcsPrivacyException.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      lcsPrivacyException.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      lcsPrivacyException.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet externalClient = lcsPrivacyException.addGroupedAvp(Avp.EXTERNAL_CLIENT, 10415, true, false);
    if (getClientIdentity() != null)
      externalClient.addAvp(Avp.CLIENT_IDENTITY, getClientIdentity(), 10415, true, false);
    if (getGMLCRestriction() > -1)
      externalClient.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      externalClient.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    if (getPLMNClient() > -1)
      lcsPrivacyException.addAvp(Avp.PLMN_CLIENT, getPLMNClient(), 10415, true, false);
    AvpSet serviceType = lcsPrivacyException.addGroupedAvp(Avp.TGPP_SERVICE_TYPE, 10415, true, false);
    if (getServiceTypeIdentity() > -1)
      serviceType.addAvp(Avp.SERVICE_TYPE_IDENTITY, getServiceTypeIdentity(), 10415, true, false, true);
    if (getGMLCRestriction() > -1)
      serviceType.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      serviceType.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet moLR = lcsInfo.addGroupedAvp(Avp.MO_LR, 10415, true, false);
    if (getSSCode() != null)
      moLR.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      moLR.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    AvpSet teleserviceList = subscriptionData.addGroupedAvp(Avp.TELESERVICE_LIST, 10415, true, false);
    if (getTSCode() != null)
      teleserviceList.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);
    AvpSet callBarringInfo = subscriptionData.addGroupedAvp(Avp.CALL_BARRING_INFO, 10415, true, false);
    if (getSSCode() != null)
      callBarringInfo.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      callBarringInfo.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      subscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet ambr = subscriptionData.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet apnConfigurationProfile = subscriptionData.addGroupedAvp(Avp.APN_CONFIGURATION_PROFILE, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getAdditionalContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.ADDITIONAL_CONTEXT_IDENTIFIER, getAdditionalContextIdentifier(), 10415, false, false, true);
    if (getThirdContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.THIRD_CONTEXT_IDENTIFIER, getThirdContextIdentifier(), 10415, false, false);
    if (getAllAPNConfigurationsIncludedIndicator() > -1)
      apnConfigurationProfile.addAvp(Avp.ALL_APN_CONFIGURATIONS_INCLUDED_INDICATOR, getAllAPNConfigurationsIncludedIndicator(), 10415, true, false);
    AvpSet apnConfiguration = apnConfigurationProfile.addGroupedAvp(Avp.APN_CONFIGURATION, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfiguration.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getServedPartyIPAddress() != null)
      apnConfiguration.addAvp(Avp.SERVED_PARTY_IP_ADDRESS, getServedPartyIPAddress(), 10415, true, false);
    if (getPDNType() > -1)
      apnConfiguration.addAvp(Avp.PDN_TYPE, getPDNType(), 10415, true, false);
    if (getServiceSelection() != null)
      apnConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet epsSubscribedQoSProfile = apnConfiguration.addGroupedAvp(Avp.EPS_SUBSCRIBED_QOS_PROFILE, 10415, true, false);
    if (getQCI() > -1)
      epsSubscribedQoSProfile.addAvp(Avp.QOS_CLASS_IDENTIFIER, getQCI(), 10415, true, false);
    AvpSet arp = epsSubscribedQoSProfile.addGroupedAvp(Avp.ALLOCATION_RETENTION_PRIORITY, 10415, true, false);
    if (getPriorityLevel() > -1)
      arp.addAvp(Avp.PRIORITY_LEVEL, getPriorityLevel(), 10415, true, false, true);
    if (getPreemptionCapability() > -1)
      arp.addAvp(Avp.PREEMPTION_CAPABILITY, getPreemptionCapability(), 10415, true, false);
    if (getPreemptionVulnerability() > -1)
      arp.addAvp(Avp.PREEMPTION_VULNERABILITY, getPreemptionVulnerability(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      apnConfiguration.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    AvpSet mip6AgentInfo = apnConfiguration.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getVisitedNetworkIdentifier() != null)
      apnConfiguration.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);
    if (getPDNGwAllocationType() > -1)
      apnConfiguration.addAvp(Avp.PDN_GW_ALLOCATION_TYPE, getPDNGwAllocationType(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      apnConfiguration.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet apnConfigurationAMBR = apnConfiguration.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet specificApnInfo = apnConfiguration.addGroupedAvp(Avp.SPECIFIC_APN_INFO, 10415, true, false);
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
    if (getAPNOiReplacement() != null)
      apnConfiguration.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      apnConfiguration.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      apnConfiguration.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRATFrequencySelectionPriorityID() > -1)
      subscriptionData.addAvp(Avp.RAT_FREQUENCY_SELECTION_PRIORITY_ID, getRATFrequencySelectionPriorityID(), 10415, true, false, true);
    AvpSet traceData = subscriptionData.addGroupedAvp(Avp.TRACE_DATA, 10415, true, false);
    if (getTraceReference() != null)
      traceData.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);
    if (getTraceDepth() > -1)
      traceData.addAvp(Avp.TRACE_DEPTH, getTraceDepth(), 10415, true, false);
    if (getTraceNETypeList() != null)
      traceData.addAvp(Avp.TRACE_NE_TYPE_LIST, getTraceNETypeList(), 10415, true, false);
    if (getTraceInterfaceList() != null)
      traceData.addAvp(Avp.TRACE_INTERFACE_LIST, getTraceInterfaceList(), 10415, true, false);
    if (getTraceEventList() != null)
      traceData.addAvp(Avp.TRACE_EVENT_LIST, getTraceEventList(), 10415, true, false);
    if (getOMCId() != null)
      traceData.addAvp(Avp.OMC_ID, getOMCId(), 10415, true, false);
    if (getTraceCollectionEntity() != null)
      traceData.addAvp(Avp.TRACE_COLLECTION_ENTITY, getTraceCollectionEntity(), 10415, true, false);
    AvpSet mdtConfiguration = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION, 10415, true, false);
    if (getJobType() > -1)
      mdtConfiguration.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScope = mdtConfiguration.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScope.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScope.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScope.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfiguration.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getLoggingInterval() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_INTERVAL, getLoggingInterval(), 10415, false, false);
    if (getLoggingDuration() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_DURATION, getLoggingDuration(), 10415, false, false);
    if (getMeasurementPeriodLTE() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_LTE, getMeasurementPeriodLTE(), 10415, false, false);
    if (getMeasurementPeriodUMTS() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_UMTS, getMeasurementPeriodUMTS(), 10415, false, false);
    if (getCollectionPeriodRMMLTE() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_LTE, getCollectionPeriodRMMLTE(), 10415, false, false);
    if (getCollectionPeriodRMMUMTS() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_UMTS, getCollectionPeriodRMMUMTS(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfiguration.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getMeasurementQuantity() != null)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_QUANTITY, getMeasurementQuantity(), 10415, false, false);
    if (getEventThresholdEvent1F() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1F, getEventThresholdEvent1F(), 10415, false, false, true);
    if (getEventThresholdEvent1I() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1I, getEventThresholdEvent1I(), 10415, false, false, true);
    if (getMDTAllowedPLMNId() != null)
      mdtConfiguration.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    AvpSet mbfsnArea = mdtConfiguration.addGroupedAvp(Avp.MBSFN_AREA, 10415, false, false);
    if (getMBSFNAreaID() > -1)
      mbfsnArea.addAvp(Avp.MBSFN_AREA_ID, getMBSFNAreaID(), 10415, false, false, true);
    if (getCarrierFrequency() > -1)
      mbfsnArea.addAvp(Avp.CARRIER_FREQUENCY, getCarrierFrequency(), 10415, false, false, true);
    AvpSet mdtConfigurationNR = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION_NR, 10415, true, false);
    if (getJobType() > -1)
      mdtConfigurationNR.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScopeNR = mdtConfigurationNR.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfigurationNR.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getEventThresholdSINR() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_SINR, getEventThresholdSINR(), 10415, false, false, true);
    if (getCollectionPeriodRRMNR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_RRM_NR, getCollectionPeriodRRMNR(), 10415, false, false);
    if (getCollectionPeriodM6NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M6_NR, getCollectionPeriodM6NR(), 10415, false, false);
    if (getCollectionPeriodM7NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M7_NR, getCollectionPeriodM7NR(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfigurationNR.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getSensorMeasurement() > -1)
      mdtConfigurationNR.addAvp(Avp.SENSOR_MEASUREMENT, getSensorMeasurement(), 10415, false, false);
    if (getMDTAllowedPLMNId() != null)
      mdtConfigurationNR.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    if (getTraceReportingConsumerUri() != null)
      traceData.addAvp(Avp.TRACE_REPORTING_CONSUMER_URI, getTraceReportingConsumerUri(), 10415, false, false, false);
    AvpSet gprsSubscriptionData = subscriptionData.addGroupedAvp(Avp.GPRS_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCompleteDataListIncludedIndicator() > -1)
      gprsSubscriptionData.addAvp(Avp.COMPLETE_DATA_LIST_INCLUDED_INDICATOR, getCompleteDataListIncludedIndicator(), 10415, true, false);
    AvpSet pdpContext = gprsSubscriptionData.addGroupedAvp(Avp.PDP_CONTEXT, 10415, true, false);
    if (getContextIdentifier() > -1)
      pdpContext.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getPDPType() != null)
      pdpContext.addAvp(Avp.PDP_TYPE, getPDPType(), 10415, true, false);
    if (getPDPAddress() != null)
      pdpContext.addAvp(Avp.PDP_ADDRESS, getPDPAddress(), 10415, true, false);
    if (getQoSSubscribed() != null)
      pdpContext.addAvp(Avp.QOS_SUBSCRIBED, getQoSSubscribed(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      pdpContext.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    if (getServiceSelection() != null)
      pdpContext.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (get3GPPChargingCharacteristics() != null)
      pdpContext.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getExtPDPType() != null)
      pdpContext.addAvp(Avp.EXT_PDP_TYPE, getExtPDPType(), 10415, true, false);
    if (getExtPDPAddress() != null)
      pdpContext.addAvp(Avp.EXT_PDP_ADDRESS, getExtPDPAddress(), 10415, true, false);
    AvpSet ambrGprs = pdpContext.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      pdpContext.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      pdpContext.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRestorationPriority() > -1)
      pdpContext.addAvp(Avp.RESTORATION_PRIORITY, getRestorationPriority(), 10415, false, false, true);
    if (getSIPTOLocalNetworkPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_LOCAL_NETWORK_PERMISSION, getSIPTOLocalNetworkPermission(), 10415, false, false, true);
    if (getNonIPDataDeliveryMechanism() > -1)
      pdpContext.addAvp(Avp.NON_IP_DATA_DELIVERY_MECHANISM, getNonIPDataDeliveryMechanism(), 10415, false, false);
    if (getSCEFId() != null)
      pdpContext.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    AvpSet csgSubscriptionData = gprsSubscriptionData.addGroupedAvp(Avp.CSG_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCSGId() > -1)
      csgSubscriptionData.addAvp(Avp.CSG_ID, getCSGId(), 10415, true, false, true);
    if (getExpirationDate() != null)
      csgSubscriptionData.addAvp(Avp.EXPIRATION_DATE, getExpirationDate(), 10415, true, false);
    if (getServiceSelection() != null)
      csgSubscriptionData.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (getVisitedPLMNId() != null)
      csgSubscriptionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getRoamingRestrictedDueToUnsupportedFeature() > -1)
      subscriptionData.addAvp(Avp.ROAMING_RESTRICTED_DUE_TO_UNSUPPORTED_FEATURE, getRoamingRestrictedDueToUnsupportedFeature(), 10415, true, false);
    if (getSubscribedPeriodicRAUTAUTimer() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_PERIODIC_RAU_TAU_TIMER, getSubscribedPeriodicRAUTAUTimer(), 10415, true, false, true);
    if (getMPSPriority() > -1)
      subscriptionData.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    if (getVPLMNLIPAAllowed() > -1)
      subscriptionData.addAvp(Avp.VPLMN_LIPA_ALLOWED, getVPLMNLIPAAllowed(), 10415, false, false);
    if (getRelayNodeIndicator() > -1)
      subscriptionData.addAvp(Avp.RELAY_NODE_INDICATOR, getRelayNodeIndicator(), 10415, false, false);
    if (getMDTUserConsent() > -1)
      subscriptionData.addAvp(Avp.MDT_USER_CONSENT, getMDTUserConsent(), 10415, false, false);
    if (getSubscribedVSRVCC() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_VSRVCC, getSubscribedVSRVCC(), 10415, false, false);
    AvpSet proSeSubscriptionData = subscriptionData.addGroupedAvp(Avp.PROSE_SUBSCRIPTION_DATA, 10415, true, false);
    if (getProSePermission() > -1)
      proSeSubscriptionData.addAvp(Avp.PROSE_PERMISSION, getProSePermission(), 10415, true, false, true);
    AvpSet proSeAllowedPLMN = proSeSubscriptionData.addGroupedAvp(Avp.PROSE_ALLOWED_PLMN, 10415, true, false);
    if (getVisitedPLMNId() != null)
      proSeAllowedPLMN.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAuthorizedDiscoveryRange() > -1)
      proSeAllowedPLMN.addAvp(Avp.AUTHORIZED_DISCOVERY_RANGE, getAuthorizedDiscoveryRange(), 10415, true, false, true);
    if (getProSeDirectAllowed() > -1)
      proSeAllowedPLMN.addAvp(Avp.PROSE_DIRECT_ALLOWED, getProSeDirectAllowed(), 10415, true, false, true);
    if (get3GPPChargingCharacteristics() != null)
      proSeSubscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getSubscriptionDataFlags() > -1)
      proSeAllowedPLMN.addAvp(Avp.SUBSCRIPTION_DATA_FLAGS, getSubscriptionDataFlags(), 10415, false, false, true);
    AvpSet adjacentAccessRestrictionData = subscriptionData.addGroupedAvp(Avp.ADJACENT_ACCESS_RESTRICTION_DATA, 10415, false, false);
    if (getVisitedPLMNId() != null)
      adjacentAccessRestrictionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      adjacentAccessRestrictionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      subscriptionData.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet imsiGroupId = subscriptionData.addGroupedAvp(Avp.IMSI_GROUP_ID, 10415, false, false);
    if (getGroupServiceId() > -1)
      imsiGroupId.addAvp(Avp.GROUP_SERVICE_ID, getGroupServiceId(), 10415, false, false, true);
    if (getGroupPLMNId() != null)
      imsiGroupId.addAvp(Avp.GROUP_PLMN_ID, getGroupPLMNId(), 10415, false, false);
    if (getLocalGroupId() != null)
      imsiGroupId.addAvp(Avp.LOCAL_GROUP_ID, getLocalGroupId(), 10415, false, false);
    if (getUeUsageType() > -1)
      subscriptionData.addAvp(Avp.UE_USAGE_TYPE, getUeUsageType(), 10415, false, false, true);
    AvpSet aeseCommunicationPattern = subscriptionData.addGroupedAvp(Avp.AESE_COMMUNICATION_PATTERN, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      aeseCommunicationPattern.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getSCEFReferenceIDForDeletion() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    AvpSet communicationPatternSet = aeseCommunicationPattern.addGroupedAvp(Avp.COMMUNICATION_PATTERN_SET, 10415, true, false);
    if (getPeriodicCommunicationIndicator() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_COMMUNICATION_INDICATOR, getPeriodicCommunicationIndicator(), 10415, true, false, true);
    if (getCommunicationDurationTime() > -1)
      communicationPatternSet.addAvp(Avp.COMMUNICATION_DURATION_TIME, getCommunicationDurationTime(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    AvpSet scheduledCommunicationTime = communicationPatternSet.addGroupedAvp(Avp.SCHEDULED_COMMUNICATION_TIME, 10415, true, false);
    if (getDayOfWeekMask() > -1)
      scheduledCommunicationTime.addAvp(Avp.DAY_OF_WEEK_MASK, getDayOfWeekMask(), 0, false, false, true);
    if (getTimeOfDayStart() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_START, getTimeOfDayStart(), 0, false, false, true);
    if (getTimeOfDayEnd() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_END, getTimeOfDayEnd(), 0, false, false, true);
    if (getStationaryIndication() > -1)
      communicationPatternSet.addAvp(Avp.STATIONARY_INDICATION, getStationaryIndication(), 10415, true, false, true);
    if (getReferenceIDValidityTime() != null)
      communicationPatternSet.addAvp(Avp.REFERENCE_ID_VALIDITY_TIME, getReferenceIDValidityTime(), 10415, true, false);
    if (getTrafficProfile() > -1)
      communicationPatternSet.addAvp(Avp.TRAFFIC_PROFILE, getTrafficProfile(), 10415, false, false);
    if (getBatteryIndicator() > -1)
      communicationPatternSet.addAvp(Avp.BATTERY_INDICATOR, getBatteryIndicator(), 10415, false, false, true);
    AvpSet mtcProviderInfo = aeseCommunicationPattern.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mtcProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet monitoringEventConfiguration = subscriptionData.addGroupedAvp(Avp.MONITORING_EVENT_CONFIGURATION, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfiguration.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getMonitoringType() > -1)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_TYPE, getMonitoringType(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletion() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    if (getMaximumNumberOfReports() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_NUMBER_OF_REPORTS, getMaximumNumberOfReports(), 10415, true, false, true);
    if (getMonitoringDuration() != null)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_DURATION, getMonitoringDuration(), 10415, true, false);
    if (getChargedParty() != null)
      monitoringEventConfiguration.addAvp(Avp.CHARGED_PARTY, getChargedParty(), 10415, true, false, false);
    if (getMaximumDetectionTime() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_DETECTION_TIME, getMaximumDetectionTime(), 10415, true, false, true);
    AvpSet ueReachabilityConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.UE_REACHABILITY_CONFIGURATION, 10415, true, false);
    if (getReachabilityType() > -1)
      ueReachabilityConfiguration.addAvp(Avp.REACHABILITY_TYPE, getReachabilityType(), 10415, true, false, true);
    if (getMaximumLatency() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_LATENCY, getMaximumLatency(), 10415, true, false, true);
    if (getMaximumResponseTime() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_RESPONSE_TIME, getMaximumResponseTime(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      ueReachabilityConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet locationInformationConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.LOCATION_INFORMATION_CONFIGURATION, 10415, true, false);
    if (getMONTELocationType() > -1)
      locationInformationConfiguration.addAvp(Avp.MONTE_LOCATION_TYPE, getMONTELocationType(), 10415, true, false, true);
    if (getAccuracy() > -1)
      locationInformationConfiguration.addAvp(Avp.ACCURACY, getAccuracy(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      locationInformationConfiguration.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    if (getAssociationType() > -1)
      monitoringEventConfiguration.addAvp(Avp.ASSOCIATION_TYPE, getAssociationType(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      monitoringEventConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    if (getPLMNIdRequested() > -1)
      monitoringEventConfiguration.addAvp(Avp.PLMN_ID_REQUESTED, getPLMNIdRequested(), 10415, true, false);
    AvpSet mecMTCProviderInfo = monitoringEventConfiguration.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mecMTCProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet pdnConnectivityStatusConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.PDN_CONNECTIVITY_STATUS_CONFIGURATION, 10415, false, false);
    if (getServiceSelection() != null)
      pdnConnectivityStatusConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet excludeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.EXCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      excludeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      excludeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet includeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.INCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      includeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      includeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet emergencyInfo = subscriptionData.addGroupedAvp(Avp.EMERGENCY_INFO, 10415, false, false);
    AvpSet emergencyInfoMIP6AgentInfo = emergencyInfo.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet emergencyInfoMIPHomeAgentHost = emergencyInfoMIP6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    AvpSet v2xSubscriptionData = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionData.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionData.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet v2xSubscriptionDataNR = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA_NR, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet uePC5QoS = v2xSubscriptionDataNR.addGroupedAvp(Avp.UE_PC5_QOS, 10415, false, false);
    AvpSet pc5QoSFlow = uePC5QoS.addGroupedAvp(Avp.PC5_QOS_FLOW, 10415, false, false);
    if (get5QI() > -1)
      pc5QoSFlow.addAvp(Avp._5QI, get5QI(), 10415, false, false);
    AvpSet pc5FlowBitrates = pc5QoSFlow.addGroupedAvp(Avp.PC5_FLOW_BITRATES, 10415, false, false);
    if (getGuaranteedFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.GUARANTEED_FLOW_BITRATES, getGuaranteedFlowBitrates(), 10415, false, false);
    if (getMaximumFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.MAXIMUM_FLOW_BITRATES, getMaximumFlowBitrates(), 10415, false, false);
    if (getPC5Range() > -1)
      pc5QoSFlow.addAvp(Avp.PC5_RANGE, getPC5Range(), 10415, false, false);
    if (getPC5LinkAMBR() > -1)
      uePC5QoS.addAvp(Avp.PC5_LINK_AMBR, getPC5LinkAMBR(), 10415, false, false);
    AvpSet eDRXCycleLength = subscriptionData.addGroupedAvp(Avp.EDRX_CYCLE_LENGTH, 10415, false, false);
    if (getRatType() > -1)
      eDRXCycleLength.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);
    if (getEDRXCycleLengthValue() != null)
      eDRXCycleLength.addAvp(Avp.EDRX_CYCLE_LENGTH_VALUE, getEDRXCycleLengthValue(), 10415, false, false);
    if (getExternalIdentifier() != null)
      subscriptionData.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getActiveTime() > -1)
      subscriptionData.addAvp(Avp.ACTIVE_TIME, getActiveTime(), 10415, false, false, true);
    if (getServiceGapTime() > -1)
      subscriptionData.addAvp(Avp.SERVICE_GAP_TIME, getServiceGapTime(), 10415, false, false, true);
    if (getBroadcastLocationAssistanceDataTypes() > -1)
      subscriptionData.addAvp(Avp.BROADCAST_LOCATION_ASSISTANCE_DATA_TYPES, getBroadcastLocationAssistanceDataTypes(), 10415, false, false, false);
    if (getAerialUESubscriptionInformation() > -1)
      subscriptionData.addAvp(Avp.AERIAL_UE_SUBSCRIPTION_INFORMATION, getAerialUESubscriptionInformation(), 10415, false, false, true);
    if (getCoreNetworkRestrictions() > -1)
      subscriptionData.addAvp(Avp.CORE_NETWORK_RESTRICTIONS, getCoreNetworkRestrictions(), 10415, false, false, true);
    AvpSet pagingTimeWindow = subscriptionData.addGroupedAvp(Avp.PAGING_TIME_WINDOW, 10415, false, false);
    if (getOperationMode() > -1)
      pagingTimeWindow.addAvp(Avp.OPERATION_MODE, getOperationMode(), 10415, false, false, true);
    if (getPagingTimeWindowLength() != null)
      pagingTimeWindow.addAvp(Avp.PAGING_TIME_WINDOW_LENGTH, getPagingTimeWindowLength(), 10415, false, false);
    if (getSubscribedARPI() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_ARPI, getSubscribedARPI(), 10415, false, false, true);
    if (getIABOperationPermission() > -1)
      subscriptionData.addAvp(Avp.IAB_OPERATION_PERMISSION, getIABOperationPermission(), 10415, false, false);
    if (getPLMNRATUsageControl() > -1)
      subscriptionData.addAvp(Avp.PLMN_RAT_USAGE_CONTROL, getPLMNRATUsageControl(), 10415, false, false, true);

    // *[ Reset-ID ]
    if (getResetID() != null)
      avpSet.addAvp(Avp.RESET_ID, getResetID(), 10415, false, false);

    return ula;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.7

    The Cancel-Location-Request (CLR) command, indicated by the Command-Code field set to 317
    and the 'R' bit set in the Command Flags field, is sent from HSS to MME or SGSN.

    Message Format
    < Cancel-Location-Request> ::= < Diameter Header: 317, REQ, PXY, 16777251 >
                             < Session-Id >
                             [ DRMP ]
                             [ Vendor-Specific-Application-Id ]
                             { Auth-Session-State }
                             { Origin-Host }
                             { Origin-Realm }
                             { Destination-Host }
                             { Destination-Realm }
                             { User-Name }
                            *[ Supported-Features ]
                             { Cancellation-Type }
                             [ CLR-Flags ]
                            *[ AVP ]
                            *[ Proxy-Info ]
                            *[ Route-Record ]
   */
  protected JCancelLocationRequest createCLR(ServerS6aSession serverS6aSession) throws Exception {
    JCancelLocationRequest clr = new JCancelLocationRequestImpl(serverS6aSession.getSessions().get(0).
        createRequest(JCancelLocationRequest.code, getApplicationId(), getClientRealmName()));

    AvpSet reqSet = clr.getMessage().getAvps();

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

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // { Cancellation-Type }
    if (getCancellationType() > -1)
      reqSet.addAvp(Avp.CANCELLATION_TYPE, getCancellationType(), 10415, true, false);

    // [ CLR-Flags ]
    if (getCLRFlags() > -1)
      reqSet.addAvp(Avp.CLR_FLAGS, getCLRFlags(), 10415, true, false, true);

    return clr;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.9

    The Insert-Subscriber-Data-Request (IDR) command, indicated by the Command-Code field set to 319
    and the 'R' bit set in the Command Flags field, is sent from HSS or CSS  to MME or SGSN.

    Message Format when used over the S6a or S6d application:
    < Insert-Subscriber-Data-Request > ::= < Diameter Header: 319, REQ, PXY, 16777251 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   { Destination-Host }
                                   { Destination-Realm }
                                   { User-Name }
                                  *[ Supported-Features ]
                                   { Subscription-Data }
                                   [ IDR-Flags ]
                                   *[ Reset-ID ]
                                   *[ AVP ]
                                   *[ Proxy-Info ]
                                   *[ Route-Record ]
   */
  protected JInsertSubscriberDataRequest createIDR(ServerS6aSession serverS6aSession) throws Exception {
    JInsertSubscriberDataRequest idr = new JInsertSubscriberDataRequestImpl(serverS6aSession.getSessions().get(0).
        createRequest(JInsertSubscriberDataRequest.code, getApplicationId(), getClientRealmName()));

    AvpSet reqSet = idr.getMessage().getAvps();

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

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // { Subscription-Data }
    AvpSet subscriptionData = reqSet.addGroupedAvp(Avp.SUBSCRIPTION_DATA, 10415, true, false);
    if (getSubscriberStatus() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBER_STATUS, getSubscriberStatus(), 10415, true, false, true);
    if (getMSISDN() != null)
      subscriptionData.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    if (getAMSISDN() != null)
      subscriptionData.addAvp(Avp.A_MSISDN, getAMSISDN(), 10415, true, false);
    if (getSTNSR() != null)
      subscriptionData.addAvp(Avp.STN_SR, getSTNSR(), 10415, true, false);
    if (getICSIndicator() > -1)
      subscriptionData.addAvp(Avp.ICS_INDICATOR, getICSIndicator(), 10415, false, false);
    if (getNetworkAccessMode() > -1)
      subscriptionData.addAvp(Avp.NETWORK_ACCESS_MODE, getNetworkAccessMode(), 10415, true, false);
    if (getOperatorDeterminedBarring() > -1)
      subscriptionData.addAvp(Avp.OPERATOR_DETERMINED_BARRING, getOperatorDeterminedBarring(), 10415, true, false);
    if (getHPLMNODB() > -1)
      subscriptionData.addAvp(Avp.HPLMN_ODB, getHPLMNODB(), 10415, true, false, true);
    if (getRegionalSubscriptionZoneCode() != null)
      subscriptionData.addAvp(Avp.REGIONAL_SUBSCRIPTION_ZONE_CODE, getRegionalSubscriptionZoneCode(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      subscriptionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      subscriptionData.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    AvpSet lcsInfo = subscriptionData.addGroupedAvp(Avp.LCS_INFO, 10415, true, false);
    if (getGMLCNumber() != null)
      lcsInfo.addAvp(Avp.GMLC_NUMBER, getGMLCNumber(), 10415, true, false);
    AvpSet lcsPrivacyException = lcsInfo.addGroupedAvp(Avp.LCS_PRIVACY_EXCEPTION, 10415, true, false);
    if (getSSCode() != null)
      lcsPrivacyException.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      lcsPrivacyException.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      lcsPrivacyException.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet externalClient = lcsPrivacyException.addGroupedAvp(Avp.EXTERNAL_CLIENT, 10415, true, false);
    if (getClientIdentity() != null)
      externalClient.addAvp(Avp.CLIENT_IDENTITY, getClientIdentity(), 10415, true, false);
    if (getGMLCRestriction() > -1)
      externalClient.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      externalClient.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    if (getPLMNClient() > -1)
      lcsPrivacyException.addAvp(Avp.PLMN_CLIENT, getPLMNClient(), 10415, true, false);
    AvpSet serviceType = lcsPrivacyException.addGroupedAvp(Avp.TGPP_SERVICE_TYPE, 10415, true, false);
    if (getServiceTypeIdentity() > -1)
      serviceType.addAvp(Avp.SERVICE_TYPE_IDENTITY, getServiceTypeIdentity(), 10415, true, false, true);
    if (getGMLCRestriction() > -1)
      serviceType.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      serviceType.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet moLR = lcsInfo.addGroupedAvp(Avp.MO_LR, 10415, true, false);
    if (getSSCode() != null)
      moLR.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      moLR.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    AvpSet teleserviceList = subscriptionData.addGroupedAvp(Avp.TELESERVICE_LIST, 10415, true, false);
    if (getTSCode() != null)
      teleserviceList.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);
    AvpSet callBarringInfo = subscriptionData.addGroupedAvp(Avp.CALL_BARRING_INFO, 10415, true, false);
    if (getSSCode() != null)
      callBarringInfo.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      callBarringInfo.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      subscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet ambr = subscriptionData.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet apnConfigurationProfile = subscriptionData.addGroupedAvp(Avp.APN_CONFIGURATION_PROFILE, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getAdditionalContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.ADDITIONAL_CONTEXT_IDENTIFIER, getAdditionalContextIdentifier(), 10415, false, false, true);
    if (getThirdContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.THIRD_CONTEXT_IDENTIFIER, getThirdContextIdentifier(), 10415, false, false);
    if (getAllAPNConfigurationsIncludedIndicator() > -1)
      apnConfigurationProfile.addAvp(Avp.ALL_APN_CONFIGURATIONS_INCLUDED_INDICATOR, getAllAPNConfigurationsIncludedIndicator(), 10415, true, false);
    AvpSet apnConfiguration = apnConfigurationProfile.addGroupedAvp(Avp.APN_CONFIGURATION, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfiguration.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getServedPartyIPAddress() != null)
      apnConfiguration.addAvp(Avp.SERVED_PARTY_IP_ADDRESS, getServedPartyIPAddress(), 10415, true, false);
    if (getPDNType() > -1)
      apnConfiguration.addAvp(Avp.PDN_TYPE, getPDNType(), 10415, true, false);
    if (getServiceSelection() != null)
      apnConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet epsSubscribedQoSProfile = apnConfiguration.addGroupedAvp(Avp.EPS_SUBSCRIBED_QOS_PROFILE, 10415, true, false);
    if (getQCI() > -1)
      epsSubscribedQoSProfile.addAvp(Avp.QOS_CLASS_IDENTIFIER, getQCI(), 10415, true, false);
    AvpSet arp = epsSubscribedQoSProfile.addGroupedAvp(Avp.ALLOCATION_RETENTION_PRIORITY, 10415, true, false);
    if (getPriorityLevel() > -1)
      arp.addAvp(Avp.PRIORITY_LEVEL, getPriorityLevel(), 10415, true, false, true);
    if (getPreemptionCapability() > -1)
      arp.addAvp(Avp.PREEMPTION_CAPABILITY, getPreemptionCapability(), 10415, true, false);
    if (getPreemptionVulnerability() > -1)
      arp.addAvp(Avp.PREEMPTION_VULNERABILITY, getPreemptionVulnerability(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      apnConfiguration.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    AvpSet mip6AgentInfo = apnConfiguration.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getVisitedNetworkIdentifier() != null)
      apnConfiguration.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);
    if (getPDNGwAllocationType() > -1)
      apnConfiguration.addAvp(Avp.PDN_GW_ALLOCATION_TYPE, getPDNGwAllocationType(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      apnConfiguration.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet apnConfigurationAMBR = apnConfiguration.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet specificApnInfo = apnConfiguration.addGroupedAvp(Avp.SPECIFIC_APN_INFO, 10415, true, false);
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
    if (getAPNOiReplacement() != null)
      apnConfiguration.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      apnConfiguration.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      apnConfiguration.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRATFrequencySelectionPriorityID() > -1)
      subscriptionData.addAvp(Avp.RAT_FREQUENCY_SELECTION_PRIORITY_ID, getRATFrequencySelectionPriorityID(), 10415, true, false, true);
    AvpSet traceData = subscriptionData.addGroupedAvp(Avp.TRACE_DATA, 10415, true, false);
    if (getTraceReference() != null)
      traceData.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);
    if (getTraceDepth() > -1)
      traceData.addAvp(Avp.TRACE_DEPTH, getTraceDepth(), 10415, true, false);
    if (getTraceNETypeList() != null)
      traceData.addAvp(Avp.TRACE_NE_TYPE_LIST, getTraceNETypeList(), 10415, true, false);
    if (getTraceInterfaceList() != null)
      traceData.addAvp(Avp.TRACE_INTERFACE_LIST, getTraceInterfaceList(), 10415, true, false);
    if (getTraceEventList() != null)
      traceData.addAvp(Avp.TRACE_EVENT_LIST, getTraceEventList(), 10415, true, false);
    if (getOMCId() != null)
      traceData.addAvp(Avp.OMC_ID, getOMCId(), 10415, true, false);
    if (getTraceCollectionEntity() != null)
      traceData.addAvp(Avp.TRACE_COLLECTION_ENTITY, getTraceCollectionEntity(), 10415, true, false);
    AvpSet mdtConfiguration = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION, 10415, true, false);
    if (getJobType() > -1)
      mdtConfiguration.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScope = mdtConfiguration.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScope.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScope.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScope.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfiguration.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getLoggingInterval() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_INTERVAL, getLoggingInterval(), 10415, false, false);
    if (getLoggingDuration() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_DURATION, getLoggingDuration(), 10415, false, false);
    if (getMeasurementPeriodLTE() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_LTE, getMeasurementPeriodLTE(), 10415, false, false);
    if (getMeasurementPeriodUMTS() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_UMTS, getMeasurementPeriodUMTS(), 10415, false, false);
    if (getCollectionPeriodRMMLTE() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_LTE, getCollectionPeriodRMMLTE(), 10415, false, false);
    if (getCollectionPeriodRMMUMTS() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_UMTS, getCollectionPeriodRMMUMTS(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfiguration.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getMeasurementQuantity() != null)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_QUANTITY, getMeasurementQuantity(), 10415, false, false);
    if (getEventThresholdEvent1F() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1F, getEventThresholdEvent1F(), 10415, false, false, true);
    if (getEventThresholdEvent1I() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1I, getEventThresholdEvent1I(), 10415, false, false, true);
    if (getMDTAllowedPLMNId() != null)
      mdtConfiguration.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    AvpSet mbfsnArea = mdtConfiguration.addGroupedAvp(Avp.MBSFN_AREA, 10415, false, false);
    if (getMBSFNAreaID() > -1)
      mbfsnArea.addAvp(Avp.MBSFN_AREA_ID, getMBSFNAreaID(), 10415, false, false, true);
    if (getCarrierFrequency() > -1)
      mbfsnArea.addAvp(Avp.CARRIER_FREQUENCY, getCarrierFrequency(), 10415, false, false, true);
    AvpSet mdtConfigurationNR = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION_NR, 10415, true, false);
    if (getJobType() > -1)
      mdtConfigurationNR.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScopeNR = mdtConfigurationNR.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfigurationNR.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getEventThresholdSINR() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_SINR, getEventThresholdSINR(), 10415, false, false, true);
    if (getCollectionPeriodRRMNR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_RRM_NR, getCollectionPeriodRRMNR(), 10415, false, false);
    if (getCollectionPeriodM6NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M6_NR, getCollectionPeriodM6NR(), 10415, false, false);
    if (getCollectionPeriodM7NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M7_NR, getCollectionPeriodM7NR(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfigurationNR.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getSensorMeasurement() > -1)
      mdtConfigurationNR.addAvp(Avp.SENSOR_MEASUREMENT, getSensorMeasurement(), 10415, false, false);
    if (getMDTAllowedPLMNId() != null)
      mdtConfigurationNR.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    if (getTraceReportingConsumerUri() != null)
      traceData.addAvp(Avp.TRACE_REPORTING_CONSUMER_URI, getTraceReportingConsumerUri(), 10415, false, false, false);
    AvpSet gprsSubscriptionData = subscriptionData.addGroupedAvp(Avp.GPRS_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCompleteDataListIncludedIndicator() > -1)
      gprsSubscriptionData.addAvp(Avp.COMPLETE_DATA_LIST_INCLUDED_INDICATOR, getCompleteDataListIncludedIndicator(), 10415, true, false);
    AvpSet pdpContext = gprsSubscriptionData.addGroupedAvp(Avp.PDP_CONTEXT, 10415, true, false);
    if (getContextIdentifier() > -1)
      pdpContext.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getPDPType() != null)
      pdpContext.addAvp(Avp.PDP_TYPE, getPDPType(), 10415, true, false);
    if (getPDPAddress() != null)
      pdpContext.addAvp(Avp.PDP_ADDRESS, getPDPAddress(), 10415, true, false);
    if (getQoSSubscribed() != null)
      pdpContext.addAvp(Avp.QOS_SUBSCRIBED, getQoSSubscribed(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      pdpContext.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    if (getServiceSelection() != null)
      pdpContext.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (get3GPPChargingCharacteristics() != null)
      pdpContext.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getExtPDPType() != null)
      pdpContext.addAvp(Avp.EXT_PDP_TYPE, getExtPDPType(), 10415, true, false);
    if (getExtPDPAddress() != null)
      pdpContext.addAvp(Avp.EXT_PDP_ADDRESS, getExtPDPAddress(), 10415, true, false);
    AvpSet ambrGprs = pdpContext.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      pdpContext.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      pdpContext.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRestorationPriority() > -1)
      pdpContext.addAvp(Avp.RESTORATION_PRIORITY, getRestorationPriority(), 10415, false, false, true);
    if (getSIPTOLocalNetworkPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_LOCAL_NETWORK_PERMISSION, getSIPTOLocalNetworkPermission(), 10415, false, false, true);
    if (getNonIPDataDeliveryMechanism() > -1)
      pdpContext.addAvp(Avp.NON_IP_DATA_DELIVERY_MECHANISM, getNonIPDataDeliveryMechanism(), 10415, false, false);
    if (getSCEFId() != null)
      pdpContext.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    AvpSet csgSubscriptionData = gprsSubscriptionData.addGroupedAvp(Avp.CSG_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCSGId() > -1)
      csgSubscriptionData.addAvp(Avp.CSG_ID, getCSGId(), 10415, true, false, true);
    if (getExpirationDate() != null)
      csgSubscriptionData.addAvp(Avp.EXPIRATION_DATE, getExpirationDate(), 10415, true, false);
    if (getServiceSelection() != null)
      csgSubscriptionData.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (getVisitedPLMNId() != null)
      csgSubscriptionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getRoamingRestrictedDueToUnsupportedFeature() > -1)
      subscriptionData.addAvp(Avp.ROAMING_RESTRICTED_DUE_TO_UNSUPPORTED_FEATURE, getRoamingRestrictedDueToUnsupportedFeature(), 10415, true, false);
    if (getSubscribedPeriodicRAUTAUTimer() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_PERIODIC_RAU_TAU_TIMER, getSubscribedPeriodicRAUTAUTimer(), 10415, true, false, true);
    if (getMPSPriority() > -1)
      subscriptionData.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    if (getVPLMNLIPAAllowed() > -1)
      subscriptionData.addAvp(Avp.VPLMN_LIPA_ALLOWED, getVPLMNLIPAAllowed(), 10415, false, false);
    if (getRelayNodeIndicator() > -1)
      subscriptionData.addAvp(Avp.RELAY_NODE_INDICATOR, getRelayNodeIndicator(), 10415, false, false);
    if (getMDTUserConsent() > -1)
      subscriptionData.addAvp(Avp.MDT_USER_CONSENT, getMDTUserConsent(), 10415, false, false);
    if (getSubscribedVSRVCC() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_VSRVCC, getSubscribedVSRVCC(), 10415, false, false);
    AvpSet proSeSubscriptionData = subscriptionData.addGroupedAvp(Avp.PROSE_SUBSCRIPTION_DATA, 10415, true, false);
    if (getProSePermission() > -1)
      proSeSubscriptionData.addAvp(Avp.PROSE_PERMISSION, getProSePermission(), 10415, true, false, true);
    AvpSet proSeAllowedPLMN = proSeSubscriptionData.addGroupedAvp(Avp.PROSE_ALLOWED_PLMN, 10415, true, false);
    if (getVisitedPLMNId() != null)
      proSeAllowedPLMN.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAuthorizedDiscoveryRange() > -1)
      proSeAllowedPLMN.addAvp(Avp.AUTHORIZED_DISCOVERY_RANGE, getAuthorizedDiscoveryRange(), 10415, true, false, true);
    if (getProSeDirectAllowed() > -1)
      proSeAllowedPLMN.addAvp(Avp.PROSE_DIRECT_ALLOWED, getProSeDirectAllowed(), 10415, true, false, true);
    if (get3GPPChargingCharacteristics() != null)
      proSeSubscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getSubscriptionDataFlags() > -1)
      proSeAllowedPLMN.addAvp(Avp.SUBSCRIPTION_DATA_FLAGS, getSubscriptionDataFlags(), 10415, false, false, true);
    AvpSet adjacentAccessRestrictionData = subscriptionData.addGroupedAvp(Avp.ADJACENT_ACCESS_RESTRICTION_DATA, 10415, false, false);
    if (getVisitedPLMNId() != null)
      adjacentAccessRestrictionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      adjacentAccessRestrictionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      subscriptionData.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet imsiGroupId = subscriptionData.addGroupedAvp(Avp.IMSI_GROUP_ID, 10415, false, false);
    if (getGroupServiceId() > -1)
      imsiGroupId.addAvp(Avp.GROUP_SERVICE_ID, getGroupServiceId(), 10415, false, false, true);
    if (getGroupPLMNId() != null)
      imsiGroupId.addAvp(Avp.GROUP_PLMN_ID, getGroupPLMNId(), 10415, false, false);
    if (getLocalGroupId() != null)
      imsiGroupId.addAvp(Avp.LOCAL_GROUP_ID, getLocalGroupId(), 10415, false, false);
    if (getUeUsageType() > -1)
      subscriptionData.addAvp(Avp.UE_USAGE_TYPE, getUeUsageType(), 10415, false, false, true);
    AvpSet aeseCommunicationPattern = subscriptionData.addGroupedAvp(Avp.AESE_COMMUNICATION_PATTERN, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      aeseCommunicationPattern.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getSCEFReferenceIDForDeletion() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    AvpSet communicationPatternSet = aeseCommunicationPattern.addGroupedAvp(Avp.COMMUNICATION_PATTERN_SET, 10415, true, false);
    if (getPeriodicCommunicationIndicator() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_COMMUNICATION_INDICATOR, getPeriodicCommunicationIndicator(), 10415, true, false, true);
    if (getCommunicationDurationTime() > -1)
      communicationPatternSet.addAvp(Avp.COMMUNICATION_DURATION_TIME, getCommunicationDurationTime(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    AvpSet scheduledCommunicationTime = communicationPatternSet.addGroupedAvp(Avp.SCHEDULED_COMMUNICATION_TIME, 10415, true, false);
    if (getDayOfWeekMask() > -1)
      scheduledCommunicationTime.addAvp(Avp.DAY_OF_WEEK_MASK, getDayOfWeekMask(), 0, false, false, true);
    if (getTimeOfDayStart() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_START, getTimeOfDayStart(), 0, false, false, true);
    if (getTimeOfDayEnd() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_END, getTimeOfDayEnd(), 0, false, false, true);
    if (getStationaryIndication() > -1)
      communicationPatternSet.addAvp(Avp.STATIONARY_INDICATION, getStationaryIndication(), 10415, true, false, true);
    if (getReferenceIDValidityTime() != null)
      communicationPatternSet.addAvp(Avp.REFERENCE_ID_VALIDITY_TIME, getReferenceIDValidityTime(), 10415, true, false);
    if (getTrafficProfile() > -1)
      communicationPatternSet.addAvp(Avp.TRAFFIC_PROFILE, getTrafficProfile(), 10415, false, false);
    if (getBatteryIndicator() > -1)
      communicationPatternSet.addAvp(Avp.BATTERY_INDICATOR, getBatteryIndicator(), 10415, false, false, true);
    AvpSet mtcProviderInfo = aeseCommunicationPattern.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mtcProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet monitoringEventConfiguration = subscriptionData.addGroupedAvp(Avp.MONITORING_EVENT_CONFIGURATION, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfiguration.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getMonitoringType() > -1)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_TYPE, getMonitoringType(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletion() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    if (getMaximumNumberOfReports() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_NUMBER_OF_REPORTS, getMaximumNumberOfReports(), 10415, true, false, true);
    if (getMonitoringDuration() != null)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_DURATION, getMonitoringDuration(), 10415, true, false);
    if (getChargedParty() != null)
      monitoringEventConfiguration.addAvp(Avp.CHARGED_PARTY, getChargedParty(), 10415, true, false, false);
    if (getMaximumDetectionTime() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_DETECTION_TIME, getMaximumDetectionTime(), 10415, true, false, true);
    AvpSet ueReachabilityConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.UE_REACHABILITY_CONFIGURATION, 10415, true, false);
    if (getReachabilityType() > -1)
      ueReachabilityConfiguration.addAvp(Avp.REACHABILITY_TYPE, getReachabilityType(), 10415, true, false, true);
    if (getMaximumLatency() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_LATENCY, getMaximumLatency(), 10415, true, false, true);
    if (getMaximumResponseTime() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_RESPONSE_TIME, getMaximumResponseTime(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      ueReachabilityConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet locationInformationConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.LOCATION_INFORMATION_CONFIGURATION, 10415, true, false);
    if (getMONTELocationType() > -1)
      locationInformationConfiguration.addAvp(Avp.MONTE_LOCATION_TYPE, getMONTELocationType(), 10415, true, false, true);
    if (getAccuracy() > -1)
      locationInformationConfiguration.addAvp(Avp.ACCURACY, getAccuracy(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      locationInformationConfiguration.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    if (getAssociationType() > -1)
      monitoringEventConfiguration.addAvp(Avp.ASSOCIATION_TYPE, getAssociationType(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      monitoringEventConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    if (getPLMNIdRequested() > -1)
      monitoringEventConfiguration.addAvp(Avp.PLMN_ID_REQUESTED, getPLMNIdRequested(), 10415, true, false);
    AvpSet mecMTCProviderInfo = monitoringEventConfiguration.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mecMTCProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet pdnConnectivityStatusConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.PDN_CONNECTIVITY_STATUS_CONFIGURATION, 10415, false, false);
    if (getServiceSelection() != null)
      pdnConnectivityStatusConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet excludeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.EXCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      excludeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      excludeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet includeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.INCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      includeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      includeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet emergencyInfo = subscriptionData.addGroupedAvp(Avp.EMERGENCY_INFO, 10415, false, false);
    AvpSet emergencyInfoMIP6AgentInfo = emergencyInfo.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet emergencyInfoMIPHomeAgentHost = emergencyInfoMIP6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    AvpSet v2xSubscriptionData = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionData.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionData.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet v2xSubscriptionDataNR = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA_NR, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet uePC5QoS = v2xSubscriptionDataNR.addGroupedAvp(Avp.UE_PC5_QOS, 10415, false, false);
    AvpSet pc5QoSFlow = uePC5QoS.addGroupedAvp(Avp.PC5_QOS_FLOW, 10415, false, false);
    if (get5QI() > -1)
      pc5QoSFlow.addAvp(Avp._5QI, get5QI(), 10415, false, false);
    AvpSet pc5FlowBitrates = pc5QoSFlow.addGroupedAvp(Avp.PC5_FLOW_BITRATES, 10415, false, false);
    if (getGuaranteedFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.GUARANTEED_FLOW_BITRATES, getGuaranteedFlowBitrates(), 10415, false, false);
    if (getMaximumFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.MAXIMUM_FLOW_BITRATES, getMaximumFlowBitrates(), 10415, false, false);
    if (getPC5Range() > -1)
      pc5QoSFlow.addAvp(Avp.PC5_RANGE, getPC5Range(), 10415, false, false);
    if (getPC5LinkAMBR() > -1)
      uePC5QoS.addAvp(Avp.PC5_LINK_AMBR, getPC5LinkAMBR(), 10415, false, false);
    AvpSet eDRXCycleLength = subscriptionData.addGroupedAvp(Avp.EDRX_CYCLE_LENGTH, 10415, false, false);
    if (getRatType() > -1)
      eDRXCycleLength.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);
    if (getEDRXCycleLengthValue() != null)
      eDRXCycleLength.addAvp(Avp.EDRX_CYCLE_LENGTH_VALUE, getEDRXCycleLengthValue(), 10415, false, false);
    if (getExternalIdentifier() != null)
      subscriptionData.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getActiveTime() > -1)
      subscriptionData.addAvp(Avp.ACTIVE_TIME, getActiveTime(), 10415, false, false, true);
    if (getServiceGapTime() > -1)
      subscriptionData.addAvp(Avp.SERVICE_GAP_TIME, getServiceGapTime(), 10415, false, false, true);
    if (getBroadcastLocationAssistanceDataTypes() > -1)
      subscriptionData.addAvp(Avp.BROADCAST_LOCATION_ASSISTANCE_DATA_TYPES, getBroadcastLocationAssistanceDataTypes(), 10415, false, false, false);
    if (getAerialUESubscriptionInformation() > -1)
      subscriptionData.addAvp(Avp.AERIAL_UE_SUBSCRIPTION_INFORMATION, getAerialUESubscriptionInformation(), 10415, false, false, true);
    if (getCoreNetworkRestrictions() > -1)
      subscriptionData.addAvp(Avp.CORE_NETWORK_RESTRICTIONS, getCoreNetworkRestrictions(), 10415, false, false, true);
    AvpSet pagingTimeWindow = subscriptionData.addGroupedAvp(Avp.PAGING_TIME_WINDOW, 10415, false, false);
    if (getOperationMode() > -1)
      pagingTimeWindow.addAvp(Avp.OPERATION_MODE, getOperationMode(), 10415, false, false, true);
    if (getPagingTimeWindowLength() != null)
      pagingTimeWindow.addAvp(Avp.PAGING_TIME_WINDOW_LENGTH, getPagingTimeWindowLength(), 10415, false, false);
    if (getSubscribedARPI() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_ARPI, getSubscribedARPI(), 10415, false, false, true);
    if (getIABOperationPermission() > -1)
      subscriptionData.addAvp(Avp.IAB_OPERATION_PERMISSION, getIABOperationPermission(), 10415, false, false);
    if (getPLMNRATUsageControl() > -1)
      subscriptionData.addAvp(Avp.PLMN_RAT_USAGE_CONTROL, getPLMNRATUsageControl(), 10415, false, false, true);

    // [ IDR-Flags ]
    if (getIDRFlags() > -1)
      reqSet.addAvp(Avp.IDR_FLAGS, getIDRFlags(), 10415, true, false, true);

    // *[ Reset-ID ]
    if (getResetID() != null)
      reqSet.addAvp(Avp.RESET_ID, getResetID(), 10415, false, false);

    return idr;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.11

    The Delete-Subscriber Data-Request (DSR) command, indicated by the Command-Code field set to 320
    and the 'R' bit set in the Command Flags field, is sent from HSS or CSS to MME or SGSN.

    Message Format when used over the S6a or S6d application:
    < Delete-Subscriber-Data-Request > ::= < Diameter Header: 320, REQ, PXY, 16777251 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   { Destination-Host }
                                   { Destination-Realm }
                                   { User-Name }
                                  *[ Supported-Features ]
                                   { Subscription-Data }
                                   { DSR-Flags }
                                   [ SCEF-ID ]
                                  *[ Context-Identifier ]
                                   [ Trace-Reference ]
                                  *[ TS-Code ]
                                  *[ SS-Code ]
                                   [ eDRX-Related-RAT ]
                                   *[ External-Identifier ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected JDeleteSubscriberDataRequest createDSR(ServerS6aSession serverS6aSession) throws Exception {
    JDeleteSubscriberDataRequest dsr = new JDeleteSubscriberDataRequestImpl(serverS6aSession.getSessions().get(0).
        createRequest(JDeleteSubscriberDataRequest.code, getApplicationId(), getClientRealmName()));

    AvpSet reqSet = dsr.getMessage().getAvps();

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

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // { Subscription-Data }
    AvpSet subscriptionData = reqSet.addGroupedAvp(Avp.SUBSCRIPTION_DATA, 10415, true, false);
    if (getSubscriberStatus() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBER_STATUS, getSubscriberStatus(), 10415, true, false, true);
    if (getMSISDN() != null)
      subscriptionData.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    if (getAMSISDN() != null)
      subscriptionData.addAvp(Avp.A_MSISDN, getAMSISDN(), 10415, true, false);
    if (getSTNSR() != null)
      subscriptionData.addAvp(Avp.STN_SR, getSTNSR(), 10415, true, false);
    if (getICSIndicator() > -1)
      subscriptionData.addAvp(Avp.ICS_INDICATOR, getICSIndicator(), 10415, false, false);
    if (getNetworkAccessMode() > -1)
      subscriptionData.addAvp(Avp.NETWORK_ACCESS_MODE, getNetworkAccessMode(), 10415, true, false);
    if (getOperatorDeterminedBarring() > -1)
      subscriptionData.addAvp(Avp.OPERATOR_DETERMINED_BARRING, getOperatorDeterminedBarring(), 10415, true, false);
    if (getHPLMNODB() > -1)
      subscriptionData.addAvp(Avp.HPLMN_ODB, getHPLMNODB(), 10415, true, false, true);
    if (getRegionalSubscriptionZoneCode() != null)
      subscriptionData.addAvp(Avp.REGIONAL_SUBSCRIPTION_ZONE_CODE, getRegionalSubscriptionZoneCode(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      subscriptionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      subscriptionData.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    AvpSet lcsInfo = subscriptionData.addGroupedAvp(Avp.LCS_INFO, 10415, true, false);
    if (getGMLCNumber() != null)
      lcsInfo.addAvp(Avp.GMLC_NUMBER, getGMLCNumber(), 10415, true, false);
    AvpSet lcsPrivacyException = lcsInfo.addGroupedAvp(Avp.LCS_PRIVACY_EXCEPTION, 10415, true, false);
    if (getSSCode() != null)
      lcsPrivacyException.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      lcsPrivacyException.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      lcsPrivacyException.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet externalClient = lcsPrivacyException.addGroupedAvp(Avp.EXTERNAL_CLIENT, 10415, true, false);
    if (getClientIdentity() != null)
      externalClient.addAvp(Avp.CLIENT_IDENTITY, getClientIdentity(), 10415, true, false);
    if (getGMLCRestriction() > -1)
      externalClient.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      externalClient.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    if (getPLMNClient() > -1)
      lcsPrivacyException.addAvp(Avp.PLMN_CLIENT, getPLMNClient(), 10415, true, false);
    AvpSet serviceType = lcsPrivacyException.addGroupedAvp(Avp.TGPP_SERVICE_TYPE, 10415, true, false);
    if (getServiceTypeIdentity() > -1)
      serviceType.addAvp(Avp.SERVICE_TYPE_IDENTITY, getServiceTypeIdentity(), 10415, true, false, true);
    if (getGMLCRestriction() > -1)
      serviceType.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      serviceType.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet moLR = lcsInfo.addGroupedAvp(Avp.MO_LR, 10415, true, false);
    if (getSSCode() != null)
      moLR.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      moLR.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    AvpSet teleserviceList = subscriptionData.addGroupedAvp(Avp.TELESERVICE_LIST, 10415, true, false);
    if (getTSCode() != null)
      teleserviceList.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);
    AvpSet callBarringInfo = subscriptionData.addGroupedAvp(Avp.CALL_BARRING_INFO, 10415, true, false);
    if (getSSCode() != null)
      callBarringInfo.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      callBarringInfo.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      subscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet ambr = subscriptionData.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet apnConfigurationProfile = subscriptionData.addGroupedAvp(Avp.APN_CONFIGURATION_PROFILE, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getAdditionalContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.ADDITIONAL_CONTEXT_IDENTIFIER, getAdditionalContextIdentifier(), 10415, false, false, true);
    if (getThirdContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.THIRD_CONTEXT_IDENTIFIER, getThirdContextIdentifier(), 10415, false, false);
    if (getAllAPNConfigurationsIncludedIndicator() > -1)
      apnConfigurationProfile.addAvp(Avp.ALL_APN_CONFIGURATIONS_INCLUDED_INDICATOR, getAllAPNConfigurationsIncludedIndicator(), 10415, true, false);
    AvpSet apnConfiguration = apnConfigurationProfile.addGroupedAvp(Avp.APN_CONFIGURATION, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfiguration.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getServedPartyIPAddress() != null)
      apnConfiguration.addAvp(Avp.SERVED_PARTY_IP_ADDRESS, getServedPartyIPAddress(), 10415, true, false);
    if (getPDNType() > -1)
      apnConfiguration.addAvp(Avp.PDN_TYPE, getPDNType(), 10415, true, false);
    if (getServiceSelection() != null)
      apnConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet epsSubscribedQoSProfile = apnConfiguration.addGroupedAvp(Avp.EPS_SUBSCRIBED_QOS_PROFILE, 10415, true, false);
    if (getQCI() > -1)
      epsSubscribedQoSProfile.addAvp(Avp.QOS_CLASS_IDENTIFIER, getQCI(), 10415, true, false);
    AvpSet arp = epsSubscribedQoSProfile.addGroupedAvp(Avp.ALLOCATION_RETENTION_PRIORITY, 10415, true, false);
    if (getPriorityLevel() > -1)
      arp.addAvp(Avp.PRIORITY_LEVEL, getPriorityLevel(), 10415, true, false, true);
    if (getPreemptionCapability() > -1)
      arp.addAvp(Avp.PREEMPTION_CAPABILITY, getPreemptionCapability(), 10415, true, false);
    if (getPreemptionVulnerability() > -1)
      arp.addAvp(Avp.PREEMPTION_VULNERABILITY, getPreemptionVulnerability(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      apnConfiguration.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    AvpSet mip6AgentInfo = apnConfiguration.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getVisitedNetworkIdentifier() != null)
      apnConfiguration.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);
    if (getPDNGwAllocationType() > -1)
      apnConfiguration.addAvp(Avp.PDN_GW_ALLOCATION_TYPE, getPDNGwAllocationType(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      apnConfiguration.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet apnConfigurationAMBR = apnConfiguration.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet specificApnInfo = apnConfiguration.addGroupedAvp(Avp.SPECIFIC_APN_INFO, 10415, true, false);
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
    if (getAPNOiReplacement() != null)
      apnConfiguration.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      apnConfiguration.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      apnConfiguration.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRATFrequencySelectionPriorityID() > -1)
      subscriptionData.addAvp(Avp.RAT_FREQUENCY_SELECTION_PRIORITY_ID, getRATFrequencySelectionPriorityID(), 10415, true, false, true);
    AvpSet traceData = subscriptionData.addGroupedAvp(Avp.TRACE_DATA, 10415, true, false);
    if (getTraceReference() != null)
      traceData.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);
    if (getTraceDepth() > -1)
      traceData.addAvp(Avp.TRACE_DEPTH, getTraceDepth(), 10415, true, false);
    if (getTraceNETypeList() != null)
      traceData.addAvp(Avp.TRACE_NE_TYPE_LIST, getTraceNETypeList(), 10415, true, false);
    if (getTraceInterfaceList() != null)
      traceData.addAvp(Avp.TRACE_INTERFACE_LIST, getTraceInterfaceList(), 10415, true, false);
    if (getTraceEventList() != null)
      traceData.addAvp(Avp.TRACE_EVENT_LIST, getTraceEventList(), 10415, true, false);
    if (getOMCId() != null)
      traceData.addAvp(Avp.OMC_ID, getOMCId(), 10415, true, false);
    if (getTraceCollectionEntity() != null)
      traceData.addAvp(Avp.TRACE_COLLECTION_ENTITY, getTraceCollectionEntity(), 10415, true, false);
    AvpSet mdtConfiguration = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION, 10415, true, false);
    if (getJobType() > -1)
      mdtConfiguration.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScope = mdtConfiguration.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScope.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScope.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScope.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfiguration.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getLoggingInterval() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_INTERVAL, getLoggingInterval(), 10415, false, false);
    if (getLoggingDuration() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_DURATION, getLoggingDuration(), 10415, false, false);
    if (getMeasurementPeriodLTE() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_LTE, getMeasurementPeriodLTE(), 10415, false, false);
    if (getMeasurementPeriodUMTS() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_UMTS, getMeasurementPeriodUMTS(), 10415, false, false);
    if (getCollectionPeriodRMMLTE() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_LTE, getCollectionPeriodRMMLTE(), 10415, false, false);
    if (getCollectionPeriodRMMUMTS() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_UMTS, getCollectionPeriodRMMUMTS(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfiguration.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getMeasurementQuantity() != null)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_QUANTITY, getMeasurementQuantity(), 10415, false, false);
    if (getEventThresholdEvent1F() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1F, getEventThresholdEvent1F(), 10415, false, false, true);
    if (getEventThresholdEvent1I() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1I, getEventThresholdEvent1I(), 10415, false, false, true);
    if (getMDTAllowedPLMNId() != null)
      mdtConfiguration.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    AvpSet mbfsnArea = mdtConfiguration.addGroupedAvp(Avp.MBSFN_AREA, 10415, false, false);
    if (getMBSFNAreaID() > -1)
      mbfsnArea.addAvp(Avp.MBSFN_AREA_ID, getMBSFNAreaID(), 10415, false, false, true);
    if (getCarrierFrequency() > -1)
      mbfsnArea.addAvp(Avp.CARRIER_FREQUENCY, getCarrierFrequency(), 10415, false, false, true);
    AvpSet mdtConfigurationNR = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION_NR, 10415, true, false);
    if (getJobType() > -1)
      mdtConfigurationNR.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScopeNR = mdtConfigurationNR.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfigurationNR.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getEventThresholdSINR() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_SINR, getEventThresholdSINR(), 10415, false, false, true);
    if (getCollectionPeriodRRMNR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_RRM_NR, getCollectionPeriodRRMNR(), 10415, false, false);
    if (getCollectionPeriodM6NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M6_NR, getCollectionPeriodM6NR(), 10415, false, false);
    if (getCollectionPeriodM7NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M7_NR, getCollectionPeriodM7NR(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfigurationNR.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getSensorMeasurement() > -1)
      mdtConfigurationNR.addAvp(Avp.SENSOR_MEASUREMENT, getSensorMeasurement(), 10415, false, false);
    if (getMDTAllowedPLMNId() != null)
      mdtConfigurationNR.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    if (getTraceReportingConsumerUri() != null)
      traceData.addAvp(Avp.TRACE_REPORTING_CONSUMER_URI, getTraceReportingConsumerUri(), 10415, false, false, false);
    AvpSet gprsSubscriptionData = subscriptionData.addGroupedAvp(Avp.GPRS_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCompleteDataListIncludedIndicator() > -1)
      gprsSubscriptionData.addAvp(Avp.COMPLETE_DATA_LIST_INCLUDED_INDICATOR, getCompleteDataListIncludedIndicator(), 10415, true, false);
    AvpSet pdpContext = gprsSubscriptionData.addGroupedAvp(Avp.PDP_CONTEXT, 10415, true, false);
    if (getContextIdentifier() > -1)
      pdpContext.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getPDPType() != null)
      pdpContext.addAvp(Avp.PDP_TYPE, getPDPType(), 10415, true, false);
    if (getPDPAddress() != null)
      pdpContext.addAvp(Avp.PDP_ADDRESS, getPDPAddress(), 10415, true, false);
    if (getQoSSubscribed() != null)
      pdpContext.addAvp(Avp.QOS_SUBSCRIBED, getQoSSubscribed(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      pdpContext.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    if (getServiceSelection() != null)
      pdpContext.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (get3GPPChargingCharacteristics() != null)
      pdpContext.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getExtPDPType() != null)
      pdpContext.addAvp(Avp.EXT_PDP_TYPE, getExtPDPType(), 10415, true, false);
    if (getExtPDPAddress() != null)
      pdpContext.addAvp(Avp.EXT_PDP_ADDRESS, getExtPDPAddress(), 10415, true, false);
    AvpSet ambrGprs = pdpContext.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      pdpContext.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      pdpContext.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRestorationPriority() > -1)
      pdpContext.addAvp(Avp.RESTORATION_PRIORITY, getRestorationPriority(), 10415, false, false, true);
    if (getSIPTOLocalNetworkPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_LOCAL_NETWORK_PERMISSION, getSIPTOLocalNetworkPermission(), 10415, false, false, true);
    if (getNonIPDataDeliveryMechanism() > -1)
      pdpContext.addAvp(Avp.NON_IP_DATA_DELIVERY_MECHANISM, getNonIPDataDeliveryMechanism(), 10415, false, false);
    if (getSCEFId() != null)
      pdpContext.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    AvpSet csgSubscriptionData = gprsSubscriptionData.addGroupedAvp(Avp.CSG_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCSGId() > -1)
      csgSubscriptionData.addAvp(Avp.CSG_ID, getCSGId(), 10415, true, false, true);
    if (getExpirationDate() != null)
      csgSubscriptionData.addAvp(Avp.EXPIRATION_DATE, getExpirationDate(), 10415, true, false);
    if (getServiceSelection() != null)
      csgSubscriptionData.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (getVisitedPLMNId() != null)
      csgSubscriptionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getRoamingRestrictedDueToUnsupportedFeature() > -1)
      subscriptionData.addAvp(Avp.ROAMING_RESTRICTED_DUE_TO_UNSUPPORTED_FEATURE, getRoamingRestrictedDueToUnsupportedFeature(), 10415, true, false);
    if (getSubscribedPeriodicRAUTAUTimer() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_PERIODIC_RAU_TAU_TIMER, getSubscribedPeriodicRAUTAUTimer(), 10415, true, false, true);
    if (getMPSPriority() > -1)
      subscriptionData.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    if (getVPLMNLIPAAllowed() > -1)
      subscriptionData.addAvp(Avp.VPLMN_LIPA_ALLOWED, getVPLMNLIPAAllowed(), 10415, false, false);
    if (getRelayNodeIndicator() > -1)
      subscriptionData.addAvp(Avp.RELAY_NODE_INDICATOR, getRelayNodeIndicator(), 10415, false, false);
    if (getMDTUserConsent() > -1)
      subscriptionData.addAvp(Avp.MDT_USER_CONSENT, getMDTUserConsent(), 10415, false, false);
    if (getSubscribedVSRVCC() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_VSRVCC, getSubscribedVSRVCC(), 10415, false, false);
    AvpSet proSeSubscriptionData = subscriptionData.addGroupedAvp(Avp.PROSE_SUBSCRIPTION_DATA, 10415, true, false);
    if (getProSePermission() > -1)
      proSeSubscriptionData.addAvp(Avp.PROSE_PERMISSION, getProSePermission(), 10415, true, false, true);
    AvpSet proSeAllowedPLMN = proSeSubscriptionData.addGroupedAvp(Avp.PROSE_ALLOWED_PLMN, 10415, true, false);
    if (getVisitedPLMNId() != null)
      proSeAllowedPLMN.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAuthorizedDiscoveryRange() > -1)
      proSeAllowedPLMN.addAvp(Avp.AUTHORIZED_DISCOVERY_RANGE, getAuthorizedDiscoveryRange(), 10415, true, false, true);
    if (getProSeDirectAllowed() > -1)
      proSeAllowedPLMN.addAvp(Avp.PROSE_DIRECT_ALLOWED, getProSeDirectAllowed(), 10415, true, false, true);
    if (get3GPPChargingCharacteristics() != null)
      proSeSubscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getSubscriptionDataFlags() > -1)
      proSeAllowedPLMN.addAvp(Avp.SUBSCRIPTION_DATA_FLAGS, getSubscriptionDataFlags(), 10415, false, false, true);
    AvpSet adjacentAccessRestrictionData = subscriptionData.addGroupedAvp(Avp.ADJACENT_ACCESS_RESTRICTION_DATA, 10415, false, false);
    if (getVisitedPLMNId() != null)
      adjacentAccessRestrictionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      adjacentAccessRestrictionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      subscriptionData.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet imsiGroupId = subscriptionData.addGroupedAvp(Avp.IMSI_GROUP_ID, 10415, false, false);
    if (getGroupServiceId() > -1)
      imsiGroupId.addAvp(Avp.GROUP_SERVICE_ID, getGroupServiceId(), 10415, false, false, true);
    if (getGroupPLMNId() != null)
      imsiGroupId.addAvp(Avp.GROUP_PLMN_ID, getGroupPLMNId(), 10415, false, false);
    if (getLocalGroupId() != null)
      imsiGroupId.addAvp(Avp.LOCAL_GROUP_ID, getLocalGroupId(), 10415, false, false);
    if (getUeUsageType() > -1)
      subscriptionData.addAvp(Avp.UE_USAGE_TYPE, getUeUsageType(), 10415, false, false, true);
    AvpSet aeseCommunicationPattern = subscriptionData.addGroupedAvp(Avp.AESE_COMMUNICATION_PATTERN, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      aeseCommunicationPattern.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getSCEFReferenceIDForDeletion() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    AvpSet communicationPatternSet = aeseCommunicationPattern.addGroupedAvp(Avp.COMMUNICATION_PATTERN_SET, 10415, true, false);
    if (getPeriodicCommunicationIndicator() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_COMMUNICATION_INDICATOR, getPeriodicCommunicationIndicator(), 10415, true, false, true);
    if (getCommunicationDurationTime() > -1)
      communicationPatternSet.addAvp(Avp.COMMUNICATION_DURATION_TIME, getCommunicationDurationTime(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    AvpSet scheduledCommunicationTime = communicationPatternSet.addGroupedAvp(Avp.SCHEDULED_COMMUNICATION_TIME, 10415, true, false);
    if (getDayOfWeekMask() > -1)
      scheduledCommunicationTime.addAvp(Avp.DAY_OF_WEEK_MASK, getDayOfWeekMask(), 0, false, false, true);
    if (getTimeOfDayStart() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_START, getTimeOfDayStart(), 0, false, false, true);
    if (getTimeOfDayEnd() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_END, getTimeOfDayEnd(), 0, false, false, true);
    if (getStationaryIndication() > -1)
      communicationPatternSet.addAvp(Avp.STATIONARY_INDICATION, getStationaryIndication(), 10415, true, false, true);
    if (getReferenceIDValidityTime() != null)
      communicationPatternSet.addAvp(Avp.REFERENCE_ID_VALIDITY_TIME, getReferenceIDValidityTime(), 10415, true, false);
    if (getTrafficProfile() > -1)
      communicationPatternSet.addAvp(Avp.TRAFFIC_PROFILE, getTrafficProfile(), 10415, false, false);
    if (getBatteryIndicator() > -1)
      communicationPatternSet.addAvp(Avp.BATTERY_INDICATOR, getBatteryIndicator(), 10415, false, false, true);
    AvpSet mtcProviderInfo = aeseCommunicationPattern.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mtcProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet monitoringEventConfiguration = subscriptionData.addGroupedAvp(Avp.MONITORING_EVENT_CONFIGURATION, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfiguration.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getMonitoringType() > -1)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_TYPE, getMonitoringType(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletion() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    if (getMaximumNumberOfReports() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_NUMBER_OF_REPORTS, getMaximumNumberOfReports(), 10415, true, false, true);
    if (getMonitoringDuration() != null)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_DURATION, getMonitoringDuration(), 10415, true, false);
    if (getChargedParty() != null)
      monitoringEventConfiguration.addAvp(Avp.CHARGED_PARTY, getChargedParty(), 10415, true, false, false);
    if (getMaximumDetectionTime() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_DETECTION_TIME, getMaximumDetectionTime(), 10415, true, false, true);
    AvpSet ueReachabilityConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.UE_REACHABILITY_CONFIGURATION, 10415, true, false);
    if (getReachabilityType() > -1)
      ueReachabilityConfiguration.addAvp(Avp.REACHABILITY_TYPE, getReachabilityType(), 10415, true, false, true);
    if (getMaximumLatency() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_LATENCY, getMaximumLatency(), 10415, true, false, true);
    if (getMaximumResponseTime() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_RESPONSE_TIME, getMaximumResponseTime(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      ueReachabilityConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet locationInformationConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.LOCATION_INFORMATION_CONFIGURATION, 10415, true, false);
    if (getMONTELocationType() > -1)
      locationInformationConfiguration.addAvp(Avp.MONTE_LOCATION_TYPE, getMONTELocationType(), 10415, true, false, true);
    if (getAccuracy() > -1)
      locationInformationConfiguration.addAvp(Avp.ACCURACY, getAccuracy(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      locationInformationConfiguration.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    if (getAssociationType() > -1)
      monitoringEventConfiguration.addAvp(Avp.ASSOCIATION_TYPE, getAssociationType(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      monitoringEventConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    if (getPLMNIdRequested() > -1)
      monitoringEventConfiguration.addAvp(Avp.PLMN_ID_REQUESTED, getPLMNIdRequested(), 10415, true, false);
    AvpSet mecMTCProviderInfo = monitoringEventConfiguration.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mecMTCProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet pdnConnectivityStatusConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.PDN_CONNECTIVITY_STATUS_CONFIGURATION, 10415, false, false);
    if (getServiceSelection() != null)
      pdnConnectivityStatusConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet excludeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.EXCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      excludeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      excludeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet includeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.INCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      includeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      includeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet emergencyInfo = subscriptionData.addGroupedAvp(Avp.EMERGENCY_INFO, 10415, false, false);
    AvpSet emergencyInfoMIP6AgentInfo = emergencyInfo.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet emergencyInfoMIPHomeAgentHost = emergencyInfoMIP6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    AvpSet v2xSubscriptionData = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionData.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionData.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet v2xSubscriptionDataNR = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA_NR, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet uePC5QoS = v2xSubscriptionDataNR.addGroupedAvp(Avp.UE_PC5_QOS, 10415, false, false);
    AvpSet pc5QoSFlow = uePC5QoS.addGroupedAvp(Avp.PC5_QOS_FLOW, 10415, false, false);
    if (get5QI() > -1)
      pc5QoSFlow.addAvp(Avp._5QI, get5QI(), 10415, false, false);
    AvpSet pc5FlowBitrates = pc5QoSFlow.addGroupedAvp(Avp.PC5_FLOW_BITRATES, 10415, false, false);
    if (getGuaranteedFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.GUARANTEED_FLOW_BITRATES, getGuaranteedFlowBitrates(), 10415, false, false);
    if (getMaximumFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.MAXIMUM_FLOW_BITRATES, getMaximumFlowBitrates(), 10415, false, false);
    if (getPC5Range() > -1)
      pc5QoSFlow.addAvp(Avp.PC5_RANGE, getPC5Range(), 10415, false, false);
    if (getPC5LinkAMBR() > -1)
      uePC5QoS.addAvp(Avp.PC5_LINK_AMBR, getPC5LinkAMBR(), 10415, false, false);
    AvpSet eDRXCycleLength = subscriptionData.addGroupedAvp(Avp.EDRX_CYCLE_LENGTH, 10415, false, false);
    if (getRatType() > -1)
      eDRXCycleLength.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);
    if (getEDRXCycleLengthValue() != null)
      eDRXCycleLength.addAvp(Avp.EDRX_CYCLE_LENGTH_VALUE, getEDRXCycleLengthValue(), 10415, false, false);
    if (getExternalIdentifier() != null)
      subscriptionData.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getActiveTime() > -1)
      subscriptionData.addAvp(Avp.ACTIVE_TIME, getActiveTime(), 10415, false, false, true);
    if (getServiceGapTime() > -1)
      subscriptionData.addAvp(Avp.SERVICE_GAP_TIME, getServiceGapTime(), 10415, false, false, true);
    if (getBroadcastLocationAssistanceDataTypes() > -1)
      subscriptionData.addAvp(Avp.BROADCAST_LOCATION_ASSISTANCE_DATA_TYPES, getBroadcastLocationAssistanceDataTypes(), 10415, false, false, false);
    if (getAerialUESubscriptionInformation() > -1)
      subscriptionData.addAvp(Avp.AERIAL_UE_SUBSCRIPTION_INFORMATION, getAerialUESubscriptionInformation(), 10415, false, false, true);
    if (getCoreNetworkRestrictions() > -1)
      subscriptionData.addAvp(Avp.CORE_NETWORK_RESTRICTIONS, getCoreNetworkRestrictions(), 10415, false, false, true);
    AvpSet pagingTimeWindow = subscriptionData.addGroupedAvp(Avp.PAGING_TIME_WINDOW, 10415, false, false);
    if (getOperationMode() > -1)
      pagingTimeWindow.addAvp(Avp.OPERATION_MODE, getOperationMode(), 10415, false, false, true);
    if (getPagingTimeWindowLength() != null)
      pagingTimeWindow.addAvp(Avp.PAGING_TIME_WINDOW_LENGTH, getPagingTimeWindowLength(), 10415, false, false);
    if (getSubscribedARPI() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_ARPI, getSubscribedARPI(), 10415, false, false, true);
    if (getIABOperationPermission() > -1)
      subscriptionData.addAvp(Avp.IAB_OPERATION_PERMISSION, getIABOperationPermission(), 10415, false, false);
    if (getPLMNRATUsageControl() > -1)
      subscriptionData.addAvp(Avp.PLMN_RAT_USAGE_CONTROL, getPLMNRATUsageControl(), 10415, false, false, true);

    // { DSR-Flags }
    if (getDSRFlags() > -1)
      reqSet.addAvp(Avp.DSR_FLAGS, getDSRFlags(), 10415, true, false, true);

    // [ SCEF-ID ]
    if (getSCEFId() != null)
      reqSet.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);

    // *[ Context-Identifier ]
    if (getContextIdentifier() > -1)
      reqSet.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);

    // [ Trace-Reference ]
    if (getTraceReference() != null)
      reqSet.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);

    // *[ TS-Code ]
    if (getTSCode() != null)
      reqSet.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);

    // *[ SS-Code ]
    if (getSSCode() != null)
      reqSet.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);

    // [ eDRX-Related-RAT ]
    AvpSet eDRXRelatedRAT = reqSet.addGroupedAvp(Avp.EDRX_RELATED_RAT, 10415, false, false);
    if (getRatType() > -1)
      eDRXRelatedRAT.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);

    // *[ External-Identifier ]
    if (getExternalIdentifier() != null)
      reqSet.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);

    return dsr;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.14

    The Purge-UE-Answer (PUA) command, indicated by the Command-Code field set to 321
    and the 'R' bit cleared in the Command Flags field, is sent from HSS to MME or SGSN.

    Message Format
    < Purge-UE-Answer > ::= < Diameter Header: 321, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                       *[ Supported-Features ]
                                        [ Result-Code ]
                                        [ Experimental-Result ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ OC-Supported-Features ]
                                        [ OC-OLR ]
                                       *[ Load ]
                                        [ PUA-Flags ]
                                       *[ AVP ]
                                        [ Failed-AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JPurgeUEAnswer createPUA(JPurgeUERequest pur, long resultCode) throws Exception {
    // < Purge-UE-Answer > ::= < Diameter Header: 321, PXY, 16777251 >
    JPurgeUEAnswer pua = new JPurgeUEAnswerImpl((Request) pur.getMessage(), resultCode);

    AvpSet reqSet = pur.getMessage().getAvps();
    AvpSet avpSet = pua.getMessage().getAvps();
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

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = avpSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = avpSet.addGroupedAvp(Avp.OC_OLR, 0, false, false);
    if (getOCSequenceNumber() > -1)
      ocOlr.addAvp(Avp.OC_SEQUENCE_NUMBER, getOCSequenceNumber(), 0, false, false);
    if (getOCReportType() > -1)
      ocOlr.addAvp(Avp.OC_REPORT_TYPE, getOCReportType(), 0, false, false);
    if (getOCReductionPercentage() > -1)
      ocOlr.addAvp(Avp.OC_REDUCTION_PERCENTAGE, getOCReductionPercentage(), 0, false, false, true);
    if (getOCValidityDuration() > -1)
      ocOlr.addAvp(Avp.OC_VALIDITY_DURATION, getOCValidityDuration(), 0, false, false, true);
    if (getSourceID() != null)
      ocOlr.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Load ]
    AvpSet load = avpSet.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // [ PUA-Flags ]
    if (getPUAFlags() > -1)
      avpSet.addAvp(Avp.PUA_FLAGS, getPUAFlags(), 10415, true, false, true);

    return pua;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.16

    The Reset-Request (RSR) command, indicated by the Command-Code field set to 322
    and the 'R' bit set in the Command Flags field, is sent from HSS or CSS to MME or SGSN.

    Message Format
    < Reset-Request > ::= < Diameter Header: 322, REQ, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        { Destination-Host }
                                        { Destination-Realm }
                                       *[ Supported-Features ]
                                       *[ User-Id ]
                                       *[ Reset-ID ]
                                       [ Subscription-Data ]
                                       [ Subscription-Data-Deletion ]
                                       *[ AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JResetRequest createRSR(ServerS6aSession serverS6aSession) throws Exception {
    JResetRequest rsr = new JResetRequestImpl(serverS6aSession.getSessions().get(0).
        createRequest(JResetRequest.code, getApplicationId(), getClientRealmName()));

    AvpSet reqSet = rsr.getMessage().getAvps();

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

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // *[ User-Id ]
    if (getUserId() != null)
      reqSet.addAvp(Avp.USER_ID, getUserId(), 10415, false, false, false);

    // *[ Reset-ID ]
    if (getResetID() != null)
      reqSet.addAvp(Avp.RESET_ID, getResetID(), 10415, false, false);

    // [ Subscription-Data ]
    AvpSet subscriptionData = reqSet.addGroupedAvp(Avp.SUBSCRIPTION_DATA, 10415, true, false);
    if (getSubscriberStatus() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBER_STATUS, getSubscriberStatus(), 10415, true, false, true);
    if (getMSISDN() != null)
      subscriptionData.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    if (getAMSISDN() != null)
      subscriptionData.addAvp(Avp.A_MSISDN, getAMSISDN(), 10415, true, false);
    if (getSTNSR() != null)
      subscriptionData.addAvp(Avp.STN_SR, getSTNSR(), 10415, true, false);
    if (getICSIndicator() > -1)
      subscriptionData.addAvp(Avp.ICS_INDICATOR, getICSIndicator(), 10415, false, false);
    if (getNetworkAccessMode() > -1)
      subscriptionData.addAvp(Avp.NETWORK_ACCESS_MODE, getNetworkAccessMode(), 10415, true, false);
    if (getOperatorDeterminedBarring() > -1)
      subscriptionData.addAvp(Avp.OPERATOR_DETERMINED_BARRING, getOperatorDeterminedBarring(), 10415, true, false);
    if (getHPLMNODB() > -1)
      subscriptionData.addAvp(Avp.HPLMN_ODB, getHPLMNODB(), 10415, true, false, true);
    if (getRegionalSubscriptionZoneCode() != null)
      subscriptionData.addAvp(Avp.REGIONAL_SUBSCRIPTION_ZONE_CODE, getRegionalSubscriptionZoneCode(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      subscriptionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      subscriptionData.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    AvpSet lcsInfo = subscriptionData.addGroupedAvp(Avp.LCS_INFO, 10415, true, false);
    if (getGMLCNumber() != null)
      lcsInfo.addAvp(Avp.GMLC_NUMBER, getGMLCNumber(), 10415, true, false);
    AvpSet lcsPrivacyException = lcsInfo.addGroupedAvp(Avp.LCS_PRIVACY_EXCEPTION, 10415, true, false);
    if (getSSCode() != null)
      lcsPrivacyException.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      lcsPrivacyException.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      lcsPrivacyException.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet externalClient = lcsPrivacyException.addGroupedAvp(Avp.EXTERNAL_CLIENT, 10415, true, false);
    if (getClientIdentity() != null)
      externalClient.addAvp(Avp.CLIENT_IDENTITY, getClientIdentity(), 10415, true, false);
    if (getGMLCRestriction() > -1)
      externalClient.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      externalClient.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    if (getPLMNClient() > -1)
      lcsPrivacyException.addAvp(Avp.PLMN_CLIENT, getPLMNClient(), 10415, true, false);
    AvpSet serviceType = lcsPrivacyException.addGroupedAvp(Avp.TGPP_SERVICE_TYPE, 10415, true, false);
    if (getServiceTypeIdentity() > -1)
      serviceType.addAvp(Avp.SERVICE_TYPE_IDENTITY, getServiceTypeIdentity(), 10415, true, false, true);
    if (getGMLCRestriction() > -1)
      serviceType.addAvp(Avp.GMLC_RESTRICTION, getGMLCRestriction(), 10415, true, false);
    if (getNotificationToUeUser() > -1)
      serviceType.addAvp(Avp.NOTIFICATION_TO_UE_USER, getNotificationToUeUser(), 10415, true, false);
    AvpSet moLR = lcsInfo.addGroupedAvp(Avp.MO_LR, 10415, true, false);
    if (getSSCode() != null)
      moLR.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      moLR.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    AvpSet teleserviceList = subscriptionData.addGroupedAvp(Avp.TELESERVICE_LIST, 10415, true, false);
    if (getTSCode() != null)
      teleserviceList.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);
    AvpSet callBarringInfo = subscriptionData.addGroupedAvp(Avp.CALL_BARRING_INFO, 10415, true, false);
    if (getSSCode() != null)
      callBarringInfo.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);
    if (getSSStatus() != null)
      callBarringInfo.addAvp(Avp.SS_STATUS, getSSStatus(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      subscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet ambr = subscriptionData.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambr.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      ambr.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet apnConfigurationProfile = subscriptionData.addGroupedAvp(Avp.APN_CONFIGURATION_PROFILE, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getAdditionalContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.ADDITIONAL_CONTEXT_IDENTIFIER, getAdditionalContextIdentifier(), 10415, false, false, true);
    if (getThirdContextIdentifier() > -1)
      apnConfigurationProfile.addAvp(Avp.THIRD_CONTEXT_IDENTIFIER, getThirdContextIdentifier(), 10415, false, false);
    if (getAllAPNConfigurationsIncludedIndicator() > -1)
      apnConfigurationProfile.addAvp(Avp.ALL_APN_CONFIGURATIONS_INCLUDED_INDICATOR, getAllAPNConfigurationsIncludedIndicator(), 10415, true, false);
    AvpSet apnConfiguration = apnConfigurationProfile.addGroupedAvp(Avp.APN_CONFIGURATION, 10415, true, false);
    if (getContextIdentifier() > -1)
      apnConfiguration.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getServedPartyIPAddress() != null)
      apnConfiguration.addAvp(Avp.SERVED_PARTY_IP_ADDRESS, getServedPartyIPAddress(), 10415, true, false);
    if (getPDNType() > -1)
      apnConfiguration.addAvp(Avp.PDN_TYPE, getPDNType(), 10415, true, false);
    if (getServiceSelection() != null)
      apnConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet epsSubscribedQoSProfile = apnConfiguration.addGroupedAvp(Avp.EPS_SUBSCRIBED_QOS_PROFILE, 10415, true, false);
    if (getQCI() > -1)
      epsSubscribedQoSProfile.addAvp(Avp.QOS_CLASS_IDENTIFIER, getQCI(), 10415, true, false);
    AvpSet arp = epsSubscribedQoSProfile.addGroupedAvp(Avp.ALLOCATION_RETENTION_PRIORITY, 10415, true, false);
    if (getPriorityLevel() > -1)
      arp.addAvp(Avp.PRIORITY_LEVEL, getPriorityLevel(), 10415, true, false, true);
    if (getPreemptionCapability() > -1)
      arp.addAvp(Avp.PREEMPTION_CAPABILITY, getPreemptionCapability(), 10415, true, false);
    if (getPreemptionVulnerability() > -1)
      arp.addAvp(Avp.PREEMPTION_VULNERABILITY, getPreemptionVulnerability(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      apnConfiguration.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    AvpSet mip6AgentInfo = apnConfiguration.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet mipHomeAgentHost = mip6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      mipHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    // The AVP MIP6-Home-Link-Prefix is not used in S6a/S6d
    if (getVisitedNetworkIdentifier() != null)
      apnConfiguration.addAvp(Avp.VISITED_NETWORK_ID, getVisitedNetworkIdentifier(), 10415, true, false);
    if (getPDNGwAllocationType() > -1)
      apnConfiguration.addAvp(Avp.PDN_GW_ALLOCATION_TYPE, getPDNGwAllocationType(), 10415, true, false);
    if (get3GPPChargingCharacteristics() != null)
      apnConfiguration.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    AvpSet apnConfigurationAMBR = apnConfiguration.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getExtendedMaxRequestedBWUL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_UL, getExtendedMaxRequestedBWUL(), 10415, false, false, true);
    if (getExtendedMaxRequestedBWDL() > -1)
      apnConfigurationAMBR.addAvp(Avp.EXTENDED_MAX_REQUESTED_BW_DL, getExtendedMaxRequestedBWDL(), 10415, true, false, true);
    AvpSet specificApnInfo = apnConfiguration.addGroupedAvp(Avp.SPECIFIC_APN_INFO, 10415, true, false);
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
    if (getAPNOiReplacement() != null)
      apnConfiguration.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      apnConfiguration.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      apnConfiguration.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRATFrequencySelectionPriorityID() > -1)
      subscriptionData.addAvp(Avp.RAT_FREQUENCY_SELECTION_PRIORITY_ID, getRATFrequencySelectionPriorityID(), 10415, true, false, true);
    AvpSet traceData = subscriptionData.addGroupedAvp(Avp.TRACE_DATA, 10415, true, false);
    if (getTraceReference() != null)
      traceData.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);
    if (getTraceDepth() > -1)
      traceData.addAvp(Avp.TRACE_DEPTH, getTraceDepth(), 10415, true, false);
    if (getTraceNETypeList() != null)
      traceData.addAvp(Avp.TRACE_NE_TYPE_LIST, getTraceNETypeList(), 10415, true, false);
    if (getTraceInterfaceList() != null)
      traceData.addAvp(Avp.TRACE_INTERFACE_LIST, getTraceInterfaceList(), 10415, true, false);
    if (getTraceEventList() != null)
      traceData.addAvp(Avp.TRACE_EVENT_LIST, getTraceEventList(), 10415, true, false);
    if (getOMCId() != null)
      traceData.addAvp(Avp.OMC_ID, getOMCId(), 10415, true, false);
    if (getTraceCollectionEntity() != null)
      traceData.addAvp(Avp.TRACE_COLLECTION_ENTITY, getTraceCollectionEntity(), 10415, true, false);
    AvpSet mdtConfiguration = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION, 10415, true, false);
    if (getJobType() > -1)
      mdtConfiguration.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScope = mdtConfiguration.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScope.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScope.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScope.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScope.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfiguration.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfiguration.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getLoggingInterval() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_INTERVAL, getLoggingInterval(), 10415, false, false);
    if (getLoggingDuration() > -1)
      mdtConfiguration.addAvp(Avp.LOGGING_DURATION, getLoggingDuration(), 10415, false, false);
    if (getMeasurementPeriodLTE() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_LTE, getMeasurementPeriodLTE(), 10415, false, false);
    if (getMeasurementPeriodUMTS() > -1)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_PERIOD_UMTS, getMeasurementPeriodUMTS(), 10415, false, false);
    if (getCollectionPeriodRMMLTE() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_LTE, getCollectionPeriodRMMLTE(), 10415, false, false);
    if (getCollectionPeriodRMMUMTS() > -1)
      mdtConfiguration.addAvp(Avp.COLLECTION_PERIOD_RRM_UMTS, getCollectionPeriodRMMUMTS(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfiguration.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getMeasurementQuantity() != null)
      mdtConfiguration.addAvp(Avp.MEASUREMENT_QUANTITY, getMeasurementQuantity(), 10415, false, false);
    if (getEventThresholdEvent1F() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1F, getEventThresholdEvent1F(), 10415, false, false, true);
    if (getEventThresholdEvent1I() > -1)
      mdtConfiguration.addAvp(Avp.EVENT_THRESHOLD_EVENT_1I, getEventThresholdEvent1I(), 10415, false, false, true);
    if (getMDTAllowedPLMNId() != null)
      mdtConfiguration.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    AvpSet mbfsnArea = mdtConfiguration.addGroupedAvp(Avp.MBSFN_AREA, 10415, false, false);
    if (getMBSFNAreaID() > -1)
      mbfsnArea.addAvp(Avp.MBSFN_AREA_ID, getMBSFNAreaID(), 10415, false, false, true);
    if (getCarrierFrequency() > -1)
      mbfsnArea.addAvp(Avp.CARRIER_FREQUENCY, getCarrierFrequency(), 10415, false, false, true);
    AvpSet mdtConfigurationNR = traceData.addGroupedAvp(Avp.MDT_CONFIGURATION_NR, 10415, true, false);
    if (getJobType() > -1)
      mdtConfigurationNR.addAvp(Avp.JOB_TYPE, getJobType(), 10415, false, false);
    AvpSet areaScopeNR = mdtConfigurationNR.addGroupedAvp(Avp.AREA_SCOPE, 10415, false, false);
    if (getCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);
    if (getEUtranCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.E_UTRAN_CELL_GLOBAL_IDENTITY, getEUtranCellGlobalIdentity(), 10415, false, true);
    if (getRoutingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    if (getLocationAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    if (getTrackingAreaIdentity() != null)
      areaScopeNR.addAvp(Avp.TRACKING_AREA_IDENTITY, getTrackingAreaIdentity(), 10415, false, true);
    if (getNRCellGlobalIdentity() != null)
      areaScopeNR.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, true);
    if (getListOfMeasurements() > -1)
      mdtConfigurationNR.addAvp(Avp.LIST_OF_MEASUREMENTS, getListOfMeasurements(), 10415, false, false, true);
    if (getReportingTrigger() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_TRIGGER, getReportingTrigger(), 10415, false, false, true);
    if (getReportingInterval() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false);
    if (getReportingAmount() > -1)
      mdtConfigurationNR.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false);
    if (getEventThresholdRSRP() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRP, getEventThresholdRSRP(), 10415, false, false, true);
    if (getEventThresholdRSRQ() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_RSRQ, getEventThresholdRSRQ(), 10415, false, false, true);
    if (getEventThresholdSINR() > -1)
      mdtConfigurationNR.addAvp(Avp.EVENT_THRESHOLD_SINR, getEventThresholdSINR(), 10415, false, false, true);
    if (getCollectionPeriodRRMNR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_RRM_NR, getCollectionPeriodRRMNR(), 10415, false, false);
    if (getCollectionPeriodM6NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M6_NR, getCollectionPeriodM6NR(), 10415, false, false);
    if (getCollectionPeriodM7NR() > -1)
      mdtConfigurationNR.addAvp(Avp.COLLECTION_PERIOD_M7_NR, getCollectionPeriodM7NR(), 10415, false, false);
    if (getPositioningMethod() != null)
      mdtConfigurationNR.addAvp(Avp.POSITIONING_METHOD, getPositioningMethod(), 10415, false, false);
    if (getSensorMeasurement() > -1)
      mdtConfigurationNR.addAvp(Avp.SENSOR_MEASUREMENT, getSensorMeasurement(), 10415, false, false);
    if (getMDTAllowedPLMNId() != null)
      mdtConfigurationNR.addAvp(Avp.MDT_ALLOWED_PLMN_ID, getMDTAllowedPLMNId(), 10415, false, true);
    if (getTraceReportingConsumerUri() != null)
      traceData.addAvp(Avp.TRACE_REPORTING_CONSUMER_URI, getTraceReportingConsumerUri(), 10415, false, false, false);
    AvpSet gprsSubscriptionData = subscriptionData.addGroupedAvp(Avp.GPRS_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCompleteDataListIncludedIndicator() > -1)
      gprsSubscriptionData.addAvp(Avp.COMPLETE_DATA_LIST_INCLUDED_INDICATOR, getCompleteDataListIncludedIndicator(), 10415, true, false);
    AvpSet pdpContext = gprsSubscriptionData.addGroupedAvp(Avp.PDP_CONTEXT, 10415, true, false);
    if (getContextIdentifier() > -1)
      pdpContext.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getPDPType() != null)
      pdpContext.addAvp(Avp.PDP_TYPE, getPDPType(), 10415, true, false);
    if (getPDPAddress() != null)
      pdpContext.addAvp(Avp.PDP_ADDRESS, getPDPAddress(), 10415, true, false);
    if (getQoSSubscribed() != null)
      pdpContext.addAvp(Avp.QOS_SUBSCRIBED, getQoSSubscribed(), 10415, true, false);
    if (getVPLMNDynamicAddressAllowed() > -1)
      pdpContext.addAvp(Avp.VPLMN_DYNAMIC_ADDRESS_ALLOWED, getVPLMNDynamicAddressAllowed(), 10415, true, false);
    if (getServiceSelection() != null)
      pdpContext.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (get3GPPChargingCharacteristics() != null)
      pdpContext.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getExtPDPType() != null)
      pdpContext.addAvp(Avp.EXT_PDP_TYPE, getExtPDPType(), 10415, true, false);
    if (getExtPDPAddress() != null)
      pdpContext.addAvp(Avp.EXT_PDP_ADDRESS, getExtPDPAddress(), 10415, true, false);
    AvpSet ambrGprs = pdpContext.addGroupedAvp(Avp.AMBR, 10415, true, false);
    if (getMaxRequestedBandwidthUL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_UL, getMaxRequestedBandwidthUL(), 10415, true, false, true);
    if (getMaxRequestedBandwidthDL() > -1)
      ambrGprs.addAvp(Avp.MAX_REQUESTED_BANDWIDTH_DL, getMaxRequestedBandwidthDL(), 10415, true, false, true);
    if (getAPNOiReplacement() != null)
      pdpContext.addAvp(Avp.APN_OI_REPLACEMENT, getAPNOiReplacement(), 10415, true, false, false);
    if (getSIPTOPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_PERMISSION, getSIPTOPermission(), 10415, false, false);
    if (getLIPAPermission() > -1)
      pdpContext.addAvp(Avp.LIPA_PERMISSION, getLIPAPermission(), 10415, false, false);
    if (getRestorationPriority() > -1)
      pdpContext.addAvp(Avp.RESTORATION_PRIORITY, getRestorationPriority(), 10415, false, false, true);
    if (getSIPTOLocalNetworkPermission() > -1)
      pdpContext.addAvp(Avp.SIPTO_LOCAL_NETWORK_PERMISSION, getSIPTOLocalNetworkPermission(), 10415, false, false, true);
    if (getNonIPDataDeliveryMechanism() > -1)
      pdpContext.addAvp(Avp.NON_IP_DATA_DELIVERY_MECHANISM, getNonIPDataDeliveryMechanism(), 10415, false, false);
    if (getSCEFId() != null)
      pdpContext.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    AvpSet csgSubscriptionData = gprsSubscriptionData.addGroupedAvp(Avp.CSG_SUBSCRIPTION_DATA, 10415, true, false);
    if (getCSGId() > -1)
      csgSubscriptionData.addAvp(Avp.CSG_ID, getCSGId(), 10415, true, false, true);
    if (getExpirationDate() != null)
      csgSubscriptionData.addAvp(Avp.EXPIRATION_DATE, getExpirationDate(), 10415, true, false);
    if (getServiceSelection() != null)
      csgSubscriptionData.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    if (getVisitedPLMNId() != null)
      csgSubscriptionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getRoamingRestrictedDueToUnsupportedFeature() > -1)
      subscriptionData.addAvp(Avp.ROAMING_RESTRICTED_DUE_TO_UNSUPPORTED_FEATURE, getRoamingRestrictedDueToUnsupportedFeature(), 10415, true, false);
    if (getSubscribedPeriodicRAUTAUTimer() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_PERIODIC_RAU_TAU_TIMER, getSubscribedPeriodicRAUTAUTimer(), 10415, true, false, true);
    if (getMPSPriority() > -1)
      subscriptionData.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    if (getVPLMNLIPAAllowed() > -1)
      subscriptionData.addAvp(Avp.VPLMN_LIPA_ALLOWED, getVPLMNLIPAAllowed(), 10415, false, false);
    if (getRelayNodeIndicator() > -1)
      subscriptionData.addAvp(Avp.RELAY_NODE_INDICATOR, getRelayNodeIndicator(), 10415, false, false);
    if (getMDTUserConsent() > -1)
      subscriptionData.addAvp(Avp.MDT_USER_CONSENT, getMDTUserConsent(), 10415, false, false);
    if (getSubscribedVSRVCC() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_VSRVCC, getSubscribedVSRVCC(), 10415, false, false);
    AvpSet proSeSubscriptionData = subscriptionData.addGroupedAvp(Avp.PROSE_SUBSCRIPTION_DATA, 10415, true, false);
    if (getProSePermission() > -1)
      proSeSubscriptionData.addAvp(Avp.PROSE_PERMISSION, getProSePermission(), 10415, true, false, true);
    AvpSet proSeAllowedPLMN = proSeSubscriptionData.addGroupedAvp(Avp.PROSE_ALLOWED_PLMN, 10415, true, false);
    if (getVisitedPLMNId() != null)
      proSeAllowedPLMN.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAuthorizedDiscoveryRange() > -1)
      proSeAllowedPLMN.addAvp(Avp.AUTHORIZED_DISCOVERY_RANGE, getAuthorizedDiscoveryRange(), 10415, true, false, true);
    if (getProSeDirectAllowed() > -1)
      proSeAllowedPLMN.addAvp(Avp.PROSE_DIRECT_ALLOWED, getProSeDirectAllowed(), 10415, true, false, true);
    if (get3GPPChargingCharacteristics() != null)
      proSeSubscriptionData.addAvp(Avp.TGPP_CHARGING_CHARACTERISTICS, get3GPPChargingCharacteristics(), 10415, true, false, false);
    if (getSubscriptionDataFlags() > -1)
      proSeAllowedPLMN.addAvp(Avp.SUBSCRIPTION_DATA_FLAGS, getSubscriptionDataFlags(), 10415, false, false, true);
    AvpSet adjacentAccessRestrictionData = subscriptionData.addGroupedAvp(Avp.ADJACENT_ACCESS_RESTRICTION_DATA, 10415, false, false);
    if (getVisitedPLMNId() != null)
      adjacentAccessRestrictionData.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, true, false);
    if (getAccessRestrictionData() > -1)
      adjacentAccessRestrictionData.addAvp(Avp.ACCESS_RESTRICTION_DATA, getAccessRestrictionData(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      subscriptionData.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet imsiGroupId = subscriptionData.addGroupedAvp(Avp.IMSI_GROUP_ID, 10415, false, false);
    if (getGroupServiceId() > -1)
      imsiGroupId.addAvp(Avp.GROUP_SERVICE_ID, getGroupServiceId(), 10415, false, false, true);
    if (getGroupPLMNId() != null)
      imsiGroupId.addAvp(Avp.GROUP_PLMN_ID, getGroupPLMNId(), 10415, false, false);
    if (getLocalGroupId() != null)
      imsiGroupId.addAvp(Avp.LOCAL_GROUP_ID, getLocalGroupId(), 10415, false, false);
    if (getUeUsageType() > -1)
      subscriptionData.addAvp(Avp.UE_USAGE_TYPE, getUeUsageType(), 10415, false, false, true);
    AvpSet aeseCommunicationPattern = subscriptionData.addGroupedAvp(Avp.AESE_COMMUNICATION_PATTERN, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      aeseCommunicationPattern.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getSCEFReferenceIDForDeletion() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      aeseCommunicationPattern.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    AvpSet communicationPatternSet = aeseCommunicationPattern.addGroupedAvp(Avp.COMMUNICATION_PATTERN_SET, 10415, true, false);
    if (getPeriodicCommunicationIndicator() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_COMMUNICATION_INDICATOR, getPeriodicCommunicationIndicator(), 10415, true, false, true);
    if (getCommunicationDurationTime() > -1)
      communicationPatternSet.addAvp(Avp.COMMUNICATION_DURATION_TIME, getCommunicationDurationTime(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      communicationPatternSet.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    AvpSet scheduledCommunicationTime = communicationPatternSet.addGroupedAvp(Avp.SCHEDULED_COMMUNICATION_TIME, 10415, true, false);
    if (getDayOfWeekMask() > -1)
      scheduledCommunicationTime.addAvp(Avp.DAY_OF_WEEK_MASK, getDayOfWeekMask(), 0, false, false, true);
    if (getTimeOfDayStart() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_START, getTimeOfDayStart(), 0, false, false, true);
    if (getTimeOfDayEnd() > -1)
      scheduledCommunicationTime.addAvp(Avp.TIME_OF_DAY_END, getTimeOfDayEnd(), 0, false, false, true);
    if (getStationaryIndication() > -1)
      communicationPatternSet.addAvp(Avp.STATIONARY_INDICATION, getStationaryIndication(), 10415, true, false, true);
    if (getReferenceIDValidityTime() != null)
      communicationPatternSet.addAvp(Avp.REFERENCE_ID_VALIDITY_TIME, getReferenceIDValidityTime(), 10415, true, false);
    if (getTrafficProfile() > -1)
      communicationPatternSet.addAvp(Avp.TRAFFIC_PROFILE, getTrafficProfile(), 10415, false, false);
    if (getBatteryIndicator() > -1)
      communicationPatternSet.addAvp(Avp.BATTERY_INDICATOR, getBatteryIndicator(), 10415, false, false, true);
    AvpSet mtcProviderInfo = aeseCommunicationPattern.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mtcProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet monitoringEventConfiguration = subscriptionData.addGroupedAvp(Avp.MONITORING_EVENT_CONFIGURATION, 10415, true, false);
    if (getSCEFReferenceID() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID, getSCEFReferenceID(), 10415, true, false, true);
    if (getSCEFReferenceIDExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_EXT, getSCEFReferenceIDExt(), 10415, false, false, false);
    if (getSCEFId() != null)
      monitoringEventConfiguration.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getMonitoringType() > -1)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_TYPE, getMonitoringType(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletion() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION, getSCEFReferenceIDForDeletion(), 10415, true, false, true);
    if (getSCEFReferenceIDForDeletionExt() > -1)
      monitoringEventConfiguration.addAvp(Avp.SCEF_REFERENCE_ID_FOR_DELETION_EXT, getSCEFReferenceIDForDeletionExt(), 10415, false, false, false);
    if (getMaximumNumberOfReports() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_NUMBER_OF_REPORTS, getMaximumNumberOfReports(), 10415, true, false, true);
    if (getMonitoringDuration() != null)
      monitoringEventConfiguration.addAvp(Avp.MONITORING_DURATION, getMonitoringDuration(), 10415, true, false);
    if (getChargedParty() != null)
      monitoringEventConfiguration.addAvp(Avp.CHARGED_PARTY, getChargedParty(), 10415, true, false, false);
    if (getMaximumDetectionTime() > -1)
      monitoringEventConfiguration.addAvp(Avp.MAXIMUM_DETECTION_TIME, getMaximumDetectionTime(), 10415, true, false, true);
    AvpSet ueReachabilityConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.UE_REACHABILITY_CONFIGURATION, 10415, true, false);
    if (getReachabilityType() > -1)
      ueReachabilityConfiguration.addAvp(Avp.REACHABILITY_TYPE, getReachabilityType(), 10415, true, false, true);
    if (getMaximumLatency() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_LATENCY, getMaximumLatency(), 10415, true, false, true);
    if (getMaximumResponseTime() > -1)
      ueReachabilityConfiguration.addAvp(Avp.MAXIMUM_RESPONSE_TIME, getMaximumResponseTime(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      ueReachabilityConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    AvpSet locationInformationConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.LOCATION_INFORMATION_CONFIGURATION, 10415, true, false);
    if (getMONTELocationType() > -1)
      locationInformationConfiguration.addAvp(Avp.MONTE_LOCATION_TYPE, getMONTELocationType(), 10415, true, false, true);
    if (getAccuracy() > -1)
      locationInformationConfiguration.addAvp(Avp.ACCURACY, getAccuracy(), 10415, true, false, true);
    if (getPeriodicTime() > -1)
      locationInformationConfiguration.addAvp(Avp.PERIODIC_TIME, getPeriodicTime(), 10415, true, false, true);
    if (getAssociationType() > -1)
      monitoringEventConfiguration.addAvp(Avp.ASSOCIATION_TYPE, getAssociationType(), 10415, true, false, true);
    if (getDLBufferingSuggestedPacketCount() > -1)
      monitoringEventConfiguration.addAvp(Avp.DL_BUFFERING_SUGGESTED_PACKET_COUNT, getDLBufferingSuggestedPacketCount(), 10415, false, false);
    if (getPLMNIdRequested() > -1)
      monitoringEventConfiguration.addAvp(Avp.PLMN_ID_REQUESTED, getPLMNIdRequested(), 10415, true, false);
    AvpSet mecMTCProviderInfo = monitoringEventConfiguration.addGroupedAvp(Avp.MTC_PROVIDER_INFO, 10415, false, false);
    if (getMTCProviderID() != null)
      mecMTCProviderInfo.addAvp(Avp.MTC_PROVIDER_ID, getMTCProviderID(), 10415, false, false, false);
    AvpSet pdnConnectivityStatusConfiguration = monitoringEventConfiguration.addGroupedAvp(Avp.PDN_CONNECTIVITY_STATUS_CONFIGURATION, 10415, false, false);
    if (getServiceSelection() != null)
      pdnConnectivityStatusConfiguration.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), 0, true, false, false);
    AvpSet excludeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.EXCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      excludeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      excludeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet includeIdentifiers = monitoringEventConfiguration.addGroupedAvp(Avp.INCLUDE_IDENTIFIERS, 10415, false, false);
    if (getExternalIdentifier() != null)
      includeIdentifiers.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getMSISDN() != null)
      includeIdentifiers.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    AvpSet emergencyInfo = subscriptionData.addGroupedAvp(Avp.EMERGENCY_INFO, 10415, false, false);
    AvpSet emergencyInfoMIP6AgentInfo = emergencyInfo.addGroupedAvp(Avp.MIP6_AGENT_INFO, 0, true, false);
    if (getMIPHomeAgentAddress() != null)
      mip6AgentInfo.addAvp(Avp.MIP_HOME_AGENT_ADDRESS, getMIPHomeAgentAddress(), 0, true, false);
    AvpSet emergencyInfoMIPHomeAgentHost = emergencyInfoMIP6AgentInfo.addGroupedAvp(Avp.MIP_HOME_AGENT_HOST, 0, true, false);
    if (getMIPHomeAgentHostDestRealm() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_REALM, getMIPHomeAgentHostDestRealm(), 0, true, false, false);
    if (getMIPHomeAgentHostDestHost() != null)
      emergencyInfoMIPHomeAgentHost.addAvp(Avp.DESTINATION_HOST, getMIPHomeAgentHostDestHost(), 0, true, false, false);
    AvpSet v2xSubscriptionData = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionData.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionData.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet v2xSubscriptionDataNR = subscriptionData.addGroupedAvp(Avp.V2X_SUBSCRIPTION_DATA_NR, 10415, false, false);
    if (getV2xPermission() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.V2X_PERMISSION, getV2xPermission(), 10415, false, false, true);
    if (getUePc5AMBR() > -1)
      v2xSubscriptionDataNR.addAvp(Avp.UE_PC5_AMBR, getUePc5AMBR(), 10415, false, false, true);
    AvpSet uePC5QoS = v2xSubscriptionDataNR.addGroupedAvp(Avp.UE_PC5_QOS, 10415, false, false);
    AvpSet pc5QoSFlow = uePC5QoS.addGroupedAvp(Avp.PC5_QOS_FLOW, 10415, false, false);
    if (get5QI() > -1)
      pc5QoSFlow.addAvp(Avp._5QI, get5QI(), 10415, false, false);
    AvpSet pc5FlowBitrates = pc5QoSFlow.addGroupedAvp(Avp.PC5_FLOW_BITRATES, 10415, false, false);
    if (getGuaranteedFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.GUARANTEED_FLOW_BITRATES, getGuaranteedFlowBitrates(), 10415, false, false);
    if (getMaximumFlowBitrates() > -1)
      pc5FlowBitrates.addAvp(Avp.MAXIMUM_FLOW_BITRATES, getMaximumFlowBitrates(), 10415, false, false);
    if (getPC5Range() > -1)
      pc5QoSFlow.addAvp(Avp.PC5_RANGE, getPC5Range(), 10415, false, false);
    if (getPC5LinkAMBR() > -1)
      uePC5QoS.addAvp(Avp.PC5_LINK_AMBR, getPC5LinkAMBR(), 10415, false, false);
    AvpSet eDRXCycleLength = subscriptionData.addGroupedAvp(Avp.EDRX_CYCLE_LENGTH, 10415, false, false);
    if (getRatType() > -1)
      eDRXCycleLength.addAvp(Avp.RAT_TYPE, getRatType(), 10415, false, false);
    if (getEDRXCycleLengthValue() != null)
      eDRXCycleLength.addAvp(Avp.EDRX_CYCLE_LENGTH_VALUE, getEDRXCycleLengthValue(), 10415, false, false);
    if (getExternalIdentifier() != null)
      subscriptionData.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    if (getActiveTime() > -1)
      subscriptionData.addAvp(Avp.ACTIVE_TIME, getActiveTime(), 10415, false, false, true);
    if (getServiceGapTime() > -1)
      subscriptionData.addAvp(Avp.SERVICE_GAP_TIME, getServiceGapTime(), 10415, false, false, true);
    if (getBroadcastLocationAssistanceDataTypes() > -1)
      subscriptionData.addAvp(Avp.BROADCAST_LOCATION_ASSISTANCE_DATA_TYPES, getBroadcastLocationAssistanceDataTypes(), 10415, false, false, false);
    if (getAerialUESubscriptionInformation() > -1)
      subscriptionData.addAvp(Avp.AERIAL_UE_SUBSCRIPTION_INFORMATION, getAerialUESubscriptionInformation(), 10415, false, false, true);
    if (getCoreNetworkRestrictions() > -1)
      subscriptionData.addAvp(Avp.CORE_NETWORK_RESTRICTIONS, getCoreNetworkRestrictions(), 10415, false, false, true);
    AvpSet pagingTimeWindow = subscriptionData.addGroupedAvp(Avp.PAGING_TIME_WINDOW, 10415, false, false);
    if (getOperationMode() > -1)
      pagingTimeWindow.addAvp(Avp.OPERATION_MODE, getOperationMode(), 10415, false, false, true);
    if (getPagingTimeWindowLength() != null)
      pagingTimeWindow.addAvp(Avp.PAGING_TIME_WINDOW_LENGTH, getPagingTimeWindowLength(), 10415, false, false);
    if (getSubscribedARPI() > -1)
      subscriptionData.addAvp(Avp.SUBSCRIBED_ARPI, getSubscribedARPI(), 10415, false, false, true);
    if (getIABOperationPermission() > -1)
      subscriptionData.addAvp(Avp.IAB_OPERATION_PERMISSION, getIABOperationPermission(), 10415, false, false);
    if (getPLMNRATUsageControl() > -1)
      subscriptionData.addAvp(Avp.PLMN_RAT_USAGE_CONTROL, getPLMNRATUsageControl(), 10415, false, false, true);

    // [ Subscription-Data-Deletion ]
    AvpSet subscriptionDataDeletion = reqSet.addGroupedAvp(Avp.SUBSCRIPTION_DATA_DELETION, 10415, false, false);
    if (getDSRFlags() > -1)
      subscriptionDataDeletion.addAvp(Avp.DSR_FLAGS, getDSRFlags(), 10415, true, false, true);
    if (getSCEFId() != null)
      subscriptionDataDeletion.addAvp(Avp.SCEF_ID, getSCEFId(), 10415, true, false, false);
    if (getContextIdentifier() > -1)
      subscriptionDataDeletion.addAvp(Avp.CONTEXT_IDENTIFIER, getContextIdentifier(), 10415, true, false, true);
    if (getTraceReference() != null)
      subscriptionDataDeletion.addAvp(Avp.TRACE_REFERENCE, getTraceReference(), 10415, true, false);
    if (getTSCode() != null)
      subscriptionDataDeletion.addAvp(Avp.TS_CODE, getTSCode(), 10415, true, false);
    if (getSSCode() != null)
      subscriptionDataDeletion.addAvp(Avp.SS_CODE, getSSCode(), 10415, true, false);

    return rsr;
  }

  /*
    3GPP TS 29.272 V19.2.0 § 7.2.18

    The Notify-Answer (NOA) command, indicated by the Command-Code field set to 323
    and the 'R' bit cleared in the Command Flags field, is sent from HSS to MME or SGSN.

    Message Format
    < Notify-Answer > ::= < Diameter Header: 323, PXY, 16777251 >
                                        < Session-Id >
                                        [ DRMP ]
                                        [ Vendor-Specific-Application-Id ]
                                        [ Result-Code ]
                                        [ Experimental-Result ]
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ OC-Supported-Features ]
                                        [ OC-OLR ]
                                       *[ Load ]
                                       *[ Supported-Features ]
                                       *[ AVP ]
                                        [ Failed-AVP ]
                                       *[ Proxy-Info ]
                                       *[ Route-Record ]
   */
  protected JNotifyAnswer createNOA(JNotifyRequest nor, long resultCode) throws Exception {
    // < Notify-Answer > ::= < Diameter Header: 323, PXY, 16777251 >
    JNotifyAnswer noa = new JNotifyAnswerImpl((Request) nor.getMessage(), resultCode);

    AvpSet reqSet = nor.getMessage().getAvps();
    AvpSet avpSet = noa.getMessage().getAvps();
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

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = avpSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = avpSet.addGroupedAvp(Avp.OC_OLR, 0, false, false);
    if (getOCSequenceNumber() > -1)
      ocOlr.addAvp(Avp.OC_SEQUENCE_NUMBER, getOCSequenceNumber(), 0, false, false);
    if (getOCReportType() > -1)
      ocOlr.addAvp(Avp.OC_REPORT_TYPE, getOCReportType(), 0, false, false);
    if (getOCReductionPercentage() > -1)
      ocOlr.addAvp(Avp.OC_REDUCTION_PERCENTAGE, getOCReductionPercentage(), 0, false, false, true);
    if (getOCValidityDuration() > -1)
      ocOlr.addAvp(Avp.OC_VALIDITY_DURATION, getOCValidityDuration(), 0, false, false, true);
    if (getSourceID() != null)
      ocOlr.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Load ]
    AvpSet load = avpSet.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = avpSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    return noa;
  }

}
