package org.mobicents.diameter.stack.functional.s6c;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
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
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.api.s6c.ServerS6cSessionListener;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.common.impl.app.s6c.AlertServiceCentreRequestImpl;
import org.jdiameter.common.impl.app.s6c.ReportSMDeliveryStatusAnswerImpl;
import org.jdiameter.common.impl.app.s6c.S6cSessionFactoryImpl;
import org.jdiameter.common.impl.app.s6c.SendRoutingInfoForSMAnswerImpl;
import org.mobicents.diameter.stack.functional.TBase;


import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 *@author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractS6cServer extends TBase implements ServerS6cSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ServerS6cSession serverS6cSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777312));
      S6cSessionFactoryImpl s6SessionFactory = new S6cSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerS6cSession.class, s6SessionFactory);
      sessionFactory.registerAppFacory(ClientS6cSession.class, s6SessionFactory);
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

  public void doSendRoutingInfoForSMRequestEvent(ServerS6cSession session, SendRoutingInfoForSMRequest srr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"SRR\" event, request[" + srr + "], on session[" + session + "]", null);
  }

  public void doReportSMDeliveryStatusRequestEvent(ServerS6cSession session, ReportSMDeliveryStatusRequest rdr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"RDR\" event, request[" + rdr + "], on session[" + session + "]", null);
  }

  public void doAlertServiceCentreAnswerEvent(ServerS6cSession session, AlertServiceCentreRequest alr, AlertServiceCentreAnswer ala)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"ALA\" event, request[" + alr + "], answer[" + ala + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // -------- conf

  public String getSessionId() {
    return this.serverS6cSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverS6cSession = stack.getSession(sessionId, ServerS6cSession.class);
  }

  public ServerS6cSession getSession() {
    return this.serverS6cSession;
  }

  // Attributes for Send-Routing-Info-for-SM-Answer (SRA), Report-SM-Delivery-Status-Answer (RDA)
  // and Alert-Service-Centre-Request (ALR)

  // [ User-Name ]
  protected abstract String getUserName();

  // [ Serving-Node ]
  protected abstract byte[] getSGSNNumber();

  protected abstract String getSGSNName();

  protected abstract String getSGSNRealm();

  protected abstract String getMMEName();

  protected abstract String getMMERealm();

  protected abstract byte[] getMSCNumber();

  protected abstract String get3GPPAAAServerName();

  protected abstract long getLCSCapabilitiesSets();

  protected abstract InetAddress getGMLCAddress();

  // [ Additional-Serving-Node ]
  protected abstract byte[] getAdditionalSGSNNumber();

  protected abstract String getAdditionalSGSNName();

  protected abstract String getAdditionalSGSNRealm();

  protected abstract String getAdditionalMMEName();

  protected abstract String getAdditionalMMERealm();

  protected abstract byte[] getAdditionalMSCNumber();

  protected abstract String getAdditional3GPPAAAServerName();

  protected abstract long getAdditionalLCSCapabilitiesSets();

  protected abstract InetAddress getAdditionalGMLCAddress();

  // [ SMSF-3GPP-Address ]
  protected abstract byte[] getSmsf3gppNumber();

  protected abstract String getSmsf3gppName();

  protected abstract String getSmsf3gppRealm();

  protected abstract int getSmsf3gppSbiSupportIndicator();

  // [ SMSF-Non-3GPP-Address ]
  protected abstract byte[] getSmsfNon3gppNumber();

  protected abstract String getSmsfNon3gppName();

  protected abstract String getSmsfNon3gppRealm();

  protected abstract int getSmsfNon3gppSbiSupportIndicator();

  // [ LMSI ]
  protected abstract byte[] getLMSI();

  // [ User-Identifier ]
  protected abstract byte[] getMSISDN();

  protected abstract String getExternalIdentifier();

  // [ MWD-Status ]
  protected abstract long getMWDStatus();

  // [ MME-Absent-User-Diagnostic-SM ]
  protected abstract long getMMEAbsentUserDiagnosticSM();

  // [ MSC-Absent-User-Diagnostic-SM ]
  protected abstract long getMSCAbsentUserDiagnosticSM();

  // [ SGSN-Absent-User-Diagnostic-SM ]
  protected abstract long getSGSNAbsentUserDiagnosticSM();

  // [ SMSF-3GPP-Absent-User-Diagnostic-SM ]
  protected abstract long getSMSF3gppAbsentUserDiagnosticSM();

  // [ SMSF-Non-3GPP-Absent-User-Diagnostic-SM ]
  protected abstract long getSMSFNon3gppAbsentUserDiagnosticSM();

  // [ SMSMI-Correlation-ID ]
  protected abstract String getHssId();

  protected abstract String getOriginatingSipUri();

  protected abstract String getDestinationSipUri();

  // [ Maximum-UE-Availability-Time ]
  protected abstract Time getMaximumUEAvailabilityTime();

  // [ SMS-GMSC-Alert-Event ]
  protected abstract long getSMSGMSCAlertEvent();

  // { SC-Address }
  protected abstract byte[] getSCAddress();

  // [ MPS-Priority]
  protected abstract long getMPSPriority();

  /*
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.4

  The Send-Routing-Info-for-SM-Answer command (SRA) command,indicated by the Command-Code field set to 8388647
  and the 'R' bit cleared in the Command Flags field, is sent from HSS to SMS-GMSC or SMS Router or from SMS Router to SMS-GMSC.

  Message Format
  < Send-Routing-Info-for-SM-Answer > ::= < Diameter Header: 8388647, PXY, 16777312 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   [ Result-Code ]
                                   [ Experimental-Result ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ User-Name ]
                                  *[ Supported-Features ]
                                   [ Serving-Node ]
                                   [ Additional-Serving-Node ]
                                   [ SMSF-3GPP-Address ]
                                   [ SMSF-Non-3GPP-Address ]
                                   [ LMSI ]
                                   [ User-Identifier ]
                                   [ MWD-Status ]
                                   [ MME-Absent-User-Diagnostic-SM ]
                                   [ MSC-Absent-User-Diagnostic-SM ]
                                   [ SGSN-Absent-User-Diagnostic-SM ]
                                   [ SMSF-3GPP-Absent-User-Diagnostic-SM ]
                                   [ SMSF-Non-3GPP-Absent-User-Diagnostic-SM ]
                                  *[ AVP ]
                                   [ Failed-AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
                                   [ MPS-Priority]
   */
  protected SendRoutingInfoForSMAnswer createSRA(SendRoutingInfoForSMRequest srr, long resultCode) throws Exception {
    // < Send-Routing-Info-for-SM-Answer > ::= < Diameter Header: 8388647, PXY, 16777312 >
    SendRoutingInfoForSMAnswer sra = new SendRoutingInfoForSMAnswerImpl((Request) srr.getMessage(), resultCode);

    AvpSet reqSet = srr.getMessage().getAvps();
    AvpSet avpSet = sra.getMessage().getAvps();
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

    // [ User-Name ]
    if (getUserName() != null) {
      avpSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);
    }

    // [ Serving-Node ]
    AvpSet servingNode = avpSet.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
    if (getSGSNNumber() != null) {
      servingNode.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, true, false);
    }
    if (getSGSNName() != null) {
      servingNode.addAvp(Avp.SGSN_NAME, getSGSNName(), 10415, false, false, false);
    }
    if (getSGSNRealm() != null) {
      servingNode.addAvp(Avp.SGSN_REALM, getSGSNRealm(), 10415, false, false, false);
    }
    if (getMMEName() != null) {
      servingNode.addAvp(Avp.MME_NAME, getMMEName(), 10415, true, false, false);
    }
    if (getMMERealm() != null) {
      servingNode.addAvp(Avp.MME_REALM, getMMERealm(), 10415, false, false, false);
    }
    if (getMSCNumber() != null) {
      servingNode.addAvp(Avp.MSC_NUMBER, getMSCNumber(), 10415, true, false);
    }
    if (get3GPPAAAServerName() != null) {
      servingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, get3GPPAAAServerName(), 10415, true, false, false);
    }
    if (getLCSCapabilitiesSets() > -1) {
      servingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getLCSCapabilitiesSets(), 10415, true, false, true);
    }
    if (getGMLCAddress() != null) {
      servingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, true, false);
    }

    // [ Additional-Serving-Node ]
    AvpSet addServingNode = avpSet.addGroupedAvp(Avp.ADDITIONAL_SERVING_NODE, 10415, true, false);
    if (getAdditionalSGSNNumber() != null) {
      addServingNode.addAvp(Avp.SGSN_NUMBER, getAdditionalSGSNNumber(), 10415, true, false);
    }
    if (getAdditionalSGSNName() != null) {
      addServingNode.addAvp(Avp.SGSN_NAME, getAdditionalSGSNName(), 10415, false, false, false);
    }
    if (getAdditionalSGSNRealm() != null) {
      addServingNode.addAvp(Avp.SGSN_REALM, getAdditionalSGSNRealm(), 10415, false, false, false);
    }
    if (getAdditionalMMEName() != null) {
      addServingNode.addAvp(Avp.MME_NAME, getAdditionalMMEName(), 10415, true, false, false);
    }
    if (getAdditionalMMERealm() != null) {
      addServingNode.addAvp(Avp.MME_REALM, getAdditionalMMERealm(), 10415, false, false, false);
    }
    if (getAdditionalMSCNumber() != null) {
      addServingNode.addAvp(Avp.MSC_NUMBER, getAdditionalMSCNumber(), 10415, true, false);
    }
    if (getAdditional3GPPAAAServerName() != null) {
      addServingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, getAdditional3GPPAAAServerName(), 10415, true, false, false);
    }
    if (getAdditionalLCSCapabilitiesSets() > -1) {
      addServingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getAdditionalLCSCapabilitiesSets(), 10415, true, false, true);
    }
    if (getAdditionalGMLCAddress() != null) {
      addServingNode.addAvp(Avp.GMLC_ADDRESS, getAdditionalGMLCAddress(), 10415, true, false);
    }

    // [ SMSF-3GPP-Address ]
    AvpSet smsf3gppAddress = avpSet.addGroupedAvp(Avp.SMSF_3GPP_ADDRESS, 10415, false, false);
    if (getSmsf3gppNumber() != null) {
      smsf3gppAddress.addAvp(Avp.SMSF_3GPP_NUMBER, getSmsf3gppNumber(), 10415, false, false);
    }
    if (getSmsf3gppName() != null) {
      smsf3gppAddress.addAvp(Avp.SMSF_3GPP_NAME, getSmsf3gppName(), 10415, false, false, false);
    }
    if (getSmsf3gppRealm() != null) {
      smsf3gppAddress.addAvp(Avp.SMSF_3GPP_REALM, getSmsf3gppRealm(), 10415, false, false, false);
    }
    if (getSmsf3gppSbiSupportIndicator() > -1) {
      smsf3gppAddress.addAvp(Avp.SMSF_3GPP_SBI_SUPPORT_INDICATOR, getSmsf3gppSbiSupportIndicator(), 10415, false, false);
    }

    // [ SMSF-Non-3GPP-Address ]
    AvpSet smsfNon3gppAddress = avpSet.addGroupedAvp(Avp.SMSF_NON_3GPP_ADDRESS, 10415, false, false);
    if (getSmsfNon3gppNumber() != null) {
      smsfNon3gppAddress.addAvp(Avp.SMSF_NON_3GPP_NUMBER, getSmsfNon3gppNumber(), 10415, false, false);
    }
    if (getSmsfNon3gppName() != null) {
      smsfNon3gppAddress.addAvp(Avp.SMSF_NON_3GPP_NAME, getSmsfNon3gppName(), 10415, false, false, false);
    }
    if (getSmsfNon3gppRealm() != null) {
      smsfNon3gppAddress.addAvp(Avp.SMSF_NON_3GPP_REALM, getSmsfNon3gppRealm(), 10415, false, false, false);
    }
    if (getSmsfNon3gppSbiSupportIndicator() > -1) {
      smsfNon3gppAddress.addAvp(Avp.SMSF_NON_3GPP_SBI_SUPPORT_INDICATOR, getSmsfNon3gppSbiSupportIndicator(), 10415, false, false);
    }

    // [ LMSI ]
    if (getLMSI() != null) {
      avpSet.addAvp(Avp.LMSI, getLMSI(), 10415, true, false);
    }

    // [ User-Identifier ]
    AvpSet userIdentifier = avpSet.addGroupedAvp(Avp.USER_IDENTIFIER, 10415, true, false);
    if (getMSISDN() != null) {
      userIdentifier.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    }
    if (getUserName() != null) {
      userIdentifier.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);
    }
    if (getExternalIdentifier() != null) {
      userIdentifier.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    }

    // [ MWD-Status ]
    if (getMWDStatus() > -1) {
      avpSet.addAvp(Avp.MWD_STATUS, getMWDStatus(), 10415, true, false, true);
    }

    // [ MME-Absent-User-Diagnostic-SM ]
    if (getMMEAbsentUserDiagnosticSM() > -1) {
      avpSet.addAvp(Avp.MME_ABSENT_USER_DIAGNOSTIC_SM, getMMEAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ MSC-Absent-User-Diagnostic-SM ]
    if (getMSCAbsentUserDiagnosticSM() > -1) {
      avpSet.addAvp(Avp.MSC_ABSENT_USER_DIAGNOSTIC_SM, getMSCAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ SGSN-Absent-User-Diagnostic-SM ]
    if (getSGSNAbsentUserDiagnosticSM() > -1) {
      avpSet.addAvp(Avp.SGSN_ABSENT_USER_DIAGNOSTIC, getSGSNAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ SMSF-3GPP-Absent-User-Diagnostic-SM ]
    if (getSMSF3gppAbsentUserDiagnosticSM() > -1) {
      avpSet.addAvp(Avp.SMSF_3GPP_ABSENT_USER_DIAGNOSTIC_SM, getSMSF3gppAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ SMSF-Non-3GPP-Absent-User-Diagnostic-SM ]
    if (getSMSFNon3gppAbsentUserDiagnosticSM() > -1) {
      avpSet.addAvp(Avp.SMSF_NON_3GPP_ABSENT_USER_DIAGNOSTIC_SM, getSMSFNon3gppAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ MPS-Priority ]
    if (getMPSPriority() != -1) {
      avpSet.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    }

    return sra;
  }

  /*
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.8

  The Report-SM-Delivery-Status-Answer (RDA) command, indicated by the Command-Code field set to 8388649
  and the 'R' bit cleared in the Command Flags field, is sent from HSS to SMS-GMSC or IP-SM-GW.

  Message Format
  < Report-SM-Delivery-Status-Answer > ::=< Diameter Header: 8388649, PXY, 16777312 >
                                    < Session-Id >
                                    [ DRMP ]
                                    [ Vendor-Specific-Application-Id ]
                                    [ Result-Code ]
                                    [ Experimental-Result ]
                                    { Auth-Session-State }
                                    { Origin-Host }
                                    { Origin-Realm }
                                   *[ Supported-Features ]
                                    [ User-Identifier ]
                                   *[ AVP ]
                                    [ Failed-AVP ]
                                   *[ Proxy-Info ]
                                   *[ Route-Record ]
   */
  protected ReportSMDeliveryStatusAnswer createRDA(ReportSMDeliveryStatusRequest rdr, long resultCode) throws Exception {
    // < Report-SM-Delivery-Status-Answer > ::=< Diameter Header: 8388649, PXY, 16777312 >
    ReportSMDeliveryStatusAnswer rda = new ReportSMDeliveryStatusAnswerImpl((Request) rdr.getMessage(), resultCode);

    AvpSet reqSet = rdr.getMessage().getAvps();
    AvpSet avpSet = rda.getMessage().getAvps();
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

    // [ User-Identifier ]
    AvpSet userIdentifier = avpSet.addGroupedAvp(Avp.USER_IDENTIFIER, 10415, true, false);
    if (getMSISDN() != null) {
      userIdentifier.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    }
    if (getUserName() != null) {
      userIdentifier.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);
    }
    if (getExternalIdentifier() != null) {
      userIdentifier.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    }

    return rda;
  }

  /*
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.5

  The Alert-Service-Centre-Request (ALR) command, indicated by the Command-Code field set to 8388648
  and the "R" bit set in the Command Flags field,
  is sent from the HSS to the SMS-IWMSC and from the MME or SGSN to the SMS-GMSC (possibly via an SMS Router).

  Message Format
  < Alert-Service-Centre-Request > ::= < Diameter Header: 8388648, REQ, PXY, 16777312 >
                                < Session-Id >
                                [ DRMP ]
                                [ Vendor-Specific-Application-Id ]
                                { Auth-Session-State }
                                { Origin-Host }
                                { Origin-Realm }
                                [ Destination-Host ]
                                { Destination-Realm }
                                { SC-Address }
                                { User-Identifier }
                                [ SMSMI-Correlation-ID ]
                                [ Maximum-UE-Availability-Time ]
                                [ SMS-GMSC-Alert-Event ]
                                [ Serving-Node ]
                               *[ Supported-Features ]
                               *[ AVP ]
                               *[ Proxy-Info ]
                               *[ Route-Record ]
   */
  protected AlertServiceCentreRequest createALR(ServerS6cSession serverS6cSession) throws Exception {
    // < Send-Routing-Info-for-SM-Request > ::= < Diameter Header: 8388647, REQ, PXY, 16777312 >
    AlertServiceCentreRequest alr = new AlertServiceCentreRequestImpl(serverS6cSession.getSessions().get(0).
        createRequest(AlertServiceCentreRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = alr.getMessage().getAvps();

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
    reqSet.addAvp(Avp.DESTINATION_HOST, clientHost, true);
    // { Destination-Realm }
    // getServerRealmName()

    // { SC-Address }
    if (getSCAddress() != null) {
      reqSet.addAvp(Avp.SC_ADDRESS, getSCAddress(), 10415, true, false);
    }

    // { User-Identifier }
    AvpSet userIdentifier = reqSet.addGroupedAvp(Avp.USER_IDENTIFIER, 10415, true, false);
    if (getMSISDN() != null) {
      userIdentifier.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    }
    if (getUserName() != null) {
      userIdentifier.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);
    }
    if (getExternalIdentifier() != null) {
      userIdentifier.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    }


    // [ SMSMI-Correlation-ID ]
    AvpSet smsMiCorrelationID = reqSet.addGroupedAvp(Avp.SMSMI_CORRELATION_ID, 10415, false, false);
    if (getHssId() != null) {
      smsMiCorrelationID.addAvp(Avp.HSS_ID, getHssId(), 10415, false, false, false);
    }
    if (getOriginatingSipUri() != null) {
      smsMiCorrelationID.addAvp(Avp.ORIGINATING_SIP_URI, getOriginatingSipUri(), 10415, false, false, false);
    }
    if (getDestinationSipUri() != null) {
      smsMiCorrelationID.addAvp(Avp.DESTINATION_SIP_URI, getDestinationSipUri(), 10415, false, false, false);
    }

    // [ Maximum-UE-Availability-Time ]
    if (getMaximumUEAvailabilityTime() != null) {
      reqSet.addAvp(Avp.MAXIMUM_UE_AVAILABILITY_TIME, getMaximumUEAvailabilityTime(),10415, false, false);
    }

    // [ SMS-GMSC-Alert-Event ]
    if (getSMSGMSCAlertEvent() > -1) {
      reqSet.addAvp(Avp.SMS_GMSC_ALERT_EVENT, getSMSGMSCAlertEvent(), 10415, false, false, true);
    }

    // [ Serving-Node ]
    AvpSet servingNode = reqSet.addGroupedAvp(Avp.SERVING_NODE, 10415, true, false);
    if (getSGSNNumber() != null) {
      servingNode.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, true, false);
    }
    if (getSGSNName() != null) {
      servingNode.addAvp(Avp.SGSN_NAME, getSGSNName(), 10415, false, false, false);
    }
    if (getSGSNRealm() != null) {
      servingNode.addAvp(Avp.SGSN_REALM, getSGSNRealm(), 10415, false, false, false);
    }
    if (getMMEName() != null) {
      servingNode.addAvp(Avp.MME_NAME, getMMEName(), 10415, true, false, false);
    }
    if (getMMERealm() != null) {
      servingNode.addAvp(Avp.MME_REALM, getMMERealm(), 10415, false, false, false);
    }
    if (getMSCNumber() != null) {
      servingNode.addAvp(Avp.MSC_NUMBER, getMSCNumber(), 10415, true, false);
    }
    if (get3GPPAAAServerName() != null) {
      servingNode.addAvp(Avp.TGPP_AAA_SERVER_NAME, get3GPPAAAServerName(), 10415, true, false, false);
    }
    if (getLCSCapabilitiesSets() > -1) {
      servingNode.addAvp(Avp.LCS_CAPABILITIES_SETS, getLCSCapabilitiesSets(), 10415, true, false, true);
    }
    if (getGMLCAddress() != null) {
      servingNode.addAvp(Avp.GMLC_ADDRESS, getGMLCAddress(), 10415, true, false);
    }

    return alr;
  }
}
