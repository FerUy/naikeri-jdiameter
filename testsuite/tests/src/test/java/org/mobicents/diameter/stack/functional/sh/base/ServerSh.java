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
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.api.sh.events.ProfileUpdateAnswer;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationAnswer;
import org.jdiameter.api.sh.events.PushNotificationRequest;
import org.jdiameter.api.sh.events.SubscribeNotificationsAnswer;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataAnswer;
import org.jdiameter.api.sh.events.UserDataRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.sh.AbstractShServer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;

import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * Base implementation of Server
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ServerSh extends AbstractShServer {

  protected boolean sentUDA;
  protected boolean sentPUA;
  protected boolean sentSNA;
  protected boolean sentPNR;
  protected boolean receivedUDR;
  protected boolean receivedPUR;
  protected boolean receivedSNR;
  protected boolean receivedPNA;

  protected UserDataRequest userDataRequest;
  protected ProfileUpdateRequest profileUpdateRequest;
  protected SubscribeNotificationsRequest subscribeNotificationsRequest;


  public boolean isSentUDA() {
    return sentUDA;
  }

  public boolean isSentSNA() {
    return sentSNA;
  }

  public boolean isSentPUA() {
    return sentPUA;
  }

  public boolean isSentPNR() {
    return sentPNR;
  }

  public boolean isReceivedUDR() {
    return receivedUDR;
  }

  public boolean isReceivedPUR() {
    return receivedPUR;
  }

  public boolean isReceivedSNR() {
    return receivedSNR;
  }

  public boolean isReceivedPNA() {
    return receivedPNA;
  }

  // ------- send methods to trigger answer

  public void sendUserDataAnswer() throws Exception {
    if (!receivedUDR || this.userDataRequest == null) {
      fail("Did not receive UDR or answer already sent.", null);
      throw new Exception("Did not receive UDR or answer already sent. Request: " + this.userDataRequest);
    }

    UserDataAnswer uda = super.createUDA(userDataRequest, 2001);

    super.serverShSession.sendUserDataAnswer(uda);

    this.sentUDA = true;
    userDataRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), uda.getMessage(), isSentUDA());
  }

  public void sendProfileUpdateAnswer() throws Exception {
    if (!this.receivedPUR || this.profileUpdateRequest == null) {
      fail("Did not receive PUR or answer already sent.", null);
      throw new Exception("Did not receive PUR or answer already sent. Request: " + this.profileUpdateRequest);
    }
    ProfileUpdateAnswer pua = super.createPUA(profileUpdateRequest, 2001);
    this.serverShSession.sendProfileUpdateAnswer(pua);

    this.sentPUA = true;
    this.profileUpdateRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), pua.getMessage(), isSentPUA());
  }

  public void sendSubscribeNotificationsAnswer() throws Exception {
    if (!this.isReceivedSNR() || this.subscribeNotificationsRequest == null) {
      fail("Did not receive SNR or answer already sent.", null);
      throw new Exception("Did not receive SNR or answer already sent. Request: " + this.subscribeNotificationsRequest);
    }

    SubscribeNotificationsAnswer sna = super.createSNA(subscribeNotificationsRequest, 2001);

    this.serverShSession.sendSubscribeNotificationsAnswer(sna);

    this.sentSNA = true;
    this.subscribeNotificationsRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), sna.getMessage(), isSentSNA());
  }

  public void sendPushNotificationRequest() throws Exception {
    try {
      super.serverShSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-Sh-TESTxx"),
          getApplicationId(), ServerShSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }

    PushNotificationRequest pnr = super.createPNR(super.serverShSession);

    this.serverShSession.sendPushNotificationRequest(pnr);
    this.sentPNR = true;
    Utils.printMessage(log, super.stack.getDictionary(), pnr.getMessage(), isSentPNR());
  }

  // ------- initial, this will be triggered for first msg.

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.api.NetworkReqListener#processRequest(org.jdiameter.api.Request)
   */
  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != ProfileUpdateRequest.code && code != UserDataRequest.code && code != SubscribeNotificationsRequest.code) {
      fail("Received Request with code not used by Sh!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverShSession == null) {
      try {
        super.serverShSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerShSession.class, (Object) null);
        ((NetworkReqListener) this.serverShSession).processRequest(request);
      } catch (Exception e) {
        fail(null, e);
      }
    } else {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    }
    return null;
  }

  // ------------- specific, app session listener.

  @Override
  public void doUserDataRequestEvent(ServerShSession session, UserDataRequest udr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedUDR) {
      fail("Received UDR more than once!", null);
      return;
    }
    this.receivedUDR = true;
    this.userDataRequest = udr;
  }

  @Override
  public void doProfileUpdateRequestEvent(ServerShSession session, ProfileUpdateRequest pur)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedPUR) {
      fail("Received PUR more than once!", null);
      return;
    }
    this.receivedPUR = true;
    this.profileUpdateRequest = pur;
  }

  @Override
  public void doSubscribeNotificationsRequestEvent(ServerShSession session, SubscribeNotificationsRequest snr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedSNR) {
      fail("Received SNR more than once!", null);
      return;
    }
    this.receivedSNR = true;
    this.subscribeNotificationsRequest = snr;
  }

  @Override
  public void doPushNotificationAnswerEvent(ServerShSession session, PushNotificationRequest pnr, PushNotificationAnswer pna) throws InternalException,
  IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), pna.getMessage(), isReceivedPNA());
    if (this.receivedPNA) {
      fail("Received PNA more than once!", null);
      return;
    }
    this.receivedPNA = true;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.api.cca.ServerRoSessionListener#doOtherEvent(org.jdiameter.api.app.AppSession,
   * org.jdiameter.api.app.AppRequestEvent, org.jdiameter.api.app.AppAnswerEvent)
   */
  @Override
  public void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    fail("Received \"Other\" event, request[" + request + "], answer[" + answer + "], on session[" + session + "]", null);
  }

  @Override
  public void timeoutExpired(Request request) {
    fail("Received \"Timeout\" event, request[" + request + "]", null);
  }


  /** Attributes for User-Data-Answer (UDA), Profile-Update-Answer (PUA),
  Subscribe-Notifications-Answer (SNA) and Push-Notifications-Request (PNR) **/

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
  protected int getLoadType() {
    // IETF RFC 8583
    // The Load-Type AVP (AVP code 651) is of type Enumerated.  It is used
    // to convey the type of Diameter node that sent the load information.
    // The following values are defined:
    //  HOST 0  The load report is for a host.
    //  PEER 1  The load report is for a peer.
    return 1;
  }

  @Override
  protected long getLoadValue() {
    // IETF RFC 8583
    // The Load-Value AVP (AVP code 652) is of type Unsigned64.  It is used
    // to convey relative load information about the sender of the load report.
    // The Load-Value AVP is specified in a manner similar to the weight value in DNS SRV ([RFC2782]).
    // The Load value has a range of 0-65535.
    // A higher value indicates a lower load on the sending node.
    // A lower value indicates that the sending node is heavily loaded.
    return 60000;
  }

  @Override
  protected long getOCSequenceNumber() {
    // IETF RFC 7683
    // The OC-Sequence-Number AVP (AVP Code 624) is of type Unsigned64.  Its
    // usage in the context of overload control is described in Section 5.2.
    // From the functionality point of view, the OC-Sequence-Number AVP is
    // used as a nonvolatile increasing counter for a sequence of overload
    // reports between two DOIC nodes for the same overload occurrence.
    // Sequence numbers are treated in a unidirectional manner, i.e., two
    // sequence numbers in each direction between two DOIC nodes are not
    // related or correlated.
    return 7090291886L;
  }

  @Override
  protected int getOCReportType() {
    // IETF RFC 7683
    // The OC-Report-Type AVP (AVP Code 626) is of type Enumerated.
    // The value of the AVP describes what the overload report concerns.
    // The following values are initially defined:
    //  HOST_REPORT 0
    //    The overload report is for a host. Overload abatement treatment applies to host-routed requests.
    //   REALM_REPORT 1
    //    The overload report is for a realm.  Overload abatement treatment applies to realm-routed requests.
    return 1;
  }

  @Override
  protected long getOCReductionPercentage() {
    // IETF RFC 7683
    // The OC-Reduction-Percentage AVP (AVP Code 627) is of type Unsigned32
    // and describes the percentage of the traffic that the sender is
    // requested to reduce, compared to what it otherwise would send.
    // The OC-Reduction-Percentage AVP applies to the default (loss) algorithm
    // specified in this specification.  However, the AVP can be reused for
    // future abatement algorithms, if its semantics fit into the new algorithm.
    //
    // The value of the Reduction-Percentage AVP is between zero (0) and one hundred (100).
    // Values greater than 100 are ignored.
    // The value of 100 means that all traffic is to be throttled,
    // i.e., the reporting node is under a severe load and ceases to process any new messages.
    // The value of 0 means that the reporting node is in a stable state.
    return 0;
  }

  @Override
  protected long getOCValidityDuration() {
    // IETF RFC 7683
    // The OC-Validity-Duration AVP (AVP Code 625) is of type Unsigned32 and
    // indicates in seconds the validity time of the overload report.
    // The number of seconds is measured after reception of the first OC-OLR AVP
    // with a given value of OC-Sequence-Number AVP.
    // The default value for the OC-Validity-Duration AVP is 30 seconds.
    // When the OC-Validity-Duration AVP is not present in the OC-OLR AVP, the default value applies.
    // The maximum value for the OC-Validity-Duration AVP is 86,400 seconds (24 hours).
    // If the value received in the OC-Validity-Duration is greater than the maximum value,
    // then the default value applies.
    return 86400;
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
  protected byte[] getServiceIndication() {
    // 3GPP TS 29.172 v18.0.0 section 6.3.5
    return "MMTEL-PSTN-ISDN-CS-BINARY".getBytes(StandardCharsets.UTF_8);
  }

  @Override
  protected long getSequenceNumber() {
    // The Sequence-Number AVP is of type Unsigned32. This AVP contains a number associated to a repository data.
    return 5;
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
  protected Time getExpiryTime() {
    // The Expiry-Time AVP is of type Time.
    // This AVP contains the expiry time of subscriptions to notifications in the HSS
    return Time.valueOf("23:59:59");
  }

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
  protected String getUserName() {
    // Information Element IMSI Mapped to AVP User-Name
    return "748039876543210";
  }
}
