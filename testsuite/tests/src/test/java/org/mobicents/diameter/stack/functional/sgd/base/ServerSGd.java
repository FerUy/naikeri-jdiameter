package org.mobicents.diameter.stack.functional.sgd.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.mobicents.diameter.stack.functional.sgd.AbstractSGdServer;
import org.mobicents.diameter.stack.functional.Utils;

import java.util.Date;
import java.util.HashMap;

public class ServerSGd extends AbstractSGdServer {

  protected boolean receivedTFR;
  protected boolean receivedOFA;
  protected boolean sentTFA;
  protected boolean sentOFR;

  protected MTForwardShortMessageRequest mtForwardShortMessageRequest;

  public ServerSGd() {
  }

  public boolean isReceivedTFR() {
    return receivedTFR;
  }

  public boolean isReceivedOFA() {
    return receivedOFA;
  }

  public boolean isSentTFA() {
    return sentTFA;
  }

  public boolean isSentOFR() {
    return sentOFR;
  }

  public void sendMOForwardShortMessageRequest() throws Exception {
    try {
      super.serverSGdSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-SGd-TESTxx"),
          getApplicationId(), ServerSGdSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    MOForwardShortMessageRequest ofr = super.createOFR(super.serverSGdSession);
    this.serverSGdSession.sendMOForwardShortMessageRequest(ofr);
    this.sentOFR = true;
    Utils.printMessage(log, super.stack.getDictionary(), ofr.getMessage(), isSentOFR());
  }

  public void sendMTForwardShortMessageAnswer() throws Exception {
    if (!receivedTFR || mtForwardShortMessageRequest == null) {
      fail("Did not receive TFR or answer already sent.", null);
      throw new Exception("Did not receive TFR or answer already sent. Request: " + this.mtForwardShortMessageRequest);
    }

    MTForwardShortMessageAnswer tfa = super.createTFA(mtForwardShortMessageRequest, 2001);

    super.serverSGdSession.sendMTForwardShortMessageAnswer(tfa);

    this.sentTFA = true;
    mtForwardShortMessageRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), tfa.getMessage(), isSentTFA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.sgd.AbstractSGdServer#doMTForwardShortMessageRequestEvent(
   *    org.jdiameter.api.sgd.ServerSGdSession, org.jdiameter.api.sgd.events.MTForwardShortMessageRequest)
   */
  @Override
  public void doMTForwardShortMessageRequestEvent(ServerSGdSession session, MTForwardShortMessageRequest tfr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    if (this.receivedTFR) {
      fail("Received TFR more than once", null);
      return;
    }
    this.receivedTFR = true;
    this.mtForwardShortMessageRequest = tfr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.sgd.AbstractSGdServer#doMOForwardShortMessageAnswerEvent(
   *    org.jdiameter.api.sgd.ServerSGdSession, org.jdiameter.api.sgd.events.MOForwardShortMessageRequest, org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer)
   */
  public void doMOForwardShortMessageAnswerEvent(ServerSGdSession session, MOForwardShortMessageRequest ofr, MOForwardShortMessageAnswer ofa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    Utils.printMessage(log, super.stack.getDictionary(), ofa.getMessage(), isReceivedOFA());
    if (this.isReceivedOFA()) {
      fail("Received OFA more than once", null);
      return;
    }
    this.receivedOFA = true;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != MTForwardShortMessageRequest.code) {
      fail("Received Request with code not used by SGd!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverSGdSession != null) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      try {
        super.serverSGdSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerSGdSession.class, (Object) null);
        ((NetworkReqListener) this.serverSGdSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  @Override
  protected byte[] getSCAddress() {
    // [ SC-Address ]
    // The SC-Address AVP is of type OctetString,
    // and it shall contain the E.164 number of the SMS-SC or MTC-IWF,
    // in international number format as described in ITU-T Recommendation E.164 [13]
    // and encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000208");
  }

  @Override
  protected long getOFRFlags() {
    // The OFR-Flags AVP is of type Unsigned32 and it shall contain a bit mask. The meaning of the bits shall be as defined in table 6.3.3.12/1:
    // Table 6.3.3.12/1: OFR-Flags
    // Bit Name               Description
    //  0  S6a/S6d-Indicator  This bit, when set, indicates that the OFR message is sent on the Gdd interface,
    //                        i.e. the source node is an SGSN
    //                        (or a combined MME/SGSN to which the UE is attached via UTRAN).
    // This bit, when cleared, indicates that the OFR message is sent on the SGd interface, i.e. the source node is an MME (or a combined MME/SGSN to which the UE is attached via UTRAN or GERAN).
    return 0;
  }

  @Override
  protected byte[] getMSISDN() {
    // [ MSISDN ]
    return parseTBCD("59899077937");
  }

  @Override
  protected String getUserName() {
    // [ User-Name ]
    return "748039876543210";
  }

  @Override
  protected String getExternalIdentifier() {
    // The External-Identifier AVP is of type UTF8String.
    // See 3GPP TS 23.003 for the definition and formatting of the External Identifier.
    return "748039876543210@restcomm.org";
  }

  @Override
  protected byte[] getLMSI() {
    // The LMSI AVP is of type OctetString, and it shall contain the Local Mobile Station Identity (LMSI) allocated by the VLR,
    // as defined in 3GPP TS 23.003 .
    return new byte[] {114, 4, (byte) 233, (byte) 141};
  }

  @Override
  protected byte[] getEUtranCellGlobalIdentity() {
    // The E-UTRAN-Cell-Global-Identity AVP is of type OctetString and shall contain
    // the E-UTRAN Cell Global Identification of the user which identifies the cell
    // the user equipment is registered, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.

    // From jSS7 MAP load test capture display in Wireshark:
    // e-utranCellGlobalIdentity: 47f870004b3605
    //    Mobile Country Code (MCC): Uruguay (748)
    //    Mobile Network Code (MNC): Telefónica Móviles del Uruguay S.A. (Movistar) (07)
    //    .... 0000 0100 1011 0011 0110 0000 0101 = ECI (E-UTRAN Cell Identifier): 4929029
    return new byte[] { 0x47, (byte) 0xf8, 0x70, 0x00, 0x4b, 0x36, 0x05 };
  }

  @Override
  protected byte[] getTrackingAreaIdentity() {
    // The Tracking-Area-Identity AVP is of type OctetString and shall contain the
    // Tracking Area Identity of the user which identifies the tracking area where the user is located,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002.

    // From jSS7 MAP load test capture display in Wireshark:
    // trackingAreaIdentity: 47f8701b58
    //    Mobile Country Code (MCC): Uruguay (748)
    //    Mobile Network Code (MNC): Telefónica Móviles del Uruguay S.A. (Movistar) (07)
    //    Tracking area code (TAC): 7000
    return new byte[] { 0x47, (byte) 0xf8, 0x70, 0x1b, 0x58 };
  }

  @Override
  protected byte[] getGeographicalInformation() {
    // The Geographical-Information AVP is of type OctetString and shall contain
    // the geographical Information of the user. For details and octet encoding,
    // see 3GPP TS 29.002.

    // From jSS7 MAP load test capture display in Wireshark:
    // geographicalInformation: 10b1a63fd812e000
    //    0001 .... = Location estimate: Ellipsoid point with uncertainty Circle (1)
    //    1... .... = Sign of latitude: South (1)
    //    .011 0001 1010 0110 0011 1111 = Degrees of latitude: 3253823 (-34.90974 degrees)
    //    1101 1000 0001 0010 1110 0000 = Degrees of longitude: -2616608 (-56.14632 degrees)
    //    .000 0000 = Uncertainty code: 0 (0.0 m)
    //    [Location OSM URI: https://www.openstreetmap.org/?mlat=-34.90974&mlon=-56.14632&zoom=12]
    //
    return new byte[] { 0x10, (byte) 0xb1, (byte) 0xa6, 0x3f, (byte) 0xd8, 0x12, (byte) 0xe0, 0x00};
  }

  @Override
  protected byte[] getGeodeticInformation() {
    // The Geodetic-Information AVP is of type OctetString and shall contain the
    // Geodetic Location of the user. For details and octet encoding, see 3GPP TS 29.002.

    // From jSS7 MAP load test capture display in Wireshark (doesn't display it nicely btw):
    // geodeticInformation: 0110b1a678d8123d0102
    //    .... 00.. = Calling Geodetic Location presentation restricted indicator: presentation allowed (0)
    //    .... ..01 = Calling Geodetic Location screening indicator: user provided, verified and passed (1)
    //    0... .... = Extension indicator: information continues through the next octet
    //    .001 0000 = Calling geodetic location type of shape: Unknown (16)
    //    Shape description: b1a678d8123d0102
    return new byte[] { 0x01, 0x10, (byte) 0xb1, (byte) 0xa6, 0x78, (byte) 0xd8, 0x12, 0x3d, 0x01, 0x02};
  }

  @Override
  protected int getCurrentLocationRetrieved() {
    // The Current-Location-Retrieved AVP is of type Enumerated. The following values are defined:
    // ACTIVE-LOCATION-RETRIEVAL (0)
    // This value is used when location information was obtained after a successful paging procedure
    // for Active Location Retrieval when the UE is in idle mode or after retrieving
    // the most up-to-date location information from the eNB when the UE is in connected mode.
    return 0;
  }

  @Override
  protected long getAgeOfLocationInformation() {
    // The Age-Of-Location-Information AVP is of type Unsigned32 and shall contain
    // the elapsed time in minutes since the last network contact of the user equipment.
    // For details, see 3GPP TS 29.002.
    return 0;
  }

  @Override
  protected long getCSGId() {
    // The CSG-Id AVP is of type Unsigned32. Values are coded according to 3GPP TS 23.003.
    // Unused bits (least significant) shall be padded with zeros.
    // 3GPP TS 23.003 § 4.7
    // A Closed Subscriber Group consists of a single cell or a collection of cells within an
    // E-UTRAN and UTRAN that are open to only a certain group of subscribers
    // Within a PLMN, a Closed Subscriber Group is identified by a Closed Subscriber Group Identity (CSG-ID).
    // The CSG ID shall be fix length 27 bit value.

    // From jSS7 MAP load test capture display in Wireshark:
    // userCSGInformation
    //    Padding: 5
    //    csg-Id: c0000060
    //    accessMode: 01
    //    cmi: 01
    return 3221225568L;
  }

  @Override
  protected int getCSGAccessMode() {
    // The CSG-Access-Mode AVP (AVP code 2317) is of type Enumerated and holds the mode in which
    // the CSG cell User is accessing to, operates. It has the following values:
    // 0   Closed mode
    // 1   Hybrid Mode
    return 1;
  }

  @Override
  protected int getCSGMembershipIndication() {
    // The CSG-Membership-Indication AVP (AVP code 2318) is of type Enumerated, and
    // indicates the UE is a member of the accessing CSG cell, if the access mode is Hybrid,
    // as described in TS 29.060, and in TS 29.274.
    // If this indication is not present, this means the UE is a Not member of the CSG cell
    // for hybrid access mode. The following values are defined:
    // 0   Not CSG member
    // 1   CSG Member
    return 1;
  }

  @Override
  protected byte[] getENodeBId() {
    // The eNodeB-Id AVP (AVP code 4008) is of type OctetString,
    // and indicates the eNodeB in which the UE is currently located.
    // The AVP shall be coded as in clause 8.51 of 3GPP TS 29.274.
    // 007437
    return new byte[] { 0x00, 0x00, 0x74, 0x37 };
  }

  @Override
  protected byte[] getExtendedENodeBId() {
    return new byte[] { 0x01, 0x74, 0x37 };
  }

  @Override
  protected byte[] getCellGlobalIdentity() {
    // The Cell-Global-Identity AVP is of type OctetString and shall contain the
    // Cell Global Identification of the user which identifies the cell the user equipment is registered,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002.
    return new byte[] { 0x47, (byte) 0xf8, 0x70, 0x20, 0x79, 0x24, 0x41 };
  }

  @Override
  protected byte[] getLocationAreaIdentity() {
    // The Location-Area-Identity AVP is of type OctetString and shall contain
    // the Location Area Identification of the user which identifies the Location area
    // where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002
    return new byte[] { 0x20, 0x79, 0x02 };
  }

  @Override
  protected byte[] getServiceAreaIdentity() {
    // The Service-Area-Identity AVP is of type OctetString and shall contain the Service Area Identifier
    // of the user where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    return new byte[] { (byte) 0xf4, 0x41 };
  }

  @Override
  protected byte[] getRoutingAreaIdentity() {
    // The Routing-Area-Identity AVP is of type OctetString and shall contain the Routing Area Identity
    // of the user which identifies the routing area where the user is located,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002
    // Wireshark example from jSS7 MAP load test:
    // routeingAreaIdentity: 47f810006517
    //    Routing area identification: 748-1-101-23
    //        Mobile Country Code (MCC): Uruguay (748)
    //        Mobile Network Code (MNC): Administración Nacional de Telecomunicaciones (ANTEL) (01)
    //        Location Area Code (LAC): 0x0065 (101)
    //        Routing Area Code (RAC): 0x17 (23)
    return new byte[] { 0x47, (byte) 0xf8, 0x10, 0x00, 0x65, 0x17 };
  }

  @Override
  protected byte[] getNRCellGlobalIdentity() {
    // The NR-Cell-Global-Identity AVP is of type OctetString and shall contain the
    // NR Cell Global Identification of the user which identifies the cell the user equipment is registered,
    // as specified in 3GPP TS 23.003. Octets are coded as described in 3GPP TS 29.002
    // Wireshark example from jSS7 MAP load test:
    // nrCellGlobalIdentity: 47f8200800000008
    return new byte[] { 0x47, (byte) 0xf8, 0x20, 0x08, 0x00, 0x00, 0x00, 0x08 };
  }

  @Override
  protected byte[] getSmRpUi() {
    // The SM-RP-UI is of type OctetString and it shall contain a short message
    // transfer protocol data unit (TPDU) which is defined in 3GPP TS 23.040
    // and represents the user data field carried by the short message service relay sub-layer protocol.
    // Its maximum length is of 200 octets
    return new byte[] { (byte) 0xd3, (byte) 0xe6, 0x14, (byte) 0xc4, 0x7e, (byte) 0x87, (byte) 0xc9,
        0x20, 0x7a, 0x79, 0x4e, 0x07};
  }

  @Override
  protected String getHssId() {
    // [ SMSMI-Correlation-ID ]
    // The HSS-ID shall consist of decimal digits (0 through 9) only and be composed of
    // the MCC consisting of three digits, the MNC consisting of two or three digits
    // and an index consisting of one to several digits.
    // The number of digits in the HSS-ID shall not exceed 15.
    // This composition is compatible with the IMSI one.
    // The HSS-ID shall not be identical to the complete IMSI of a UE.
    return "748030000000071";
  }

  @Override
  protected String getOriginatingSipUri() {
    // The Originating-SIP-URI AVP is of type UTF8String.
    // It shall contain the Public identity of the IMS UE without MSISDN
    // which is the sender of a short message, in the context of MSISDN-less SMS delivery in IMS
    // (see 3GPP TS 23.204).
    return "fer@restcomm.org";
  }

  @Override
  protected String getDestinationSipUri() {
    // The Destination-SIP-URI AVP is of type UTF8String.
    // It shall contain the Public identity of the IMS UE without MSISDN
    // which is the recipient of a short message, in the context of MSISDN-less SMS delivery in IMS
    // (see 3GPP TS 23.204)
    return "nando@restcomm.org";
  }

  /** { SM-Delivery-Outcome } **/
  @Override
  protected HashMap<Integer, Long> getMmeSmDeliveryOutcome() {
    // The MME-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery
    // for setting the message waiting data in the HSS when the SM delivery is with an MME.
    // AVP format:
    // MME-SM-Delivery-Outcome::= <AVP header: 3317 10415>>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> mmeSmDeliveryOutcome = new HashMap<>();
    mmeSmDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return mmeSmDeliveryOutcome;
  }

  @Override
  protected HashMap<Integer, Long> getMscSmDeliveryOutcome() {
    // The MSC-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery for setting
    // the message waiting data in the HSS when the SM delivery is with an MSC.
    // AVP format:
    // MSC-SM-Delivery-Outcome::= <AVP header: 3318 10415>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> mscSmDeliveryOutcome = new HashMap<>();
    mscSmDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return mscSmDeliveryOutcome;
  }

  @Override
  protected HashMap<Integer, Long> getSgsnSmDeliveryOutcome() {
    // The SGSN-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery
    // for setting the message waiting data in the HSS when the SM delivery is with an SGSN.
    // AVP format:
    // SGSN-SM-Delivery-Outcome::= <AVP header: 3319 10415>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> sgsnSmDeliveryOutcome = new HashMap<>();
    sgsnSmDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return sgsnSmDeliveryOutcome;
  }

  @Override
  protected HashMap<Integer, Long> getIpSmGwSmDeliveryOutcome() {
    // The IP-SM-GW-SM-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery
    // for setting the message waiting data when the SM delivery is with an IP-SM-GW.
    // AVP format:
    // IP-SM-GW-SM-Delivery-Outcome::= <AVP header: 3320 10415>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> ipSmGwSmDeliveryOutcome = new HashMap<>();
    ipSmGwSmDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return ipSmGwSmDeliveryOutcome;
  }

  @Override
  protected HashMap<Integer, Long> getSmsf3gppSmDeliveryOutcome() {
    // The SMSF-3GPP-SM-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery
    // for setting the message waiting data in the HSS when the SM delivery is with an SMSF registered for 3GPP access.
    // AVP format:
    // SMSF-3GPP-SM-Delivery-Outcome::= <AVP header: 3336 10415>>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> smsfDeliveryOutcome = new HashMap<>();
    smsfDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return smsfDeliveryOutcome;
  }

  @Override
  protected HashMap<Integer, Long> getSmsfNon3gppSmDeliveryOutcome() {
    // The SMSF-Non-3GPP-SM-Delivery-Outcome AVP is of type grouped and shall indicate the outcome of the SM delivery
    // for setting the message waiting data in the HSS when the SM delivery is with an SMSF registered for Non-3GPP access.
    // AVP format:
    // SMSF-Non-3GPP-SM-Delivery-Outcome::= <AVP header: 3337 10415>>
    //              [ SM-Delivery-Cause ]
    //              [ Absent-User-Diagnostic-SM ]
    HashMap<Integer, Long> smsfNon3gppDeliveryOutcome = new HashMap<>();
    smsfNon3gppDeliveryOutcome.put(getSMDeliveryCause(), getAbsentUserDiagnosticSM());
    return smsfNon3gppDeliveryOutcome;
  }

  @Override
  protected int getSMDeliveryCause() {
    // The SM-Delivery-Cause AVP is of type Enumerated and shall indicate the cause of the SMP delivery result.
    // The following values are defined:
    //  UE_ MEMORY_CAPACITY_EXCEEDED (0)
    //  ABSENT_USER (1)
    //  SUCCESSFUL_TRANSFER (2)
    return 1;
  }

  @Override
  protected long getAbsentUserDiagnosticSM() {
    // The Absent-User-Diagnostic-SM AVP is of type Unsigned32 and shall indicate the diagnostic
    // explaining the absence of the subscriber. The values are defined in 3GPP TS 23.040 clause 3.3.2
    return 27L;
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
  protected int getSMEnumeratedDeliveryFailureCause() {
    // The SM-Enumerated-Delivery-Failure-Cause AVP is of type enumerated, and it shall contain
    // the cause of the failure of an SM delivery. The following values are defined:
    // MEMORY_CAPACITY_EXCEEDED (0),
    // EQUIPMENT_PROTOCOL_ERROR (1),
    // EQUIPMENT_NOT_SM-EQUIPPED (2),
    // UNKNOWN_SERVICE_CENTRE (3),
    // SC-CONGESTION (4),
    // INVALID_SME-ADDRESS (5),
    // USER_NOT_SC-USER (6).
    return 4;
  }

  @Override
  protected byte[] getSMDiagnosticInfo() {
    // The SM-Diagnostic-Info AVP is of type OctetString, and it shall contain
    // complementary information associated to the SM Delivery Failure cause.
    return new byte[] { 0x02 };
  }

  @Override
  protected Date getRequestedRetransmissionTime() {
    // The Requested-Retransmission-Time is of type Time and in shall contain the timestamp (in UTC)
    // at which the SMS-GMSC is requested to retransmit the MT Short Message.
    return new Date();
  }

  private static byte[] parseTBCD(String tbcd) {
    int length = (tbcd == null ? 0:tbcd.length());
    int size = (length + 1)/2;
    byte[] buffer = new byte[size];

    for (int i=0, i1=0, i2=1; i<size; ++i, i1+=2, i2+=2) {

      char c = tbcd.charAt(i1);
      int n2 = getTBCDNibble(c, i1);
      int octet = 0;
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

  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
          + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }
}
