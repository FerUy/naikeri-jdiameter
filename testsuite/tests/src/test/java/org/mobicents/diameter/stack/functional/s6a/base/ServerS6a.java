package org.mobicents.diameter.stack.functional.s6a.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.s6a.ServerS6aSession;
import org.jdiameter.api.s6a.events.JAuthenticationInformationAnswer;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.api.s6a.events.JCancelLocationAnswer;
import org.jdiameter.api.s6a.events.JCancelLocationRequest;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.api.s6a.events.JNotifyAnswer;
import org.jdiameter.api.s6a.events.JNotifyRequest;
import org.jdiameter.api.s6a.events.JPurgeUEAnswer;
import org.jdiameter.api.s6a.events.JPurgeUERequest;
import org.jdiameter.api.s6a.events.JResetAnswer;
import org.jdiameter.api.s6a.events.JResetRequest;
import org.jdiameter.api.s6a.events.JUpdateLocationAnswer;
import org.jdiameter.api.s6a.events.JUpdateLocationRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ServerS6a extends AbstractS6aServer {

  protected boolean receivedAIR;
  protected boolean receivedULR;
  protected boolean receivedCLA;
  protected boolean receivedIDA;
  protected boolean receivedDSA;
  protected boolean receivedPUR;
  protected boolean receivedRSA;
  protected boolean receivedNOR;
  protected boolean sentAIA;
  protected boolean sentULA;
  protected boolean sentCLR;
  protected boolean sentIDR;
  protected boolean sentDSR;
  protected boolean sentPUA;
  protected boolean sentRSR;
  protected boolean sentNOA;

  protected JAuthenticationInformationRequest authenticationInformationRequest;
  protected JUpdateLocationRequest updateLocationRequest;
  protected JPurgeUERequest purgeUERequest;
  protected JNotifyRequest notifyRequest;

  public ServerS6a() {
  }

  public boolean isReceivedAIR() {
    return receivedAIR;
  }

  public boolean isReceivedULR() {
    return receivedULR;
  }

  public boolean isReceivedCLA() {
    return receivedCLA;
  }

  public boolean isReceivedIDA() {
    return receivedIDA;
  }

  public boolean isReceivedDSA() {
    return receivedDSA;
  }

  public boolean isReceivedPUR() {
    return receivedPUR;
  }

  public boolean isReceivedRSA() {
    return receivedRSA;
  }

  public boolean isReceivedNOR() {
    return receivedNOR;
  }

  public boolean isSentAIA() {
    return sentAIA;
  }

  public boolean isSentULA() {
    return sentULA;
  }

  public boolean isSentCLR() {
    return sentCLR;
  }

  public boolean isSentIDR() {
    return sentIDR;
  }

  public boolean isSentDSR() {
    return sentDSR;
  }

  public boolean isSentPUA() {
    return sentPUA;
  }

  public boolean isSentRSR() {
    return sentRSR;
  }

  public boolean isSentNOA() {
    return sentNOA;
  }

  public void sendAuthenticationInformationAnswer() throws Exception {
    if (!receivedAIR || authenticationInformationRequest == null) {
      fail("Did not receive AIR or answer already sent.", null);
      throw new Exception("Did not receive AIR or answer already sent. Request: " + this.authenticationInformationRequest);
    }

    JAuthenticationInformationAnswer aia = super.createAIA(authenticationInformationRequest, 2001);

    this.serverS6aSession.sendAuthenticationInformationAnswer(aia);

    this.sentAIA = true;
    authenticationInformationRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), aia.getMessage(), isSentAIA());
  }

  public void sendUpdateLocationAnswer() throws Exception {
    if (!receivedULR || updateLocationRequest == null) {
      fail("Did not receive ULR or answer already sent.", null);
      throw new Exception("Did not receive ULR or answer already sent. Request: " + this.updateLocationRequest);
    }

    JUpdateLocationAnswer ula = super.createULA(updateLocationRequest, 2001);

    this.serverS6aSession.sendUpdateLocationAnswer(ula);

    this.sentULA = true;
    updateLocationRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), ula.getMessage(), isSentULA());
  }

  public void sendCancelLocationRequest() throws Exception {
    try {
      super.serverS6aSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-S6a-TESTxx"),
          getApplicationId(), ServerS6aSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    JCancelLocationRequest clr = super.createCLR(super.serverS6aSession);
    this.serverS6aSession.sendCancelLocationRequest(clr);
    this.sentCLR = true;
    Utils.printMessage(log, super.stack.getDictionary(), clr.getMessage(), isSentCLR());
  }

  public void sendInsertSubscriberDataRequest() throws Exception {
    try {
      super.serverS6aSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-S6a-TESTxx"),
          getApplicationId(), ServerS6aSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    JInsertSubscriberDataRequest idr = super.createIDR(super.serverS6aSession);
    this.serverS6aSession.sendInsertSubscriberDataRequest(idr);
    this.sentIDR = true;
    Utils.printMessage(log, super.stack.getDictionary(), idr.getMessage(), isSentIDR());
  }

  public void sendDeleteSubscriberDataRequest() throws Exception {
    try {
      super.serverS6aSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-S6a-TESTxx"),
          getApplicationId(), ServerS6aSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    JDeleteSubscriberDataRequest dsr = super.createDSR(super.serverS6aSession);
    this.serverS6aSession.sendDeleteSubscriberDataRequest(dsr);
    this.sentDSR = true;
    Utils.printMessage(log, super.stack.getDictionary(), dsr.getMessage(), isSentDSR());
  }

  public void sendPurgeUEAnswer() throws Exception {
    if (!receivedPUR || purgeUERequest == null) {
      fail("Did not receive PUR or answer already sent.", null);
      throw new Exception("Did not receive PUR or answer already sent. Request: " + this.purgeUERequest);
    }

    JPurgeUEAnswer pua = super.createPUA(purgeUERequest, 2001);

    this.serverS6aSession.sendPurgeUEAnswer(pua);

    this.sentPUA = true;
    purgeUERequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), pua.getMessage(), isSentPUA());
  }

  public void sendResetRequest() throws Exception {
    try {
      super.serverS6aSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-S6a-TESTxx"),
          getApplicationId(), ServerS6aSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    JResetRequest rsr = super.createRSR(super.serverS6aSession);
    this.serverS6aSession.sendResetRequest(rsr);
    this.sentRSR = true;
    Utils.printMessage(log, super.stack.getDictionary(), rsr.getMessage(), isSentRSR());
  }

  public void sendNotifyAnswer() throws Exception {
    if (!receivedNOR || notifyRequest == null) {
      fail("Did not receive NOR or answer already sent.", null);
      throw new Exception("Did not receive NOR or answer already sent. Request: " + this.notifyRequest);
    }

    JNotifyAnswer noa = super.createNOA(notifyRequest, 2001);

    this.serverS6aSession.sendNotifyAnswer(noa);

    this.sentNOA = true;
    notifyRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), noa.getMessage(), isSentNOA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doAuthenticationInformationRequestEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JAuthenticationInformationRequest)
   */
  @Override
  public void doAuthenticationInformationRequestEvent(ServerS6aSession session, JAuthenticationInformationRequest air)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedAIR) {
      fail("Received AIR more than once", null);
      return;
    }
    this.receivedAIR = true;
    this.authenticationInformationRequest = air;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doUpdateLocationRequestEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JUpdateLocationRequest)
   */
  @Override
  public void doUpdateLocationRequestEvent(ServerS6aSession session, JUpdateLocationRequest ulr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedULR) {
      fail("Received ULR more than once", null);
      return;
    }
    this.receivedULR = true;
    this.updateLocationRequest = ulr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doCancelLocationAnswerEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JCancelLocationRequest, org.jdiameter.api.s6a.events.JCancelLocationAnswer)
   */
  @Override
  public void doCancelLocationAnswerEvent(ServerS6aSession session, JCancelLocationRequest clr, JCancelLocationAnswer cla)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), cla.getMessage(), isReceivedCLA());
    if (this.receivedCLA) {
      fail("Received CLA more than once", null);
      return;
    }
    this.receivedCLA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doInsertSubscriberDataAnswerEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest, org.jdiameter.api.s6a.events.JInsertSubscriberDataAnswer)
   */
  @Override
  public void doInsertSubscriberDataAnswerEvent(ServerS6aSession session, JInsertSubscriberDataRequest idr, JInsertSubscriberDataAnswer ida)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), ida.getMessage(), isReceivedIDA());
    if (this.receivedIDA) {
      fail("Received IDA more than once", null);
      return;
    }
    this.receivedIDA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doDeleteSubscriberDataAnswerEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest, org.jdiameter.api.s6a.events.JDeleteSubscriberDataAnswer)
   */
  @Override
  public void doDeleteSubscriberDataAnswerEvent(ServerS6aSession session, JDeleteSubscriberDataRequest dsr, JDeleteSubscriberDataAnswer dsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), dsa.getMessage(), isReceivedDSA());
    if (this.receivedDSA) {
      fail("Received DSA more than once", null);
      return;
    }
    this.receivedDSA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doPurgeUERequestEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JPurgeUERequest)
   */
  @Override
  public void doPurgeUERequestEvent(ServerS6aSession session, JPurgeUERequest pur)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedPUR) {
      fail("Received PUR more than once", null);
      return;
    }
    this.receivedPUR = true;
    this.purgeUERequest = pur;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doResetAnswerEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JResetRequest, org.jdiameter.api.s6a.events.JResetAnswer)
   */
  @Override
  public void doResetAnswerEvent(ServerS6aSession session, JResetRequest rsr, JResetAnswer rsa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), rsa.getMessage(), isReceivedDSA());
    if (this.receivedRSA) {
      fail("Received RSA more than once", null);
      return;
    }
    this.receivedRSA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aServer#doNotifyRequestEvent(
   *    org.jdiameter.api.s6a.ServerS6aSession, org.jdiameter.api.s6a.events.JNotifyRequest)
   */
  @Override
  public void doNotifyRequestEvent(ServerS6aSession session, JNotifyRequest nor)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedNOR) {
      fail("Received NOR more than once", null);
      return;
    }
    this.receivedNOR = true;
    this.notifyRequest = nor;
  }

  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != JAuthenticationInformationRequest.code && code != JUpdateLocationRequest.code && code
        != JPurgeUERequest.code && code != JNotifyRequest.code ) {
      fail("Received Request with code not used by S6a!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverS6aSession == null) {
      try {
        super.serverS6aSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerS6aSession.class, (Object) null);
        ((NetworkReqListener) this.serverS6aSession).processRequest(request);
      } catch (Exception e) {
        fail(null, e);
      }
    } else {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    }
    return null;
  }

  // Attributes for Authentication-Information-Answer (AIA), Update-Location-Answer (ULA),
  // Cancel-Location-Request (CLR), Insert-Subscriber-Data-Request (IDR), Delete-Subscriber-Data-Request (DSR),
  // Purge-UE-Answer (PUA), Reset-Request (RSR), Notify-Answer (NOA)

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
  protected long getEUtranItemNumber() {
    // The Item-Number AVP is of type Unsigned32.
    // The Item Number is used to order Vectors received within one request.
    // Wireshark example taken from a real network capture:
    // AVP: Item-Number(1419) l=16 f=VM- vnd=TGPP val=1
    //    AVP Code: 1419 Item-Number
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Item-Number: 1
    return 1;
  }

  @Override
  protected byte[] getEUtranRAND() {
    // The RAND AVP is of type OctetString. This AVP shall contain the RAND. See 3GPP TS 33.401.
    // Wireshark example taken from a real network capture:
    // AVP: RAND(1447) l=28 f=VM- vnd=TGPP val=0866ef95890f175c92bcce402a502df5
    //    AVP Code: 1447 RAND
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 28
    //    AVP Vendor Id: 3GPP (10415)
    //    RAND: 0866ef95890f175c92bcce402a502df5
    return new byte[] {0x08, 0x66, (byte) 0xef, (byte) 0x95, (byte) 0x89, 0x0f, 0x17, 0x5c,
        (byte) 0x92, (byte) 0xbc, (byte) 0xce, 0x40, 0x2a, 0x50, 0x2d, (byte) 0xf5};
  }

  @Override
  protected byte[] getEUtranXRES() {
    // The XRES  AVP is of type OctetString. This AVP shall contain the XRES. See 3GPP TS 33.401.
    // Wireshark example taken from a real network capture:
    // AVP: XRES(1448) l=20 f=VM- vnd=TGPP val=0941e79080aa54f6
    //    AVP Code: 1448 XRES
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 20
    //    AVP Vendor Id: 3GPP (10415)
    //    XRES: 0941e79080aa54f6
    return new byte[] {0x09, 0x41, (byte) 0xe7, (byte) 0x90, (byte) 0x80, (byte) 0xaa, 0x54, (byte) 0xf6};
  }

  @Override
  protected byte[] getEUtranAUTN() {
    // The AUTN AVP is of type OctetString. This AVP shall contain the AUTN. See 3GPP TS 33.401
    // Wireshark example taken from a real network capture:
    // AVP: AUTN(1449) l=28 f=VM- vnd=TGPP val=43779b3f0eaa8000ae57c71ee7da5885
    //    AVP Code: 1449 AUTN
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 28
    //    AVP Vendor Id: 3GPP (10415)
    //    AUTN: 43779b3f0eaa8000ae57c71ee7da5885
    return new byte[] {0x43, 0x77, (byte) 0x9b, 0x3f, 0x0e, (byte) 0xaa, (byte) 0x80, 0x00,
        (byte) 0xae, 0x57, (byte) 0xc7, 0x1e, (byte) 0xe7, (byte) 0xda, 0x58, (byte) 0x85};
  }

  @Override
  protected byte[] getEUtranKASME() {
    // The KASME AVP is of type OctetString. This AVP shall contain the KASME. See 3GPP TS 33.401.
    // Wireshark example taken from a real network capture:
    // AVP: KASME(1450) l=44 f=VM- vnd=TGPP val=ffa3f69fad25bff0e5506a5696640a351b641228b39ea2811396aa52579a9dc8
    //    AVP Code: 1450 KASME
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 44
    //    AVP Vendor Id: 3GPP (10415)
    //    KASME: ffa3f69fad25bff0e5506a5696640a351b641228b39ea2811396aa52579a9dc8
    return new byte[] {(byte) 0xff, (byte) 0xa3, (byte) 0xf6, (byte) 0x9f, (byte) 0xad, 0x25, (byte) 0xbf, (byte) 0xf0,
        (byte) 0xe5, 0x50, 0x6a, 0x56, (byte) 0x96, 0x64, 0x0a, 0x35, 0x1b, 0x64, 0x12, 0x28, (byte) 0xb3, (byte) 0x9e, (byte) 0xa2, (byte) 0x81,
        0x13, (byte) 0x96, (byte) 0xaa, 0x52, 0x57, (byte) 0x9a, (byte) 0x9d, (byte) 0xc8};
  }

  @Override
  protected long getUtranItemNumber() {
    return 1;
  }

  @Override
  protected byte[] getUtranRAND() {
    return new byte[] {(byte) 0xba, 0x73, 0x31, 0x2e, (byte) 0x8b, (byte) 0xa1, 0x19, 0x75,
        (byte) 0xe0, (byte) 0xe7, (byte) 0xae, 0x2b, (byte) 0xd1, 0x44, (byte) 0xa7, 0x74};
  }

  @Override
  protected byte[] getUtranXRES() {
    return new byte[] {(byte) 0xe4, (byte) 0xb9, (byte) 0xca, 0x0c, 0x2b, 0x12, 0x37, (byte) 0xb6};
  }

  @Override
  protected byte[] getUtranAUTN() {
    return new byte[] {0x5a, (byte) 0xcd, 0x63, 0x56, (byte) 0x83, (byte) 0xfe, (byte) 0x80, 0x00,
        (byte) 0xba, (byte) 0x95, (byte) 0xba, (byte) 0xae, 0x08, 0x0a, 0x30, 0x73};
  }

  @Override
  protected byte[] getConfidentialityKey() {
    return new byte[] {(byte) 0x81, (byte) 0xe8, 0x64, (byte) 0xf0, (byte) 0xc5, 0x0a, 0x53, 0x64,
        (byte) 0xda, (byte) 0xed, 0x49, 0x76, 0x03, (byte) 0xc9, (byte) 0xbf, 0x5d};
  }

  @Override
  protected byte[] getIntegrityKey() {
    return new byte[] {(byte) 0x92, 0x59, 0x62, 0x13, (byte) 0xf2, 0x43, 0x75, 0x69,
        (byte) 0x96, (byte) 0x9d, 0x26, 0x0d, (byte) 0xac, 0x60, (byte) 0xbf, 0x6b};
  }

  @Override
  protected long getGeranItemNumber() {
    return 1;
  }

  @Override
  protected byte[] getGeranRAND() {
    return new byte[] {(byte) 0xba, 0x73, 0x31, 0x2e, (byte) 0x8b, (byte) 0xa1, 0x19, 0x75,
        (byte) 0xe0, (byte) 0xe7, (byte) 0xae, 0x2b, (byte) 0xd1, 0x44, (byte) 0xa7, 0x74};
  }

  @Override
  protected byte[] getSRES() {
    return new byte[] {(byte) 0xe4, (byte) 0xb9, (byte) 0xca, 0x0c, 0x2b, 0x12, 0x37, (byte) 0xb6};
  }

  @Override
  protected byte[] getKc() {
    return new byte[] {(byte) 0x81, (byte) 0xe8, 0x64, (byte) 0xf0, (byte) 0xc5, 0x0a, 0x53, 0x64,
        (byte) 0xda, (byte) 0xed, 0x49, 0x76, 0x03, (byte) 0xc9, (byte) 0xbf, 0x5d};
  }

  @Override
  protected long getUeUsageType() {
    // The UE-Usage-Type AVP is of type Unsigned32. This value shall indicate the usage characteristics of the UE that enables the selection of a specific Dedicated Core Network (DCN). See clause 4.3.25 of 3GPP TS 23.401 [2].
    // The allowed values of UE-Usage-Type shall be in the range of 0 to 255.
    // Values in the range of 0 to 127 are standardized and defined as follows:
    //  0: Spare, for future use
    //  …
    //  127: Spare, for future use
    // Values in the range of 128 to 255 are operator-specific.
    return 201;
  }

  @Override
  protected long getULAFlags() {
    // Wireshark example taken from a real network capture:
    // AVP: ULA-Flags(1406) l=16 f=VM- vnd=TGPP val=1
    //    AVP Code: 1406 ULA-Flags
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    ULA Flags: 0x00000001
    //        0000 0000 0000 0000 0000 0000 0000 00.. = Spare: 0x00000000
    //        .... .... .... .... .... .... .... ..0. = MME Registered for SMS: Not set
    //        .... .... .... .... .... .... .... ...1 = Separation Indication: Set
    return 1;
  }

  @Override
  protected int getSubscriberStatus() {
    // Wireshark example taken from a real network capture:
    // AVP: Subscriber-Status(1424) l=16 f=VM- vnd=TGPP val=SERVICE_GRANTED (0)
    //    AVP Code: 1424 Subscriber-Status
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Subscriber-Status: SERVICE_GRANTED (0)
    return 0;
  }

  @Override
  protected byte[] getMSISDN() {
    // [ MSISDN ]
    return parseTBCD("59899077937");
  }

  @Override
  protected byte[] getAMSISDN() {
    // [ A-MSISDN ]
    return parseTBCD("59898078970");
  }

  @Override
  protected byte[] getSTNSR() {
    // The STN-SR AVP is of type OctetString and shall contain the Session Transfer Number for SRVCC.
    // See 3GPP TS 23.003 for the definition of STN-SR.
    // This AVP contains an STN-SR, in international number format as described in ITU-T Rec E.164,
    // encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    return parseTBCD("598991230057");
  }

  @Override
  protected int getICSIndicator() {
    // The ICS-Indicator AVP is of type Enumerated.
    // The meaning of the values is defined in 3GPP TS 23.292 and 3GPP TS 23.216.
    // The following values are defined:
    //  FALSE (0)
    //  TRUE (1)
    return 0;
  }

  @Override
  protected int getNetworkAccessMode() {
    // Wireshark example taken from a real network capture:
    // AVP: Network-Access-Mode(1417) l=16 f=VM- vnd=TGPP val=PACKET_AND_CIRCUIT (0)
    //    AVP Code: 1417 Network-Access-Mode
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Network-Access-Mode: PACKET_AND_CIRCUIT (0)
    return 0;
  }

  @Override
  protected int getOperatorDeterminedBarring() {
    // The Operator-Determined-Barring AVP is of type Unsigned32, and it shall contain a bit mask indicating the services of a subscriber that are barred by the operator. The meaning of the bits is the following:
    // Table 7.3.30/1: Operator-Determined-Barring
    // Bit  Description
    //  0   All Packet Oriented Services Barred
    //  1   Roamer Access HPLMN-AP Barred
    //  2   Roamer Access to VPLMN-AP Barred
    //  3   Barring of all outgoing calls
    //  4   Barring of all outgoing international calls
    //  5   Barring of all outgoing international calls except those directed to the home PLMN country
    //  6   Barring of all outgoing inter-zonal calls
    //  7   Barring of all outgoing inter-zonal calls except those directed to the home PLMN country
    //  8   Barring of all outgoing international calls except those directed to the home PLMN country
    //      and Barring of all outgoing inter-zonal calls
    return 256;
  }

  @Override
  protected long getHPLMNODB() {
    // The HPLMN-ODB AVP is of type Unsigned32, and it shall contain a bit mask indicating the HPLMN specific services of a subscriber that are barred by the operator. The meaning of the bits is HPLMN specific:
    // Table 7.3.22/1: HPLMN-ODB
    // Bit    Description
    //  0     HPLMN specific barring type 1
    //  1     HPLMN specific barring type 2
    //  2     HPLMN specific barring type 3
    //  3     HPLMN specific barring type 4
    // HPLMN-ODB may apply to mobile originated short messages; See 3GPP TS 23.015.
    return 8;
  }

  @Override
  protected byte[] getRegionalSubscriptionZoneCode() {
    // The Regional-Subscription-Zone-Code AVP is of type OctetString.
    // It shall contain a Zone Code (ZC) as defined in 3GPP TS 23.003, clause 4.4.
    // Up to 10 Zone Codes per VPLMN can be defined as part of the user's subscription data.
    return new byte[] {0x01, 0x08};
  }

  @Override
  protected long getAccessRestrictionData() {
    // Wireshark example taken from a real network capture:
    // AVP: Access-Restriction-Data(1426) l=16 f=VM- vnd=TGPP val=0
    // Access-Restriction-Data Flags: 0x00000100
    //    0000 0000 0000 0000 000. .... .... .... = Spare: 0x00000
    //    .... .... .... .... ...0 .... .... .... = WB-E-UTRAN Except LTE-M Not Allowed: Not set
    //    .... .... .... .... .... 0... .... .... = LTE-M Not Allowed: Not set
    //    .... .... .... .... .... .0.. .... .... = NR in 5G Not Allowed: Not set
    //    .... .... .... .... .... ..0. .... .... = Unlicensed Spectrum as Secondary RAT Not Allowed: Not set
    //    .... .... .... .... .... ...1 .... .... = NR as Secondary RAT Not Allowed: Set
    //    .... .... .... .... .... .... 0... .... = Enhanced Coverage Not Allowed: Not set
    //    .... .... .... .... .... .... .0.. .... = NB-IoT Not Allowed: Not set
    //    .... .... .... .... .... .... ..0. .... = HO-To-Non-3GPP-Access Not Allowed: Not set
    //    .... .... .... .... .... .... ...0 .... = WB-E-UTRAN Not Allowed: Not set
    //    .... .... .... .... .... .... .... 0... = I-HSPA-Evolution Not Allowed: Not set
    //    .... .... .... .... .... .... .... .0.. = GAN Not Allowed: Not set
    //    .... .... .... .... .... .... .... ..0. = GERAN Not Allowed: Not set
    //    .... .... .... .... .... .... .... ...0 = UTRAN Not Allowed: Not set
    return 256;
  }

  @Override
  protected String getAPNOiReplacement() {
    // The APN-OI-Replacement AVP is of type UTF8String.
    // This AVP shall indicate the domain name to replace the APN OI for the non-roaming case
    // and the home routed roaming case when constructing the APN,
    // and the APN-FQDN upon which to perform a DNS resolution.
    // See 3GPP TS 23.003 and 3GPP TS 29.303.
    // The contents of the APN-OI-Replacement AVP shall be formatted as a character string
    // composed of one or more labels separated by dots (".").
    return "ggsn-cluster-A.provinceB.mnc012.mcc345.gprs";
  }

  @Override
  protected byte[] getGMLCNumber() {
    // The GMLC-Number AVP is of type OctetString.
    // This AVP shall contain the ISDN number of the GMLC in international number format
    // as described in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address.
    return parseTBCD("598991230301");
  }

  @Override
  protected byte[] getSSCode() {
    // The SS-Code AVP is of type OctetString. Octets are coded according to 3GPP TS 29.002.
    // Wireshark example taken from a real network capture:
    //  ss-Code: cfu - call forwarding unconditional (33)
    return new byte[] {0x21};
  }

  @Override
  protected byte[] getSSStatus() {
    // The SS-Status AVP is of type OctetString.
    // Octets are coded according to 3GPP TS 29.002. For details, see 3GPP TS 23.011.
    // Wireshark example taken from a real network capture:
    // ss-Status: 0a
    return new byte[] {0x0a};
  }

  @Override
  protected int getNotificationToUeUser() {
    // The Notification- To-UE-User AVP is of type Enumerated. The following values are defined:
    // NOTIFY_LOCATION_ALLOWED (0)
    // NOTIFYANDVERIFY_LOCATION_ALLOWED_IF_NO_RESPONSE (1)
    // NOTIFYANDVERIFY_LOCATION_NOT_ALLOWED_IF_NO_RESPONSE (2)
    // LOCATION_NOT_ALLOWED (3)
    return 0;
  }

  @Override
  protected byte[] getClientIdentity() {
    // The Client-Identity AVP is of type OctetString.
    // This AVP shall contain the ISDN number of the external client  in international number format
    // as described in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address.
    return parseTBCD("575196728474");
  }

  @Override
  protected int getGMLCRestriction() {
    // The GMLC-Restriction AVP is of type Enumerated. The following values are defined:
    // GMLC_LIST (0)
    // HOME_COUNTRY (1)
    return 0;
  }

  @Override
  protected int getPLMNClient() {
    // The PLMN-Client AVP is of type Enumerated. The following values are defined:
    // BROADCAST_SERVICE (0)
    // O_AND_M_HPLMN (1)
    // O_AND_M_VPLMN (2)
    // ANONYMOUS_LOCATION (3)
    // TARGET_UE_SUBSCRIBED_SERVICE (4)
    return 3;
  }

  @Override
  protected long getServiceTypeIdentity() {
    // The ServiceTypeIdentity AVP is of type Unsigned32.
    // For details on the values of this AVP, see 3GPP TS 29.002.
    return 1;
  }

  @Override
  protected byte[] getTSCode() {
    // The TS-Code AVP is of type OctetString. Octets are coded according to 3GPP TS 29.002.
    return new byte[] {0x21};
  }

  @Override
  protected String get3GPPChargingCharacteristics() {
    // Holds the EPS PDN Connection Charging Characteristics data for an EPS APN Configuration,
    // or the PDP context Charging Characteristics for GPRS PDP context,
    // or the Subscribed Charging Characteristics data for the subscriber level 3GPP Charging Characteristics;
    // refer to 3GPP TS 23.008
    // Wireshark example taken from a real network capture:
    // AVP: 3GPP-Charging-Characteristics(13) l=16 f=V-- vnd=TGPP val=0800
    //    AVP Code: 13 3GPP-Charging-Characteristics
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    3GPP-Charging-Characteristics: 0800
    return "0800";
  }

  @Override
  protected long getMaxRequestedBandwidthUL() {
    // The Max-Requested-Bandwidth-UL AVP shall encode the bandwidth value in bits per second,
    // having an upper limit of 4294967295 bits per second.
    // Wireshark example taken from a real network capture:
    // AVP: Max-Requested-Bandwidth-UL(516) l=16 f=VM- vnd=TGPP val=4294967295
    //    AVP Code: 516 Max-Requested-Bandwidth-UL
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Max-Requested-Bandwidth-UL: 4294967295
    return 4294967295L;
  }

  @Override
  protected long getMaxRequestedBandwidthDL() {
    // The Max-Requested-Bandwidth-DL AVP shall encode the bandwidth value in bits per second,
    // having an upper limit of 4294967295 bits per second.
    // Wireshark example taken from a real network capture:
    // AVP: Max-Requested-Bandwidth-DL(515) l=16 f=VM- vnd=TGPP val=4294967295
    //    AVP Code: 515 Max-Requested-Bandwidth-DL
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Max-Requested-Bandwidth-DL: 4294967295
    return 4294967295L;
  }

  @Override
  protected long getExtendedMaxRequestedBWUL() {
    // The Extended-Max-Requested-BW-UL AVPs shall encode the bandwidth value in kilobits
    // (1000 bits) per second, having an upper limit of 4294967295 kilobits per second.
    // Wireshark example taken from a real network capture:
    // AVP: Extended-Max-Requested-BW-UL(555) l=16 f=V-- vnd=TGPP val=4294967295
    //    AVP Code: 555 Extended-Max-Requested-BW-UL
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Extended-Max-Requested-BW-UL: 4294967295
    return 4294967295L;
  }

  @Override
  protected long getExtendedMaxRequestedBWDL() {
    // The Extended-Max-Requested-BW-DL) AVPs shall encode the bandwidth value in kilobits
    // (1000 bits) per second, having an upper limit of 4294967295 kilobits per second.
    // Wireshark example taken from a real network capture:
    // AVP: Extended-Max-Requested-BW-DL(554) l=16 f=V-- vnd=TGPP val=4294967295
    //    AVP Code: 554 Extended-Max-Requested-BW-DL
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Extended-Max-Requested-BW-DL: 4294967295
    return 4294967295L;
  }

  @Override
  protected long getContextIdentifier() {
    // Wireshark example taken from a real network capture:
    // AVP: Context-Identifier(1423) l=16 f=VM- vnd=TGPP val=1
    //    AVP Code: 1423 Context-Identifier
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Context-Identifier: 1
    return 1;
  }

  @Override
  protected long getAdditionalContextIdentifier() {
    // The Additional-Context-Identifier AVP is of type Unsigned32 and
    // indicates the identity of another default APN to be used
    // when the subscription profile of the user contains APNs
    // with more than one PDN type among IP-based PDN types,
    // non-IP PDN types and Ethernet PDN types
    return 2;
  }

  @Override
  protected int getThirdContextIdentifier() {
    // The Third-Context-Identifier AVP is of type Unsigned32 and indicates the identity of
    // another default APN to be used when the subscription profile of the user contains APNs
    // with three PDN types, i.e. IP-based PDN types, non-IP PDN types and Ethernet PDN types.
    return 3;
  }

  @Override
  protected int getAllAPNConfigurationsIncludedIndicator() {
    // The All-APN-Configurations-Included-Indicator AVP is of type Enumerated.
    // The following values are defined:
    //  All_APN_CONFIGURATIONS_INCLUDED (0)
    //  MODIFIED_ADDED_APN_CONFIGURATIONS_INCLUDED (1)
    // Wireshark example taken from a real network capture:
    // AVP: All-APN-Configurations-Included-Indicator(1428) l=16 f=VM- vnd=TGPP val=ALL_APN_CONFIGURATIONS_INCLUDED (0)
    //    AVP Code: 1428 All-APN-Configurations-Included-Indicator
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    All-APN-Configurations-Included-Indicator: ALL_APN_CONFIGURATIONS_INCLUDED (0)
    return 0;
  }

  @Override
  protected InetAddress getServedPartyIPAddress() {
    // The Served-Party-IP-Address AVP may be present 0, 1 or 2 times.
    // These AVPs shall be present if static IP address allocation is used for the UE, and they shall contain either of:
    // - an IPv4 address, or
    // - an IPv6 address/prefix, or
    // - both, an IPv4 address and an IPv6 address/prefix.
    // For the IPv6 prefix, the lower 64 bits of the address shall be set to zero.
    InetAddress servedPartyIPAddress = null;
    try {
      servedPartyIPAddress = InetAddress.getByName("2001:0db8:85a3:8a2e:0000:0000:0000:0000");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return servedPartyIPAddress;
  }

  @Override
  protected int getPDNType() {
    // The PDN-Type AVP is of type Enumerated and indicates the address type of the PDN, when it is IP-based.
    // The following values are defined:
    //  IPv4 (0)
    //  IPv6 (1)
    //  IPv4v6 (2)
    //  IPv4_OR_IPv6 (3)
    // Wireshark example taken from a real network capture:
    // AVP: PDN-Type(1456) l=16 f=VM- vnd=TGPP val=IPv4 (0)
    //    AVP Code: 1456 PDN-Type
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    PDN-Type: IPv4 (0)
    return 0;
  }

  @Override
  protected String getServiceSelection() {
    // Wireshark example taken from a real network capture:
    // AVP: Service-Selection(493) l=26 f=-M- val=stg.eu.ng.1nce.net
    //    AVP Code: 493 Service-Selection
    //    AVP Flags: 0x40, Mandatory: Set
    //    AVP Length: 26
    //    Service-Selection: stg.eu.ng.1nce.net
    //    Padding: 0000
    return "seairis.dev.eu.1nce.net";
  }

  @Override
  protected int getQCI() {
    // QoS-Class-Identifier is defined in 3GPP TS 29.212 as an Enumerated AVP.
    // The values allowed for this AVP over the S6a/S6d interface are only those associated
    // to non-GBR bearers, as indicated in 3GPP TS 23.008;
    // e.g., values QCI_1, QCI_2, QCI_3 and QCI_4, which are associated to GBR bearers, cannot be sent over S6a/S6d
    // Wireshark example taken from a real network capture:
    // AVP: QoS-Class-Identifier(1028) l=16 f=VM- vnd=TGPP val=QCI_9 (9)
    //    AVP Code: 1028 QoS-Class-Identifier
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    QoS-Class-Identifier: QCI_9 (9)
    return 9;
  }

  @Override
  protected long getPriorityLevel() {
    // The Priority-Level AVP (AVP code 1046) is of type Unsigned 32.
    // The AVP is used for deciding whether a bearer establishment or modification request
    // can be accepted or needs to be rejected in case of resource limitations
    // (typically used for admission control of GBR traffic).
    // The AVP can also be used to decide which existing bearers to pre-empt during
    // resource limitations. The priority level defines the relative importance of a resource request.
    // Values 1 to 15 are defined, with value 1 as the highest level of priority.
    // Values 1 to 8 should only be assigned for services that are authorized
    // to receive prioritized treatment within an operator domain.
    // Values 9 to 15 may be assigned to resources that are authorized by the home network
    // and thus applicable when a UE is roaming.
    // Wireshark example taken from a real network capture:
    // AVP: Priority-Level(1046) l=16 f=V-- vnd=TGPP val=9
    //    AVP Code: 1046 Priority-Level
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Priority-Level: 9
    return 9;
  }

  @Override
  protected int getPreemptionCapability() {
    // The Pre-emption-Capability AVP (AVP code 1047) is of type Enumerated.
    // If it is provided within the QoS-Information AVP, the AVP defines whether a service data flow
    // can get resources that were already assigned to another service data flow with a lower priority level.
    // If it is provided within the Default-EPS-Bearer-QoS AVP, the AVP defines whether the default bearer
    // can get resources that were already assigned to another bearer with a lower priority level.
    // The following values are defined:
    //  PRE-EMPTION_CAPABILITY_ENABLED (0)
    //	PRE-EMPTION_CAPABILITY_DISABLED (1)
    // Wireshark example taken from a real network capture:
    // AVP: Pre-emption-Capability(1047) l=16 f=V-- vnd=TGPP val=PRE-EMPTION_CAPABILITY_DISABLED (1)
    //    AVP Code: 1047 Pre-emption-Capability
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Pre-emption-Capability: PRE-EMPTION_CAPABILITY_DISABLED (1)
    return 1;
  }

  @Override
  protected int getPreemptionVulnerability() {
    // The Pre-emption Vulnerability AVP (AVP code 1048) is of type Enumerated.
    // If it is provided within the QoS-Information AVP, the AVP defines whether a service data flow
    // can lose the resources assigned to it in order to admit a service data flow with higher priority level.
    // If it is provided within the Default-EPS-Bearer-QoS AVP,
    // the AVP defines whether the default bearer can lose the resources assigned
    // to it in order to admit a pre-emption capable bearer with a higher priority level.
    // The following values are defined:
    //  PRE-EMPTION_VULNERABILITY_ENABLED (0)
    //  PRE-EMPTION_VULNERABILITY_DISABLED (1)
    // Wireshark example taken from a real network capture:
    // AVP: Pre-emption-Vulnerability(1048) l=16 f=V-- vnd=TGPP val=PRE-EMPTION_VULNERABILITY_ENABLED (0)
    //    AVP Code: 1048 Pre-emption-Vulnerability
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Pre-emption-Vulnerability: PRE-EMPTION_VULNERABILITY_ENABLED (0)
    return 0;
  }

  @Override
  protected int getVPLMNDynamicAddressAllowed() {
    // The VPLMN-Dynamic-Address-Allowed AVP is of type Enumerated.
    // It shall indicate whether for this APN, the UE is allowed to use the PDN GW in the domain
    // of the HPLMN only, or additionally, the PDN GW in the domain of the VPLMN.
    // If this AVP is not present, this means that the UE is not allowed to use PDN GWs in the domain
    // of the VPLMN. The following values are defined:
    //  NOTALLOWED (0)
    //  ALLOWED (1)
    // Wireshark example taken from a real network capture:
    // AVP: VPLMN-Dynamic-Address-Allowed(1432) l=16 f=VM- vnd=TGPP val=NOTALLOWED (0)
    //    AVP Code: 1432 VPLMN-Dynamic-Address-Allowed
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    VPLMN-Dynamic-Address-Allowed: NOTALLOWED (0)
    return 0;
  }

  @Override
  protected InetAddress getMIPHomeAgentAddress() {
    // [ MIP-Home-Agent-Address ]
    // IETF RFC 4004:
    // The MIP-Home-Agent-Address AVP (AVP Code 334) is of type Address and
    // contains the mobile node's home agent IP address
    InetAddress mipHomeAgentAddress = null;
    try {
      mipHomeAgentAddress = InetAddress.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return mipHomeAgentAddress;
  }

  // [ MIP-Home-Agent-Host ]
  // The MIP-Home-Agent-Host is of type Grouped and is defined in IETF RFC 4004.
  // This AVP shall contain a FQDN of the PDN-GW which shall be used to resolve the PDN-GW IP address
  // using the Domain Name Service function.
  // MIP-Home-Agent-Host grouped AVP is composed by Destination-Host and Destination-Realm AVPs.
  //  Destination-Host shall contain the hostname of the PDN-GW, formatted as described in 3GPP TS 29.303 [38], clause 4.3.2.
  //  Destination-Realm shall be formatted as:
  //   epc.mnc<MNC>.mcc<MCC>.3gppnetwork.org
  //  where MNC and MCC values indicate the PLMN where the PDN-GW is located.
  @Override
  protected String getMIPHomeAgentHostDestRealm() {
    return "epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected String getMIPHomeAgentHostDestHost() {
    return "topoff.s5.v01.stg-eu-ng-01-2.mvno1.node.epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected byte[] getVisitedNetworkIdentifier() {
    // The Visited-Network-Identifier AVP contains the identity of the network where the PDN-GW was allocated.
    return new byte[] {0x47, (byte) 0xf8, 0x10};
  }

  @Override
  protected int getPDNGwAllocationType() {
    // The PDN-GW-Allocation-Type AVP is of type Enumerated.
    // It shall indicate whether the PDN GW address included in MIP6-Agent-Info has been statically
    // allocated (i.e. provisioned in the HSS by the operator), or dynamically selected by other nodes.
    // The following values are defined:
    //  STATIC (0)
    //  DYNAMIC (1)
    // Wireshark example taken from a real network capture:
    // AVP: PDN-GW-Allocation-Type(1438) l=16 f=VM- vnd=TGPP val=DYNAMIC (1)
    //    AVP Code: 1438 PDN-GW-Allocation-Type
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    PDN-GW-Allocation-Type: DYNAMIC (1)
    return 1;
  }

  @Override
  protected String getSpecificApnServiceSelection() {
    return "stg.eu.ng.1nce.net";
  }

  @Override
  protected InetAddress getSpecificApnMIPHomeAgentAddress() {
    InetAddress mipHomeAgentAddress = null;
    try {
      mipHomeAgentAddress = InetAddress.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return mipHomeAgentAddress;
  }

  @Override
  protected String getSpecificApnMIPHomeAgentHostDestRealm() {
    return "epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected String getSpecificApnMIPHomeAgentHostDestHost() {
    return "topoff.s5.v01.stg-eu-ng-01-2.mvno1.node.epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected byte[] getSpecificApnVisitedNetworkIdentifier() {
    return new byte[] {0x47, (byte) 0xf8, 0x10};
  }

  @Override
  protected int getSIPTOPermission() {
    // The SIPTO-Permission AVP is of type Enumerated.
    // It shall indicate whether the traffic associated with this particular APN is allowed or not for SIPTO above RAN.
    // The following values are defined:
    //  SIPTO_above_RAN_ALLOWED (0)
    //  SIPTO_above_RAN_NOTALLOWED (1)
    return 0;
  }

  @Override
  protected int getLIPAPermission() {
    // The LIPA-Permission AVP is of type Enumerated.
    // It shall indicate whether the APN can be accessed via Local IP Access.
    // The following values are defined:
    //  LIPA_PROHIBITED (0)
    //  LIPA_ONLY (1)
    //  LIPA_CONDITIONAL (2)
    return 2;
  }

  @Override
  protected long getRATFrequencySelectionPriorityID() {
    // The RAT-Frequency-Selection-Priority-ID AVP is of type Unsigned32
    // and shall contain the subscribed value of Subscriber Profile ID for RAT/Frequency Priority.
    // For details, see 3GPP TS 23.401 and 3GPP TS 23.060.
    // The coding is defined in 3GPP TS 36.413. Values shall be in the range of 1 to 256.
    return 256;
  }

  @Override
  protected byte[] getTraceReference() {
    // The Trace-Reference AVP is of type OctetString.
    // This AVP shall contain the concatenation of MCC, MNC and Trace ID,
    // where the Trace ID is a 3 byte Octet String. See 3GPP TS 32.422.
    // The content of this AVP shall be encoded as octet strings according to table 7.3.64/1.
    // See 3GPP TS 24.008, clause 10.5.1.13, PLMN list, for the coding of MCC and MNC.
    // If MNC is 2 digits long, bits 5 to 8 of octet 2 are coded as "1111".
    return parseTBCD("59899123030");
  }

  @Override
  protected int getTraceDepth() {
    // The Trace-Depth AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422:
    // Trace depth shall be an enumerated parameter with the following possible values:
    // Minimum (0),
    // Medium (1),
    // Maximum (2),
    // MinimumWithoutVendorSpecificExtension (3),
    // MediumWithoutVendorSpecificExtension (4),
    // MaximumWithoutVendorSpecificExtension (5),
    // MinimumOnlyVendorSpecificTraceRecord (6),
    // MediumOnlyVendorSpecificTraceRecord (7),
    // MaximumOnlyVendorSpecificTraceRecord (8).
    return 2;
  }

  @Override
  protected byte[] getTraceNETypeList() {
    // The Trace-NE-Type-List AVP is of type OctetString. Octets are coded according to 3GPP TS 32.422.
    return new byte[] {(byte) 0xFE, 0x03, 0x00};
  }

  @Override
  protected byte[] getTraceInterfaceList() {
    // The Trace-Interface-List AVP is of type OctetString. Octets are coded according to 3GPP TS 32.422.
    return new byte[] {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  }

  @Override
  protected byte[] getTraceEventList() {
    // The Trace-Event-List AVP is of type OctetString. Octets are coded according to 3GPP TS 32.422.
    return new byte[] {0x0F, 0x00, 0x00, 0x00};
  }

  @Override
  protected byte[] getOMCId() {
    // The OMC-Id AVP is of type OctetString. Octets are coded according to 3GPP TS 29.002.
    // Refers to the identity of an Operation and Maintenance Centre (AddressString).
    return new byte[] {(byte) 0x91, (byte) 0x94, 0x71, 0x01, 0x64, 0x00, 0x00};
  }

  @Override
  protected InetAddress getTraceCollectionEntity() {
    // The Trace-Collection-Entity AVP is of type Address and contains the IPv4 or IPv6 address
    // of the Trace Collection Entity, as defined in 3GPP TS 32.422, clause 5.9
    InetAddress tracesCollectionEntity = null;
    try {
      tracesCollectionEntity = InetAddress.getByName("10.0.0.9");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return tracesCollectionEntity;
  }

  @Override
  protected int getJobType() {
    // The Job-Type AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422
    //   <avpdefn name="Job-Type" code="1623" vendor-id="TGPP" mandatory="mustnot" protected="mustnot" may-encrypt="no" vendor-bit="must" >
    //    <type type-name="Enumerated">
    //      <enum code="0" name="IMMEDIATE_MDT_ONLY"/>
    //      <enum code="1" name="LOGGED_MDT_ONLY"/>
    //      <enum code="2" name="TRACE_ONLY"/>
    //      <enum code="3" name="IMMEDIATE_MDT_AND_TRACE"/>
    //      <enum code="4" name="RLF_REPORTS_ONLY"/>
    //      <enum code="5" name="RCEF_REPORTS_ONLY"/>
    //      <enum code="6" name="LOGGED_MBSFN_MDT"/>
    //      <enum code="7" name="5GC_UE_LEVEL_MEASUREMENTS_ONLY"/>
    //      <enum code="8" name="TRACE_AND_5GC_UE_LEVEL_MEASUREMENTS"/>
    //      <enum code="9" name="IMMEDIATE_MDT_AND_5GC_UE_LEVEL_MEASUREMENTS"/>
    //      <enum code="10" name="TRACE_AND_IMMEDIATE_MDT_AND_5GC_UE_LEVEL_MEASUREMENTS"/>
    //      <enum code="11" name="RRC_REPORT"/>
    //    </type>
    //  </avpdefn>
    return 3;
  }

  @Override
  protected byte[] getCellGlobalIdentity() {
    // The Cell-Global-Identity AVP is of type OctetString and shall contain the Cell Global Identification
    // of the user which identifies the cell the user equipment is registered, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    // AVP: Cell-Global-Identity(1604) l=19 f=V-- vnd=TGPP val=47f81000773be8
    //    AVP Code: 1604 Cell-Global-Identity
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 19
    //    AVP Vendor Id: 3GPP (10415)
    //    Cell-Global-Identity: 47f81000773be8
    //    Padding: 00
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x77, 0x3b, (byte) 0xe8};
  }

  @Override
  protected byte[] getEUtranCellGlobalIdentity() {
    // The E-UTRAN-Cell-Global-Identity AVP is of type OctetString and shall contain the
    // E-UTRAN Cell Global Identification of the user which identifies the cell the user equipment is registered,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x09, 0x5f, 0x02};
  }

  @Override
  protected byte[] getRoutingAreaIdentity() {
    // The Routing-Area-Identity AVP is of type OctetString and shall contain the Routing Area Identity
    // of the user which identifies the routing area where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x65, 0x17};
  }

  @Override
  protected byte[] getLocationAreaIdentity() {
    // The Location-Area-Identity AVP is of type OctetString and shall contain the Location Area Identification
    // of the user which identifies the Location area where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {4, 5, 82, (byte) 240, 16, 17, 92};
  }

  @Override
  protected byte[] getTrackingAreaIdentity() {
    // The Tracking-Area-Identity AVP is of type OctetString and shall contain the
    // Tracking Area Identity of the user which identifies the tracking area where the user is located,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x6d};
  }

  @Override
  protected byte[] getNRCellGlobalIdentity() {
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x09, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfa};
  }

  @Override
  protected long getListOfMeasurements() {
    // The List-Of-Measurements AVP is of type Unsigned32,
    // and it shall contain a bit mask. The meaning of the bits is defined in 3GPP TS 32.422.
    // This parameter is mandatory if the Job Type parameter indicates Immediate MDT.
    // This parameter defines the measurements that shall be collected.
    // For further details see also TS 37.320.
    // The parameter is 4 octet long bitmap
    return 268435456L;
  }

  @Override
  protected long getReportingTrigger() {
    // The Reporting-Trigger AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits is defined in 3GPP TS 32.422.
    // The most significant bit is bit 8 of the first octet
    return 2;
  }

  @Override
  protected int getReportingInterval() {
    // The Report-Interval AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422
    return 2; // 480 ms (LTE)
  }

  @Override
  protected int getReportingAmount() {
    // The Report-Amount AVP is of type Enumerated. The possible values are those defined in 3GPP TS 32.422.
    return 6; // 64 (LTE)
  }

  @Override
  protected long getEventThresholdRSRP() {
    // The Event-Threshold-RSRP AVP is of type Unsigned32. See 3GPP TS 32.422 for allowed values.
    // RSRP range: 0 – 127 (for calculating the actual value see RSRP-Range in TS 38.331)
    return 0;
  }

  @Override
  protected long getEventThresholdRSRQ() {
    // // The Event-Threshold-RSRQ AVP is of type Unsigned32. See 3GPP TS 32.422 for allowed values.
    // RSRQ range: 0 – 127 (for calculating the actual value see RSRQ-Range in TS 38.331).
    return 127;
  }

  @Override
  protected int getLoggingInterval() {
    // The Logging-Interval AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 3; // 10240 ms (3) (LTE)
  }

  @Override
  protected int getLoggingDuration() {
    // The Logging-Duration AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 5; // 7200 sec (5)
  }

  @Override
  protected int getMeasurementPeriodLTE() {
    // The Measurement-Period-LTE AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 6; // 1 min (6)
  }

  @Override
  protected int getMeasurementPeriodUMTS() {
    // The Measurement-Period-UMTS AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 12; // 64000 ms (12)
  }

  @Override
  protected int getCollectionPeriodRMMLTE() {
    // The Collection-Period-RRM-LTE AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 9; // 1 min (9)
  }

  @Override
  protected int getCollectionPeriodRMMUMTS() {
    // The Collection-Period-RRM-UMTS AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 7; // 6000 ms (7)
  }

  @Override
  protected byte[] getPositioningMethod() {
    // The Positioning-Method AVP is of type OctetString.
    // It contains one octet carrying a bit map of 8 bits.
    // The possible values are those defined in 3GPP TS 32.422.
    return new byte[] {0x03};
  }

  @Override
  protected byte[] getMeasurementQuantity() {
    // The Measurement-Quantity AVP is of type OctetString.
    // It contains one octet carrying a bit map of 8 bits.
    // The possible values are those defined in 3GPP TS 32.422.
    return new byte[] {0x04};
  }

  @Override
  protected int getEventThresholdEvent1F() {
    // The Event-Threshold-Event-1F AVP is of type Integer32. See 3GPP TS 32.422 [23] for allowed values.
    // The parameter is an Integer number with the value range -120..165
    return 165;
  }

  @Override
  protected int getEventThresholdEvent1I() {
    // The Event-Threshold-Event-1I AVP is of type Integer32. See 3GPP TS 32.422 [23] for allowed values.
    // The parameter is an Integer number with the value range -120..-25
    return 25;
  }

  @Override
  protected byte[] getMDTAllowedPLMNId() {
    return new byte[] {0x47, (byte) 0xf8, 0x10};
  }

  @Override
  protected long getMBSFNAreaID() {
    // The MBSFN-Area-ID AVP is of type Unsigned32, and it shall contain the MBSFN Area ID value,
    // in the range of 0..255 (see 3GPP TS 36.331).
    return 255;
  }

  @Override
  protected long getCarrierFrequency() {
    // The Carrier-Frequency AVP is of type Unsigned32, and it shall contain the Carrier Frequency value,
    // in the range of 0..262143 (see 3GPP TS 36.331)
    return 262143;
  }

  @Override
  protected long getEventThresholdSINR() {
    // The Event-Threshold-SINR AVP is of type Unsigned32. See 3GPP TS 32.422 [23] for allowed values.
    return 127;
  }

  @Override
  protected int getCollectionPeriodRRMNR() {
    // The Collection-Period-RRM-NR AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 4; // 60000 ms (4)
  }

  @Override
  protected int getCollectionPeriodM6NR() {
    // The Collection-Period-M6-NR AVP is of type Enumerated.
    // The possible values are those defined in 3GPP TS 32.422.
    return 3; // 10240 ms (3)
  }

  @Override
  protected int getCollectionPeriodM7NR() {
    // The Collection-Period-M7-NR AVP is of type Unsigned32.
    // The possible values are those defined in 3GPP TS 32.422 for Collection period M7 in NR in units of minutes.
    // The parameter is an integer type with the following values (detailed definition is in 3GPP TS 36.413 [36]):
    // 1..60 min
    return 60;
  }

  @Override
  protected int getSensorMeasurement() {
    // The Sensor-Measurement AVP is of type Enumerated and shall contain the values defined in 3GPP TS 32.422 [23] for Sensor Information.
    // The following values are defined:
    //  BAROMETRIC_PRESSURE (0)
    //  UE_SPEED (1)
    //  UE_ORIENTATION (2)
    return 2;
  }

  @Override
  protected String getTraceReportingConsumerUri() {
    // The Trace-Reporting-Consumer-Uri AVP is of type DiameterURI. For Streaming based Reporting,
    // the possible values are those defined in 3GPP TS 32.422 for Trace Reporting Consumer URI.
    // This AVP shall have priority if both Trace-Collection-Entity AVP and
    // Trace-Reporting-Consumer-Uri AVP are present.
    return "aaa://trace.example.com:3868";
  }

  @Override
  protected int getCompleteDataListIncludedIndicator() {
    // The Complete-Data-List-Included-Indicator AVP is of type Enumerated.
    // The following values are defined:
    //  All_PDP_CONTEXTS_INCLUDED (0)
    //  MODIFIED_ADDED_PDP CONTEXTS_INCLUDED (1)
    return 0;
  }

  @Override
  protected byte[] getPDPType() {
    // The PDP-Type AVP is of type OctetString. Octets are coded according to 3GPP TS 29.002.
    // The allowed values are one of IPv4 encoded as HEX (21) or IPv6 encoded as HEX (57) or Non-IP encoded as HEX (02)
    return new byte[] {0x21};
  }

  @Override
  protected InetAddress getPDPAddress() {
    // The PDP-Address AVP (AVP code 1227) is of type Address and holds the IP-address
    // associated with the IP CAN bearer session (PDP context / PDN connection).
    // The PDP-Address-Prefix-Length AVP needs not be available for IPv6 typed IP-address prefix length of 64 bits.
    InetAddress pdpAddress = null;
    try {
      pdpAddress = InetAddress.getByName("2001:0db8:85a3:8a2f:0000:0000:0000:0000");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return pdpAddress;
  }

  @Override
  protected byte[] getQoSSubscribed() {
    // The QoS-Subscribed AVP is of type OctetString. Octets are coded according to 3GPP TS 29.002
    // (octets of QoS-Subscribed, Ext-QoS-Subscribed, Ext2-QoS-Subscribed, Ext3-QoS-Subscribed
    // and Ext4-QoS-Subscribed values are concatenated).
    // Wireshark example taken from real network capture:
    // PDP-Context
    //    qos-Subscribed: 13831f
    //    00.. .... = Spare bit(s): 0
    //    ..01 0... = Quality of Service Delay class: Delay class 2 (2)
    //    .... .011 = Reliability class: Unacknowledged GTP/LLC, Ack RLC, Protected data (3)
    //    1000 .... = Peak throughput: Up to 128 000 octet/s (8)
    //    .... 0... = Spare bit(s): 0
    //    .... .011 = Precedence class: Low priority (3)
    //    000. .... = Spare bit(s): 0
    //    ...1 1111 = Mean throughput: Best effort (31)
    //    ext-QoS-Subscribed: 097297804000a34000
    //        0000 1001 = Allocation/Retention priority: 9
    //        011. .... = Traffic class: Interactive class (3)
    //        ...1 0... = Delivery order: Streaming class (2)
    //        .... ..10 = Delivery of erroneous SDUs: Erroneous SDUs are delivered('yes') (2)
    //        Maximum SDU size: 0x97 not defined in TS 24.008
    //        Maximum bit rate for uplink in kbit/s: 576
    //        Maximum bit rate for downlink in kbit/s: 64
    //        0000 .... = Residual Bit Error Rate (BER): Subscribed residual BER/Reserved (0)
    //        .... 0000 = SDU error ratio: Subscribed SDU error ratio/Reserved (0)
    //        1010 00.. = Transfer delay (Raw data see TS 24.008 for interpretation): 40
    //        .... ..11 = Traffic handling priority: Priority level 3 (3)
    //        Guaranteed bit rate for uplink in kbit/s: 64
    //        Guaranteed bit rate for downlink in kbit/s: Subscribed guaranteed bit rate for downlink/reserved
    //    .... 1000 .... .... = pdp-ChargingCharacteristics: N (Normal billing) (8)
    //    ext2-QoS-Subscribed: 100000
    //        000. .... = Spare bit(s): 0
    //        ...1 .... = Signalling indication: Optimised for signalling traffic
    //        .... 0000 = Source statistics description: unknown (0)
    //        Maximum bitrate for downlink (extended): Use the value indicated by the Maximum bit rate for downlink (0)
    //        Guaranteed bitrate for downlink (extended): Use the value indicated by the Guaranteed bit rate for downlink (0)
    //    ext3-QoS-Subscribed: 0000
    //        Maximum bitrate for uplink (extended): Use the value indicated by the Maximum bit rate for uplink (0)
    //        Guaranteed bitrate for uplink (extended): Use the value indicated by the Guaranteed bit rate for uplink (0)
    //    ext4-QoS-Subscribed: 5b
    //        .... ...1 = PVI Pre-emption Vulnerability: Disabled
    //        ..01 10.. = PL Priority Level: 6
    //        .1.. .... = PCI Pre-emption Capability: Disabled
    return new byte[] {0x13, (byte) 0x83, 0x1f, 0x09, 0x72,
        (byte) 0x97, (byte) 0x80, 0x40, 0x00, (byte) 0xa3, 0x40, 0x00,
        0x10, 0x00, 0x00,
        0x00, 0x00,
        0x5b};
  }

  @Override
  protected byte[] getExtPDPType() {
    // Wireshark example taken from jSS7 MAP load network capture:
    // PDP-Context
    //    pdp-Type: f121
    //    .... 0001 = PDP Type Organization: IETF (0x1)
    return new byte[] {(byte) 0xf1, 0x21};
  }

  @Override
  protected InetAddress getExtPDPAddress() {
    // The Ext-PDP-Address AVP may be present only if the PDP-Address AVP is present.
    // If the Ext-PDP-Address AVP is present, then it shall not contain the same address type
    // (IPv4 or IPv6) as the PDP-Address AVP
    InetAddress pdpAddress = null;
    try {
      pdpAddress = InetAddress.getByName("2001:0db8:85a3:8a1e:0000:0000:0000:0000");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return pdpAddress;
  }

  @Override
  protected long getRestorationPriority() {
    // The Restoration-Priority AVP is of type Unsigned32.
    // It shall indicate the relative priority of a user's PDN connection among PDN connections
    // to the same APN when restoring PDN connections affected by an SGW or PGW failure/restart
    // (see 3GPP TS 23.007).
    // Values 1 to 16 are defined, with value 1 as the highest level of priority
    return 9;
  }

  @Override
  protected long getSIPTOLocalNetworkPermission() {
    // The SIPTO-Local-Network-Permission AVP is of type Unsigned32.
    // It shall indicate whether the traffic associated with this particular APN
    // is allowed or not for SIPTO at the local network.
    // The following values are defined:
    // "SIPTO at Local Network ALLOWED"     0
    // "SIPTO at Local Network NOTALLOWED"  1
    return 0;
  }

  @Override
  protected int getNonIPDataDeliveryMechanism() {
    // The Non-IP-Data-Delivery-Mechanism AVP is of type Unsigned32 and indicates the mechanism
    // to be used for Non-IP data delivery for a given APN. The following values are defined:
    //  SGi-BASED-DATA-DELIVERY (0)
    //   This value indicates that the Non-IP data is delivered via Point-To-Point tunnelling over the SGi interface.
    //  SCEF-BASED-DATA-DELIVERY (1)
    //   This value indicates that the Non-IP data is delivered via the SCEF.
    return 0;
  }

  @Override
  protected String getSCEFId() {
    // The SCEF-ID AVP is of type DiameterIdentity, and it shall contain the identity of the SCEF
    // which has originated the service request towards the HSS,
    // i.e. when sent within a Monitoring-Event-Configuration AVP in S6t-CIR,
    // SCEF-ID AVP and Origin-Host AVP shall have the same value.
    return "scefo1.epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected long getCSGId() {
    // The CSG-Id AVP is of type Unsigned32. Values are coded according to 3GPP TS 23.003.
    return 32L;
  }

  @Override
  protected Time getExpirationDate() {
    // The Expiration-Date AVP is of type Time (see IETF RFC 6733) and contains
    // the point in time when subscription to the CSG-Id expires.
    return Time.valueOf("23:59:59");
  }

  @Override
  protected byte[] getVisitedPLMNId() {
    // Wireshark example taken from a real network capture:
    // AVP: Visited-PLMN-Id(1407) l=15 f=VM- vnd=TGPP val=MCC 748 Uruguay, MNC 01 Administración Nacional de Telecomunicaciones (ANTEL)
    //    AVP Code: 1407 Visited-PLMN-Id
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 15
    //    AVP Vendor Id: 3GPP (10415)
    //    Visited-PLMN-Id: 47f810
    //    Mobile Country Code (MCC): Uruguay (748)
    //    Mobile Network Code (MNC): Administración Nacional de Telecomunicaciones (ANTEL) (01)
    //    Padding: 00
    return new byte[] {0x47, (byte) 0xf8, 0x10};
  }

  @Override
  protected int getRoamingRestrictedDueToUnsupportedFeature() {
    // The Roaming-Restricted-Due-To-Unsupported-Feature AVP is of type Enumerated and indicates that
    // roaming is restricted due to unsupported feature. The following value is defined:
    //  Roaming-Restricted-Due-To-Unsupported-Feature (0)
    return 0;
  }

  @Override
  protected long getSubscribedPeriodicRAUTAUTimer() {
    // The Subscribed-Periodic-RAU-TAU-Timer AVP is of type Unsigned32, and it shall contain
    // the subscribed periodic RAU/TAU timer value in seconds as specified in 3GPP TS 24.008.
    return 60;
  }

  @Override
  protected long getMPSPriority() {
    // The MPS-Priority AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.3.131/1:
    // Table 7.3.131/1: MPS-Priority
    // Bit  Name              Description
    //  0   MPS-CS-Priority	  This bit, when set, indicates that the UE is subscribed to the eMLPP or 1x RTT priority service in the CS domain.
    //  1   MPS-EPS-Priority  This bit, when set, indicates that the UE is subscribed to the MPS in the EPS domain.
    //  2   MPS-for-Messaging This bit, when set, indicates that the UE is enabled for MPS priority for messaging.
    return 6;
  }

  @Override
  protected int getVPLMNLIPAAllowed() {
    // The VPLMN-LIPA-Allowed AVP is of type Enumerated.
    // It shall indicate whether the UE is allowed to use LIPA in the VPLMN where the UE is roaming.
    // The following values are defined:
    //  LIPA_NOTALLOWED (0)
    //   This value indicates that the UE is not allowed to use LIPA in the VPLMN where the UE is roaming.
    //  LIPA_ALLOWED (1)
    //   This value indicates that the UE is allowed to use LIPA in the VPLMN where the UE is roaming.
    return 1;
  }

  @Override
  protected int getRelayNodeIndicator() {
    // The Relay-Node-Indicator AVP is of type Enumerated.
    // It shall indicate whether the subscription data belongs to a Relay Node or not (see 3GPP TS 36.300).
    // The following values are defined:
    //  NOT_RELAY_NODE (0)
    //   This value indicates that the subscription data does not belong to a Relay Node.
    //  RELAY_NODE (1)
    //   This value indicates that the subscription data belongs to a Relay Node.
    return 1;
  }

  @Override
  protected int getMDTUserConsent() {
    // The MDT-User-Consent AVP is of type Enumerated.
    // It shall indicate whether the user has given his consent for MDT activation or not
    // (see 3GPP TS 32.422). The following values are defined:
    // CONSENT_NOT_GIVEN (0)
    // CONSENT_GIVEN (1)
    return 1;
  }

  @Override
  protected int getSubscribedVSRVCC() {
    // The Subscribed-VSRVCC AVP is of type Enumerated.
    // It shall indicate that the user is subscribed to the vSRVCC.
    // The following value is defined:
    // VSRVCC_SUBSCRIBED (0)
    return 0;
  }

  @Override
  protected long getProSePermission() {
    // The ProSe-Permission AVP is of type Unsigned32, and it shall contain a bit mask that indicates the permissions for ProSe subscribed by the user.
    // The meaning of the bits shall be as defined in table 6.3.3/1:
    // Table 6.3.3-1: ProSe-Permission
    // Bit  Name                        Description
    //  0   ProSe Direct Discovery      This bit, when set, indicates that the user is allowed to use ProSe Direct Discovery.
    //  1   EPC-level ProSe Discovery   This bit, when set, indicates that the user is allowed to use EPC-level ProSe Discovery.
    //  2   EPC support WLAN Direct Discovery and Communication	This bit, when set, indicates that the user is allowed to use EPC support WLAN Direct Discovery and Communication.
    //  3   one-to-many ProSe Direct Communication  This bit, when set, indicates that the user is allowed to use one-to-many ProSe Direct Communication.
    //  4   one-to-one ProSe Direct Communication   This bit, when set, indicates that the user is allowed to use one-to-one ProSe Direct Communication.
    //  5   UE-to-Network Relay         This bit, when set, indicates that the user is allowed to act as a UE-to-Network relay.
    //  6   Remote-UE-access            This bit, when set, indicates that the user is allowed to act as a Remote-UE.
    //  7   Restricted ProSe Direct Discovery  This bit, when set, indicates that the user is allowed to use restricted ProSe Direct Discovery.
    return 7;
  }

  @Override
  protected long getAuthorizedDiscoveryRange() {
    // The Authorized-Discovery-Range AVP is of type Unsigned32, and it shall contain a value that
    // indicates the authorised announcing range (short/medium/long) at which
    // the UE is allowed to announce in the given PLMN according to the
    // defined announcing authorisation policy for this UE.
    // Refer to 3GPP TS 24.334 for the policy handling and to 3GPP TS 24.333 for the possible values of the range
    return 0;
  }

  @Override
  protected long getProSeDirectAllowed() {
    // The ProSe-Direct-Allowed AVP is of type Unsigned32, and it shall contain a bit mask that indicates
    // the services the UE is authorised to use for ProSe Direct functionalities in a specific PLMN.
    // The meaning of the bits shall be as defined in table 6.3.5-1:
    // Table 6.3.5-1: ProSe-Direct-Allowed
    // Bit  Name          Description
    //  0   Announce      This bit, when set, indicates that the user is allowed to announce in the corresponding PLMN for open ProSe Discovery.
    //  1   Monitor       This bit, when set, indicates that the user is allowed to monitor in the corresponding PLMN for open ProSe Discovery.
    //  2   Communication This bit, when set, indicates that the user is allowed for ProSe direct one to many communication in the corresponding PLMN.
    //  3   One-to-one Communication This bit, when set, indicates that the user is allowed to perform one-to-one ProSe Direct Communication.
    //  4   Discoverer    This bit, when set, indicates that the user is allowed to perform discoverer operation in the corresponding PLMN for ProSe Discovery Model B.
    //  5   Discoveree    This bit, when set, indicates that the user is allowed to perform discoveree operation in the corresponding PLMN for ProSe Discovery Model B.
    //  6   Restricted-announce   This bit, when set, indicates that the user is allowed to announce in the corresponding PLMN for restricted ProSe Discovery.
    //  7   Restricted-monitoring This bit, when set, indicates that the user is allowed to monitor in the corresponding PLMN for restricted ProSe Discovery.
    //  8   Application-controlled extension  This bit, when set, indicates that the user is allowed to announce or monitor with application-controlled extension in the corresponding PLMN for restricted ProSe Discovery
    //  9   On-demand announcing  This bit, when set, indicates that the user is allowed to perform on-demand announcing in the corresponding PLMN for restricted ProSe Discovery
    return 1;
  }

  @Override
  protected long getSubscriptionDataFlags() {
    // The Subscription-Data-Flags is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.3.165/1
    // Table 7.3.165/1: Subscription-Data-Flags
    // Bit  Name                                                Description
    //  0   PS-And-SMS-Only-Service-Provision-Indication        This bit, when set, indicates that the subscription is for PS Only and permits CS service access only for SMS.
    //  1   SMS-In-SGSN-Allowed-Indication                      This bit, when set, indicates that SMS in SGSN for the user is allowed.
    //  2   User Plane Integrity Protection                     This bit, when set, indicates that the SGSN may decide to activate integrity protection of the user plane when GERAN is used (see 3GPP TS 43.020). The MME shall ignore it.
    //  3   PDN-Connection-Restricted                           This bit, when set, indicates to the MME that it shall not establish any non-emergency PDN connection for this user if the MME and the UE supports Attach without PDN connection. The SGSN shall ignore it.
    //  4   Acknowledgement-Of-Downlink-NAS-Data PDUs disabled  This bit, when set, indicates to the MME that acknowledgement of downlink NAS data PDUs for Control Plane CIoT Optimization is disabled for this UE (even for APN configurations with RDS Indicator set to ENABLED (1)). When not set it indicated to the MME that acknowledgement of downlink NAS data PDUs for Control Plane CIoT Optimization is enabled (for APN configurations with RDS Indicator set to ENABLED (1)) for this UE, which is the default (see 3GPP TS 23.401). The SGSN shall ignore it.
    //  5   Time-Reference-Information-Distribution-Indication  This bit, when set, indicates that the UE is subscribed to receive time reference information in access stratum in EPS. (see 3GPP TS 23.401 [2]).
    return 15;
  }

  @Override
  protected int getDLBufferingSuggestedPacketCount() {
    // The DL-Buffering-Suggested-Packet-Count AVP is of type Integer32.
    // It shall indicate whether extended buffering of downlink packets at the SGW,
    // for High Latency Communication, is requested or not.
    // When requested, it may also suggest the number of downlink packets to buffer at the SGW
    // for this user.
    // The following values are defined:
    //  "Extended DL Data Buffering NOT REQUESTED"  0
    //  "Extended DL Data Buffering REQUESTED, without a suggested number of packets"  -1
    //  "Extended DL Data Buffering REQUESTED, with a suggested number of packets"  > 0
    return 0;
  }

  @Override
  protected long getGroupServiceId() {
    // The Group-Service-Id AVP is of type Unsigned32, and it shall identify the specific service
    // for which the IMSI-Group-Id is used. The following values are defined:
    // 1  Group specific NAS level congestion control
    // 2  Group specific Monitoring of Number of UEs present in a geographical area
    return 2;
  }

  @Override
  protected byte[] getGroupPLMNId() {
    // The Group-PLMN-Id AVP is of type OctetString.
    // This AVP shall contain the concatenation of MCC and MNC. See 3GPP TS 23.003.
    return new byte[] {0x47, (byte) 0xf8, 0x10};
  }

  @Override
  protected byte[] getLocalGroupId() {
    // The Local-Group-Id AVP is of type OctetString.
    // It shall contain an operator defined value, representing a group.
    return new byte[] {0x01};
  }

  @Override
  protected long getSCEFReferenceID() {
    // The SCEF-Reference-ID AVP is of type Unsigned32, and it shall contain the identifier provided by the SCEF.
    return 2147615214L;
  }

  @Override
  protected long getSCEFReferenceIDExt() {
    // The SCEF-Reference-ID-Ext AVP is of type Unsigned64, and it shall contain a 64-bit identifier
    // provided by the SCEF, which shall be used instead of the 32-bit identifier SCEF-Reference-ID,
    // when supported by both SCEF and HSS.
    return 4612266700153422318L;
  }

  @Override
  protected long getSCEFReferenceIDForDeletion() {
    // The SCEF-Reference-ID-for-Deletion AVP is of type Unsigned32,
    // and it shall contain the SCEF-Reference-ID
    // (in combination with the SCEF identified by the SCEF-ID) for the event to be deleted.
    return 2147615214L;
  }

  @Override
  protected long getSCEFReferenceIDForDeletionExt() {
    // The SCEF-Reference-ID-for-Deletion-Ext AVP is of type Unsigned64,
    // and it shall contain a 64-bit identifier provided by the SCEF,
    // which shall be used instead of the 32-bit identifier SCEF-Reference-ID-for-Deletion,
    // when supported by both SCEF and HSS.
    return 2345288000973521000L;
  }

  @Override
  protected long getPeriodicCommunicationIndicator() {
    // The Periodic-communication-indicator AVP is of type Unsigned32.
    // The following values are defined:
    // PERIODICALLY (0)
    // ON_DEMAND (1)
    return 1;
  }

  @Override
  protected long getCommunicationDurationTime() {
    // The Communication-duration-time AVP is of type Unsigned32,
    // and shall provide the time in seconds of the duration of the periodic communication.
    return 60000;
  }

  @Override
  protected long getPeriodicTime() {
    // Periodic-time AVP is of type Unsigned32,
    // and shall provide the time in seconds of the interval for periodic communication.
    return 600;
  }

  @Override
  protected long getDayOfWeekMask() {
    // The Day-Of-Week-Mask AVP (AVP Code 563) is of type Unsigned32.
    // The value is a bit mask that specifies the day of the week for the time window to match.
    // This document specifies the following bits:
    // Bit  | Name
    //------+------------
    //  0   | SUNDAY
    //  1   | MONDAY
    //  2   | TUESDAY
    //  3   | WEDNESDAY
    //  4   | THURSDAY
    //  5   | FRIDAY
    //  6   | SATURDAY
    //  The bit MUST be set for the time window to match on the corresponding day of the week.
    return 1;
  }

  @Override
  protected long getTimeOfDayStart() {
    // The Time-Of-Day-Start AVP (AVP Code 561) is of type Unsigned32.
    // The value MUST be in the range from 0 to 86400.  The value of this AVP
    // specifies the start of an inclusive time window expressed as the
    // offset in seconds from midnight.  If this AVP is absent from the
    // Time-Of-Day-Condition AVP, the time window starts at midnight.
    return 0;
  }

  @Override
  protected long getTimeOfDayEnd() {
    // The Time-Of-Day-End AVP (AVP Code 562) is of type Unsigned32.
    // The value MUST be in the range from 1 to 86400.  The value of this AVP
    // specifies the end of an inclusive time window expressed as the offset
    // in seconds from midnight.  If this AVP is absent from the Time-Of-
    // Day-Condition AVP, the time window ends one second before midnight.
    return 86400;
  }

  @Override
  protected long getStationaryIndication() {
    // The Stationary-Indication AVP may have the value of STATIONARY_UE (0) or MOBILE_UE (1).
    return 1;
  }

  @Override
  protected Time getReferenceIDValidityTime() {
    // The Reference-ID-Validity-Time AVP is of type Time (see IETF RFC 6733),
    // and contains the point of time when the CP sets associated to an SCEF-Reference-ID
    // (in combination with an SCEF-ID) becoming invalid and shall be deleted.
    return Time.valueOf("23:59:59");
  }

  @Override
  protected int getTrafficProfile() {
    // The Traffic-Profile AVP is of type Unsigned32.
    // The following values are defined:
    //  SINGLE_TRANSMISSION_UL (0)
    //  SINGLE_TRANSMISSION_DL (1)
    //  DUAL_TRANSMISSION_UL_WITH_SUBSEQUENT_DL (2)
    //  DUAL_TRANSMISSION_DL_WITH_SUBSEQUENT_UL (3)
    //  MULTI_TRANSMISSION (4)
    return 4;
  }

  @Override
  protected long getBatteryIndicator() {
    // The Battery-Indicator AVP is of type Unsigned32, and it shall contain a bitmask.
    // The meaning of the bits shall be as defined in table 8.4.81-1:
    // Table 8.4.81-1: Battery-Indicator
    // Bit  Name                            Description
    //  0   NO_BATTERY                      When this bit is set it indicates that UE is not battery powered.
    //  1   BATTERY_REPLACEABLE_INDICATION  When this bit is set it indicates that battery of the UE is replaceable, when this bit is not set it indicates that battery of UE is not replaceable.
    //  2   BATTERY_RECHARGEABLE_INDICATION When this bit is set it indicates that the battery of the UE is rechargeable, when this bit is not set it indicates that battery of the UE is not  rechargeable.
    return 4;
  }

  @Override
  protected String getMTCProviderID() {
    // The MTC-Provider-ID AVP is of type UTF8String, and it contains a character string
    // representing the identity of the MTC Service Provider and/or MTC Application.
    return "acme_iot";
  }

  @Override
  protected long getMonitoringType() {
    // The Monitoring-Type AVP is of type Unsigned32 and shall identify the type of event to be monitored.
    // The following values are defined:
    //  LOSS_OF_CONNECTIVITY (0)
    //  UE_REACHABILITY (1)
    //  LOCATION_REPORTING (2)
    //  CHANGE_OF_IMSI_IMEI(SV)_ASSOCIATION (3)
    //  ROAMING_STATUS (4)
    //  COMMUNICATION_FAILURE (5)
    //  AVAILABILITY_AFTER_DDN_FAILURE (6)
    //  NUMBER_OF_UES_PRESENT_IN_A_GEOGRAPHICAL_AREA (7)
    //  UE_REACHABILITY_AND_IDLE_STATUS_INDICATION (8)
    //  AVAILABILITY_AFTER_DDN_FAILURE_AND_IDLE_STATUS_INDICATION (9)
    //  PDN_CONNECTIVITY_STATUS (10)
    //  SAT_SF_OPERATION_INFO (11)
    return 2;
  }

  @Override
  protected long getMaximumNumberOfReports() {
    // The Maximum-Number-of-Reports AVP is of type Unsigned32.
    // It shall contain the number of reports to be generated and sent to the SCEF.
    // This AVP shall not be present when Monitoring-Type is AVAILABILITY_AFTER_DDN_FAILURE (6).
    // This AVP shall not be greater than one if:
    //  - Monitoring-Type is UE_REACHABILITY (1) and Reachability-Type is "Reachability for SMS" or
    //  - Monitoring-Type is LOCATION_REPORTING (2) and MONTE-Location-Type is LAST_KNOWN_LOCATION (1).
    return 5;
  }

  @Override
  protected Time getMonitoringDuration() {
    // The Monitoring-Duration AVP is of typeTime. It shall contain the absolute time
    // at which the related monitoring event request is considered to expire.
    return Time.valueOf("23:59:59");
  }

  @Override
  protected String getChargedParty() {
    // The Charged-Party AVP (AVP code 857) is of type UTF8String and holds the address
    // (Public User ID: SIP URI, Tel URI, etc.) of the party to be charged.
    // For Monitoring Event charging, it contains a string that identifies the entity
    // towards which accounting/charging functionality is performed by the involved 3GPP network elements.
    return "nando@restcomm.org";
  }

  @Override
  protected long getMaximumDetectionTime() {
    // The Maximum-Detection-Time AVP is of type Unsigned32.
    // It shall contain the maximum number of seconds without any communication with the UE
    // after which the SCEF is to be informed that the UE is considered to be unreachable.
    // It is used to set the subscribed periodic RAU/TAU timer by the HSS
    return 60;
  }

  @Override
  protected long getReachabilityType() {
    // The Reachability-Type AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 8.4.12-1:
    // Table 8.4.12-1: Reachability-Type
    // Bit  Name                    Description
    //  0   Reachability for SMS    This bit, when set, indicates that the monitoring for reachability for SMS of the UE is to be configured.
    //  1   Reachability for Data   This bit, when set, indicates that the monitoring for reachability for data of the UE is to be configured.
    return 1;
  }

  @Override
  protected long getMaximumLatency() {
    // The Maximum-Latency AVP is of type Unsigned32.
    // It shall contain the maximum acceptable delay time for downlink data transfer in seconds.
    // It is used to set the subscribed periodic RAU/TAU timer by the HSS.
    return 60;
  }

  @Override
  protected long getMaximumResponseTime() {
    // The Maximum-Response-Time AVP is of type Unsigned32.
    // It shall contain the maximum time in seconds for which the UE stays reachable.
    // It is used to set the active time by the HSS.
    return 10;
  }

  @Override
  protected long getMONTELocationType() {
    // The MONTE-Location-Type AVP is of type Unsigned32.
    // It indicates the type of location information to be provided. The following values are defined:
    //  CURRENT_LOCATION (0)
    //  LAST_KNOWN_LOCATION (1)
    return 0;
  }

  @Override
  protected long getAccuracy() {
    // The Accuracy AVP is of type Unsigned32. It shall indicate the requested accuracy.
    // The following values are defined:
    //  CGI-ECGI (0)
    //  eNB (1)
    //  LA-TA-RA (2)
    //  PRA(3)
    //  PLMN-ID (4)
    return 0;
  }

  @Override
  protected long getAssociationType() {
    // The Association-Type AVP is of type Unsigned32.
    // It shall indicate the details of the event configuration related
    // to the change of IMEI(SV)-IMSI association.
    // The following values are defined:
    //  IMEI-CHANGE (0)
    //   The event shall be reported if the association between IMSI and IMEI has changed; if only the Software Version (SV) has changed, no event shall be reported.
    //  IMEISV-CHANGE (1)
    //   The event shall be reported if the association between IMSI and IMEI, or SV, or both, has changed (this includes the case where only the SV has changed).
    return 1;
  }

  @Override
  protected int getPLMNIdRequested() {
    // The PLMN-ID-Requested AVP is of type Enumerated,
    // and it shall indicate whether the PLMN-ID value needs to be returned in the event report
    // associated to the monitoring type "ROAMING_STATUS".
    // The following values are defined:
    //  TRUE (0)
    //  FALSE (1)
    return 0;
  }

  @Override
  protected String getExternalIdentifier() {
    // The External-Identifier AVP is of type UTF8String.
    // For S6m/S6n interface it shall contain an external identifier of the UE.
    // See 3GPP TS 23.003 for the definition and formatting of the External Identifier.
    // For S6t interface, it shall contain an external identifier for an individual UE
    // or a group of UEs, as indicated by Type-Of-External-Identifier AVP.
    // See 3GPP TS 23.003 for the definition and formatting of the External Group Identifier.
    return "Pineapple";
  }

  @Override
  protected long getV2xPermission() {
    // The V2X-Permission AVP is of type Unsigned32, and it shall contain a bit mask that indicates
    // the permissions for V2X service subscribed by the user.
    // The meaning of the bits shall be as defined in table 7.3.x2-1:
    // Table 7.3.x2-1: V2X-Permission
    // Bit  Name                                             Description
    //  0  Allow V2X communication over PC5 as Vehicle UE    This bit, when set, indicates that the user is allowed to use V2X communication over PC5 as Vehicle UE in the serving PLMN.
    //  1  Allow V2X communication over PC5 as Pedestrian UE This bit, when set, indicates that the user is allowed to use V2X communication over PC5 as Pedestrian UE in the serving PLMN.
    return 1;
  }

  @Override
  protected long getUePc5AMBR() {
    // The UE-PC5-AMBR AVP is of type Unsigned32.
    // It indicates the maximum bits delivered by UE over the PC5 interface within a period of time.
    // The unit of UE-PC5-AMBR is bits/s
    return 4294967295L;
  }

  @Override
  protected int get5QI() {
    // The 5QI AVP is of type Integer32. It shall contain the 5QI.
    // See 3GPP TS 23.501 for allowed values. If the 5QI is used in PC5 QoS parameter,
    // it shall contain PQI, PQI is a special 5QI (see clause 5.4.2.1 of 3GPP TS 23.287).
    // 3GPP TS 23.287
    // A 5QI is a scalar that is used as a reference to 5G QoS characteristics,
    // i.e. access node-specific parameters that control QoS forwarding treatment for the QoS Flow
    // (e.g. scheduling weights, admission thresholds, queue management thresholds, link layer protocol configuration, etc.).
    // Standardized 5QI values have one-to-one mapping to a standardized combination of
    // 5G QoS characteristics as specified in Table 5.7.4-1.
    return 75;
  }

  @Override
  protected int getGuaranteedFlowBitrates() {
    // The Guaranteed-Flow-Bitrates AVP is of type Integer32.
    // It indicates the guaranteed bits delivered for the PC5 QoS flow by UE over the PC5 interface
    // within a period of time. The unit of Guaranteed-Flow-Bitrates is bits/s
    return 1024000;
  }

  @Override
  protected int getMaximumFlowBitrates() {
    // The Maximum-Flow-Bitrates AVP is of type Integer32.
    // It indicates the maximum bits delivered for the PC5 QoS flow by UE over the PC5 interface within a period of time.
    // The unit of Maximum-Flow-Bitrates is bits/s.
    return 2147483647;
  }

  @Override
  protected int getPC5Range() {
    // The PC5-Range AVP is of type Integer32.
    // It indicates the Range in the unit of meters.
    // See clause 5.4.2.4 of 3GPP TS 23.287
    return 1000;
  }

  @Override
  protected int getPC5LinkAMBR() {
    // The PC5-Link-AMBR AVP is of type Integer32.
    // It indicates the PC5 Link Aggregated Bit Rates for all the Non-GBR QoS Flows.
    // The unit of PC5-Link-AMBR is bits/s
    return 2147483647;
  }

  @Override
  protected int getRatType() {
    return 1005;
  }

  @Override
  protected byte[] getEDRXCycleLengthValue() {
    // The eDRX-Cycle-Length-Value AVP is of type OctetString.
    // This AVP shall contain the extended DRX cycle value subscribed for this user for a given RAT type.
    // The contents of eDRX-Cycle-Length-Value shall consist of 1 octet.
    // The encoding shall be as defined in 3GPP TS 24.008, clause 10.5.5.32,
    // and it shall only contain the value of the field "eDRX value" for a given RAT type,
    // i.e., the 4 least significant bits of the octet in this AVP shall contain
    // bits 1-4 of octet 3 in the "Extended DRX parameter" IE (see Figure 10.5.5.32 of 3GPP TS 24.008),
    // and the 4 most significant bits of the octet in this AVP shall be set to 0.
    return new byte[] {2}; // 0 0 1 0   20,48 seconds
  }

  @Override
  protected long getActiveTime() {
    // Active-Time AVP is of type Unsigned32 and shall provide the active time granted to the UE in seconds.
    return 62;
  }

  @Override
  protected long getServiceGapTime() {
    // The Service-Gap-Time AVP is of type Unsigned32 and indicates the minimum number of seconds
    // during which the UE shall stay in ECM-IDLE mode, after leaving the ECM-CONNECTED mode,
    // before being allowed to send a subsequent connection request to enter ECM-CONNECTED mode again.
    // See description of the Service Gap Control feature in 3GPP TS 23.401.
    return 5;
  }

  @Override
  protected long getBroadcastLocationAssistanceDataTypes() {
    // The Broadcast-Location-Assistance-Data-Types AVP is of type Unsigned64.
    // The content of this AVP is a bit mask which indicates the broadcast location assistance data types
    // for which the UE is subscribed to receive ciphering keys used to decipher broadcast assistance data.
    // The meaning of the bits is defined in table 7.3.225-1 (3GPP TS 29.272)
    return 3;
  }

  @Override
  protected long getAerialUESubscriptionInformation() {
    // The Aerial-UE-Subscription-Information AVP is of type Unsigned32 and indicates the subscription
    // of Aerial UE function.
    // The following values are defined:
    // AERIAL_UE_ALLOWED (0)
    // AERIAL_UE_NOT_ALLOWED (1)
    return 0;
  }

  @Override
  protected long getCoreNetworkRestrictions() {
    // The Core-Network-Restrictions AVP is of type Unsigned32 and shall contain a bitmask indicating
    // the types of Core Network that are disallowed for a given user.
    // The meaning of the bits shall be as defined in table 7.3.230-1:
    // Table 7.3.230-1: Core-Network-Restrictions
    // Bit Name             Description
    //  0  Reserved         The use of this bit is deprecated. This bit shall be discarded by the receiving MME.
    //  1  5GC not allowed  Access to 5GC not allowed.
    return 0;
  }

  @Override
  protected long getOperationMode() {
    // The Operation-Mode AVP is of type Unsigned32.
    // This value shall indicate the operation mode for which the Paging-Time-Window-Length applies.
    // See clause 3GPP TS 24.008 [31], clause 10.5.5.32.
    // The allowed values of Operation-Mode shall be in the range of 0 to 255.
    // Values are defined as follows:
    //  0: Spare, for future use
    //  1: Iu mode
    //  2: WB-S1 mode
    //  3: NB-S1 mode
    //  4 to 255: Spare, for future use
    return 3;
  }

  @Override
  protected byte[] getPagingTimeWindowLength() {
    // The Paging-Time-Window-Length AVP is of type OctetString.
    // This AVP shall contain the Paging time window length subscribed for this user for a given operation mode.
    // The contents of Paging-Time-Window-Length shall consist of 1 octet.
    // The encoding shall be as defined in 3GPP TS 24.008, clause 10.5.5.32,
    // and it shall only contain the value of the field "Paging Time Window length" for a given RAT type,
    // i.e., the 4 most significant bits of the octet in this AVP shall contain
    // bits 5-8 of octet 3 in the "Extended DRX parameter" IE
    // (see Figure 10.5.5.32 of 3GPP TS 24.008),
    // and the 4 least significant bits of the octet in this AVP shall be set to 0.
    return new byte[] {0x30}; // 0 0 1 1   10,24 seconds
  }

  @Override
  protected long getSubscribedARPI() {
    // The Subscribed-ARPI AVP is of type Unsigned32 and shall contain the subscribed value of the
    // Additional RRM Policy Index. For details, see 3GPP TS 23.401.
    return 0;
  }

  @Override
  protected int getIABOperationPermission() {
    // The IAB-Operation-Permission AVP is of type Enumerated.
    // It shall indicate to the MME or SGSN whether the UE is allowed for IAB operation.
    // See 3GPP TS 23.401.
    // The following values are defined:
    //  IAB_OPERATION_ALLOWED (0)
    //  IAB_OPERATION_NOTALLOWED (1)
    return 0;
  }

  @Override
  protected long getPLMNRATUsageControl() {
    // The PLMN-RAT-Usage-Control AVP is of type Unsigned32, and it shall contain a bit mask
    // where each bit when set to 1 indicates the potential restriction subjected to the serving PLMN
    // operator policy. The meaning of the bits is the following:
    // Table 7.3.253/1: PLMN-RAT-Usage-Control
    // Bit  Description
    //  0   Restriction to GERAN access subjected to the serving PLMN operator policy
    //  1   Restriction to UTRAN access subjected to the serving PLMN operator policy
    //  2   Restriction to E-UTRAN access subjected to the serving PLMN operator policy
    //  3   Restriction to NR access subjected to the serving PLMN operator policy
    //  4   Restriction to E-UTRAN over satellite access subjected to the serving PLMN operator policy
    //  5   Restriction to NR over satellite access subjected to the serving PLMN operator policy
    return 16;
  }

  @Override
  protected byte[] getResetID() {
    // The Reset-ID is of type OctetString. The value shall uniquely (within the HSS' realm)
    // identify a resource in the HSS that may fail or has restarted.
    // In the Reset procedure, when used to add/modify/delete subscription data shared by multiple subscribers,
    // the Reset-ID is used to identify the set of affected subscribers.
    return new byte[] {0x01, 0x00, 0x00, 0x45};
  }

  @Override
  protected String getUserName() {
    // [ User-Name ]
    return "748039876543210";
  }

  @Override
  protected int getCancellationType() {
    // The Cancellation-Type AVP is of type Enumerated and indicates the type of cancellation.
    // The following values are defined:
    //  MME_UPDATE_PROCEDURE (0)
    //  SGSN_UPDATE_PROCEDURE (1)
    //  SUBSCRIPTION_WITHDRAWAL (2)
    //  UPDATE_PROCEDURE_IWF (3)
    //  INITIAL_ATTACH_PROCEDURE (4)
    return 2;
  }

  @Override
  protected long getCLRFlags() {
    // The CLR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.3.152/1:
    // Table 7.3.152/1: CLR-Flags
    // Bit Name                   Description
    //  0  S6a/S6d-Indicator      This bit, when set, indicates that the CLR message is sent on the S6a interface, i.e. the message is to the MME or the MME part on the combined MME/SGSN.
    //                            This bit, when cleared, indicates that the CLR message is sent on the S6d interface, i.e. the message is to the SGSN or the SGSN part on the combined MME/SGSN.
    //                            The S6a/S6d-Indicator flag shall be used during initial attach procedure for a combined MME/SGSN. The S6a/S6d-Indicator flag may also be sent to a standalone node.
    //  1  Reattach-Required      This bit, when set, indicates that the MME or SGSN shall request the UE to initiate an immediate re-attach procedure as described in 3GPP TS 23.401 and in 3GPP TS 23.060.
    return 2;
  }

  @Override
  protected long getIDRFlags() {
    //
    return 0;
  }

  @Override
  protected long getDSRFlags() {
    // The DSR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits is defined in table 7.3.25/1:
    // Table 7.3.25/1: DSR-Flags
    // Bit Name  Description
    //  0  Regional Subscription Withdrawal This bit, when set, indicates that Regional Subscription shall be deleted from the subscriber data.
    //  1  Complete APN Configuration Profile Withdrawal   This bit, when set, indicates that all EPS APN configuration data for the subscriber shall be deleted from the subscriber data. This flag only applies to the S6d interface.
    //  2  Subscribed Charging Characteristics Withdrawal  This bit, when set, indicates that the Subscribed Charging Characteristics have been deleted from the subscription data.
    //  3  PDN subscription contexts Withdrawal   This bit, when set, indicates that the PDN subscription contexts whose identifier is included in the Context-Identifier AVP shall be deleted.
    //  4  STN-SR   This bit, when set, indicates that the Session Transfer Number for SRVCC shall be deleted from the subscriber data.
    //  5  Complete PDP context list Withdrawal   This bit, when set, indicates that all PDP contexts for the subscriber shall be deleted from the subscriber data.
    //  6  PDP contexts Withdrawal This bit, when set, indicates that the PDP contexts whose identifier is included in the Context-Identifier AVP shall be deleted.
    //  7  Roaming Restricted due to unsupported feature   This bit, when set, indicates that the roaming restriction shall be deleted from the subscriber data in the MME or SGSN.
    //  8  Trace Data Withdrawal   This bit, when set, indicates that the Trace Data shall be deleted from the subscriber data.
    //  9  CSG Deleted This bit, when set, indicates  that
    //     -  the "CSG-Subscription-Data from HSS" shall be deleted in the MME or SGSN when received over the S6a or S6d interface
    //     -  the "CSG-Subscription-Data from CSS" shall be deleted in the MME or SGSN when received over the S7a or S7d interface.
    // 10  APN-OI-Replacement   This bit, when set, indicates that the UE level APN-OI-Replacement shall be deleted from the subscriber data.
    // 11  GMLC List Withdrawal This bit, when set, indicates that the subscriber's LCS GMLC List shall be deleted from the MME or SGSN.
    // 12  LCS Withdrawal This bit, when set, indicates that the LCS service whose code is included in the SS-Code AVP shall be deleted from the MME or SGSN.
    // 13  SMS Withdrawal This bit, when set, indicates that the SMS service whose code is included in the SS-Code AVP or TS-Code AVP shall be deleted from the MME or SGSN.
    // 14  Subscribed periodic RAU-TAU Timer Withdrawal This bit, when set, indicates that the subscribed periodic RAU TAU Timer value shall be deleted from the subscriber data.
    // 15  Subscribed VSRVCC Withdrawal  This bit, when set, indicates that the Subscribed VSRVCC shall be deleted from the subscriber data.
    // 16  A-MSISDN Withdrawal  This bit, when set, indicates that the additional MSISDN, if present, shall be deleted from the subscriber data.
    // 17  ProSe Withdrawal  This bit, when set, indicates that the ProSe subscription data shall be deleted from the MME or combined MME/SGSN.
    // 18  Reset-IDs   This bit, when set, indicates that the set of Reset-IDs shall be deleted from the MME or SGSN.
    // 19  DL-Buffering-Suggested-Packet-Count Withdrawal  This bit, when set, indicates that the DL-Buffering-Suggested-Packet-Count shall be deleted in the MME or SGSN.
    // 20  Subscribed IMSI-Group-Id Withdrawal  This bit, when set, indicates that all subscribed IMSI-Group-Id(s) shall be deleted in the MME or SGSN.
    // 21  Delete monitoring events   This bit when set indicates to the MME or SGSN to delete all the Monitoring events for the subscriber which are associated with the provided SCEF-ID.
    // 22  User Plane Integrity Protection Withdrawal   This bit, when set, indicates to the SGSN that User Plane Integrity Protection may no longer be required when GERAN is used. The MME shall ignore it.
    // 23  MSISDN Withdrawal This bit, when set, indicates that the MSISDN shall be deleted from the subscriber data. It is also used by the MME/SGSN to delete those monitoring events created using the MSISDN.
    // 24  UE Usage Type Withdrawal   This bit, when set, indicates to the MME or SGSN that the UE Usage Type shall be deleted from the subscription data.
    // 25  V2X Withdrawal This bit, when set, indicates that the V2X subscription data shall be deleted from the MME or combined MME/SGSN.
    // 26  External-Identifier-Withdrawal   This bit, when set, indicates that the External-Identifier shall be deleted from the subscriber data. It is also used by the MME/SGSN to delete those monitoring events created using the removed External Identifier or all monitoring events created for any External Identifier in case of removing the default External Identifier.
    // 27  Aerial-UE-Subscription Withdrawal   This bit, when set, indicates that the Aerial UE subscription shall be deleted from the subscriber data.
    // 28  Paging Time Window Subscription Withdrawal   This bit, when set, indicates that the Paging Time Window subscription shall be deleted from the subscriber data.
    // 29  Active-Time-Withdrawal  This bit, when set, indicates that the Active Time used for PSM shall be deleted from the subscriber data.
    // 30  eDRX-Cycle-Length -Withdrawal This bit, when set, indicates that the eDRX-Cycle-Length shall be deleted from the subscriber data. If the eDRX-Related-RAT is present in the DSR command, only the eDRX Cycle Length for indicated RAT types shall be deleted. Otherwise, the entire eDRX Cycle Length subscription for all RAT types shall be deleted.
    // 31  Service-Gap-Time-Withdrawal   This bit, when set, indicates that the Service Gap Time shall be deleted from the subscriber data.
    return 4294967296L;
  }

  @Override
  protected long getPUAFlags() {
    // The PUA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meanings of the bits are defined in table 7.3.48/1:
    // Table 7.3.48/1: DSR-Flags
    // Bit Name             Description
    //  0  Freeze M-TMSI    This bit, when set, shall indicate to the MME that the M-TMSI needs to be frozen, i.e. shall not be immediately re-used.
    //  1  Freeze P-TMSI    This bit, when set, shall indicate to the SGSN that the P-TMSI needs to be frozen, i.e. shall not be immediately re-used.
    return 1;
  }

  @Override
  protected String getUserId() {
    // The User-Id AVP shall be of type UTF8String. It shall contain the leading digits of an IMSI
    // (i.e. MCC, MNC, leading digits of MSIN, see 3GPP TS 23.003, clause 2.2) formatted as a character string.
    // Within an HSS, a User-Id identifies a set of subscribers, each with identical leading IMSI digits.
    return "74803";
  }

  private static byte[] parseTBCD(String tbcd) {
    int length = (tbcd == null ? 0:tbcd.length());
    int size = (length + 1)/2;
    byte[] buffer = new byte[size];

    for (int i=0, i1=0, i2=1; i<size; ++i, i1+=2, i2+=2) {

      char c = tbcd.charAt(i1);
      int n2 = getTBCDNibble(c, i1);
      int octet;
      int n1 = 15;
      if (i2 < length) {
        c = tbcd.charAt(i2);
        n1 = getTBCDNibble(c, i2);
      }
      octet = (n1 << 4) + n2;
      buffer[i] = (byte)(octet & 0xFF);
    }

    return buffer;
  }

  private static int getTBCDNibble(char c, int i1) {

    int n = Character.digit(c, 10);

    if (n < 0 || n > 9) {
      switch (c) {
        case '*':
          n = 10;
          break;
        case '#':
          n = 11;
          break;
        case 'a':
          n = 12;
          break;
        case 'b':
          n = 13;
          break;
        case 'c':
          n = 14;
          break;
        default:
          throw new NumberFormatException("Bad character '" + c
              + "' at position " + i1);
      }
    }
    return n;
  }
}
