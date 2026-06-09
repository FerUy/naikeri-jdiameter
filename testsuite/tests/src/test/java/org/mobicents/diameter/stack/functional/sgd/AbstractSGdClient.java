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
import org.jdiameter.api.sgd.ClientSGdSessionListener;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.jdiameter.common.impl.app.sgd.MOForwardShortMessageAnswerImpl;
import org.jdiameter.common.impl.app.sgd.MTForwardShortMessageRequestImpl;
import org.jdiameter.common.impl.app.sgd.SGdSessionFactoryImpl;
import org.mobicents.diameter.stack.functional.TBase;

import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractSGdClient extends TBase implements ClientSGdSessionListener {

  // NOTE: implementing NetworkReqListener since it is required for stack to know we support it... ech.

  protected ClientSGdSession clientSGdSession;
  protected ServerSGdSession serverSGdSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777313));
      SGdSessionFactoryImpl sgdSessionFactory = new SGdSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerSGdSession.class, sgdSessionFactory);
      sessionFactory.registerAppFacory(ClientSGdSession.class, sgdSessionFactory);

      sgdSessionFactory.setClientSessionListener(this);

      this.clientSGdSession = (this.sessionFactory).getNewAppSession(this.sessionFactory.getSessionId("xx-SGd-TESTxx"), getApplicationId(),
          ClientSGdSession.class, (Object) null);
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

  public void doMTForwardShortMessageAnswerEvent(ClientSGdSession session, MTForwardShortMessageRequest tfr, MTForwardShortMessageAnswer tfa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"TFA\" event, request[" + tfr + "], answer[" + tfa + "], on session[" + session + "]", null);
  }

  public void doMOForwardShortMessageRequestEvent(ClientSGdSession session, MOForwardShortMessageRequest ofr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    fail("Received \"OFR\" event, request[" + ofr + "], on session[" + session + "]", null);
  }

  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer) throws InternalException, IllegalDiameterStateException,
      RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  // ----------- conf parts

  public String getSessionId() {
    return this.clientSGdSession.getSessionId();
  }

  public ClientSGdSession getSession() {
    return this.clientSGdSession;
  }

  public void fetchSession(String sessionId) throws InternalException {
    this.serverSGdSession = stack.getSession(sessionId, ServerSGdSession.class);
  }

  // Attributes for MT-Forward-Short-Message-Request (TFR) and MO-Forward-Short-Message-Answer (OFA)

  // { User-Name }
  protected abstract String getUserName();

  // [ SMSMI-Correlation-ID ]
  protected abstract String getHssId();
  protected abstract String getOriginatingSipUri();
  protected abstract String getDestinationSipUri();

  // { SC-Address }
  protected abstract byte[] getSCAddress();

  // { SM-RP-UI }
  protected abstract byte[] getSmRpUi();

  // [ MME-Number-for-MT-SMS ]
  protected abstract byte[] getMMENumberForMtSMS();

  // [ SGSN-Number ]
  protected abstract byte[] getSGSNNumber();

  // [ TFR-Flags ]
  protected abstract long getTFRFlags();

  // [ SM-Delivery-Timer ]
  protected abstract int getSMDeliveryTimer();

  // [ SM-Delivery-Start-Time ]
  protected abstract Date getSMDeliveryStartTime();

  // [ Maximum-Retransmission-Time ]
  protected abstract Date getMaximumRetransmissionTime();

  // [ SMS-GMSC-Address ]
  protected abstract byte[] getSmsGMSCAddress();

  // [ MPS-Priority ]
  protected abstract long getMPSPriority();

  // [ SM-Delivery-Failure-Cause ]
  protected abstract int getSMEnumeratedDeliveryFailureCause();
  protected abstract byte[] getSMDiagnosticInfo();

  // [ External-Identifier ]
  protected abstract String getExternalIdentifier();

  /*
  < MT-Forward-Short-Message-Request > ::= < Diameter Header: 8388646, REQ, PXY, 16777313 >
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
                                  *[ SMSMI-Correlation-ID ]
                                   { SC-Address }
                                   { SM-RP-UI }
                                   [ MME-Number-for-MT-SMS ]
                                   [ SGSN-Number ]
                                   [ TFR-Flags ]
                                   [ SM-Delivery-Timer ]
                                   [ SM-Delivery-Start-Time ]
                                   [ Maximum-Retransmission-Time ]
                                   [ SMS-GMSC-Address ]
                                   [ MPS-Priority ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected MTForwardShortMessageRequest createTFR(ClientSGdSession clientSGdSession) throws Exception {
    // < MT-Forward-Short-Message-Request > ::= < Diameter Header: 8388646, REQ, PXY, 16777313 >
    MTForwardShortMessageRequest tfr = new MTForwardShortMessageRequestImpl(clientSGdSession.getSessions().get(0).
        createRequest(MTForwardShortMessageRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = tfr.getMessage().getAvps();

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

    // { SC-Address }
    if (getSCAddress() != null) {
      reqSet.addAvp(Avp.SC_ADDRESS, getSCAddress(), 10415, true, false);
    }

    // { SM-RP-UI }
    if (getSmRpUi() != null) {
      reqSet.addAvp(Avp.SM_RP_UI, getSmRpUi(), 10415, true, false);
    }

    // [ MME-Number-for-MT-SMS ]
    if (getMMENumberForMtSMS() != null) {
      reqSet.addAvp(Avp.MME_NUMBER_FOR_MT_SMS, getMMENumberForMtSMS(), 10415, false, false);
    }

    // [ SGSN-Number ]
    if (getSGSNNumber() != null) {
      reqSet.addAvp(Avp.SGSN_NUMBER, getSGSNNumber(), 10415, true, false);
    }

    // [ TFR-Flags ]
    if (getTFRFlags() != -1) {
      reqSet.addAvp(Avp.TFR_FLAGS, getTFRFlags(), 10415, true, false, true);
    }

    // [ SM-Delivery-Timer ]
    if (getSMDeliveryTimer() != -1) {
      reqSet.addAvp(Avp.SM_DELIVERY_TIMER, getSMDeliveryTimer(), 10415, true, false, true);
    }

    // [ SM-Delivery-Start-Time ]
    if (getSMDeliveryStartTime() != null) {
      reqSet.addAvp(Avp.SM_DELIVERY_START_TIME, getSMDeliveryStartTime(), 10415, true, false);
    }

    // [ Maximum-Retransmission-Time ]
    if (getMaximumRetransmissionTime() != null) {
      reqSet.addAvp(Avp.MAXIMUM_RETRANSMISSION_TIME, getMaximumRetransmissionTime(), 10415, false, false);
    }

    // [ SMS-GMSC-Address ]
    if (getSmsGMSCAddress() != null) {
      reqSet.addAvp(Avp.SMS_GMSC_ADDRESS, getSmsGMSCAddress(), 10415, false, false);
    }

    if (getMPSPriority() != -1) {
      reqSet.addAvp(Avp.MPS_PRIORITY, getMPSPriority(), 10415, false, false, true);
    }

    return tfr;
  }

  /*
  < MO-Forward-Short-Message-Answer > ::= < Diameter Header: 8388645, PXY, 16777313 >
                                  < Session-Id >
                                  [ DRMP ]
                                  [ Vendor-Specific-Application-Id ]
                                  [ Result-Code ]
                                  [ Experimental-Result ]
                                  { Auth-Session-State }
                                  { Origin-Host }
                                 *[ Supported-Features ]
                                  [ SM-Delivery-Failure-Cause ]
                                  [ SM-RP-UI ]
                                  [ External-Identifier ]
                                 *[ AVP ]
                                  [ Failed-AVP ]
                                 *[ Proxy-Info ]
                                 *[ Route-Record ]
   */
  protected MOForwardShortMessageAnswer createOFA(MOForwardShortMessageRequest ofr, long resultCode) throws Exception{
    // < MO-Forward-Short-Message-Answer > ::= < Diameter Header: 8388645, PXY, 16777313 >
    MOForwardShortMessageAnswer ofa = new MOForwardShortMessageAnswerImpl((Request) ofr.getMessage(), resultCode);

    AvpSet reqSet = ofr.getMessage().getAvps();
    AvpSet avpSet = ofa.getMessage().getAvps();
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

    // [ SM-Delivery-Failure-Cause ]
    AvpSet smDeliveryFailureCause = avpSet.addGroupedAvp(Avp.SM_DELIVERY_FAILURE_CAUSE, 10415, true, false);
    if (getSMEnumeratedDeliveryFailureCause() != -1) {
      smDeliveryFailureCause.addAvp(Avp.SM_ENUMERATED_DELIVERY_FAILURE_CAUSE, getSMEnumeratedDeliveryFailureCause(), 10415, true, false);
    }
    if (getSMDiagnosticInfo() != null) {
      smDeliveryFailureCause.addAvp(Avp.SM_DIAGNOSTIC_INFO, getSMDiagnosticInfo(), 10415, true, false);
    }

    // // [ External-Identifier ]
    if (getExternalIdentifier() != null) {
      avpSet.addAvp(Avp.EXTERNAL_IDENTIFIER, getExternalIdentifier(), 10415, true, false, false);
    }

    return ofa;
  }

}
