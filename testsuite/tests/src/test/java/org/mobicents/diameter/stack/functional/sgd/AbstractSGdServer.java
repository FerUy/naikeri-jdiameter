package org.mobicents.diameter.stack.functional.sgd;

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
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.api.sgd.ServerSGdSessionListener;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.jdiameter.common.impl.app.sgd.MOForwardShortMessageRequestImpl;
import org.jdiameter.common.impl.app.sgd.MTForwardShortMessageAnswerImpl;
import org.jdiameter.common.impl.app.sgd.SGdSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *@author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractSGdServer extends TBase implements ServerSGdSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ServerSGdSession serverSGdSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777313));
      SGdSessionFactoryImpl s6SessionFactory = new SGdSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerSGdSession.class, s6SessionFactory);
      sessionFactory.registerAppFacory(ClientSGdSession.class, s6SessionFactory);
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

  public void doMTForwardShortMessageRequestEvent(ServerSGdSession session, MTForwardShortMessageRequest tfr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"TFR\" event, request[" + tfr + "], on session[" + session + "]", null);
  }

  public void doMOForwardShortMessageAnswerEvent(ServerSGdSession session, MOForwardShortMessageRequest ofr, MOForwardShortMessageAnswer ofa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"OFA\" event, request[" + ofr + "], answer[" + ofa + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException,
      OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // -------- conf

  public String getSessionId() {
    return this.serverSGdSession.getSessionId();
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverSGdSession = stack.getSession(sessionId, ServerSGdSession.class);
  }

  public ServerSGdSession getSession() {
    return this.serverSGdSession;
  }


  // Attributes for MO-Forward-Short-Message-Request (OFR) and MT-Forward-Short-Message-Answer (TFA)

  // { SC-Address }
  protected abstract byte[] getSCAddress();

  // [ OFR-Flags ]
  protected abstract long getOFRFlags();

  // { User-Identifier }
  protected abstract byte[] getMSISDN();
  protected abstract String getUserName();
  protected abstract String getExternalIdentifier();
  protected abstract byte[] getLMSI();

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

  // [ NR-Cell-Global-Identity ]
  protected abstract byte[] getNRCellGlobalIdentity();

  // { SM-RP-UI }
  protected abstract byte[] getSmRpUi();

  // [ SMSMI-Correlation-ID ]
  protected abstract String getHssId();
  protected abstract String getOriginatingSipUri();
  protected abstract String getDestinationSipUri();

  // { SM-Delivery-Outcome }
  protected abstract HashMap<Integer, Long> getMmeSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getMscSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSgsnSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getIpSmGwSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSmsf3gppSmDeliveryOutcome();
  protected abstract HashMap<Integer, Long> getSmsfNon3gppSmDeliveryOutcome();
  protected abstract int getSMDeliveryCause();
  protected abstract long getAbsentUserDiagnosticSM();

  // [ MPS-Priority]
  protected abstract long getMPSPriority();

  // [ SM-Delivery-Failure-Cause ]
  protected abstract int getSMEnumeratedDeliveryFailureCause();
  protected abstract byte[] getSMDiagnosticInfo();

  // [ Requested-Retransmission-Time ]
  protected abstract Date getRequestedRetransmissionTime();

  /*
  < MO-Forward-Short-Message-Request > ::= < Diameter Header: 8388645, REQ, PXY, 16777313 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ Destination-Host ]
                                   { Destination-Realm }
                                   { SC-Address }
                                   [ OFR-Flags ]
                                  *[ Supported-Features ]
                                   { User-Identifier }
                                   [ EPS-Location-Information ]
                                   [ NR-Cell-Global-Identity ]
                                   { SM-RP-UI }
                                   [ SMSMI-Correlation-ID ]
                                   [ SM-Delivery-Outcome ]
                                   [ MPS-Priority ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected MOForwardShortMessageRequest createOFR(ServerSGdSession serverSGdSession) throws Exception {
    // < MO-Forward-Short-Message-Request > ::= < Diameter Header: 8388645, REQ, PXY, 16777313 >
    MOForwardShortMessageRequest ofr = new MOForwardShortMessageRequestImpl(serverSGdSession.getSessions().get(0).
        createRequest(MOForwardShortMessageRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = ofr.getMessage().getAvps();

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

    // [ OFR-Flags ]
    if (getOFRFlags() != -1) {
      reqSet.addAvp(Avp.TFR_FLAGS, getOFRFlags(), 10415, true, false, true);
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
    if (getLMSI() != null) {
      userIdentifier.addAvp(Avp.LMSI, getLMSI(), 10415, true, false);
    }

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
    if (getENodeBId() != null) {
      mmeLocationInformation.addAvp(Avp.E_NODE_B_ID, getENodeBId(), 10415, false, true);
    }
    if (getExtendedENodeBId() != null) {
      mmeLocationInformation.addAvp(Avp.EXTENDED_E_NODE_B_ID, getExtendedENodeBId(), 10415, false, true);
    }
    if (getCellGlobalIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, true);
    }
    if (getLocationAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    }
    if (getServiceAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, true);
    }
    if (getRoutingAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    }

    // [ NR-Cell-Global-Identity ]
    if (getNRCellGlobalIdentity() != null) {
      reqSet.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, false);
    }

    // { SM-RP-UI }
    if (getSmRpUi() != null) {
      reqSet.addAvp(Avp.SM_RP_UI, getSmRpUi(), 10415, true, false);
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

    // [ MPS-Priority]
    if (getMPSPriority() != -1) {
      reqSet.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    }

    return ofr;
  }

  /*
  < MT-Forward-Short-Message-Answer > ::= < Diameter Header: 8388646, PXY, 16777313 >
                                  < Session-Id >
                                  [ DRMP ]
                                  [ Vendor-Specific-Application-Id ]
                                  [ Result-Code ]
                                  [ Experimental-Result ]
                                  { Auth-Session-State }
                                  { Origin-Host }
                                  { Origin-Realm }
                                 *[ Supported-Features ]
                                  [ Absent-User-Diagnostic-SM ]
                                  [ SM-Delivery-Failure-Cause ]
                                  [ SM-RP-UI ]
                                  [ Requested-Retransmission-Time ]
                                  [ User-Identifier ]
                                  [ EPS-Location-Information ]
                                  [ NR-Cell-Global-Identity ]
                                 *[ AVP ]
                                  [ Failed-AVP ]
                                 *[ Proxy-Info ]
                                 *[ Route-Record ]
  7 */
  protected MTForwardShortMessageAnswer createTFA(MTForwardShortMessageRequest tfr, long resultCode) throws Exception {
    // < MT-Forward-Short-Message-Answer > ::= < Diameter Header: 8388646, PXY, 16777313 >
    MTForwardShortMessageAnswer tfa = new MTForwardShortMessageAnswerImpl((Request) tfr.getMessage(), resultCode);

    AvpSet reqSet = tfr.getMessage().getAvps();
    AvpSet avpSet = tfa.getMessage().getAvps();
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

    // [ Absent-User-Diagnostic-SM ]
    if (getAbsentUserDiagnosticSM() != -1) {
      avpSet.addAvp(Avp.ABSENT_USER_DIAGNOSTIC_SM, getAbsentUserDiagnosticSM(), 10415, true, false, true);
    }

    // [ SM-Delivery-Failure-Cause ]
    AvpSet smDeliveryFailureCause = avpSet.addGroupedAvp(Avp.SM_DELIVERY_FAILURE_CAUSE, 10415, true, false);
    if (getSMEnumeratedDeliveryFailureCause() != -1) {
      smDeliveryFailureCause.addAvp(Avp.SM_ENUMERATED_DELIVERY_FAILURE_CAUSE, getSMEnumeratedDeliveryFailureCause(), 10415, true, false);
    }
    if (getSMDiagnosticInfo() != null) {
      smDeliveryFailureCause.addAvp(Avp.SM_DIAGNOSTIC_INFO, getSMDiagnosticInfo(), 10415, true, false);
    }

    // [ SM-RP-UI ]
    if (getSmRpUi() != null) {
      avpSet.addAvp(Avp.SM_RP_UI, getSmRpUi(), 10415, true, false);
    }

    // [ Requested-Retransmission-Time ]
    if (getRequestedRetransmissionTime() != null) {
      avpSet.addAvp(Avp.REQUESTED_RETRANSMISSION_TIME, getRequestedRetransmissionTime(), 10415, false, false);
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
    if (getLMSI() != null) {
      userIdentifier.addAvp(Avp.LMSI, getLMSI(), 10415, true, false);
    }

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
    if (getENodeBId() != null) {
      mmeLocationInformation.addAvp(Avp.E_NODE_B_ID, getENodeBId(), 10415, false, true);
    }
    if (getExtendedENodeBId() != null) {
      mmeLocationInformation.addAvp(Avp.EXTENDED_E_NODE_B_ID, getExtendedENodeBId(), 10415, false, true);
    }
    if (getCellGlobalIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.CELL_GLOBAL_IDENTITY, getCellGlobalIdentity(), 10415, false, true);
    }
    if (getLocationAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.LOCATION_AREA_IDENTITY, getLocationAreaIdentity(), 10415, false, true);
    }
    if (getServiceAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.SERVICE_AREA_IDENTITY, getServiceAreaIdentity(), 10415, false, true);
    }
    if (getRoutingAreaIdentity() != null) {
      sgsnLocationInformation.addAvp(Avp.ROUTING_AREA_IDENTITY, getRoutingAreaIdentity(), 10415, false, true);
    }

    // [ NR-Cell-Global-Identity ]
    if (getNRCellGlobalIdentity() != null) {
      avpSet.addAvp(Avp.NR_CELL_GLOBAL_IDENTITY, getNRCellGlobalIdentity(), 10415, false, false);
    }

    return tfa;
  }

}
