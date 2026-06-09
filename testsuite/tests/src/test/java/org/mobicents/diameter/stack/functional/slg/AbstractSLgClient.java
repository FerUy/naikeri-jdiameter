package org.mobicents.diameter.stack.functional.slg;

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
import org.jdiameter.api.slg.ClientSLgSession;
import org.jdiameter.api.slg.ClientSLgSessionListener;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.common.impl.app.slg.LocationReportAnswerImpl;
import org.jdiameter.common.impl.app.slg.ProvideLocationRequestImpl;
import org.jdiameter.common.impl.app.slg.SLgSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 *
 *@author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public abstract class AbstractSLgClient extends TBase implements ClientSLgSessionListener {

  // NOTE: implementing NetworkReqListener since it's required for stack to know we support it... ech.

  protected ClientSLgSession clientSLgSession;
  protected ServerSLgSession serverSLgSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777255));
      SLgSessionFactoryImpl sLgSessionFactory = new SLgSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerSLgSession.class, sLgSessionFactory);
      sessionFactory.registerAppFacory(ClientSLgSession.class, sLgSessionFactory);

      sLgSessionFactory.setClientSessionListener(this);

      this.clientSLgSession = (this.sessionFactory).getNewAppSession(this.sessionFactory.getSessionId("xx-SLg-TESTxx"), getApplicationId(),
          ClientSLgSession.class, (Object) null);
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

  public void doProvideLocationAnswerEvent(ClientSLgSession session, ProvideLocationRequest request, ProvideLocationAnswer answer) throws InternalException,
      IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"PLA\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  public void doLocationReportRequestEvent(ClientSLgSession session, LocationReportRequest request) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"LRR\" event, request[" + request + "], on session[" + session + "]", null);
  }

  // ----------- conf parts

  public String getSessionId() {
    return this.clientSLgSession.getSessionId();
  }

  public ClientSLgSession getSession() {
    return this.clientSLgSession;
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverSLgSession = stack.getSession(sessionId, ServerSLgSession.class);
  }

  /*
   3GPP TS 29.172 v18.1.0 § 6
    ELP Procedures
    6.1 General
    The ELP procedures, between the GMLC and the MME over SLg interface and between GMLC and SGSN over Lgd interface, are used to exchange messages related to location services. The ELP can be divided into the following sub-procedures.
    - Provide Subscriber Location
    - Subscriber Location Report

    6.2	Provide Subscriber Location
    6.2.1 General
    The Provide Subscriber Location operation is used by a GMLC to request the location of a target UE from the MME or SGSN
    at any time, as part of EPC-MT-LR or PS-MT-LR positioning procedures. The response contains a location estimate of the
    target UE and other additional information.

   The Provide Subscriber Location operation is also used by a GMLC to request the location of the target UE from the
   SGSN or MME at any time, as part of deferred MT-LR procedure. The response contains the acknowledgment of the
   receipt of the request and other additional information.

   6.3 Subscriber Location Report
   6.3.1	General
   The Subscriber Location Report operation is used by an MME or SGSN to provide the location of a target UE to a GMLC,
   when a request for location has been implicitly issued or when a Delayed Location Reporting is triggered after
   receipt of a request for location for a UE transiently not reachable.
  */

  /*** Attributes for Provide Location Request (PLR) and Location Report Answer (LRA) ***/
  protected abstract int getSLgLocationType();
  protected abstract String getUserName();
  protected abstract byte[] getMSISDN();
  protected abstract String getIMEI();
  protected abstract String getLCSNameString();
  protected abstract int getLCSFormatIndicator();
  protected abstract int getLCSClientType();
  protected abstract String getLCSRequestorIdString();
  protected abstract int getReqLCSFormatIndicator();
  protected abstract long getLCSPriority();
  protected abstract int getLCSQoSClass();
  protected abstract long getHorizontalAccuracy();
  protected abstract long getVerticalAccuracy();
  protected abstract int getVerticalRequested();
  protected abstract int getResponseTime();
  protected abstract int getVelocityRequested();
  protected abstract long getLCSSupportedGADShapes();
  protected abstract long getLSCServiceTypeId();
  protected abstract String getLCSCodeword();
  protected abstract String getServiceSelection();
  protected abstract int getLCSPrivacyCheckSession();
  protected abstract int getLCSPrivacyCheckNonSession();
  protected abstract long getDeferredLocationType();
  protected abstract byte[] getLCSReferenceNumber();
  protected abstract int getOccurrenceInfo();
  protected abstract long getIntervalTime();
  protected abstract long getMaximumInterval();
  protected abstract long getSamplingInterval();
  protected abstract long getReportingDuration();
  protected abstract long  getReportingLocationRequirements();
  protected abstract long getAreaType();
  protected abstract byte[] getAreaIdentification();
  protected abstract long getAdditionalAreaType();
  protected abstract byte[] getAdditionalAreaIdentification();
  protected abstract java.net.InetAddress getGMLCAddress();
  protected abstract long getPLRFLags();
  protected abstract long getReportingAmount();
  protected abstract long getReportingInterval();
  protected abstract int getPrioritizedListIndicator();
  protected abstract byte[] getVisitedPLMNId();
  protected abstract int getPeriodicLocationSupportIndicator();
  protected abstract long getLinearDistance();
  protected abstract long getLRAFLags();

  /*
  3GPP TS 29.172 v18.1.0 § 7.3.1

  The Provide-Location-Request (PLR) command, indicated by the Command-Code field set to 8388620
  and the 'R' bit set in the Command Flags field, is sent by the GMLC in order to request subscriber location
  to the MME or SGSN.

  Message Format:
   < Provide-Location-Request> ::=	< Diameter Header: 8388620, REQ, PXY, 16777255 >
	                         < Session-Id >
	                         [ DRMP ]
	                         [ Vendor-Specific-Application-Id ]
	                         { Auth-Session-State }
	                         { Origin-Host }
	                         { Origin-Realm }
	                         { Destination-Host }
	                         { Destination-Realm }
	                         { SLg-Location-Type }
	                         [ User-Name ]
	                         [ MSISDN ]
	                         [ IMEI ]
	                         { LCS-EPS-Client-Name }
	                         { LCS-Client-Type }
	                         [ LCS-Requestor-Name ]
	                         [ LCS-Priority ]
	                         [ LCS-QoS ]
	                         [ Velocity-Requested ]
	                         [ LCS-Supported-GAD-Shapes ]
	                         [ LCS-Service-Type-ID ]
	                         [ LCS-Codeword ]
	                         [ LCS-Privacy-Check-Non-Session ]
	                         [ LCS-Privacy-Check-Session ]
	                         [ Service-Selection ]
	                         [ Deferred-Location-Type ]
	                         [ LCS-Reference-Number ]
	                         [ Area-Event-Info ]
	                         [ GMLC-Address ]
	                         [ PLR-Flags ]
	                         [ Periodic-LDR-Information ]
	                         [ Reporting-PLMN-List ]
	                         [ Motion-Event-Info ]
	                         *[ Supported-Features ]
	                         *[ AVP ]
	                         *[ Proxy-Info ]
	                         *[ Route-Record ]
  */
  protected ProvideLocationRequest createPLR(ClientSLgSession slgSession) throws Exception {
    // < Provide-Location-Request> ::=	< Diameter Header: 8388620, REQ, PXY, 16777255 >
    ProvideLocationRequest plr = new ProvideLocationRequestImpl(slgSession.getSessions().get(0).
        createRequest(ProvideLocationRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = plr.getMessage().getAvps();

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

    // { SLg-Location-Type }
    if (getSLgLocationType() != -1)
      reqSet.addAvp(Avp.SLG_LOCATION_TYPE, getSLgLocationType(), 10415, true, false);

    // [ User-Name ] IE: IMSI
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 10415, true, false, false);

    // [ MSISDN ]
    if (getMSISDN() != null)
      reqSet.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ IMEI ]
    if (getIMEI() != null)
      reqSet.addAvp(Avp.TGPP_IMEI, getIMEI(), 10415, false, false, false);

    // { LCS-EPS-Client-Name }
    AvpSet lcsEPSClientName = reqSet.addGroupedAvp(Avp.LCS_EPS_CLIENT_NAME, 10415, false, false);
    if (getLCSNameString() != null)
      lcsEPSClientName.addAvp(Avp.LCS_NAME_STRING, getLCSNameString(), 10415, false, false, false);
    if (getLCSFormatIndicator() != -1)
      lcsEPSClientName.addAvp(Avp.LCS_FORMAT_INDICATOR, getLCSFormatIndicator(), 10415, false, false);

    // { LCS-Client-Type }
    if (getLCSClientType() != -1)
      reqSet.addAvp(Avp.LCS_CLIENT_TYPE, getLCSClientType(), 10415, false, false);

    // [ LCS-Requestor-Name ]
    AvpSet lcsRequestorName = reqSet.addGroupedAvp(Avp.LCS_REQUESTOR_NAME, 10415, false, false);
    if (getLCSRequestorIdString() != null)
      lcsRequestorName.addAvp(Avp.LCS_REQUESTOR_ID_STRING, getLCSRequestorIdString(), 10415, false, false, false);
    if (getReqLCSFormatIndicator() != -1)
      lcsRequestorName.addAvp(Avp.LCS_FORMAT_INDICATOR, getReqLCSFormatIndicator(), 10415, false, false);

    // [ LCS-Priority ]
    if (getLCSPriority() != -1)
      reqSet.addAvp(Avp.LCS_PRIORITY, getLCSPriority(), 10415, false, false, true);

    // [ LCS-QoS ]
    AvpSet lcsQoS = reqSet.addGroupedAvp(Avp.LCS_QOS, 10415, false, false);
    if (getLCSQoSClass() != -1)
      lcsQoS.addAvp(Avp.LCS_QOS_CLASS, getLCSQoSClass(), 10415, false, false);
    if (getHorizontalAccuracy() != -1)
      lcsQoS.addAvp(Avp.HORIZONTAL_ACCURACY, getHorizontalAccuracy(), 10415, false, false, true);
    if (getVerticalAccuracy() != -1)
      lcsQoS.addAvp(Avp.VERTICAL_ACCURACY, getVerticalAccuracy(), 10415, false, false, true);
    if (getVerticalRequested() != -1)
      lcsQoS.addAvp(Avp.VERTICAL_REQUESTED, getVerticalRequested(), 10415, false, false);
    if (getResponseTime() != -1)
      lcsQoS.addAvp(Avp.RESPONSE_TIME, getResponseTime(), 10415, false, false);

    // [ Velocity-Requested ]
    if (getVelocityRequested() != -1)
      reqSet.addAvp(Avp.VELOCITY_REQUESTED, getVelocityRequested(), 10415, false, false);

    // [ LCS-Supported-GAD-Shapes ]
    if (getLCSSupportedGADShapes() != -1)
      reqSet.addAvp(Avp.LCS_SUPPORTED_GAD_SHAPES, getLCSSupportedGADShapes(), 10415, false, false, true);

    // [ LCS-Service-Type-ID ]
    if (getLSCServiceTypeId() != -1)
      reqSet.addAvp(Avp.LCS_SERVICE_TYPE_ID, getLSCServiceTypeId(), 10415, true, false, true);

    // [ LCS-Codeword ]
    if (getLCSCodeword() != null)
      reqSet.addAvp(Avp.LCS_CODEWORD, getLCSCodeword(), 10415, false, false, false);

    // [ Service-Selection ]
    if (getServiceSelection() != null) {
      reqSet.addAvp(Avp.SERVICE_SELECTION, getServiceSelection(), false, false, false);
    }

    // [ LCS-Privacy-Check-Session ] // IE: Session-Related Privacy Check
    AvpSet lcsPrivacyCheckSession = reqSet.addGroupedAvp(Avp.LCS_PRIVACY_CHECK_SESSION, 10415, false, false);
    if (getLCSPrivacyCheckSession() != -1)
      lcsPrivacyCheckSession.addAvp(Avp.LCS_PRIVACY_CHECK, getLCSPrivacyCheckSession(), 10415, false, false);

    // [ LCS-Privacy-Check-Non-Session ] // IE: Non-Session-Related Privacy Check
    AvpSet lcsPrivacyCheckNonSession = reqSet.addGroupedAvp(Avp.LCS_PRIVACY_CHECK_NON_SESSION, 10415, false, false);
    if (getLCSPrivacyCheckNonSession() != -1)
      lcsPrivacyCheckNonSession.addAvp(Avp.LCS_PRIVACY_CHECK, getLCSPrivacyCheckNonSession(), 10415, false, false);

    // [ Deferred-Location-Type ]
    if (getDeferredLocationType() != -1)
      reqSet.addAvp(Avp.DEFERRED_LOCATION_TYPE, getDeferredLocationType(), 10415, false, false, true);

    // [ LCS-Reference-Number ]
    if (getLCSReferenceNumber() != null)
      reqSet.addAvp(Avp.LCS_REFERENCE_NUMBER, getLCSReferenceNumber(), 10415, true, false);

    // [ Area-Event-Info ]
    AvpSet areaEventInfo = reqSet.addGroupedAvp(Avp.AREA_EVENT_INFO, 10415, false, false);
    // { Area-Definition }
    AvpSet areaDefinition = areaEventInfo.addGroupedAvp(Avp.AREA_DEFINITION, 10415, false, false);
    AvpSet area = areaDefinition.addGroupedAvp(Avp.AREA, 10415, false, false);
    if (getAreaType() != -1)
      area.addAvp(Avp.AREA_TYPE, getAreaType(), 10415, false, false, true);
    if (getAreaIdentification() != null)
      area.addAvp(Avp.AREA_IDENTIFICATION, getAreaIdentification(), 10415, false, false);
    AvpSet additionalArea = areaDefinition.addGroupedAvp(Avp.ADDITIONAL_AREA, 10415, false, false);
    if (getAdditionalAreaType() != -1)
      additionalArea.addAvp(Avp.AREA_TYPE, getAdditionalAreaType(), 10415, false, false, true);
    if (getAdditionalAreaIdentification() != null)
      additionalArea.addAvp(Avp.AREA_IDENTIFICATION, getAdditionalAreaIdentification(), 10415, false, false);
    // [ Occurrence-Info ]
    if (getOccurrenceInfo() != -1)
      areaEventInfo.addAvp(Avp.OCCURRENCE_INFO, getOccurrenceInfo(), 10415, false, false);
    // [ Interval-Time ]
    if (getIntervalTime() != -1)
      areaEventInfo.addAvp(Avp.INTERVAL_TIME, getIntervalTime(), 10415, false, false, true);
    // [ Maximum-Interval ]
    if (getMaximumInterval() != -1)
      areaEventInfo.addAvp(Avp.MAXIMUM_INTERVAL, getMaximumInterval(), 10415, false, false, true);
    // [ Sampling-Interval ]
    if (getSamplingInterval() != -1)
      areaEventInfo.addAvp(Avp.SAMPLING_INTERVAL, getSamplingInterval(), 10415, false, false, true);
    // [ Reporting-Duration ]
    if (getReportingDuration() != -1)
      areaEventInfo.addAvp(Avp.REPORTING_DURATION, getReportingDuration(), 10415, false, false, true);
    // [ Reporting-Location-Requirements ] TODO
    if (getReportingLocationRequirements() != -1)
      areaEventInfo.addAvp(Avp.REPORTING_LOCATION_REQUIREMENTS, getReportingLocationRequirements(), 10415, false, false, true);

    // [ GMLC-Address ]
    if (getGMLCAddress() != null) {
      reqSet.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);
    }

    // [ PLR-Flags ]
    if (getPLRFLags() != -1) {
      reqSet.addAvp(Avp.PLR_FLAGS, getPLRFLags(), 10415, false, false, true);
    }

    // [ Periodic-LDR-Information ]
    AvpSet periodicLDRInformation = reqSet.addGroupedAvp(Avp.AREA_EVENT_INFO, 10415, false, false);
    if (getReportingAmount() != -1)
      periodicLDRInformation.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false, true);
    if (getReportingInterval() != -1)
      periodicLDRInformation.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false, true);

    // [ Reporting-PLMN-List ]
    AvpSet reportingPLMNList = reqSet.addGroupedAvp(Avp.REPORTING_PLMN_LIST, 10415, false, false);
    // { PLMN-ID-List }
    AvpSet plmnIdList = reportingPLMNList.addGroupedAvp(Avp.PLMN_ID_LIST, 10415, false, false);
    // { Visited-PLMN-Id }
    if (getVisitedPLMNId() != null)
      plmnIdList.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, false, false);
    // [ Periodic-Location-Support-Indicator ]
    if (getPeriodicLocationSupportIndicator() != -1)
      plmnIdList.addAvp(Avp.PERIODIC_LOCATION_SUPPORT_INDICATOR, getPeriodicLocationSupportIndicator(), 10415, false, false);
    // [ Prioritized-List-Indicator ]
    if (getPrioritizedListIndicator() != -1)
      reportingPLMNList.addAvp(Avp.PRIORITIZED_LIST_INDICATOR, getPrioritizedListIndicator(), 10415, false, false);

    // [ Motion-Event-Info ]
    AvpSet motionEventInfo = reqSet.addGroupedAvp(Avp.MOTION_EVENT_INFO, 10415, false, false);
    // { Linear-Distance }
    if (getLinearDistance() > -1)
      motionEventInfo.addAvp(Avp.LINEAR_DISTANCE, getLinearDistance(), 10415, false, false, true);
    // [ Occurrence-Info ]
    if (getOccurrenceInfo() != -1)
      motionEventInfo.addAvp(Avp.OCCURRENCE_INFO, getOccurrenceInfo(), 10415, false, false);
    // [ Interval-Time ]
    if (getIntervalTime() != -1)
      motionEventInfo.addAvp(Avp.INTERVAL_TIME, getIntervalTime(), 10415, false, false, true);
    // [ Maximum-Interval ]
    if (getMaximumInterval() != -1)
      motionEventInfo.addAvp(Avp.MAXIMUM_INTERVAL, getMaximumInterval(), 10415, false, false, true);
    // [ Sampling-Interval ]
    if (getSamplingInterval() != -1)
      motionEventInfo.addAvp(Avp.SAMPLING_INTERVAL, getSamplingInterval(), 10415, false, false, true);
    // [ Reporting-Duration ]
    if (getReportingDuration() != -1)
      motionEventInfo.addAvp(Avp.REPORTING_DURATION, getReportingDuration(), 10415, false, false, true);
    // [ Reporting-Location-Requirements ] TODO
    if (getReportingLocationRequirements() != -1)
      motionEventInfo.addAvp(Avp.REPORTING_LOCATION_REQUIREMENTS, getReportingLocationRequirements(), 10415, false, false, true);

    return plr;
  }

  /*
  3GPP TS 29.172 v18.1.0 § 7.3.4

  The Location-Report-Answer (LRA) command, indicated by the Command-Code field set to 8388621 
  and the 'R' bit cleared in the Command Flags field, is sent by the GMLC to the MME or SGSN 
  in response to the Location-Report-Request command.

  Message Format:
  < Location-Report-Answer > ::= < Diameter Header: 8388621, PXY, 16777255 >
	                         < Session-Id >
	                         [ DRMP ]
	                         [ Vendor-Specific-Application-Id ]
	                         [ Result-Code ]
	                         [ Experimental-Result ]
	                         { Auth-Session-State }
	                         { Origin-Host }
	                         { Origin-Realm }
	                         [ GMLC-Address ]
	                         [ LRA-Flags ]
	                         [ Reporting-PLMN-List ]
	                         [ LCS-Reference-Number ]
	                        *[ Supported-Features ]
	                        *[ AVP ]
	                         [ Failed-AVP ]
	                        *[ Proxy-Info ]
	                        *[ Route-Record ]

  */
  public LocationReportAnswer createLRA(LocationReportRequest lrr, long resultCode) throws Exception {
    // < Location-Report-Answer > ::= < Diameter Header: 8388621, PXY, 16777255 >
    LocationReportAnswer lra = new LocationReportAnswerImpl((Request) lrr.getMessage(), resultCode);

    AvpSet reqSet = lrr.getMessage().getAvps();
    AvpSet set = lra.getMessage().getAvps();
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

    // [ GMLC-Address ]
    if (getGMLCAddress() != null)
      set.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ LRA-Flags ]
    if (getLRAFLags() != -1)
      set.addAvp(Avp.LRA_FLAGS, getLRAFLags(), 10415, false, false, true);

    AvpSet reportingPLMNList = set.addGroupedAvp(Avp.REPORTING_PLMN_LIST, 10415, false, false);
    // { PLMN-ID-List }
    AvpSet plmnIdList = reportingPLMNList.addGroupedAvp(Avp.PLMN_ID_LIST, 10415, false, false);
    // { Visited-PLMN-Id }
    if (getVisitedPLMNId() != null)
      plmnIdList.addAvp(Avp.VISITED_PLMN_ID, getVisitedPLMNId(), 10415, false, false);
    // [ Periodic-Location-Support-Indicator ]
    if (getPeriodicLocationSupportIndicator() != -1)
      plmnIdList.addAvp(Avp.PERIODIC_LOCATION_SUPPORT_INDICATOR, getPeriodicLocationSupportIndicator(), 10415, false, false);
    // [ Prioritized-List-Indicator ]
    if (getPrioritizedListIndicator() != -1)
      reportingPLMNList.addAvp(Avp.PRIORITIZED_LIST_INDICATOR, getPrioritizedListIndicator(), 10415, false, false);

    // [ LCS-Reference-Number ]
    if (getLCSReferenceNumber() != null) {
      set.addAvp(Avp.LCS_REFERENCE_NUMBER, getLCSReferenceNumber(), 10415, true, false);
    }

    return lra;
  }

}
