package org.mobicents.diameter.stack.functional.sh.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sh.ClientShSession;
import org.jdiameter.api.sh.events.ProfileUpdateAnswer;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationAnswer;
import org.jdiameter.api.sh.events.PushNotificationRequest;
import org.jdiameter.api.sh.events.SubscribeNotificationsAnswer;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataAnswer;
import org.jdiameter.api.sh.events.UserDataRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.sh.AbstractShClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;

import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * Base implementation of Client
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientSh extends AbstractShClient {

  protected boolean sentUDR;
  protected boolean sentPUR;
  protected boolean sentSNR;
  protected boolean sentPNA;
  protected boolean receivedUDA;
  protected boolean receivedPUA;

  protected boolean receivedSNA;
  protected boolean receivedPNR;

  protected PushNotificationRequest pushNotificationRequest;

  public ClientSh() {
  }

  public boolean isSentUDR() {
    return sentUDR;
  }

  public boolean isSentPUR() {
    return sentPUR;
  }

  public boolean isSentSNR() {
    return sentSNR;
  }

  public boolean isSentPNA() {
    return sentPNA;
  }

  public boolean isReceivedUDA() {
    return receivedUDA;
  }

  public boolean isReceivedPUA() {
    return receivedPUA;
  }

  public boolean isReceivedSNA() {
    return receivedSNA;
  }

  public boolean isReceivedPNR() {
    return receivedPNR;
  }

  public void sendUserDataRequest() throws Exception {
    UserDataRequest udr = super.createUDR(super.clientShSession);
    super.clientShSession.sendUserDataRequest(udr);
    this.sentUDR = true;
    Utils.printMessage(log, super.stack.getDictionary(), udr.getMessage(), isSentUDR());
  }

  public void sendSubscribeNotificationsRequest() throws Exception {
    SubscribeNotificationsRequest snr = super.createSNR(super.clientShSession);
    super.clientShSession.sendSubscribeNotificationsRequest(snr);
    this.sentSNR = true;
    Utils.printMessage(log, super.stack.getDictionary(), snr.getMessage(), isSentSNR());
  }

  public void sendProfileUpdateRequest() throws Exception {
    ProfileUpdateRequest pur = super.createPUR(super.clientShSession);
    super.clientShSession.sendProfileUpdateRequest(pur);
    this.sentPUR = true;
    Utils.printMessage(log, super.stack.getDictionary(), pur.getMessage(), isSentPUR());
  }

  public void sendPushNotificationAnswer() throws Exception {
    if (!this.receivedPNR || this.pushNotificationRequest == null) {
      fail("Did not receive PNR or answer already sent.", null);
      throw new Exception("Did not receive PNR or answer already sent. Request: " + this.pushNotificationRequest);
    }

    PushNotificationAnswer pna = super.createPNA(pushNotificationRequest, 2001);

    this.clientShSession.sendPushNotificationAnswer(pna);

    this.sentPNA = true;
    pushNotificationRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), pna.getMessage(), isSentPNA());
  }

  // ------------ event handlers;

  @Override
  public void doUserDataAnswerEvent(ClientShSession session, UserDataRequest udr, UserDataAnswer uda)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), uda.getMessage(), isReceivedUDA());
    if (this.receivedUDA) {
      fail("Received UDA more than once", null);
      return;
    }
    this.receivedUDA = true;
  }

  @Override
  public void doProfileUpdateAnswerEvent(ClientShSession session, ProfileUpdateRequest pur, ProfileUpdateAnswer pua) throws InternalException,
      IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), pua.getMessage(), isReceivedPUA());
    if (this.receivedPUA) {
      fail("Received PUA more than once", null);
      return;
    }
    this.receivedPUA = true;
  }

  @Override
  public void doSubscribeNotificationsAnswerEvent(ClientShSession session, SubscribeNotificationsRequest snr, SubscribeNotificationsAnswer sna)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), sna.getMessage(), isReceivedSNA());
    if (this.receivedSNA) {
      fail("Received SNA more than once", null);
      return;
    }
    this.receivedSNA = true;
  }

  @Override
  public void doPushNotificationRequestEvent(ClientShSession session, PushNotificationRequest pnr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedPNR) {
      fail("Received PNR more than once", null);
      return;
    }
    this.receivedPNR = true;
    this.pushNotificationRequest = pnr;
  }

  @Override
  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.api.NetworkReqListener#processRequest(org.jdiameter.api.Request)
   */
  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != PushNotificationRequest.code) {
      fail("Received Request with code not used by Sh!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientShSession.getSessionId().equals(request.getSessionId())) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      super.clientShSession.release();
      try {
        super.clientShSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientShSession.class, (Object) null);
        ((NetworkReqListener) this.clientShSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  /** Attributes for User-Data-Request (UDR), Profile-Update-Request (PUR),
   Subscribe-Notifications-Request (SNR) and Push-Notifications-Answer (PNA) **/

  @Override
  protected String getPublicIdentity() {
    // 3GPP TS 29.172 v15.1.0 section 6.3.15
    return "sip:fer@restcomm.org";
  }

  @Override
  protected byte[] getMSISDN() {
    return parseTBCD("59899077937");
  }

  @Override
  protected String getWildcardedPublicIdentity() {
    // 3GPP TS 29.172 v15.1.0 section 6.3.19
    return "sip:*@restcomm.org";
  }

  @Override
  protected String getWildcardedIMPU() {
    // 3GPP TS 29.172 v15.1.0 section 6.3.20
    return "tel:+598*";
  }

  @Override
  protected String getServerName() {
    // 3GPP TS 29.172 v15.1.0 section 6.3.9
    return "mme732@o2.com";
  }

  @Override
  protected byte[] getServiceIndication() {
    // 3GPP TS 29.172 v18.0.0 section 6.3.5
    return "MMTEL-PSTN-ISDN-CS-BINARY".getBytes(StandardCharsets.UTF_8);
  }

  @Override
  protected int getDataReference() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.4
      The Data-Reference AVP is of type Enumerated, and indicates the type of the requested user data in the operation UDR and SNR.
      Its exact values and meaning is defined in 3GPP TS 29.328 [1]. The following values are defined (more details are given in 3GPP TS 29.328:
        RepositoryData (0)
        IMSPublicIdentity (10)
        IMSUserState (11)
        S-CSCFName (12)
        InitialFilterCriteria (13)
        This value is used to request initial filter criteria relevant to the requesting AS
        LocationInformation (14)
        UserState (15)
        ChargingInformation (16)
        MSISDN (17)
        PSIActivation (18)
        DSAI (19)
        ServiceLevelTraceInfo (21)
        IPAddressSecureBindingInformation (22)
        ServicePriorityLevel (23)
        SMSRegistrationInfo (24)
        UEReachabilityForIP (25)
        TADSinformation (26)
        STN-SR (27)
        UE-SRVCC-Capability (28)
        ExtendedPriority (29)
        CSRN (30)
        ReferenceLocationInformation (31)
        IMSI (32)
        IMSPrivateUserIdentity (33)
     */
    return 14;
  }

  @Override
  protected int getIdentitySet() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.10
      The Identity-Set AVP is of type Enumerated and indicates the requested set of IMS Public Identities.  The following values are defined:
        ALL_IDENTITIES (0)
        REGISTERED_IDENTITIES (1)
        IMPLICIT_IDENTITIES (2)
        ALIAS_IDENTITIES (3)
     */
    return 0;
  }

  @Override
  protected int getRequestedDomain() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.7
      The Requested-Domain AVP is of type Enumerated, and indicates the access domain for which certain data (e.g. user state) are requested.
      The following values are defined:
        CS-Domain (0)
        The requested data apply to the CS domain.
        PS-Domain (1)
        The requested data apply to the PS domain.
     */
    return 1;
  }

  @Override
  protected int getCurrentLocation() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.8
      The Current-Location AVP is of type Enumerated, and indicates whether an active location retrieval has to be initiated or not:
        DoNotNeedInitiateActiveLocationRetrieval (0)
          The request indicates that the initiation of an active location retrieval is not required.
        InitiateActiveLocationRetrieval (1)
          It is requested that an active location retrieval is initiated.
     */
    return 0;
  }

  @Override
  protected byte[] getDSAITag() {
    // 3GPP TS 29.172 v15.1.0 section 6.3.18
    // The DSAI-Tag AVP is of type OctetString.
    // This AVP contains the DSAI-Tag identifying
    // the instance of the Dynamic Service Activation Information being accessed for the Public Identity.
    return new byte[] {0x01, 0x11};
  }

  @Override
  protected int getSessionPriority() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.21
      The Session-Priority AVP is of type Enumerated and indicates to the HSS the session's priority.
      The following values are defined:
        PRIORITY-0 (0)
        PRIORITY-1 (1)
        PRIORITY-2 (2)
        PRIORITY-3 (3)
        PRIORITY-4 (4)

        PRIORITY-0 is the highest priority.
     */
    return 2;
  }

  @Override
  protected String getUserName() {
    // Information Element IMSI Mapped to AVP User-Name
    return "748039876543210";
  }

  @Override
  protected long getRequestedNodes() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.7A
        The Requested-Nodes AVP is of type Unsigned32, and it shall contain a bit mask. The meaning of the bits shall be as defined in table 6.3.7A/1:
        Table 6.3.7A/1: Requested-Nodes
        Bit	Name	                Description
        0	MME	                    The requested data apply to the MME
        1	SGSN	                The requested data apply to the SGSN
        2	3GPP-AAA-SERVER-TWAN	The requested data apply to the 3GPP AAA Server for TWAN
        3	AMF	                    The requested data apply to the AMF (for 3GPP access)
     */
    return 2L;
  }

  @Override
  protected int getServingNodeIndication() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.23
      The Serving-Node-Indication AVP is of type Enumerated.
      If present it indicates that the sender does not require any location information other than the Serving Node Addresses/Identities requested
      (e.g. MME name, VLR number). Other location information (e.g. Global Cell ID, Tracking Area ID) may be absent.
      The following values are defined:
      ONLY_SERVING_NODES_REQUIRED (0)
     */
    return 2;
  }

  @Override
  protected int getPrePagingSupported() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.26
    The Pre-paging-Supported AVP is of type Enumerated. It indicates whether the sender supports pre-paging or not. The following values are defined:
      PREPAGING_NOT_SUPPORTED (0)
      PREPAGING_SUPPORTED (1)
     */
    return 1;
  }

  @Override
  protected int getLocalTimeZoneIndication() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.27
        The Local-Time-Zone-Indication AVP is of type Enumerated. If present it indicates that the Local Time Zone information (time zone and daylight saving time) of the visited network where the UE is attached is requested with or without other location information. The following values are defined:
          ONLY_LOCAL_TIME_ZONE_REQUESTED (0)
          LOCAL_TIME_ZONE_WITH_LOCATION_INFO_REQUESTED (1)
     */
    return 0;
  }

  @Override
  protected long getUDRFlags() {
    /*
    3GPP TS 29.172 v15.1.0 section 6.3.28
        The UDR-Flags AVP is of type Unsigned32, and it shall contain a bit mask. The meaning of the bits shall be as defined in 3GPP TS 29.328 [1].
          Table 6.3.28/1: UDR-Flags
          Bit	Name
          0	Location-Information-EPS-Supported
          1	RAT-Type-Requested
      NOTE:	Bits not defined in this table shall be cleared by the sender of the request and discarded by the receiver of the request.
     */
    return 0L;
  }

  @Override
  protected byte[] getCallReferenceNumber() {
  /*
    3GPP TS 29.172 v15.1.0 section 6.3.30
      The Call-Reference-Number AVP is of type OctetString. The exact content and format of this AVP is described in 3GPP TS 29.002.
    3GPP TS 29.002 v15.0.0 section 7.6.5.1
      Call reference number
        This parameter refers to a call reference number allocated by a call control MSC.

    CallReferenceNumber ::= OCTET STRING (SIZE (1..8))
  */
    return new byte[] {0x10, 0x2f};
  }

  @Override
  protected byte[] getAsNumber() {
  /*
    3GPP TS 29.172 v15.1.0 section 6.3.31
      The AS-Number AVP is of type OctetString. The exact content and format of this AVP corresponds to the gmsc-address parameter described in 3GPP TS 29.002.
  */
    return new byte[] {0x49};
  }

  @Override
  protected long getOCFeatureVector() {
    // The OC-Feature-Vector AVP (AVP Code 622) is of type Unsigned64 and
    // contains a 64-bit flags field of announced capabilities of a
    // Diameter Overload Indication Conveyance (DOIC) node
    // From RFC 8581
    // The Peer-Report feature defines a new feature bit for the OC-Feature-Vector AVP.
    // OC_PEER_REPORT (0x0000000000000010)
    // When this flag is set by a DOIC node, it indicates that the DOIC node supports the Peer Overload report type.
    return 2;
  }

  @Override
  protected String getSourceID() {
    // The SourceID AVP (AVP code 649) is of type DiameterIdentity and is
    // inserted by a Diameter node to indicate the source of the AVP in which it is a part.
    return "mmec03.mmegi3000.mme.epc.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected long getOCPeerAlgo() {
    // The OC-Peer-Algo AVP (AVP code 648) is of type Unsigned64 and
    // contains a 64-bit flags field of announced capabilities for a DOIC node.
    // The value of zero ("0") is reserved.
    return 3;
  }

  // *[ Supported-Features ]
  // The Supported-Features AVP is of type Grouped.
  // If this AVP is present it may inform the destination host about
  // the features that the origin host supports for the application.
  @Override
  protected long getVendorId() {
    // Where a Supported-Features AVP is used to identify features that have been defined by 3GPP,
    // the Vendor-Id AVP shall contain the vendor ID of 3GPP
    return 10415;
  }

  @Override
  protected long getFeatureListID() {
    // The Feature-List-ID AVP is of type Unsigned32, and it contains the identity of a feature list
    // The Vendor-Id AVP and the Feature-List-ID AVP shall together identify
    // which feature list is carried in the Supported-Features AVP for the Application-ID
    // present in the command header.
    // If there are multiple feature lists defined by the same vendor and the same application,
    // the Feature-List-ID AVP shall differentiate those lists from one another.
    // The destination host shall use the value of the Feature-List-ID AVP to identify the feature list.
    return 1;
  }

  @Override
  protected long getFeatureList() {
    // The Feature-List AVP contains a list of supported features of the origin host.
    // Wireshark example taken from a real network capture:
    // AVP: Feature-List(630) l=16 f=V-- vnd=TGPP val=469762567
    //    AVP Code: 630 Feature-List
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Feature-List Flags: 0x1c000207
    //        0... .... .... .... .... .... .... .... = Additional MSISDN: Not supported
    //        .0.. .... .... .... .... .... .... .... = UE Time Zone Retrieval: Not supported
    //        ..0. .... .... .... .... .... .... .... = Partial Purge from a Combined MME/SGSN: Not supported
    //        ...1 .... .... .... .... .... .... .... = State/Location Information Retrieval: Supported
    //        .... 1... .... .... .... .... .... .... = Terminating Access Domain Selection Data Retrieval: Supported
    //        .... .1.. .... .... .... .... .... .... = UE Reachability Notification: Supported
    //        .... ..0. .... .... .... .... .... .... = Barring of outgoing international calls except those directed to the home PLMN Country: Not supported
    //        .... ...0 .... .... .... .... .... .... = Barring of outgoing international calls: Not supported
    //        .... .... 0... .... .... .... .... .... = Barring of all outgoing calls: Not supported
    //        .... .... .0.. .... .... .... .... .... = Barring of Outgoing Calls: Not supported
    //        .... .... ..0. .... .... .... .... .... = Short Message MO-PP: Not supported
    //        .... .... ...0 .... .... .... .... .... = Allow an MS to request transfer of its location to another LCS client: Not supported
    //        .... .... .... 0... .... .... .... .... = Allow an MS to perform self location without interaction with the PLMN: Not supported
    //        .... .... .... .0.. .... .... .... .... = Allow an MS to request its own location: Not supported
    //        .... .... .... ..0. .... .... .... .... = All Mobile Originating Location Request Classes: Not supported
    //        .... .... .... ...0 .... .... .... .... = Allow location by LCS clients of a designated LCS service type: Not supported
    //        .... .... .... .... 0... .... .... .... = Allow location by designated PLMN operator LCS clients: Not supported
    //        .... .... .... .... .0.. .... .... .... = Allow location by designated external value added LCS clients: Not supported
    //        .... .... .... .... ..0. .... .... .... = Allow location by any value added LCS client to which a call/session is established from the target UE: Not supported
    //        .... .... .... .... ...0 .... .... .... = Allow location by any LCS client: Not supported
    //        .... .... .... .... .... 0... .... .... = All LCS Privacy Exception Classes: Not supported
    //        .... .... .... .... .... .0.. .... .... = Trace Function: Not supported
    //        .... .... .... .... .... ..1. .... .... = Regional Subscription: Supported
    //        .... .... .... .... .... ...0 .... .... = Operator Determined Barring of all outgoing international calls except those directed to the home PLMN country and Barring of all outgoing inter-zonal calls: Not supported
    //        .... .... .... .... .... .... 0... .... = Operator Determined Barring of all outgoing inter-zonal calls except those directed to the home PLMN country: Not supported
    //        .... .... .... .... .... .... .0.. .... = Operator Determined Barring of all outgoing inter-zonal calls: Not supported
    //        .... .... .... .... .... .... ..0. .... = Operator Determined Barring of all outgoing international calls except those directed to the home PLMN country: Not supported
    //        .... .... .... .... .... .... ...0 .... = Operator Determined Barring of all outgoing international calls: Not supported
    //        .... .... .... .... .... .... .... 0... = Operator Determined Barring of all outgoing calls: Not supported
    //        .... .... .... .... .... .... .... .1.. = Operator Determined Barring of Packet Oriented Services from access points that are within the roamed to VPLMN: Supported
    //        .... .... .... .... .... .... .... ..1. = Operator Determined Barring of Packet Oriented Services from access points that are within the HPLMN whilst the subscriber is roaming in a VPLMN: Supported
    //        .... .... .... .... .... .... .... ...1 = Operator Determined Barring of all Packet Oriented Services: Supported
    return 469762567L;
  }

  @Override
  protected byte[] getUserData() {
    String userDataXML;
    try {
      String path = "src/test/java/org/mobicents/diameter/stack/functional/sh/base/Sh-Data.xml";
      userDataXML = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return userDataXML.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  protected int getSendDataIndication() {
    // The Send-Data-Indication AVP is of type Enumerated.
    // If present it indicates that the sender requests the User-Data. The following values are defined:
    //  USER_DATA_NOT_REQUESTED (0)
    //  USER_DATA_REQUESTED (1)
    return 1;
  }

  @Override
  protected int getSubsReqType() {
    // The Subs-Req-Type AVP is of type Enumerated, and indicates the type of the subscription-to-notifications request.
    // The following values are defined:
    //  Subscribe (0)
    //    This value is used by an AS to subscribe to notifications of changes in data.
    //  Unsubscribe (1)
    //    This value is used by an AS to unsubscribe to notifications of changes in data.
    return 0;
  }

  @Override
  protected Time getExpiryTime() {
    // The Expiry-Time AVP is of type Time.
    // This AVP contains the expiry time of subscriptions to notifications in the HSS
    return Time.valueOf("23:59:59");
  }

  @Override
  protected int getOneTimeNotification() {
    // The One-Time-Notification AVP is of type Enumerated. If present it indicates that the sender requests to be notified only one time. The following values are defined:
    //  ONE_TIME_NOTIFICATION_REQUESTED (0)
    // This AVP is only applicable to UE reachability for IP (25)
    return 0;
  }
}
