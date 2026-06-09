package org.mobicents.diameter.stack.functional.sh;

import java.io.InputStream;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.api.Request;
import org.jdiameter.api.sh.ClientShSession;
import org.jdiameter.api.sh.ClientShSessionListener;
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationAnswer;
import org.jdiameter.api.sh.events.PushNotificationRequest;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataRequest;
import org.jdiameter.common.impl.app.sh.ProfileUpdateRequestImpl;
import org.jdiameter.common.impl.app.sh.PushNotificationAnswerImpl;
import org.jdiameter.common.impl.app.sh.ShSessionFactoryImpl;
import org.jdiameter.common.impl.app.sh.SubscribeNotificationsRequestImpl;
import org.jdiameter.common.impl.app.sh.UserDataRequestImpl;
import org.mobicents.diameter.stack.functional.TBase;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractShClient extends TBase implements ClientShSessionListener {

  // NOTE: implementing NetworkReqListener since it's required for stack to know we support it... ech.

  protected ClientShSession clientShSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777217));
      ShSessionFactoryImpl shSessionFactory = new ShSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerShSession.class, shSessionFactory);
      sessionFactory.registerAppFacory(ClientShSession.class, shSessionFactory);

      shSessionFactory.setClientShSessionListener(this);

      this.clientShSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-Sh-TESTxx"),
          getApplicationId(), ClientShSession.class, (Object) null);
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

  // ----------- conf parts

  public String getSessionId() {
    return this.clientShSession.getSessionId();
  }

  public ClientShSession getSession() {
    return this.clientShSession;
  }

  /** Attributes for User-Data-Request (UDR), Profile-Update-Request (PUR),
  Subscribe-Notifications-Request (SNR) and Push-Notifications-Answer (PNA) **/

  // *[ Supported-Features ]
  protected abstract long getVendorId();
  protected abstract long getFeatureListID();
  protected abstract long getFeatureList();

  // { User-Identity }
  protected abstract String getPublicIdentity();
  protected abstract byte[] getMSISDN();

  // [ Wildcarded-Public-Identity ]
  protected abstract String getWildcardedPublicIdentity();

  // [ Wildcarded-IMPU ]
  protected abstract String getWildcardedIMPU();

  // [ Server-Name ]
  protected abstract String getServerName();

  // *[ Service-Indication ]
  protected abstract byte[] getServiceIndication();

  // *{ Data-Reference }
  protected abstract int getDataReference();

  // *[ Identity-Set ]
  protected abstract int getIdentitySet();

  // [ Requested-Domain ]
  protected abstract int getRequestedDomain();

  // [ Current-Location ]
  protected abstract int getCurrentLocation();

  // *[ DSAI-Tag ]
  protected abstract byte[] getDSAITag();

  // [ Session-Priority ]
  protected abstract int getSessionPriority();

  // [ User-Name ]
  protected abstract String getUserName();

  // [ Requested-Nodes ]
  protected abstract long getRequestedNodes();

  // [ Serving-Node-Indication ]
  protected abstract int getServingNodeIndication();

  // [ Pre-paging-Supported ]
  protected abstract int getPrePagingSupported();

  // [ Local-Time-Zone-Indication ]
  protected abstract int getLocalTimeZoneIndication();

  // [ UDR-Flags ]
  protected abstract long getUDRFlags();

  // [ Call-Reference-Info ]
  protected abstract byte[] getCallReferenceNumber();
  protected abstract byte[] getAsNumber();

  // [ OC-Supported-Features ]
  protected abstract long getOCFeatureVector();
  protected abstract String getSourceID();
  protected abstract long getOCPeerAlgo();


  // { User-Data }
  protected abstract byte[] getUserData();

  // [ Send-Data-Indication ]
  protected abstract int getSendDataIndication();

  // Subs-Req-Type
  protected abstract int getSubsReqType();

  // [ Expiry-Time ]
  protected abstract Time getExpiryTime();

  // [ One-Time-Notification ]
  protected abstract int getOneTimeNotification();


  /*
    3GPP TS 29.329 v18.0.0 § 6.1.1

      The User-Data-Request (UDR) command, indicated by the Command-Code field set to 306 and the ‘R’ bit set in the Command Flags field,
      is sent by a Diameter client to a Diameter server in order to request user data.

      Message Format
        < User-Data -Request> ::=	< Diameter Header: 306, REQ, PXY, 16777217 >
                                        < Session-Id >
                                        [ DRMP ]
                                        { Vendor-Specific-Application-Id }
                                        { Auth-Session-State }
                                        { Origin-Host }
                                        { Origin-Realm }
                                        [ Destination-Host ]
                                        { Destination-Realm }
                                        *[ Supported-Features ]
                                        { User-Identity }
                                        [ Wildcarded-Public-Identity ]
                                        [ Wildcarded-IMPU ]
                                        [ Server-Name ]
                                        *[ Service-Indication ]
                                        *{ Data-Reference }
                                        *[ Identity-Set ]
                                        [ Requested-Domain ]
                                        [ Current-Location ]
                                        *[ DSAI-Tag ]
                                        [ Session-Priority ]
                                        [ User-Name ]
                                        [ Requested-Nodes ]
                                        [ Serving-Node-Indication ]
                                        [ Pre-paging-Supported ]
                                        [ Local-Time-Zone-Indication ]
                                        [ UDR-Flags ]
                                        [ Call-Reference-Info ]
                                        [ OC-Supported-Features ]
                                        *[ AVP ]
                                        *[ Proxy-Info ]
                                        *[ Route-Record ]
    */
  protected UserDataRequest createUDR(ClientShSession clientShSession) throws Exception {
    // < User-Data -Request> ::= < Diameter Header: 306, REQ, PXY, 16777217 >
    UserDataRequest udr = new UserDataRequestImpl(clientShSession.getSessions().get(0).
        createRequest(UserDataRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = udr.getMessage().getAvps();

    if (reqSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = reqSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // { Auth-Session-State }
    if (reqSet.getAvp(Avp.AUTH_SESSION_STATE) == null)
      reqSet.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // { Origin-Host }
    reqSet.removeAvp(Avp.ORIGIN_HOST);
    reqSet.addAvp(Avp.ORIGIN_HOST, getClientURI(), true);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // { User-Identity }
    AvpSet userIdentity = reqSet.addGroupedAvp(Avp.USER_IDENTITY, 10415, true, false);
    if (getPublicIdentity() != null)
      userIdentity.addAvp(Avp.PUBLIC_IDENTITY, getPublicIdentity(), 10415, false, false, false);
    if (getMSISDN() != null)
      userIdentity.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      reqSet.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      reqSet.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // [ Server-Name ]
    if (getServerName() != null)
      reqSet.addAvp(Avp.SERVER_NAME, getServerName(), 10415, false, false, false);

    // [ Service-Indication ]
    if (getServiceIndication() != null)
      reqSet.addAvp(Avp.SERVICE_INDICATION, getServiceIndication(), 10415, false, false);

    // { Data-Reference }
    if (getDataReference() != -1)
      reqSet.addAvp(Avp.DATA_REFERENCE, getDataReference(), 10415, true, false);

    // [ Identity-Set ]
    if (getIdentitySet() != -1)
      reqSet.addAvp(Avp.IDENTITY_SET, getIdentitySet(), 10415, false, false);

    // [ Requested-Domain ]
    if (getRequestedDomain() != -1)
      reqSet.addAvp(Avp.REQUESTED_DOMAIN, getRequestedDomain(), 10415, false, false);

    // [ Current-Location ]
    if (getCurrentLocation() != -1)
      reqSet.addAvp(Avp.CURRENT_LOCATION, getCurrentLocation(), 10415, false, false);

    // [ DSAI-Tag ]
    if (getDSAITag() != null)
      reqSet.addAvp(Avp.DSAI_TAG, getDSAITag(), 10415, false, true);

    // [ Session-Priority ]
    if (getSessionPriority() != -1)
      reqSet.addAvp(Avp.SESSION_PRIORITY, getSessionPriority(), 10415, false, false);

    // [ User-Name ]
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // [ Requested-Nodes ]
    if (getRequestedNodes() != -1)
      reqSet.addAvp(Avp.REQUESTED_NODES, getRequestedNodes(), 10415, false, false, true);

    // [ Serving-Node-Indication ]
    if (getServingNodeIndication() != -1)
      reqSet.addAvp(Avp.SERVING_NODE_INDICATION, getServingNodeIndication(), 10415, false, false);

    // [ Pre-paging-Supported ]
    if (getPrePagingSupported() != -1)
      reqSet.addAvp(Avp.PRE_PAGING_SUPPORTED, getPrePagingSupported(), 10415, false, false);

    // [ Local-Time-Zone-Indication ]
    if (getLocalTimeZoneIndication() != -1)
      reqSet.addAvp(Avp.LOCAL_TIME_ZONE_INDICATION, getLocalTimeZoneIndication(), 10415, false, false);

    // [ UDR-Flags ]
    if (getUDRFlags() != -1)
      reqSet.addAvp(Avp.UDR_FLAGS, getUDRFlags(), 10415, false, false, true);

    // [ Call-Reference-Info ]
    AvpSet callReferenceInfo = reqSet.addGroupedAvp(Avp.CALL_REFERENCE_INFO, 10415, true, false);
    if (getCallReferenceNumber() != null)
      callReferenceInfo.addAvp(Avp.CALL_REFERENCE_NUMBER, getCallReferenceNumber(), 10415, false, false);
    if (getAsNumber() != null)
      callReferenceInfo.addAvp(Avp.AS_NUMBER, getAsNumber(), 10415, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    return udr;
  }


  /*
  3GPP TS 29.329 v18.0.0 § 6.1.3

    The Profile-Update-Request (PUR) command, indicated by the Command-Code field set to 307 and the 'R' bit set in the Command Flags field, is sent by a Diameter client to a Diameter server in order to update user data in the server.
    Message Format
    < Profile-Update-Request > ::= < Diameter Header: 307, REQ, PXY, 16777217 >
                                   < Session-Id >
                                   [ DRMP ]
                                   { Vendor-Specific-Application-Id }
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ Destination-Host ]
                                   { Destination-Realm }
                                  *[ Supported-Features ]
                                   { User-Identity }
                                   [ Wildcarded-Public-Identity ]
                                   [ Wildcarded-IMPU ]
                                   [ User-Name ]
                                  *{ Data-Reference }
                                   { User-Data }
                                   [ OC-Supported-Features ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
   */
  protected ProfileUpdateRequest createPUR(ClientShSession clientShSession) throws Exception {
    // < Profile-Update-Request > ::= < Diameter Header: 307, REQ, PXY, 16777217 >
    ProfileUpdateRequest pur = new ProfileUpdateRequestImpl(clientShSession.getSessions().get(0).
        createRequest(ProfileUpdateRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = pur.getMessage().getAvps();

    if (reqSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = reqSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // { Auth-Session-State }
    if (reqSet.getAvp(Avp.AUTH_SESSION_STATE) == null)
      reqSet.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // { Origin-Host }
    reqSet.removeAvp(Avp.ORIGIN_HOST);
    reqSet.addAvp(Avp.ORIGIN_HOST, getClientURI(), true);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = reqSet.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // { User-Identity }
    AvpSet userIdentity = reqSet.addGroupedAvp(Avp.USER_IDENTITY, 10415, true, false);
    if (getPublicIdentity() != null)
      userIdentity.addAvp(Avp.PUBLIC_IDENTITY, getPublicIdentity(), 10415, false, false, false);
    if (getMSISDN() != null)
      userIdentity.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      reqSet.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      reqSet.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // [ User-Name ]
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

    // *{ Data-Reference }
    if (getDataReference() != -1)
      reqSet.addAvp(Avp.DATA_REFERENCE, getDataReference(), 10415, true, false);

    // { User-Data }
    if (getUserData() != null)
      reqSet.addAvp(Avp.USER_DATA_SH, getUserData(), 10415, true, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = reqSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    return pur;
  }


  /*
  3GPP TS 29.329 v18.0.0 § 6.1.5

   The Subscribe-Notifications-Request (SNR) command, indicated by the Command-Code field set to 308
   and the 'R' bit set in the Command Flags field, is sent by a Diameter client to a Diameter server
   in order to request notifications of changes in user data.

   Message Format
   < Subscribe-Notifications-Request > ::= < Diameter Header: 308, REQ, PXY, 16777217 >
                                           < Session-Id >
                                           [ DRMP ]
                                           { Vendor-Specific-Application-Id }
                                           { Auth-Session-State }
                                           { Origin-Host }
                                           { Origin-Realm }
                                           [ Destination-Host ]
                                           { Destination-Realm }
                                          *[ Supported-Features ]
                                           { User-Identity }
                                           [ Wildcarded-Public-Identity ]
                                           [ Wildcarded-IMPU ]
                                          *[ Service-Indication ]
                                           [ Send-Data-Indication ]
                                           [ Server-Name ]
                                           { Subs-Req-Type }
                                          *{ Data-Reference }
                                          *[ Identity-Set ]
                                           [ Expiry-Time ]
                                          *[ DSAI-Tag ]
                                           [ One-Time-Notification ]
                                           [ User-Name ]
                                           [ OC-Supported-Features ]
                                          *[ AVP ]
                                          *[ Proxy-Info ]
                                          *[ Route-Record ]
   */
  protected SubscribeNotificationsRequest createSNR(ClientShSession clientShSession) throws Exception {
    // < Subscribe-Notifications-Request > ::= < Diameter Header: 308, REQ, PXY, 16777217 >
    SubscribeNotificationsRequest snr = new SubscribeNotificationsRequestImpl(clientShSession.getSessions().get(0).
        createRequest(SubscribeNotificationsRequest.code, getApplicationId(), getServerRealmName()));

    AvpSet reqSet = snr.getMessage().getAvps();

    if (reqSet.getAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID) == null) {
      AvpSet vendorSpecificApplicationId = reqSet.addGroupedAvp(Avp.VENDOR_SPECIFIC_APPLICATION_ID, 0, false, false);
      // 1* [ Vendor-Id ]
      vendorSpecificApplicationId.addAvp(Avp.VENDOR_ID, getApplicationId().getVendorId(), true);
      // 0*1{ Auth-Application-Id }
      vendorSpecificApplicationId.addAvp(Avp.AUTH_APPLICATION_ID, getApplicationId().getAuthAppId(), true);
    }

    // { Auth-Session-State }
    if (reqSet.getAvp(Avp.AUTH_SESSION_STATE) == null)
      reqSet.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // { Origin-Host }
    reqSet.removeAvp(Avp.ORIGIN_HOST);
    reqSet.addAvp(Avp.ORIGIN_HOST, getClientURI(), true);

    // { User-Identity }
    AvpSet userIdentity = reqSet.addGroupedAvp(Avp.USER_IDENTITY, 10415, true, false);
    if (getPublicIdentity() != null)
      userIdentity.addAvp(Avp.PUBLIC_IDENTITY, getPublicIdentity(), 10415, false, false, false);
    if (getMSISDN() != null)
      userIdentity.addAvp(Avp.MSISDN, getMSISDN(), 10415, true, false);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      reqSet.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      reqSet.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // [ Service-Indication ]
    if (getServiceIndication() != null) {
      reqSet.addAvp(Avp.SERVICE_INDICATION, getServiceIndication(), 10415, false, false);
    }

    // [ Send-Data-Indication ]
    if (getSendDataIndication() > -1)
      reqSet.addAvp(Avp.SEND_DATA_INDICATION, getSendDataIndication(), 10415, false, false);

    // [ Server-Name ]
    if (getServerName() != null)
      reqSet.addAvp(Avp.SERVER_NAME, getServerName(), 10415, false, false, false);

    // { Subs-Req-Type }
    if (getSubsReqType() > -1)
      reqSet.addAvp(Avp.SUBS_REQ_TYPE, getSubsReqType(), 10415, true, false);

    // *{ Data-Reference }
    if (getDataReference() != -1)
      reqSet.addAvp(Avp.DATA_REFERENCE, getDataReference(), 10415, true, false);

    // [ Identity-Set ]
    if (getIdentitySet() != -1)
      reqSet.addAvp(Avp.IDENTITY_SET, getIdentitySet(), 10415, false, false);

    // [ Expiry-Time ]
    if (getExpiryTime() != null)
      reqSet.addAvp(Avp.EXPIRY_TIME, getExpiryTime(), 10415, false, false);

    // [ DSAI-Tag ]
    if (getDSAITag() != null)
      reqSet.addAvp(Avp.DSAI_TAG, getDSAITag(), 10415, false, true);

    // [ One-Time-Notification ]
    if (getOneTimeNotification() > -1)
      reqSet.addAvp(Avp.ONE_TIME_NOTIFICATION, getOneTimeNotification(), 10415, false, false);

    // [ User-Name ]
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

    return snr;
  }


  /*
    3GPP TS 29.329 v18.0.0 § 6.1.8

    The Push-Notifications-Answer (PNA) command, indicated by the Command-Code field set to 309
    and the 'R' bit cleared in the Command Flags field, is sent by a client in response to the
    Push-Notification-Request command. The Experimental-Result AVP may contain one of the values
    defined in clause 6.2 or in 3GPP TS 29.229.

    Message Format
    < Push-Notification-Answer > ::= < Diameter Header: 309, PXY, 16777217 >
                                     < Session-Id >
                                     [ DRMP ]
                                     { Vendor-Specific-Application-Id }
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
  protected PushNotificationAnswer createPNA(PushNotificationRequest pur, long resultCode) throws Exception {
    // < Push-Notification-Answer > ::= < Diameter Header: 309, PXY, 16777217 >
    PushNotificationAnswer pua = new PushNotificationAnswerImpl((Request) pur.getMessage(), resultCode);

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
    if (avpSet.getAvp(Avp.AUTH_SESSION_STATE) == null)
      avpSet.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = avpSet.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    return pua;
  }

}
