package org.mobicents.diameter.stack.functional.s6a.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.s6a.ClientS6aSession;
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
import org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientS6a extends AbstractS6aClient {

  protected boolean receivedAIA;
  protected boolean receivedULA;
  protected boolean receivedCLR;
  protected boolean receivedIDR;
  protected boolean receivedDSR;
  protected boolean receivedPUA;
  protected boolean receivedRSR;
  protected boolean receivedNOA;
  protected boolean sentAIR;
  protected boolean sentULR;
  protected boolean sentCLA;
  protected boolean sentIDA;
  protected boolean sentDSA;
  protected boolean sentPUR;
  protected boolean sentRSA;
  protected boolean sentNOR;

  protected JCancelLocationRequest cancelLocationRequest;
  protected JInsertSubscriberDataRequest insertSubscriberDataRequest;
  protected JDeleteSubscriberDataRequest deleteSubscriberDataRequest;
  protected JResetRequest resetRequest;

  public ClientS6a() {
  }

  public boolean isReceivedAIA() {
    return receivedAIA;
  }

  public boolean isReceivedULA() {
    return receivedULA;
  }

  public boolean isReceivedCLR() {
    return receivedCLR;
  }

  public boolean isReceivedIDR() {
    return receivedIDR;
  }

  public boolean isReceivedDSR() {
    return receivedDSR;
  }

  public boolean isReceivedPUA() {
    return receivedPUA;
  }

  public boolean isReceivedRSR() {
    return receivedRSR;
  }

  public boolean isReceivedNOA() {
    return receivedNOA;
  }

  public boolean isSentAIR() {
    return sentAIR;
  }

  public boolean isSentULR() {
    return sentULR;
  }

  public boolean isSentCLA() {
    return sentCLA;
  }

  public boolean isSentIDA() {
    return sentIDA;
  }

  public boolean isSentDSA() {
    return sentDSA;
  }

  public boolean isSentPUR() {
    return sentPUR;
  }

  public boolean isSentRSA() {
    return sentRSA;
  }

  public boolean isSentNOR() {
    return sentNOR;
  }

  public void sendAuthenticationInformationRequest() throws Exception {
    JAuthenticationInformationRequest air = super.createAIR(super.clientS6aSession);
    super.clientS6aSession.sendAuthenticationInformationRequest(air);
    this.sentAIR = true;
    Utils.printMessage(log, super.stack.getDictionary(), air.getMessage(), isSentAIR());
  }

  public void sendUpdateLocationRequest() throws Exception {
    JUpdateLocationRequest ulr = super.createULR(super.clientS6aSession);
    super.clientS6aSession.sendUpdateLocationRequest(ulr);
    this.sentULR = true;
    Utils.printMessage(log, super.stack.getDictionary(), ulr.getMessage(), isSentULR());
  }

  public void sendCancelLocationAnswer() throws Exception {
    if (!receivedCLR || cancelLocationRequest == null) {
      fail("Did not receive CLR or answer already sent.", null);
      throw new Exception("Did not receive CLR or answer already sent. Request: " + this.cancelLocationRequest);
    }

    JCancelLocationAnswer cla = super.createCLA(cancelLocationRequest, 2001);

    this.clientS6aSession.sendCancelLocationAnswer(cla);

    this.sentCLA = true;
    cancelLocationRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), cla.getMessage(), isSentCLA());
  }

  public void sendInsertSubscriberDataAnswer() throws Exception {
    if (!receivedIDR || insertSubscriberDataRequest == null) {
      fail("Did not receive IDR or answer already sent.", null);
      throw new Exception("Did not receive IDR or answer already sent. Request: " + this.insertSubscriberDataRequest);
    }

    JInsertSubscriberDataAnswer ida = super.createIDA(insertSubscriberDataRequest, 2001);

    this.clientS6aSession.sendInsertSubscriberDataAnswer(ida);

    this.sentIDA = true;
    insertSubscriberDataRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), ida.getMessage(), isSentIDA());
  }

  public void sendDeleteSubscriberDataAnswer() throws Exception {
    if (!receivedDSR || deleteSubscriberDataRequest == null) {
      fail("Did not receive DSR or answer already sent.", null);
      throw new Exception("Did not receive DSR or answer already sent. Request: " + this.deleteSubscriberDataRequest);
    }

    JDeleteSubscriberDataAnswer dsa = super.createDSA(deleteSubscriberDataRequest, 2001);

    this.clientS6aSession.sendDeleteSubscriberDataAnswer(dsa);

    this.sentDSA = true;
    deleteSubscriberDataRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), dsa.getMessage(), isSentDSA());
  }

  public void sendPurgeUERequest() throws Exception {
    JPurgeUERequest pur = super.createPUR(super.clientS6aSession);
    super.clientS6aSession.sendPurgeUERequest(pur);
    this.sentPUR = true;
    Utils.printMessage(log, super.stack.getDictionary(), pur.getMessage(), isSentPUR());
  }

  public void sendResetAnswer() throws Exception {
    if (!receivedRSR || resetRequest == null) {
      fail("Did not receive RSR or answer already sent.", null);
      throw new Exception("Did not receive RSR or answer already sent. Request: " + this.resetRequest);
    }

    JResetAnswer rsa = super.createRSA(resetRequest, 2001);

    this.clientS6aSession.sendResetAnswer(rsa);

    this.sentRSA = true;
    resetRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), rsa.getMessage(), isSentRSA());
  }

  public void sendNotifyRequest() throws Exception {
    JNotifyRequest nor = super.createNOR(super.clientS6aSession);
    super.clientS6aSession.sendNotifyRequest(nor);
    this.sentNOR = true;
    Utils.printMessage(log, super.stack.getDictionary(), nor.getMessage(), isSentNOR());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doAuthenticationInformationAnswerEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JAuthenticationInformationRequest, org.jdiameter.api.s6a.events.JAuthenticationInformationAnswer)
   */
  @Override
  public void doAuthenticationInformationAnswerEvent(ClientS6aSession session, JAuthenticationInformationRequest air, JAuthenticationInformationAnswer aia)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), aia.getMessage(), isReceivedAIA());
    if (this.receivedAIA) {
      fail("Received AIA more than once", null);
      return;
    }
    this.receivedAIA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doUpdateLocationAnswerEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JUpdateLocationRequest, org.jdiameter.api.s6a.events.JUpdateLocationAnswer)
   */
  @Override
  public void doUpdateLocationAnswerEvent(ClientS6aSession session, JUpdateLocationRequest ulr, JUpdateLocationAnswer ula)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), ula.getMessage(), isReceivedULA());
    if (this.receivedULA) {
      fail("Received ULA more than once", null);
      return;
    }
    this.receivedULA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doCancelLocationRequestEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JCancelLocationRequest)
   */
  @Override
  public void doCancelLocationRequestEvent(ClientS6aSession session, JCancelLocationRequest clr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedCLR) {
      fail("Received CLR more than once", null);
      return;
    }
    this.receivedCLR = true;
    this.cancelLocationRequest = clr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doInsertSubscriberDataRequestEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest)
   */
  @Override
  public void doInsertSubscriberDataRequestEvent(ClientS6aSession session, JInsertSubscriberDataRequest idr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedIDR) {
      fail("Received IDR more than once", null);
      return;
    }
    this.receivedIDR = true;
    this.insertSubscriberDataRequest = idr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doDeleteSubscriberDataRequestEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest)
   */
  @Override
  public void doDeleteSubscriberDataRequestEvent(ClientS6aSession session, JDeleteSubscriberDataRequest dsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedDSR) {
      fail("Received DSR more than once", null);
      return;
    }
    this.receivedDSR = true;
    this.deleteSubscriberDataRequest = dsr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doPurgeUEAnswerEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JPurgeUERequest, org.jdiameter.api.s6a.events.JPurgeUEAnswer)
   */
  @Override
  public void doPurgeUEAnswerEvent(ClientS6aSession session, JPurgeUERequest pur, JPurgeUEAnswer pua)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), pua.getMessage(), isReceivedPUA());
    if (this.receivedPUA) {
      fail("Received PUA more than once", null);
      return;
    }
    this.receivedPUA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doResetRequestEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JResetRequest)
   */
  @Override
  public void doResetRequestEvent(ClientS6aSession session, JResetRequest rsr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedRSR) {
      fail("Received RSR more than once", null);
      return;
    }
    this.receivedRSR = true;
    this.resetRequest = rsr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6a.AbstractS6aClient#doNotifyAnswerEvent(
   *    org.jdiameter.api.s6a.ClientS6aSession, org.jdiameter.api.s6a.events.JNotifyRequest, org.jdiameter.api.s6a.events.JNotifyAnswer)
   */
  @Override
  public void doNotifyAnswerEvent(ClientS6aSession session, JNotifyRequest nor, JNotifyAnswer noa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), noa.getMessage(), isReceivedNOA());
    if (this.receivedNOA) {
      fail("Received NOA more than once", null);
      return;
    }
    this.receivedNOA = true;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != JCancelLocationRequest.code && code != JInsertSubscriberDataRequest.code && code
        != JDeleteSubscriberDataRequest.code && code != JResetRequest.code ) {
      fail("Received Request with code not used by S6a!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientS6aSession.getSessionId().equals(request.getSessionId())) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      super.clientS6aSession.release();
      try {
        super.clientS6aSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientS6aSession.class, (Object) null);
        ((NetworkReqListener) this.clientS6aSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  // Attributes for Authentication-Information-Request (AIR), Update-Location-Request (ULR),
  // Cancel-Location-Answer (CLA), Insert-Subscriber-Data-Answer (IDA), Delete-Subscriber-Data-Answer (DSA),
  // Purge-UE-Request (PUR), Reset-Answer (RSA), Notify-Request (NOR)

  @Override
  protected String getUserName() {
    // [ User-Name ]
    return "748039876543210";
  }

  // [ OC-Supported-Features ]
  // This AVP is used to support Diameter overload control mechanism defined in IETF RFC 7683
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

  // [ Requested-EUTRAN-Authentication-Info ]
  @Override
  protected long getEUTRANNumberOfRequestedVectors() {
    // [ Number-Of-Requested-Vectors ]
    // The Number-Of-Requested-Vectors AVP is of type Unsigned32.
    // This AVP shall contain the number of AVs the MME or SGSN is prepared to receive
    return 1;
  }

  @Override
  protected long getEUTRANImmediateResponsePreferred() {
    // The Immediate-Response-Preferred AVP is of type Unsigned32.
    // This optional AVP indicates by its presence that immediate response is preferred,
    // and by its absence that immediate response is not preferred.
    // If present, the value of this AVP is not significant.
    // When EUTRAN-AVs and UTRAN-AVs or GERAN-AVs are requested,
    // presence of this AVP within the Requested-EUTRAN-Authentication-Info AVP
    // shall indicate that EUTRAN-AVs are requested for immediate use in the MME/SGSN;
    // presence of this AVP within the Requested-UTRAN-GERAN-Authentication-Info AVP
    // shall indicate that UTRAN-AVs or GERAN-AVs are requested for immediate use in the MME/SGSN.
    // It may be used by the HSS to determine the number of vectors to be obtained from the AuC
    // and the number of vectors downloaded to the MME or SGSN.
    return 1;
  }

  @Override
  protected byte[] getEUTRANReSynchronizationInfo() {
    // The Re-Synchronization-Info AVP is of type OctetString.
    // It shall contain the concatenation of RAND and AUTS.
    return new byte[] { 0x08, 0x66, (byte) 0xef, (byte) 0x95, (byte) 0x89, 0x0f, 0x17, 0x5c,
        (byte) 0x92, (byte) 0xbc, (byte) 0xce, 0x40, 0x2a, 0x50, 0x2d, (byte) 0xf5,
        0x43, 0x77, (byte) 0x9b, 0x3f, 0x0e, (byte) 0xaa, (byte) 0x80, 0x00,
        (byte) 0xae, 0x57, (byte) 0xc7, 0x1e, (byte) 0xe7, (byte) 0xda, 0x58, (byte) 0x85};
  }

  // [ Requested-UTRAN-GERAN-Authentication-Info ]
  @Override
  protected long getNumberOfRequestedVectors() {
    return 1;
  }

  @Override
  protected long getImmediateResponsePreferred() {
    return 0;
  }

  @Override
  protected byte[] getReSynchronizationInfo() {
    return new byte[] {0x30, 0x22, 0x04, 0x10, (byte) 0xf6, (byte) 0xe2, (byte) 0xc3, (byte) 0xdc,
        (byte) 0xa4, (byte) 0xca, (byte) 0xae, (byte) 0x9e, 0x4c, (byte) 0xba, 0x0f, (byte) 0xd3,
        0x42, 0x72, (byte) 0xee, 0x46, 0x04, 0x0e, (byte) 0xe9, 0x15,
        (byte) 0x97, (byte) 0x88, (byte) 0xbc, (byte) 0xeb, (byte) 0x80, 0x00, (byte) 0x81, 0x3f,
        (byte) 0xc0, 0x40, (byte) 0xff, 0x53};
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
  protected long getAirFlags() {
    // The AIR-Flags AVP is of type Unsigned32, and it shall contain a bitmask.
    // The meaning of the bits is defined in table 7.3.201/1:
    // Table 7.3.201/1: AIR-Flags
    // bit  name                Description
    // 0    Send UE Usage Type  This bit, when set, indicates that the MME or SGSN requests the HSS
    //                          to send the subscription parameter "UE Usage Type".
    return 1;
  }

  @Override
  protected String getIMEI() {
    return "86532606342775";
  }

  @Override
  protected String get3gpp2MEID() {
    // This AVP is of type OctetString.
    // This AVP contains the Mobile Equipment Identifier of the user's terminal.
    return "606342775";
  }

  @Override
  protected String getSoftwareVersion() {
    // Wireshark example taken from a real network capture:
    // AVP: Software-Version(1403) l=14 f=VM- vnd=TGPP val=08
    //    AVP Code: 1403 Software-Version
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 14
    //    AVP Vendor Id: 3GPP (10415)
    //    Software-Version: 08
    //    Padding: 0000
    return "08";
  }

  @Override
  protected int getRatType() {
    return 1005;
  }

  @Override
  protected long getUlrFlags() {
    // Wireshark example taken from a real network capture:
    // AVP: ULR-Flags(1405) l=16 f=VM- vnd=TGPP val=3
    //    AVP Code: 1405 ULR-Flags
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    ULR Flags: 0x00000003
    //        0000 0000 0000 0000 0000 000. .... .... = Spare: 0x000000
    //        .... .... .... .... .... ...0 .... .... = Dual-Registration-5G-Indicator: Not set
    //        .... .... .... .... .... .... 0... .... = SMS-Only-Indication: Not set
    //        .... .... .... .... .... .... .0.. .... = PS-LCS-Not-Supported-By-UE: Not set
    //        .... .... .... .... .... .... ..0. .... = Initial-Attach-Indicator: Not set
    //        .... .... .... .... .... .... ...0 .... = Node-Type-Indicator: Not set
    //        .... .... .... .... .... .... .... 0... = GPRS-Subscription-Data-Indicator: Not set
    //        .... .... .... .... .... .... .... .0.. = Skip-Subscriber-Data: Not set
    //        .... .... .... .... .... .... .... ..1. = S6a/S6d-Indicator: Set
    //        .... .... .... .... .... .... .... ...1 = Single-Registration-Indication: Set
    return 3;
  }

  @Override
  protected int getUeSRVCCCapability() {
    // Wireshark example taken from a real network capture:
    // AVP: UE-SRVCC-Capability(1615) l=16 f=V-- vnd=TGPP val=UE-SRVCC-NOT-SUPPORTED (0)
    //    AVP Code: 1615 UE-SRVCC-Capability
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    UE-SRVCC-Capability: UE-SRVCC-SUPPORTED (1)
    return 1;
  }

  @Override
  protected byte[] getSgsnNumber() {
    // The SGSN-Number AVP is of type OctetString, and it shall contain the ISDN number of the SGSN.
    // For further details on the definition of this AVP, see 3GPP TS 23.003.
    // This AVP contains an SGSN-Number in international number format as described in ITU-T Rec E.164 [41]
    // and shall be encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000208");
  }

  @Override
  protected int getHomogeneousSupportOfIMSVoiceOverPsSessions() {
    // The Homogeneous-Support-of-IMS-Voice-Over-PS-Sessions AVP is of type Enumerated.
    // The following values are defined:
    // NOT_SUPPORTED (0)
    //  This value indicates that "IMS Voice over PS Sessions" is not supported, homogeneously,
    //  in any of the TAs or RAs associated to the serving node for the served subscribers
    //  including consideration on roaming relationship for IMS Voice over PS.
    // SUPPORTED (1)
    //  This value indicates that "IMS Voice over PS Sessions" is supported, homogeneously,
    //  in all the TAs or RAs associated to the serving node for the served subscriber
    //  including consideration on roaming relationship for IMS Voice over PS.
    return 1;
  }

  @Override
  protected InetAddress getGMLCAddress() {
    // The GMLC-Address AVP is of type Address and shall contain the IPv4 or IPv6 address of H-GMLC
    // or the V-GMLC associated with the serving node
    InetAddress gmlcAddress = null;
    try {
      gmlcAddress = InetAddress.getByName("10.0.0.14");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return gmlcAddress;
  }

  // *[ Active-APN ]
  @Override
  protected long getContextIdentifier() {
    return 1;
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
    return "stg.eu.ng.1nce.net";
  }

  // MIP6-Agent-Info ::= < AVP Header: 486 >
  //  *2[ MIP-Home-Agent-Address ]
  //   [ MIP-Home-Agent-Host ]
  //   [ MIP6-Home-Link-Prefix ]
  //  *[ AVP ]
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
  protected byte[] getMMENumberForMtSMS() {
    // The MME-Number-for-MT-SMS AVP is of type OctetString, and it shall contain the ISDN number
    // corresponding to the MME for MT SMS. For further details on the definition of this AVP,
    // see 3GPP TS 23.003. This AVP contains an international number with the format as described
    // in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002  for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000407");
  }

  @Override
  protected int getSMSRegisterRequest() {
    // The SMS-Register-Request AVP is of type Enumerated, and it shall indicate
    // whether the MME or the SGSN requires to be registered for SMS (e.g. SGs interface not supported)
    // or if the MME or the SGSN prefers not to be registered for SMS
    // or if the MME or the SGSN has no preference.
    // The following values are defined:
    //  SMS_REGISTRATION_REQUIRED (0)
    //  SMS_REGISTRATION_NOT_PREFERRED (1)
    //  NO_PREFERENCE (2
    return 1;
  }

  @Override
  protected String getSGsMMEIdentity() {
    // The SGs-MME-Identity AVP is of type UTF8String.
    // This AVP shall contain the MME identity used over the SGs interface and
    // specified in 3GPP TS 23.003 clause 19.4.2.4
    return "mmec03.mmegi3000.mme.epc.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected String getCoupledNodeDiameterId() {
    // The Coupled-Node-Diameter-ID AVP is of type DiameterIdentity.
    // This AVP shall contain the S6a or S6d Diameter identity of the coupled node
    // as specified in 3GPP TS 23.003 clause 19.4.2.4 and clause 19.4.2.6.
    return "nri-sgsn02.rac3.lac80.rac.epc.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected long getSupportedMonitoringEvents() {
    // The Supported-Monitoring-Events AVP is of type Unsigned64, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.3.200-1:
    // Table 7.3.200 -1: Supported-Monitoring-Events
    // Bit NameDescription
    // 0   UE and UICC and/or new IMSI-IMEI-SV association  only used on S6t interface
    // 1   UE-reachability           This bit shall be set if UE reachability Monitoring event is supported in the MME/SGSN
    // 2   Location-of-the-UE        This bit shall be set if Location of the UE and change in location of the UE Monitoring event is supported in the MME/SGSN
    // 3   Loss-of-connectivity      This bit shall be set if Loss of connectivity Monitoring event is supported in the MME/SGSN
    // 4   Communication-failure     This bit shall be set if Communication failure Monitoring event is supported in the MME/SGSN
    // 5   Roaming-status            only used on S6t interface
    // 6   Availability after DDN failure  This bit shall be set if Availability after DDN failure Monitoring event is supported in the MME/SGSN
    // 7   Idle Status Indication    This bit shall be set if Idle Status Indication reporting is supported in the MME/SGSN
    // 8   PDN Connectivity Status   This bit shall be set if PDN Connectivity Status monitoring event is supported in the MME/SGSN
    // 9   SAT-SF-Operation          This bit shall be set if Store and Forward operation for satellite access monitoring event is supported in the MME/SGSN.
    return 494L;
  }

  @Override
  protected Time getSfUlrTimestamp() {
    // The SF-ULR-Timestamp is of type Time and in shall contain the timestamp (in UTC)
    // indicating when the MME operating in satellite S&F mode receives NAS procedure request
    // (e.g. Initial Attach request) from the UE accessing via satellite.
    return Time.valueOf("10:15:35");
  }

  @Override
  protected int getSfProvisionalIndication() {
    // The SF-Provisional-Indication AVP is of type Enumerated. The following values are defined:
    // PROVISIONAL_ULR (0)
    return 0;
  }

  @Override
  protected int getIMSVoiceOverPSSessionsSupported() {
    // The IMS-Voice-Over-PS-Sessions-Supported AVP is of type Enumerated, and indicates that
    // "IMS Voice over PS Sessions" is supported or not by the UE's most recently used TA or RA in the serving node.
    // The following values are defined:
    //  NOT_SUPPORTED (0)
    //  SUPPORTED (1)
    return 1;
  }

  @Override
  protected Time getLastUEActivityTime() {
    // The Last-UE-Activity-Time AVP is of type Time (IETF RFC 6733), and contains
    // the point of time of the last radio contact of the serving node (MME or SGSN) with the UE.
    return Time.valueOf("12:25:37");
  }

  @Override
  protected long getIDAFlags() {
    // The IDA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meanings of the bits are defined in table 7.3.47/1:
    // Table 7.3.47/1: IDA-Flags
    // Bit  Name                            Description
    //  0   Network Node area restricted    This bit, when set, shall indicate that the
    //                                      complete Network Node area (SGSN area)
    //                                      is restricted due to regional subscription.
    return 0;
  }

  @Override
  protected int getMMEUserState() {
    // The MME-User-State AVP is of type Grouped.
    // It shall contain the information related to the user state in the MME.
    // The following values are defined:
    // DETACHED (0)
    // ATTACHED_NOT_REACHABLE_FOR_PAGING (1)
    // ATTACHED_REACHABLE_FOR_PAGING (2)
    // CONNECTED_NOT_REACHABLE_FOR_PAGING (3)
    // CONNECTED_REACHABLE_FOR_PAGING (4)
    // RESERVED (5)
    return 4;
  }

  @Override
  protected int getSGSNUserState() {
    // The SGSN-User-State AVP is of type Grouped.
    // It shall contain the information related to the user state in the SGSN.
    // Same value defined for MME-User-State AVP apply.
    return 1;
  }

  @Override
  protected byte[] getEUtranCellGlobalIdentity() {
    // The E-UTRAN-Cell-Global-Identity AVP is of type OctetString and shall contain the
    // E-UTRAN Cell Global Identification of the user which identifies the cell the user equipment is registered,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x09, 0x5f, 0x02};
  }

  @Override
  protected byte[] getTrackingAreaIdentity() {
    // The Tracking-Area-Identity AVP is of type OctetString and shall contain the
    // Tracking Area Identity of the user which identifies the tracking area where the user is located,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x6d};
  }

  @Override
  protected byte[] getGeographicalInformation() {
    // The Geographical-Information AVP is of type OctetString and shall contain
    // the geographical Information of the user. For details and octet encoding, see 3GPP TS 29.002.
    return new byte[] {0x10, (byte) 0xb1, (byte) 0xa6, 0x3f, (byte) 0xd8, 0x12, (byte) 0xe0, 0x00};
  }

  @Override
  protected byte[] getGeodeticInformation() {
    // The Geodetic-Information AVP is of type OctetString and shall contain
    // the Geodetic Location of the user. For details and octet encoding, see 3GPP TS 29.002.
    return new byte[] {0x03, 0x10, (byte) 0xb1, (byte) 0xa6, 0x78, (byte) 0xd8, 0x12, 0x3d, 0x01, 0x01};
  }

  @Override
  protected int getCurrentLocationRetrieved() {
    // The Current-Location-Retrieved AVP is of type Enumerated. The following values are defined:
    // ACTIVE-LOCATION-RETRIEVAL (0)
    return 0;
  }

  @Override
  protected long getAgeOfLocationInformation() {
    // The Age-Of-Location-Information AVP is of type Unsigned32 and shall contain the elapsed time
    // in minutes since the last network contact of the user equipment. For details, see 3GPP TS 29.002.
    return 0;
  }

  @Override
  protected long getCSGId() {
    // The CSG-Id AVP is of type Unsigned32. Values are coded according to 3GPP TS 23.003.
    return 32L;
  }

  @Override
  protected int getCSGAccessMode() {
    // <avpdefn name="CSG-Access-Mode" code="2317" vendor-id="TGPP" mandatory="mustnot" protected="may" may-encrypt="no" vendor-bit="must" >
    //    <type type-name="Enumerated">
    //      <enum code="0" name="CLOSED_MODE" />
    //      <enum code="1" name="HYBRID_MODE" />
    //    </type>
    //  </avpdefn>
    return 1;
  }

  @Override
  protected int getCSGMembershipIndication() {
    // <avpdefn name="CSG-Membership-Indication" code="2318" vendor-id="TGPP" mandatory="mustnot" protected="may" may-encrypt="no" vendor-bit="must" >
    //    <type type-name="Enumerated">
    //      <enum code="0" name="NOT_CSG_MEMBER" />
    //      <enum code="1" name="CSG_MEMBER" />
    //    </type>
    //  </avpdefn>
    return 1;
  }

  @Override
  protected byte[] getENodeBId() {
    // The eNodeB-ID AVP is of type OctetString, and indicates the eNodeB in which the UE is currently located.
    // It is originally defined in 3GPP TS 29.217
    return new byte[] {0x00, 0x09, 0x5f};
  }

  @Override
  protected byte[] getExtendedENodeBId() {
    return new byte[] {0x01, 0x00, 0x09, 0x5f};
  }

  @Override
  protected byte[] getCellGlobalIdentity() {
    // The Cell-Global-Identity AVP is of type OctetString and shall contain the Cell Global Identification
    // of the user which identifies the cell the user equipment is registered, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x77, 0x3b, (byte) 0xe8};
  }

  @Override
  protected byte[] getLocationAreaIdentity() {
    // The Location-Area-Identity AVP is of type OctetString and shall contain the Location Area Identification
    // of the user which identifies the Location area where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {4, 5, 82, (byte) 240, 16, 17, 92};
  }

  @Override
  protected byte[] getServiceAreaIdentity() {
    // The Service-Area-Identity AVP is of type OctetString and shall contain the Service Area Identifier
    // of the user where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {0x47, (byte) 0xf8, 0x01, 0x25, 0x1d, (byte) 0x89, 0x1c};
  }

  @Override
  protected byte[] getRoutingAreaIdentity() {
    // The Routing-Area-Identity AVP is of type OctetString and shall contain the Routing Area Identity
    // of the user which identifies the routing area where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x65, 0x17};
  }

  @Override
  protected String getTimeZone() {
    // The Time-Zone AVP is of type UTF8String and shall contain the time zone
    // of the location in the visited network where the UE is attached
    return "-3";
  }

  @Override
  protected int getDaylightSavingTime() {
    // The Daylight-Saving-Time AVP is of type Enumerated and shall contain the Daylight Saving Time (in steps of 1 hour) used to adjust for summer time the time zone of the location where the UE is attached in the visited network.
    // The following values are defined:
    //  NO_ADJUSTMENT (0)
    //  PLUS_ONE_HOUR_ADJUSTMENT (1)
    //  PLUS_TWO_HOURS_ADJUSTMENT (2)
    return 0;
  }

  @Override
  protected long getServiceResultCode() {
    // The Service-Result-Code AVP is of type Unsigned32.
    // This AVP shall contain either the value of an Experimental-Result-Code defined by 3GPP
    // or the value of a Result-Code defined in Diameter base protocol by IETF (see IETF RFC 6733).
    return 2001;
  }

  @Override
  protected long getNodeType() {
    // The Node-Type AVP is of type Unsigned32 and shall identify the type of node sending the information.
    // The following values are defined:
    // HSS (0)
    // MME (1)
    // SGSN (2)
    return 1;
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
  protected String getSCEFId() {
    // The SCEF-ID AVP is of type DiameterIdentity, and it shall contain the identity of the SCEF
    // which has originated the service request towards the HSS,
    // i.e. when sent within a Monitoring-Event-Configuration AVP in S6t-CIR,
    // SCEF-ID AVP and Origin-Host AVP shall have the same value.
    return "scefo1.epc.mnc040.mcc901.3gppnetwork.org";
  }

  @Override
  protected long getDSAFlags() {
    // The DSA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits is defined in table 7.3.26/1:
    // Table 7.3.26/1: DSA-Flags
    // Bit Name                          Description
    //  0  Network Node area restricted  This bit, when set, shall indicate that the complete Network Node area (SGSN area)
    //                                   is restricted due to regional subscription
    return 1;
  }

  @Override
  protected long getPURFlags() {
    // The PUR-Flags AVP is of type Unsigned32, and it shall contain a bitmask.
    // The meaning of the bits is defined in table 7.3.149/1:
    // Table 7.3.149/1: PUR-Flags
    // Bit Name                 Description
    //  0  UE Purged in MME	    This bit, when set, indicates that the combined MME/SGSN has purged the UE in the MME part of the node. This bit shall not be set by a standalone SGSN.
    //  1  UE Purged in SGSN    This bit, when set, shall indicate that the combined MME/SGSN has purged the UE in the SGSN part of the node. This bit shall not be set by a standalone MME.
    return 1;
  }

  @Override
  protected int getAlertReason() {
    // The Alert-Reason AVP is of type Enumerated.
    // The following values are defined:
    //  UE_PRESENT (0)
    //  UE_MEMORY_AVAILABLE (1)
    return 0;
  }

  @Override
  protected long getNORFlags() {
    // The NOR-Flags AVP is of type Unsigned32, and it contains a bit mask.
    // Wireshark example taken from real network trace:
    // AVP: NOR-Flags(1443) l=16 f=VM- vnd=TGPP val=0
    //    AVP Code: 1443 NOR-Flags
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    NOR Flags: 0x00000000
    //        0000 0000 0000 0000 0000 00.. .... .... = Spare: 0x000000
    //        .... .... .... .... .... ..0. .... .... = Removal of MME Registration for SMS: Not set
    //        .... .... .... .... .... ...0 .... .... = S6a/S6d-Indicator: Not set
    //        .... .... .... .... .... .... 0... .... = Homogeneous Support of IMS Voice Over PS Sessions: Not set
    //        .... .... .... .... .... .... .0.. .... = Ready for SM from MME: Not set
    //        .... .... .... .... .... .... ..0. .... = UE Reachable from SGSN: Not set
    //        .... .... .... .... .... .... ...0 .... = Delete all APN and PDN GW identity pairs: Not set
    //        .... .... .... .... .... .... .... 0... = UE Reachable: Not set
    //        .... .... .... .... .... .... .... .0.. = Ready for SM: Not set
    //        .... .... .... .... .... .... .... ..0. = SGSN area restricted: Not set
    //        .... .... .... .... .... .... .... ...0 = Single-Registration-Indication: Not set
    return 0;
  }

  @Override
  protected Time getMaximumUEAvailabilityTime() {
    // The Maximum-UE-Availability-Time is of type Time and in shall contain the timestamp (in UTC)
    // until which a UE using a power saving mechanism (such as extended idle mode DRX)
    // is expected to be reachable for SM Delivery.
    return Time.valueOf("18:01:30");
  }

  @Override
  protected long getEmergencyServices() {
    // The Emergency-Services AVP is of type Unsigned32, and it shall contain a bitmask.
    // The meaning of the bits is defined in table 7.2.3.4/1:
    // Table 7.2.3.4/1: Emergency-Services
    // Bit  Name                   Description
    //  0   Emergency-Indication   This bit, when set, indicates a request to establish a
    //                             PDN connection for emergency services.
    return 1;
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
