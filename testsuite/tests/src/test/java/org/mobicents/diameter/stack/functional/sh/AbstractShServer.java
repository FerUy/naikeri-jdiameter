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
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.api.sh.ServerShSessionListener;
import org.jdiameter.api.sh.events.ProfileUpdateAnswer;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationRequest;
import org.jdiameter.api.sh.events.SubscribeNotificationsAnswer;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataAnswer;
import org.jdiameter.api.sh.events.UserDataRequest;
import org.jdiameter.common.impl.app.sh.ProfileUpdateAnswerImpl;
import org.jdiameter.common.impl.app.sh.PushNotificationRequestImpl;
import org.jdiameter.common.impl.app.sh.ShSessionFactoryImpl;
import org.jdiameter.common.impl.app.sh.SubscribeNotificationsAnswerImpl;
import org.jdiameter.common.impl.app.sh.UserDataAnswerImpl;
import org.mobicents.diameter.stack.functional.TBase;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class AbstractShServer extends TBase implements ServerShSessionListener {

  // NOTE: implementing NetworkReqListener since it's required for stack to know we support it... ech.

  protected ServerShSession serverShSession;

  public void init(InputStream configStream, String clientID) throws Exception {
    try {
      super.init(configStream, clientID, ApplicationId.createByAuthAppId(10415, 16777217));
      ShSessionFactoryImpl shSessionFactory = new ShSessionFactoryImpl(this.sessionFactory);
      sessionFactory.registerAppFacory(ServerShSession.class, shSessionFactory);
      sessionFactory.registerAppFacory(ClientShSession.class, shSessionFactory);
      shSessionFactory.setServerShSessionListener(this);
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

  public String getSessionId() {
    return this.serverShSession.getSessionId();
  }

  public ServerShSession getSession() {
    return this.serverShSession;
  }

  /** Attributes for User-Data-Answer (UDA), Profile-Update-Answer (PUA),
   Subscribe-Notifications-Answer (SNA) and Push-Notifications-Request (PNR) **/

  // *[ Supported-Features ]
  protected abstract long getVendorId();
  protected abstract long getFeatureListID();
  protected abstract long getFeatureList();

  // [ OC-Supported-Features ]
  protected abstract long getOCFeatureVector();
  protected abstract String getSourceID();
  protected abstract long getOCPeerAlgo();

  // [ Wildcarded-Public-Identity ]
  protected abstract String getWildcardedPublicIdentity();

  // [ Wildcarded-IMPU ]
  protected abstract String getWildcardedIMPU();

  // [ User-Data ]
  protected abstract byte[] getUserData();


  // [ OC-OLR ]
  protected abstract long getOCSequenceNumber();
  protected abstract int getOCReportType();
  protected abstract long getOCReductionPercentage();
  protected abstract long getOCValidityDuration();

  // *[ Load ]
  protected abstract int getLoadType();
  protected abstract long getLoadValue();

  // [ Repository-Data-ID ]
  protected abstract byte[] getServiceIndication();
  protected abstract long getSequenceNumber();

  // [ Data-Reference ]
  protected abstract int getDataReference();

  // [ Expiry-Time ]
  protected abstract Time getExpiryTime();

  // { User-Identity }
  protected abstract String getPublicIdentity();
  protected abstract byte[] getMSISDN();

  // [ User-Name ]
  protected abstract String getUserName();



  /*
  3GPP TS 29.329 v15.1.0 § 6.1.2

        The User-Data-Answer (UDA) command, indicated by the Command-Code field set to 306 and the ‘R’ bit cleared in the Command Flags field, is sent by a server in response to the User-Data-Request command. The Experimental-Result AVP may contain one of the values defined in section 6.2 or in 3GPP TS 29.229 [6].
        Message Format
        < User-Data-Answer > ::= < Diameter Header: 306, PXY, 16777217 >
                                 < Session-Id >
                                 [ DRMP ]
                                 { Vendor-Specific-Application-Id }
                                 [ Result-Code ]
                                 [ Experimental-Result ]
                                 { Auth-Session-State }
                                 { Origin-Host }
                                 { Origin-Realm }
                                *[ Supported-Features ]
                                 [ Wildcarded-Public-Identity ]
                                 [ Wildcarded-IMPU ]
                                 [ User-Data ]
                                 [ OC-Supported-Features ]
                                 [ OC-OLR ]
                                *[ Load ]
                                *[ AVP ]
                                 [ Failed-AVP ]
                                *[ Proxy-Info ]
                                *[ Route-Record ]
  */
  protected UserDataAnswer createUDA(UserDataRequest udr, long resultCode) throws Exception {
    UserDataAnswer uda = new UserDataAnswerImpl((Request) udr.getMessage(), resultCode);

    AvpSet reqSet = udr.getMessage().getAvps();
    AvpSet set = uda.getMessage().getAvps();
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
    if (set.getAvp(Avp.AUTH_SESSION_STATE) == null)
      set.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = set.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      set.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      set.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // [ User-Data ]
    if (getUserData() != null)
      set.addAvp(Avp.USER_DATA_SH, getUserData(), 10415, true, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = set.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = set.addGroupedAvp(Avp.OC_OLR, 0, false, false);
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
    AvpSet load = set.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    return uda;
  }


  /*
  3GPP TS 29.329 v15.1.0 § 6.1.4

   The Profile-Update-Answer (PUA) command, indicated by the Command-Code field set to 307
   and the 'R' bit cleared in the Command Flags field, is sent by a server in response to the
   Profile-Update-Request command.
   The Experimental-Result AVP may contain one of the values defined in clause 6.2 or in 3GPP TS 29.229.
   Message Format
   < Profile-Update-Answer > ::= < Diameter Header: 307, PXY, 16777217 >
                                 < Session-Id >
                                 [ DRMP ]
                                 { Vendor-Specific-Application-Id }
                                 [ Result-Code ]
                                 [ Experimental-Result ]
                                 { Auth-Session-State }
                                 { Origin-Host }
                                 { Origin-Realm }
                                 [ Wildcarded-Public-Identity ]
                                 [ Wildcarded-IMPU ]
                                 [ Repository-Data-ID ]
                                 [ Data-Reference ]
                                *[ Supported-Features ]
                                 [ OC-Supported-Features ]
                                 [ OC-OLR ]
                                *[ Load ]
                                *[ AVP ]
                                 [ Failed-AVP ]
                                *[ Proxy-Info ]
                                *[ Route-Record ]
   */
  protected ProfileUpdateAnswer createPUA(ProfileUpdateRequest pur, long resultCode) throws Exception {
    ProfileUpdateAnswer pua = new ProfileUpdateAnswerImpl((Request) pur.getMessage(), resultCode);

    AvpSet reqSet = pur.getMessage().getAvps();
    AvpSet set = pua.getMessage().getAvps();
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
    if (set.getAvp(Avp.AUTH_SESSION_STATE) == null)
      set.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      set.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      set.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // [ Repository-Data-ID ]
    AvpSet repositoryDataID = set.addGroupedAvp(Avp.REPOSITORY_DATA_ID, 10415, false, false);
    if (getServiceIndication() != null)
      repositoryDataID.addAvp(Avp.SERVICE_INDICATION, getServiceIndication(), 10415, true, false);
    if (getSequenceNumber() > -1)
      repositoryDataID.addAvp(Avp.SEQUENCE_NUMBER, getSequenceNumber(), 10415, false, false, true);

    // [ Data-Reference ]
    if (getDataReference() > -1)
      set.addAvp(Avp.DATA_REFERENCE, getDataReference(), 10415, true, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = set.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = set.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = set.addGroupedAvp(Avp.OC_OLR, 0, false, false);
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
    AvpSet load = set.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    return pua;
  }

  /*
  3GPP TS 29.329 v15.1.0 § 6.1.6

   The Subscribe-Notifications-Answer command, indicated by the Command-Code field set to 308
   and the 'R' bit cleared in the Command Flags field, is sent by a server in response to the
   Subscribe-Notifications-Request command.
   The Result-Code or Experimental-Result AVP may contain one of the values defined in clause 6.2 or in 3GPP TS 29.229.

   Message Format
   < Subscribe-Notifications-Answer > ::= < Diameter Header: 308, PXY, 16777217 >
                                          < Session-Id >
                                          [ DRMP ]
                                          { Vendor-Specific-Application-Id }
                                          { Auth-Session-State }
                                          [ Result-Code ]
                                          [ Experimental-Result ]
                                          { Origin-Host }
                                          { Origin-Realm }
                                          [ Wildcarded-Public-Identity ]
                                          [ Wildcarded-IMPU ]
                                         *[ Supported-Features ]
                                          [ User-Data ]
                                          [ Expiry-Time ]
                                          [ OC-Supported-Features ]
                                          [ OC-OLR ]
                                         *[ Load ]
                                         *[ AVP ]
                                          [ Failed-AVP ]
                                         *[ Proxy-Info ]
                                         *[ Route-Record ]
   */
  protected SubscribeNotificationsAnswer createSNA(SubscribeNotificationsRequest snr, long resultCode) throws Exception {
    // < Subscribe-Notifications-Answer > ::= < Diameter Header: 308, PXY, 16777217 >
    SubscribeNotificationsAnswer sna = new SubscribeNotificationsAnswerImpl((Request) snr.getMessage(), resultCode);

    AvpSet reqSet = snr.getMessage().getAvps();
    AvpSet set = sna.getMessage().getAvps();
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
    if (set.getAvp(Avp.AUTH_SESSION_STATE) == null)
      set.addAvp(Avp.AUTH_SESSION_STATE, 1);

    // [ Wildcarded-Public-Identity ]
    if (getWildcardedPublicIdentity() != null)
      set.addAvp(Avp.WILDCARDED_PUBLIC_IDENTITY, getWildcardedPublicIdentity(), 10415, false, false, false);

    // [ Wildcarded-IMPU ]
    if (getWildcardedIMPU() != null)
      set.addAvp(Avp.WILDCARDED_IMPU, getWildcardedIMPU(), 10415, false, false, false);

    // *[ Supported-Features ]
    AvpSet supportedFeatures = set.addGroupedAvp(Avp.SUPPORTED_FEATURES, 10415, false, false);
    if (getVendorId() > -1)
      supportedFeatures.addAvp(Avp.VENDOR_ID, getVendorId(), 0, true, false, true);
    if (getFeatureListID() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST_ID, getFeatureListID(), 10415, false, false, true);
    if (getFeatureList() > -1)
      supportedFeatures.addAvp(Avp.FEATURE_LIST, getFeatureList(), 10415, false, false, true);

    // [ User-Data ]
    if (getUserData() != null)
      set.addAvp(Avp.USER_DATA_SH, getUserData(), 10415, true, false);

    // [ Expiry-Time ]
    if (getExpiryTime() != null)
      reqSet.addAvp(Avp.EXPIRY_TIME, getExpiryTime(), 10415, false, false);

    // [ OC-Supported-Features ]
    AvpSet ocSupportedFeatures = set.addGroupedAvp(Avp.OC_SUPPORTED_FEATURES, 0, false, false);
    if (getOCFeatureVector() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_FEATURE_VECTOR, getOCFeatureVector(), 0, false, false, false);
    if (getSourceID() != null)
      ocSupportedFeatures.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);
    if (getOCPeerAlgo() > -1)
      ocSupportedFeatures.addAvp(Avp.OC_PEER_ALGO, getOCPeerAlgo(), 0, false, false, false);

    // [ OC-OLR ]
    AvpSet ocOlr = set.addGroupedAvp(Avp.OC_OLR, 0, false, false);
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
    AvpSet load = set.addGroupedAvp(Avp.LOAD, 0, false, false);
    if (getLoadType() > -1)
      load.addAvp(Avp.LOAD_TYPE, getLoadType(), 0, false, false);
    if (getLoadValue() > -1)
      load.addAvp(Avp.LOAD_VALUE, getLoadValue(), 0, false, false, false);
    if (getSourceID() != null)
      load.addAvp(Avp.SOURCE_ID, getSourceID(), 0, false, false, false);

    return sna;
  }

  /*
  3GPP TS 29.329 v15.1.0 § 6.1.7

   The Push-Notification-Request (PNR) command, indicated by the Command-Code field set to 309
   and the 'R' bit set in the Command Flags field, is sent by a Diameter server to a Diameter client
   in order to notify changes in the user data in the server.

   Message Format
   < Push-Notification-Request > ::= < Diameter Header: 309, REQ, PXY, 16777217 >
                                     < Session-Id >
                                     [ DRMP ]
                                     { Vendor-Specific-Application-Id }
                                     { Auth-Session-State }
                                     { Origin-Host }
                                     { Origin-Realm }
                                     { Destination-Host }
                                     { Destination-Realm }
                                    *[ Supported-Features ]
                                     { User-Identity }
                                     [ Wildcarded-Public-Identity ]
                                     [ Wildcarded-IMPU ]
                                     [ User-Name ]
                                     { User-Data }
                                    *[ AVP ]
                                    *[ Proxy-Info ]
                                    *[ Route-Record ]
   */
  protected PushNotificationRequest createPNR(ServerShSession serverShSession) throws Exception {
    // < Push-Notification-Request > ::= < Diameter Header: 309, REQ, PXY, 16777217 >
    PushNotificationRequest pnr = new PushNotificationRequestImpl(serverShSession.getSessions().get(0).
        createRequest(PushNotificationRequest.code, getApplicationId(), getClientRealmName()));

    AvpSet reqSet = pnr.getMessage().getAvps();

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

    // { Destination-Host }
    reqSet.addAvp(Avp.DESTINATION_HOST, clientHost, true);

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

    // [ Service-Indication ]
    if (getServiceIndication() != null)
      reqSet.addAvp(Avp.SERVICE_INDICATION, getServiceIndication(), 10415, false, false);

    // [ User-Name ]
    if (getUserName() != null)
      reqSet.addAvp(Avp.USER_NAME, getUserName(), 0, true, false, false);

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

    return pnr;
  }


}
