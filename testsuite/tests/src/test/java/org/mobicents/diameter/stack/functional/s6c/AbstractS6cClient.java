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
import org.jdiameter.api.s6c.ClientS6cSessionListener;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.common.impl.app.s6c.AlertServiceCentreAnswerImpl;
import org.jdiameter.common.impl.app.s6c.ReportSMDeliveryStatusRequestImpl;
import org.jdiameter.common.impl.app.s6c.S6cSessionFactoryImpl;
import org.jdiameter.common.impl.app.s6c.SendRoutingInfoForSMRequestImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractS6cClient extends TBase implements ClientS6cSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ClientS6cSession clientS6cSession;
  protected ServerS6cSession serverS6cSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777312));
      S6cSessionFactoryImpl s6cSessionFactory = new S6cSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerS6cSession.class, s6cSessionFactory);
      sessionFactory.registerAppFacory(ClientS6cSession.class, s6cSessionFactory);

      s6cSessionFactory.setClientSessionListener(this);

      this.clientS6cSession = (this.sessionFactory).getNewAppSession(this.sessionFactory.getSessionId("xx-S6c-TESTxx"), getApplicationId(),
          ClientS6cSession.class, (Object) null);
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

  public void doSendRoutingInfoForSMAnswerEvent(ClientS6cSession session, SendRoutingInfoForSMRequest srr, SendRoutingInfoForSMAnswer sra)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"SRA\" event, request[" + srr + "], answer[" + sra + "], on session[" + session + "]", null);
  }

  public void doReportSMDeliveryStatusAnswerEvent(ClientS6cSession session, ReportSMDeliveryStatusRequest rdr, ReportSMDeliveryStatusAnswer rda)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"RDA\" event, request[" + rdr + "], answer[" + rda + "], on session[" + session + "]", null);
  }

  public void doAlertServiceCentreRequestEvent(ClientS6cSession session, AlertServiceCentreRequest alr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"ALR\" event, request[" + alr + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // ----------- conf parts

  public String getSessionId() {
    return this.clientS6cSession.getSessionId();
  }

  public ClientS6cSession getSession() {
    return this.clientS6cSession;
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverS6cSession = stack.getSession(sessionId, ServerS6cSession.class);
  }

  // Attributes for Send-Routing-Info-for-SM-Request (SRR), Report-SM-Delivery-Status-Request (RDR)
  // and Alert-Service-Centre-Answer (ALA)

  // [ MSISDN ]
  protected abstract byte[] getMSISDN();

  // [ User-Name ]
  protected abstract String getUserName();

  // [ SMSMI-Correlation-ID ]
  protected abstract String getHssId();
  protected abstract String getOriginatingSipUri();
  protected abstract String getDestinationSipUri();

  // [ SC-Address ]
  protected abstract byte[] getSCAddress();

  // [ SM-RP-MTI ]
  protected abstract int getSM_RP_MTI();

  // [ SM-RP-SMEA ]
  protected abstract byte[] getSM_RP_SMEA();

  // [ SRR-Flags ]
  protected abstract long getSRRFlags();

  // [ SM-Delivery-Not-Intended ]
  protected abstract int getSMDeliveryNotIntended();

  // { User-Identifier }
  protected abstract String getExternalIdentifier();
  protected abstract byte[] getLMSI();

  // { SM-Delivery-Outcome }
  protected abstract HashMap<Integer, Long> getMmeSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getMscSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSgsnSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getIpSmGwSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSmsf3gppSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSmsfNon3gppSmDeliveryOutcome();
  protected abstract int getSMDeliveryCause();
  protected abstract long getAbsentUserDiagnosticSM();

  // [ RDR-Flags ]
  protected abstract long getRDRFlags();

  /*
   3GPP TS 29.338 V19.1.0 § 5.3.2.3

   The Send-Routing-Info-for-SM-Request (SRR) command, indicated by the Command-Code field set to 8388647
   and the "R" bit set in the Command Flags field, is sent from SMS-GMSC to HSS or SMS Router or from SMS Router to HSS

   Message Format
   < Send-Routing-Info-for-SM-Request > ::= < Diameter Header: 8388647, REQ, PXY, 16777312 >
                                     < Session-Id >
                                     [ DRMP ]
                                     [ Vendor-Specific-Application-Id ]
                                     { Auth-Session-State }
                                     { Origin-Host }
                                     { Origin-Realm }
                                     [ Destination-Host ]
                                     { Destination-Realm }
                                     [ MSISDN ]
                                     [ User-Name ]
                                     [ SMSMI-Correlation-ID ]
                                    *[ Supported-Features ]
                                     [ SC-Address ]
                                     [ SM-RP-MTI ]
                                     [ SM-RP-SMEA ]
                                     [ SRR-Flags ]
                                     [ SM-Delivery-Not-Intended ]
                                    *[ AVP ]
                                    *[ Proxy-Info ]
                                    *[ Route-Record ]



   */
  protected SendRoutingInfoForSMRequest createSRR(ClientS6cSession clientS6cSession) throws Exception {
    // < Send-Routing-Info-for-SM-Request > ::= < Diameter Header: 8388647, REQ, PXY, 16777312 >
    SendRoutingInfoForSMRequest srr = new SendRoutingInfoForSMRequestImpl(clientS6cSession.getSessions().get(0).
        createRequest(SendRoutingInfoForSMRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = srr.getMessage().getAvps();

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

    // [ MSISDN ]
    if (getMSISDN() != null) {
      reqSet.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    }

    // [ User-Name ] IE: IMSI
    if (getUserName() != null) {
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);
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

    // [ SC-Address ]
    if (getSCAddress() != null) {
      reqSet.addAvp(Avp.SC_ADDRESS, getSCAddress(), 10415, true, false);
    }

    // [ SM-RP-MTI ]
    if (getSM_RP_MTI() > -1) {
      reqSet.addAvp(Avp.SM_RP_MTI, getSM_RP_MTI(), 10415, true, false);
    }

    // [ SM-RP-SMEA ]
    if (getSM_RP_SMEA() != null) {
      reqSet.addAvp(Avp.SM_RP_SMEA, getSM_RP_SMEA(), 10415, true, false);
    }

    // [ SRR-Flags ]
    if (getSRRFlags() > -1) {
      reqSet.addAvp(Avp.SRR_FLAGS, getSRRFlags(), 10415, true, false, true);
    }

    // [ SM-Delivery-Not-Intended ]
    if (getSMDeliveryNotIntended() > -1) {
      reqSet.addAvp(Avp.SM_DELIVERY_NOT_INTENDED, getSMDeliveryNotIntended(), 10415, true, false);
    }

    return srr;
  }

  /*
   3GPP TS 29.338 V19.1.0 § 5.3.2.7

   The Report-SM-Delivery-Status-Request (RDR) command, indicated by the Command-Code field set to 8388649
   and the "R" bit set in the Command Flags field, is sent from SMS-GMSC or IP-SM-GW to HSS.

   Message Format:
   < Report-SM-Delivery-Status-Request > ::= < Diameter Header: 8388649, REQ, PXY, 16777312 >
                                     < Session-Id >
                                     [ DRMP ]
                                     [ Vendor-Specific-Application-Id ]
                                     { Auth-Session-State }
                                     { Origin-Host }
                                     { Origin-Realm }
                                     [ Destination-Host ]
                                     { Destination-Realm }
                                    *[ Supported-Features ]
                                     { User-Identifier }
                                     [ SMSMI-Correlation-ID ]
                                     { SC-Address }
                                     { SM-Delivery-Outcome }
                                     [ RDR-Flags ]
                                    *[ AVP ]
                                    *[ Proxy-Info ]
                                    *[ Route-Record ]
   */
  protected ReportSMDeliveryStatusRequest createRDR(ClientS6cSession clientS6cSession) throws Exception {
    // < Report-SM-Delivery-Status-Request > ::= < Diameter Header: 8388649, REQ, PXY, 16777312 >
    ReportSMDeliveryStatusRequest rdr = new ReportSMDeliveryStatusRequestImpl(clientS6cSession.getSessions().get(0).
        createRequest(ReportSMDeliveryStatusRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = rdr.getMessage().getAvps();

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

    /// { Origin-Host }
    // { Origin-Realm }

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, serverHost, true);
    // { Destination-Realm }
    // getServerRealmName()

    // { User-Identifier }
    AvpSet userIdentifier = reqSet.addGroupedAvp(Avp.USER_IDENTIFIER, 10415, true, false);
    if (getMSISDN() != null) {
      userIdentifier.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);
    }
    if (getUserName() != null) {
      userIdentifier.addAvp(Avp.USER_NAME, getUserName(), 10415, true, false, false);
    }
    if (getExternalIdentifier() != null) {
      userIdentifier.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    }
    if (getLMSI() != null) {
      userIdentifier.addAvp(Avp.LMSI, getLMSI(), 10415, true, false);
    }

    //  [ SMSMI-Correlation-ID ]
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

    // { SC-Address }
    if (getSCAddress() != null) {
      reqSet.addAvp(Avp.SC_ADDRESS, getSCAddress(), 10415, true, false);
    }

    // { SM-Delivery-Outcome }
    AvpSet smDeliveryOutcome = reqSet.addGroupedAvp(Avp.SM_DELIVERY_OUTCOME, 10415, false, false);
    if (getMmeSmDeliveryOutcome() != null) {
      AvpSet mmeSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.MME_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        mmeSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        mmeSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }
    if (getMscSmDeliveryOutcome() != null) {
      AvpSet mscSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.MSC_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        mscSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        mscSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }
    if (getSgsnSmDeliveryOutcome() != null) {
      AvpSet sgsnSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.SGSN_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        sgsnSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        sgsnSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }
    if (getIpSmGwSmDeliveryOutcome() != null) {
      AvpSet ipSmGwSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.IP_SM_GW_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        ipSmGwSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        ipSmGwSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }
    if (getSmsf3gppSmDeliveryOutcome() != null) {
      AvpSet smsf3gppSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.SMSF_3GPP_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        smsf3gppSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        smsf3gppSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }
    if (getSmsfNon3gppSmDeliveryOutcome() != null) {
      AvpSet smsfNon3gppSmDeliveryOutcome = smDeliveryOutcome.addGroupedAvp(Avp.SMSF_NON_3GPP_SM_DELIVERY_OUTCOME, 10415, false, false);
      if (getSMDeliveryCause() > -1) {
        smsfNon3gppSmDeliveryOutcome.addAvp(Avp.SM_DELIVERY_CAUSE, getSMDeliveryCause(), 10415, true, false);
      }
      if (getAbsentUserDiagnosticSM() > -1) {
        smsfNon3gppSmDeliveryOutcome.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
      }
    }

    // [ RDR-Flags ]
    if (getRDRFlags() != -1) {
      reqSet.addAvp(Avp.RDR_FLAGS, getRDRFlags(), 10415, false, false, true);
    }

    return rdr;
  }

  /*
  3GPP TS 29.338 V19.1.0 § 5.3.2.6

  The Alert-Service-Centre-Answer (ALA) command, indicated by the Command-Code field set to 8388648
  and the 'R' bit cleared in the Command Flags field,
  is sent from the SMS-IWMSC to the HSS and from the SMS-GMSC to the MME or SGSN (possibly via an SMS Router).

  Message Format
  < Alert-Service-Centre-Answer > ::= < Diameter Header: 8388648, PXY, 16777312 >
                               < Session-Id >
                               [ DRMP ]
                               [ Vendor-Specific-Application-Id ]
                               [ Result-Code ]
                               [ Experimental-Result ]
                               { Auth-Session-State }
                               { Origin-Host }
                               { Origin-Realm }
                              *[ Supported-Features ]
                              *[ AVP ]
                               [ Failed-AVP ]
                              *[ Proxy-Info ]
                              *[ Route-Record ]
   */
  protected AlertServiceCentreAnswer createALA(AlertServiceCentreRequest alr, long resultCode) throws Exception {
    // < Alert-Service-Centre-Answer > ::= < Diameter Header: 8388648, PXY, 16777312 >
    AlertServiceCentreAnswer ala = new AlertServiceCentreAnswerImpl((Request) alr.getMessage(), resultCode);

    AvpSet reqSet = alr.getMessage().getAvps();
    AvpSet avpSet = ala.getMessage().getAvps();
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

    return ala;
  }
}
