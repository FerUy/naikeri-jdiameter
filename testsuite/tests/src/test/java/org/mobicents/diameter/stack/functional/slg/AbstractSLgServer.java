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
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.api.slg.ServerSLgSessionListener;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.common.impl.app.slg.LocationReportRequestImpl;
import org.jdiameter.common.impl.app.slg.ProvideLocationAnswerImpl;
import org.jdiameter.common.impl.app.slg.SLgSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public abstract class AbstractSLgServer extends TBase implements ServerSLgSessionListener {

  // NOTE: implementing NetworkReqListener since it's required for stack to know we support it... ech.

  protected ServerSLgSession serverSLgSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777255));
      SLgSessionFactoryImpl slgSessionFactory = new SLgSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerSLgSession.class, slgSessionFactory);
      sessionFactory.registerAppFacory(ClientSLgSession.class, slgSessionFactory);
      slgSessionFactory.setServerSessionListener(this);
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
      RouteException,
      OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  public void doProvideLocationRequestEvent(ServerSLgSession session, ProvideLocationRequest request) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"PLR\" event, request[" + request + "], on session[" + session + "]", null);
  }

  public void doLocationReportAnswerEvent(ServerSLgSession session, LocationReportRequest request, LocationReportAnswer answer) throws InternalException,
      IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"LRA\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // -------- conf

  public String getSessionId() {
    return this.serverSLgSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverSLgSession = stack.getSession(sessionId, ServerSLgSession.class);
  }

  public ServerSLgSession getSession() {
    return this.serverSLgSession;
  }

  // Attributes for Provide Location Answer (PLA)
  protected abstract byte[] getLocationEstimate();
  protected abstract int getAccuracyFulfilmentIndicator();
  protected abstract long getAgeOfLocationEstimate();
  protected abstract byte[] getVelocityEstimate();
  protected abstract byte[] getEUTRANPositioningData();
  protected abstract byte[] getECGI();
  protected abstract byte[] getGERANPositioningData();
  protected abstract byte[] getGERANGANSSPositioningData();
  protected abstract byte[] getCellGlobalIdentity();
  protected abstract byte[] getUTRANPositioningData();
  protected abstract byte[] getUTRANGANSSPositioningData();
  protected abstract byte[] getUTRANAdditionalPositioningData();
  protected abstract byte[] getServiceAreaIdentity();
  protected abstract byte[] getSGSNNumber();
  protected abstract String getSGSNName();
  protected abstract String getSGSNRealm();
  protected abstract String getMMEName();
  protected abstract String getMMERealm();
  protected abstract byte[] getMSCNumber();
  protected abstract String get3GPPAAAServerName();
  protected abstract long getLCSCapabilitiesSets();
  protected abstract long getPLAFLags();
  protected abstract long getCellPortionId();
  protected abstract String getCivicAddress();
  protected abstract long getBarometricPressure();
  protected abstract java.net.InetAddress getGMLCAddress();

  // Attributes for Location Report Request (LRR)
  protected abstract String getUserName(); // IE: IMSI
  protected abstract byte[] getMSISDN();
  protected abstract String getIMEI();
  protected abstract String getLCSNameString();
  protected abstract int getLCSFormatIndicator();
  protected abstract int getLCSQoSClass();
  protected abstract long getLSCServiceTypeId();
  protected abstract long getDeferredLocationType();
  protected abstract byte[] getLCSReferenceNumber();
  //protected abstract java.net.InetAddress getGMLCAddress();
  protected abstract long getReportingAmount();
  protected abstract long getReportingInterval();
  protected abstract int getLocationEvent();
  protected abstract int getPseudonymIndicator();
  protected abstract long getLRRFLags();
  protected abstract long getTerminationCause();
  protected abstract byte[] get1xRTTRCID();

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
    6.3.1 General
    The Subscriber Location Report operation is used by an MME or SGSN to provide the location of a target UE to a GMLC,
    when a request for location has been implicitly issued or when a Delayed Location Reporting is triggered after
    receipt of a request for location for a UE transiently not reachable.
  */


  /*
   3GPP TS 29.172 v18.1.0 § 7.3.2
   The Provide-Location-Answer (PLA) command, indicated by the Command-Code field set to 8388620
   and the 'R' bit cleared in the Command Flags field, is sent by the MME or SGSN to the GMLC
   in response to the Provide-Location-Request command.

   Message Format:
   < Provide-Location-Answer > ::=	< Diameter Header: 8388620, PXY, 16777255 >
                                    < Session-Id >
                                    [ DRMP ]
                                    [ Vendor-Specific-Application-Id ]
                                    [ Result-Code ]
                                    [ Experimental-Result ]
                                    { Auth-Session-State }
                                    { Origin-Host }
                                    { Origin-Realm }
                                    [ Location-Estimate ]
                                    [ Accuracy-Fulfilment-Indicator ]
                                    [ Age-Of-Location-Estimate]
                                    [ Velocity-Estimate ]
                                    [ EUTRAN-Positioning-Data]
                                    [ ECGI ]
                                    [ GERAN-Positioning-Info ]
                                    [ Cell-Global-Identity ]
                                    [ UTRAN-Positioning-Info ]
                                    [ Service-Area-Identity ]
                                    [ Serving-Node ]
                                    [ PLA-Flags ]
                                    [ ESMLC-Cell-Info ]
                                    [ Civic-Address ]
                                    [ Barometric-Pressure ]
                                   *[ Supported-Features ]
                                   *[ AVP ]
                                   [ Failed-AVP ]
                                   *[ Proxy-Info ]
                                   *[ Route-Record ]
  */
  public ProvideLocationAnswer createPLA(ProvideLocationRequest plr, long resultCode) throws Exception {
    // < Provide-Location-Answer > ::=	< Diameter Header: 8388620, PXY, 16777255 >
    ProvideLocationAnswer pla = new ProvideLocationAnswerImpl((Request) plr.getMessage(), resultCode);

    AvpSet reqSet = plr.getMessage().getAvps();
    AvpSet set = pla.getMessage().getAvps();
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

    // [ Location-Estimate ]
    byte[] locationEstimate = getLocationEstimate();
    if (locationEstimate != null)
      set.addAvp(Avp.LOCATION_ESTIMATE, locationEstimate, 10415, true, false);

    // [ Accuracy-Fulfilment-Indicator ]
    if (getAccuracyFulfilmentIndicator() != -1)
      set.addAvp(Avp.ACCURACY_FULFILMENT_INDICATOR, getAccuracyFulfilmentIndicator(), 10415, false, false);

    // [ Age-Of-Location-Estimate ]
    if (getAgeOfLocationEstimate() != -1)
      set.addAvp(Avp.AGE_OF_LOCATION_ESTIMATE, getAgeOfLocationEstimate(), 10415, false, false, true);

    // [ Velocity-Estimate ]
    if (getVelocityEstimate() != null)
      set.addAvp(Avp.VELOCITY_ESTIMATE, getVelocityEstimate(), 10415, false, false);

    // [ EUTRAN-Positioning-Data ]
    if (getEUTRANPositioningData() != null)
      set.addAvp(Avp.EUTRAN_POSITIONING_DATA, getEUTRANPositioningData(), 10415, false, false);

    // [ ECGI ]
    if (getECGI() != null)
      set.addAvp(Avp.ECGI, getECGI(), 10415, false, false);

    // [ GERAN-Positioning-Info ]
    AvpSet geranPositioningInfo = set.addGroupedAvp(Avp.GERAN_POSITIONING_INFO, 10415, false, false);
    if (getGERANPositioningData() != null)
      geranPositioningInfo.addAvp(Avp.GERAN_POSITIONING_DATA, getGERANPositioningData(), 10415, false, false);
    if ( getGERANGANSSPositioningData() != null)
      geranPositioningInfo.addAvp(Avp.GERAN_GANSS_POSITIONING_DATA,  getGERANGANSSPositioningData(), 10415, false, false);

    // [ Cell-Global-Identity ]
    if (getCellGlobalIdentity() != null)
      set.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);

    // [ UTRAN-Positioning-Info ]
    AvpSet utranPositioningInfo = set.addGroupedAvp(Avp.UTRAN_POSITIONING_INFO, 10415, false, false);
    if (getUTRANPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_POSITIONING_DATA, getUTRANPositioningData(), 10415, false, false);
    if (getUTRANGANSSPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_GANSS_POSITIONING_DATA, getUTRANGANSSPositioningData(), 10415, false, false);
    if (getUTRANAdditionalPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_ADDITIONAL_POSITIONING_DATA, getUTRANAdditionalPositioningData(), 10415, false, false);

    // [ Service-Area-Identity ]
    if (getServiceAreaIdentity() != null)
      set.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, false);

    // [ Serving-Node ] (Target Serving Node Identity)
    AvpSet servingNode = set.addGroupedAvp(Avp.SERVING_NODE, 10415, false, false);
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
      servingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ PLA-Flags ]
    if (getPLAFLags() != -1)
      set.addAvp(Avp.PLA_FLAGS, getPLAFLags(), 10415, false, false, true);

    // [ ESMLC-Cell-Info ]
    AvpSet esmlcCellInfo = set.addGroupedAvp(Avp.ESMLC_CELL_INFO, 10415, false, false);
    if (getECGI() != null)
      esmlcCellInfo.addAvp(Avp.ECGI, getECGI(), 10415, false, false);
    if (getCellPortionId() != -1)
      esmlcCellInfo.addAvp(Avp.CELL_PORTION_ID, getCellPortionId(), 10415, false, false, true);

    // [ Civic-Address ]
    if (getCivicAddress() != null){
      set.addAvp(Avp.CIVIC_ADDRESS, getCivicAddress(), 10415, false, false, false);
    }

    // [ Barometric-Pressure ]
    if (getBarometricPressure() != -1){
      set.addAvp(Avp.BAROMETRIC_PRESSURE, getBarometricPressure(), 10415, false, false, true);
    }

    return pla;
  }


  /*
    3GPP TS 29.172 v18.1.0 § 7.3.3
    The Location-Report-Request (LRR) command, indicated by the Command-Code field set to 8388621
    and the 'R' bit set in the Command Flags field, is sent by the MME or SGSN in order to
    provide subscriber location data to the GMLC.

    Message Format:
    < Location-Report-Request> ::= < Diameter Header: 8388621, REQ, PXY, 16777255 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   { Destination-Host }
                                   { Destination-Realm }
                                   { Location-Event }
                                   [ LCS-EPS-Client-Name ]
                                   [ User-Name ]
                                   [ MSISDN]
                                   [ IMEI ]
                                   [ Location-Estimate ]
                                   [ Accuracy-Fulfilment-Indicator ]
                                   [ Age-Of-Location-Estimate ]
                                   [ Velocity-Estimate ]
                                   [ EUTRAN-Positioning-Data ]
                                   [ ECGI]
                                   [ GERAN-Positioning-Info ]
                                   [ Cell-Global-Identity ]
                                   [ UTRAN-Positioning-Info ]
                                   [ Service-Area-Identity ]
                                   [ LCS-Service-Type-ID ]
                                   [ Pseudonym-Indicator ]
                                   [ LCS-QoS-Class ]
                                   [ Serving-Node ]
                                   [ LRR-Flags ]
                                   [ LCS-Reference-Number ]
                                   [ Deferred-MT-LR-Data]
                                   [ GMLC-Address ]
                                   [ Reporting-Amount ]
                                   [ Periodic-LDR-Information ]
                                   [ ESMLC-Cell-Info ]
                                   [ 1xRTT-RCID ] ]
                                   [ Delayed-Location-Reporting-Data ]
                                   [ Civic-Address ]
                                   [ Barometric-Pressure ]
                                  *[ Supported-Features ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected LocationReportRequest createLRR(ServerSLgSession slgSession) throws Exception {
    // < Location-Report-Request> ::= < Diameter Header: 8388621, REQ, PXY, 16777255 >
    LocationReportRequest lrr = new LocationReportRequestImpl(slgSession.getSessions().get(0).createRequest(LocationReportRequest.code, getApplicationId(),
        getServerRealmName()));

    AvpSet reqSet = lrr.getMessage().getAvps();

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
    reqSet.addAvp(Avp.DESTINATION_HOST, clientHost, true);

    // { Destination-Realm }

    // { Location-Event }
    if (getLocationEvent() != -1)
      reqSet.addAvp(Avp.LOCATION_EVENT, getLocationEvent(), 10415, true, false);

    // { LCS-EPS-Client-Name }
    AvpSet lcsEPSClientName = reqSet.addGroupedAvp(Avp.LCS_EPS_CLIENT_NAME, 10415, false, false);
    if (getLCSNameString() != null)
      lcsEPSClientName.addAvp(Avp.LCS_NAME_STRING, getLCSNameString(), 10415, false, false, false);
    if (getLCSFormatIndicator() != -1)
      lcsEPSClientName.addAvp(Avp.LCS_FORMAT_INDICATOR, getLCSFormatIndicator(), 10415, false, false);

    // [ User-Name ] IE: IMSI
    if (getUserName() != null) {
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 10415, true, false, false);
    }

    // [ MSISDN ]
    if (getMSISDN() != null)
      reqSet.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ IMEI ]
    if (getIMEI() != null)
      reqSet.addAvp(Avp.TGPP_IMEI, getIMEI(), 10415, false, false, false);

    // [ Location-Estimate ]
    if (getLocationEstimate() != null)
      reqSet.addAvp(Avp.LOCATION_ESTIMATE, getLocationEstimate(), 10415, true, false);

    // [ Accuracy-Fulfilment-Indicator ]
    if (getAccuracyFulfilmentIndicator() != -1){
      reqSet.addAvp(Avp.ACCURACY_FULFILMENT_INDICATOR, getAccuracyFulfilmentIndicator(), 10415, false, false);
    }

    // [ Age-Of-Location-Estimate ]
    if (getAgeOfLocationEstimate() != -1)
      reqSet.addAvp(Avp.AGE_OF_LOCATION_ESTIMATE, getAgeOfLocationEstimate(), 10415, false, false, true);

    // [ Velocity-Estimate ]
    if (getVelocityEstimate() != null)
      reqSet.addAvp(Avp.VELOCITY_ESTIMATE, getVelocityEstimate(), 10415, false, false);

    // [ EUTRAN-Positioning-Data ]
    if (getEUTRANPositioningData() != null)
      reqSet.addAvp(Avp.EUTRAN_POSITIONING_DATA, getEUTRANPositioningData(), 10415, false, false);

    // [ ECGI ]
    if (getECGI() != null)
      reqSet.addAvp(Avp.ECGI, getECGI(), 10415, false, false);

    // [ GERAN-Positioning-Info ]
    AvpSet geranPositioningInfo = reqSet.addGroupedAvp(Avp.GERAN_POSITIONING_INFO, 10415, false, false);
    if (getGERANPositioningData() != null)
      geranPositioningInfo.addAvp(Avp.GERAN_POSITIONING_DATA, getGERANPositioningData(), 10415, false, false);
    if ( getGERANGANSSPositioningData() != null)
      geranPositioningInfo.addAvp(Avp.GERAN_GANSS_POSITIONING_DATA,  getGERANGANSSPositioningData(), 10415, false, false);

    // [ Cell-Global-Identity ]
    if (getCellGlobalIdentity() != null)
      reqSet.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, false);

    // [ UTRAN-Positioning-Info ]
    AvpSet utranPositioningInfo = reqSet.addGroupedAvp(Avp.UTRAN_POSITIONING_INFO, 10415, false, false);
    if (getUTRANPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_POSITIONING_DATA, getUTRANPositioningData(), 10415, false, false);
    if (getUTRANGANSSPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_GANSS_POSITIONING_DATA, getUTRANGANSSPositioningData(), 10415, false, false);
    if (getUTRANAdditionalPositioningData() != null)
      utranPositioningInfo.addAvp(Avp.UTRAN_ADDITIONAL_POSITIONING_DATA, getUTRANAdditionalPositioningData(), 10415, false, false);

    // [ Service-Area-Identity ]
    if (getServiceAreaIdentity() != null)
      reqSet.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, false);

    // [ LCS-Service-Type-ID ]
    if (getLSCServiceTypeId() != -1)
      reqSet.addAvp(Avp.LCS_SERVICE_TYPE_ID, getLSCServiceTypeId(), 10415, false, false, true);

    // [ Pseudonym-Indicator ]
    if (getPseudonymIndicator() != -1)
      reqSet.addAvp(Avp.PSEUDONYM_INDICATOR, getPseudonymIndicator(), 10415, false, false);

    // [ LCS-QoS-Class ]
    if (getLCSQoSClass() != -1)
      reqSet.addAvp(Avp.LCS_QOS_CLASS, getLCSQoSClass(), 10415, false, false);

    // [ Serving-Node ] IE: Target Serving Node Identity
    AvpSet servingNode = reqSet.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
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
      servingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ LRR-Flags ]
    if (getLRRFLags() != -1)
      reqSet.addAvp(Avp.LRR_FLAGS, getLRRFLags(), 10415, false, false, true);

    // [ LCS-Reference-Number ]
    if (getLCSReferenceNumber() != null)
      reqSet.addAvp(Avp.LCS_REFERENCE_NUMBER, getLCSReferenceNumber(), 10415, true, false);

    // [ Deferred-MT-LR-Data]
    AvpSet deferredMTLRData = reqSet.addGroupedAvp(Avp.DEFERRED_MT_LR_DATA, 10415, false, false);
    if (getDeferredLocationType() != -1)
      deferredMTLRData.addAvp(Avp.DEFERRED_LOCATION_TYPE, getDeferredLocationType() , 10415, false, false, true);
    if (getTerminationCause() != -1)
      deferredMTLRData.addAvp(Avp.TERMINATION_CAUSE_3GPP, getTerminationCause(), 10415, false, false, true);
    AvpSet deferredMTLRDataServingNode = deferredMTLRData.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
    if (getSGSNNumber() != null)
      deferredMTLRDataServingNode.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, false, false);
    if (getSGSNName() != null)
      deferredMTLRDataServingNode.addAvp(Avp.SGSN_NAME, getSGSNName(), 10415, false, false, false);
    if (getSGSNRealm() != null)
      deferredMTLRDataServingNode.addAvp(Avp.SGSN_REALM, getSGSNRealm(), 10415, false, false, false);
    if (getMMEName() != null)
      deferredMTLRDataServingNode.addAvp(Avp.MME_NAME, getMMEName(), 10415, false, false, false);
    if (getMMERealm() != null)
      deferredMTLRDataServingNode.addAvp(Avp.MME_REALM, getMMERealm(), 10415, false, false, false);
    if (getMSCNumber() != null)
      deferredMTLRDataServingNode.addAvp(Avp.MSC_NUMBER, getMSCNumber(), 10415, false, false);
    if (get3GPPAAAServerName() != null)
      deferredMTLRDataServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, get3GPPAAAServerName(), 10415, false, false, false);
    if (getLCSCapabilitiesSets() != -1)
      deferredMTLRDataServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getLCSCapabilitiesSets(), 10415, false, false, true);
    if (getGMLCAddress() != null)
      deferredMTLRDataServingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ GMLC-Address ]
    if (getGMLCAddress() != null)
      reqSet.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ Reporting-Amount ]
    if (getReportingAmount() > -1)
      reqSet.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false, true);

    // [ Periodic-LDR-Information ]
    AvpSet periodicLDRInfo = reqSet.addGroupedAvp(Avp.PERIODIC_LDR_INFORMATION, 10415, false, false);
    if (getReportingAmount() != -1)
      periodicLDRInfo.addAvp(Avp.REPORTING_AMOUNT, getReportingAmount(), 10415, false, false, true);
    if (getReportingInterval() != -1)
      periodicLDRInfo.addAvp(Avp.REPORTING_INTERVAL, getReportingInterval(), 10415, false, false, true);

    // [ ESMLC-Cell-Info ]
    AvpSet esmlcCellInfo = reqSet.addGroupedAvp(Avp.ESMLC_CELL_INFO, 10415, false, false);
    if (getECGI() != null)
      esmlcCellInfo.addAvp(Avp.ECGI, getECGI(), 10415, false, false);
    if (getCellPortionId() != -1)
      esmlcCellInfo.addAvp(Avp.CELL_PORTION_ID, getCellPortionId(), 10415, false, false, true);

    // [ 1xRTT-RCID ]
    if (get1xRTTRCID() != null)
      reqSet.addAvp(Avp.ONE_X_RTT_RCID, get1xRTTRCID(), 10415, false, false);

    // [ Delayed-Location-Reporting-Data ]
    AvpSet delayedLocationReportingData = reqSet.addGroupedAvp(Avp.DELAYED_LOCATION_REPORTING_DATA, 10415, false, false);
    if (getTerminationCause() != -1)
      delayedLocationReportingData.addAvp(Avp.TERMINATION_CAUSE_3GPP, getTerminationCause(), 10415, false, false, true);
    AvpSet delayedLocationReportingDataServingNode = delayedLocationReportingData.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
    if (getSGSNNumber() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, false, false);
    if (getSGSNName() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.SGSN_NAME, getSGSNName(), 10415, false, false, false);
    if (getSGSNRealm() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.SGSN_REALM, getSGSNRealm(), 10415, false, false, false);
    if (getMMEName() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.MME_NAME, getMMEName(), 10415, false, false, false);
    if (getMMERealm() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.MME_REALM, getMMERealm(), 10415, false, false, false);
    if (getMSCNumber() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.MSC_NUMBER, getMSCNumber(), 10415, false, false);
    if (get3GPPAAAServerName() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, get3GPPAAAServerName(), 10415, false, false, false);
    if (getLCSCapabilitiesSets() != -1)
      delayedLocationReportingDataServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getLCSCapabilitiesSets(), 10415, false, false, true);
    if (getGMLCAddress() != null)
      delayedLocationReportingDataServingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, false, false);

    // [ Civic-Address ]
    if (getCivicAddress() != null)
      reqSet.addAvp(Avp.CIVIC_ADDRESS, getCivicAddress(), 10415, false, false, false);

    // [ Barometric-Pressure ]
    if (getBarometricPressure() != -1)
      reqSet.addAvp(Avp.BAROMETRIC_PRESSURE, getBarometricPressure(), 10415, false, false, true);

    return lrr;
  }


}