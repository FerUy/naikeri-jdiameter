package org.mobicents.diameter.stack.functional.slg.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.slg.AbstractSLgServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ServerSLg extends AbstractSLgServer {

  protected boolean receivedPLR;
  protected boolean receivedLRA;
  protected boolean sentPLA;
  protected boolean sentLRR;

  protected ProvideLocationRequest provideLocationRequest;

  public boolean isReceivedLRA() {
    return receivedLRA;
  }

  public boolean isSentLRR() {
    return sentLRR;
  }

  public boolean isReceivedPLR() {
    return receivedPLR;
  }

  public boolean isSentPLA() {
    return sentPLA;
  }

  public void sendProvideLocationAnswer() throws Exception {
    if (!receivedPLR || provideLocationRequest == null) {
      fail("Did not receive PLR or answer already sent.", null);
      throw new Exception("Did not receive PLR or answer already sent. Request: " + this.provideLocationRequest);
    }

    ProvideLocationAnswer pla = super.createPLA(provideLocationRequest, 2001);

    super.serverSLgSession.sendProvideLocationAnswer(pla);

    this.sentPLA = true;
    provideLocationRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), pla.getMessage(), isSentPLA());
  }

  public void sendLocationReportRequest() throws Exception {
    try {
      super.serverSLgSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-SLg-TESTxx"), getApplicationId(), ServerSLgSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    LocationReportRequest lrr = super.createLRR(super.serverSLgSession);
    this.serverSLgSession.sendLocationReportRequest(lrr);
    this.sentLRR = true;
    Utils.printMessage(log, super.stack.getDictionary(), lrr.getMessage(), isSentLRR());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.TBase#processRequest(org.jdiameter.api.Request)
   */
  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != ProvideLocationRequest.code) {
      fail("Received Request with code not used by SLg!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverSLgSession != null) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      try {

        super.serverSLgSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerSLgSession.class, (Object) null);
        ((NetworkReqListener) this.serverSLgSession).processRequest(request);

      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  @Override
  public void doProvideLocationRequestEvent(ServerSLgSession session, ProvideLocationRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedPLR) {
      fail("Received PLR more than once", null);
      return;
    }
    this.receivedPLR = true;
    this.provideLocationRequest = request;
  }

  @Override
  public void doLocationReportAnswerEvent(ServerSLgSession session, LocationReportRequest request, LocationReportAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), answer.getMessage(), false);

    if (this.receivedLRA) {
      fail("Received LRA more than once", null);
      return;
    }
    this.receivedLRA = true;
  }

  /*** Attributes for Provide-Location-Answer (PLA), Location-Report-Request (LRR) ***/

  @Override
  protected byte[] getLocationEstimate() {
    // The Location-Estimate AVP (AVP code 1242) is of type OctetString and contains an estimate of the location
    // of an MS in universal coordinates and the accuracy of the estimate.
    // Refers to the geographical area description in TS 23.032 for the internal structure and encoding of this AVP.
    // Wireshark example taken from a real network capture:
    // AVP: Location-Estimate(1242) l=25 f=VM- vnd=TGPP val=a0369abc1334c3000028571f5a
    //    AVP Code: 1242 Location-Estimate
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 25
    //    AVP Vendor Id: 3GPP (10415)
    //    Location-Estimate: a0369abc1334c3000028571f5a
    //    1010 .... = Location estimate: Ellipsoid Arc (10)
    //    0... .... = Sign of latitude: North (0)
    //    .011 0110 1001 1010 1011 1100 = Degrees of latitude: 3578556 (38.39375 degrees)
    //    0001 0011 0011 0100 1100 0011 = Degrees of longitude: 1258691 (27.00858 degrees)
    //    Inner radius: 0
    //    .010 1000 = Uncertainty radius: 40
    //    Offset angle: 87
    //    Included angle: 31
    //    .101 1010 = Confidence(%): 90
    //    [Location OSM URI: https://www.openstreetmap.org/?mlat=38.39375&mlon=27.00858&zoom=12]
    //    Padding: 000000
    return new byte[] {(byte) 0xa0, 0x36, (byte) 0x9a, (byte) 0xbc, 0x13, 0x34, (byte) 0xc3, 0x00,
        0x00, 0x28, 0x57, 0x1f, 0x5a};
  }

  @Override
  protected int getAccuracyFulfilmentIndicator() {
    // The Accuracy-Fulfilment-Indicator AVP is of type Enumerated. The following values are defined:
    // REQUESTED_ACCURACY_FULFILLED (0)
    // REQUESTED_ACCURACY_NOT_FULFILLED (1)
    // Wireshark example taken from a real network capture:
    // AVP: Accuracy-Fulfilment-Indicator(2513) l=16 f=VM- vnd=TGPP val=REQUESTED_ACCURACY_FULFILLED (0)
    //    AVP Code: 2513 Accuracy-Fulfilment-Indicator
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Accuracy-Fulfilment-Indicator: REQUESTED_ACCURACY_FULFILLED (0)
    return 0;
  }

  @Override
  protected long getAgeOfLocationEstimate() {
    // The Age-Of-Location-Estimate AVP is of type Unsigned32.
    // It indicates how long ago the location estimate was obtained in minutes, as indicated in 3GPP TS 29.002.
    return 0;
  }

  @Override
  protected byte[] getVelocityEstimate() {
    // The Velocity-Estimate AVP is of type OctetString.
    // It is composed of 4 or more octets with an internal structure according to 3GPP TS 23.032
    // AVP: Velocity-Estimate(2515) l=16 f=VM- vnd=TGPP val=00000068
    //    AVP Code: 2515 Velocity-Estimate
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Velocity-Estimate: 00000068
    return new byte[] {0x00, 0x00, 0x00, 0x68};
  }

  @Override
  protected byte[] getEUTRANPositioningData() {
    // The EUTRAN-Positioning-Data AVP is of type OctetString.
    // It shall contain the encoded content of the "Positioning-Data" Information Element as defined in 3GPP TS 29.171.
    // Wireshark example taken from a real network capture:
    // AVP: EUTRAN-Positioning-Data(2516) l=15 f=VM- vnd=TGPP val=411003
    //    AVP Code: 2516 EUTRAN-Positioning-Data
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 15
    //    AVP Vendor Id: 3GPP (10415)
    //    EUTRAN-Positioning-Data: 411003
    //    Positioning-Data
    //        positioning-Data-Set: 2 items
    //            Item 0
    //                Positioning-Method-And-Usage: 10
    //                0001 0... = Positioning Method: E-CID (2)
    //                .... .000 = Positioning usage: Attempted unsuccessfully due to failure or interruption - not used (0)
    //            Item 1
    //                Positioning-Method-And-Usage: 03
    //                0000 0... = Positioning Method: Cell ID (0)
    //                .... .011 = Positioning usage: Attempted successfully: results used to generate location (3)
    //    Padding: 00
    return new byte[] {0x41, 0x10, 0x03};
  }

  @Override
  protected byte[] getECGI() {
    // The ECGI AVP is of type OctetString. It indicates the E-UTRAN Cell Global Identifier.
    // It is coded according to clause 8.21.5, in 3GPP TS 29.274.
    // Wireshark example taken from a real network capture:
    // AVP: ECGI(2517) l=19 f=VM- vnd=TGPP val=34f25302abf53d
    //    AVP Code: 2517 ECGI
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 19
    //    AVP Vendor Id: 3GPP (10415)
    //    ECGI: 34f25302abf53d
    //    Padding: 00
    return new byte[] {0x34, (byte) 0xf2, 0x53, 0x02, (byte) 0xab, (byte) 0xf5, 0x3d};
  }

  @Override
  protected byte[] getGERANPositioningData() {
    // The GERAN-Positioning-Data AVP is of type OctetString.
    // It shall contain the encoded content of the "Positioning Data" Information Element as defined in 3GPP TS 49.031-
    // AVP: GERAN-Positioning-Data(2525) l=20 f=V-- vnd=TGPP val=00031b212b3a6043
    //    AVP Code: 2525 GERAN-Positioning-Data
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 20
    //    AVP Vendor Id: 3GPP (10415)
    //    GERAN-Positioning-Data: 00031b212b3a6043
    return new byte[] {0x00, 0x03, 0x1b, 0x21, 0x2b, 0x3a, 0x60, 0x43};
  }

  @Override
  protected byte[] getGERANGANSSPositioningData() {
    // The GERAN-GANSS-Positioning-Data AVP is of type OctetString.
    // It shall contain the encoded content of the "GANSS Positioning Data" Information Element as defined in 3GPP TS 49.031.
    // AVP: GERAN-GANSS-Positioning-Data(2526) l=19 f=V-- vnd=TGPP val=068c021158e863
    //    AVP Code: 2526 GERAN-GANSS-Positioning-Data
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 19
    //    AVP Vendor Id: 3GPP (10415)
    //    GERAN-GANSS-Positioning-Data: 068c021158e863
    //    Padding: 00
    return new byte[] {0x06, (byte) 0x8c, 0x02, 0x11, 0x58, (byte) 0xe8, 0x63};
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
  protected byte[] getUTRANPositioningData() {
    // The UTRAN-Positioning-Data AVP is of type OctetString.
    // It shall contain the encoded content of the "positioningDataDiscriminator" and the "positioningDataSet"
    // included in the "positionData" Information Element as defined in 3GPP TS 25.413.
    // AVP: UTRAN-Positioning-Data(2528) l=21 f=V-- vnd=TGPP val=0000283140515c4b3a
    //    AVP Code: 2528 UTRAN-Positioning-Data
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 21
    //    AVP Vendor Id: 3GPP (10415)
    //    UTRAN-Positioning-Data: 0000283140515c4b3a
    //    Padding: 000000
    return new byte[] {0x00, 0x00, 0x28, 0x31, 0x40, 0x51, 0x5c, 0x4b, 0x3a};
  }

  @Override
  protected byte[] getUTRANGANSSPositioningData() {
    // The UTRAN-GANSS-Positioning-Data AVP is of type OctetString.
    // It shall contain the encoded content of the "GANSS-PositioningDataSet" only, included in the "positionData"
    // Information Element as defined in 3GPP TS 25.413.
    // AVP: UTRAN-GANSS-Positioning-Data(2529) l=18 f=V-- vnd=TGPP val=014a9018ec63
    //    AVP Code: 2529 UTRAN-GANSS-Positioning-Data
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 18
    //    AVP Vendor Id: 3GPP (10415)
    //    UTRAN-GANSS-Positioning-Data: 014a9018ec63
    //    Padding: 0000
    return new byte[] {0x01, 0x4a, (byte) 0x90, 0x18, (byte) 0xec, 0x63};
  }

  @Override
  protected byte[] getUTRANAdditionalPositioningData() {
    // The UTRAN-Additional-Positioning-Data AVP is of type OctetString.
    // It contains the "UTRAN Additional Positioning Data" Information Element as defined in 3GPP 25.413.
    // AVP: UTRAN-Additional-Positioning-Data(2558) l=14 f=V-- vnd=TGPP val=944b
    //    AVP Code: 2558 UTRAN-Additional-Positioning-Data
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 14
    //    AVP Vendor Id: 3GPP (10415)
    //    UTRAN-Additional-Positioning-Data: 944b
    //    Padding: 0000
    return new byte[] {(byte) 0x94, 0x4b};
  }

  @Override
  protected byte[] getServiceAreaIdentity() {
    // The Service-Area-Identity AVP is of type OctetString and shall contain the Service Area Identifier
    // of the user where the user is located, as specified in 3GPP TS 23.003.
    // Octets are coded as described in 3GPP TS 29.002.
    // AVP: Service-Area-Identity(1607) l=19 f=V-- vnd=TGPP val=47f801251d891c
    //    AVP Code: 1607 Service-Area-Identity
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 19
    //    AVP Vendor Id: 3GPP (10415)
    //    Service-Area-Identity: 47f801251d891c
    //    Padding: 00
    return new byte[] {0x47, (byte) 0xf8, 0x01, 0x25, 0x1d, (byte) 0x89, 0x1c};
  }

  @Override
  protected byte[] getSGSNNumber() {
    // The SGSN-Number AVP is of type OctetString, and it shall contain the ISDN number of the SGSN.
    // For further details on the definition of this AVP, see 3GPP TS 23.003.
    // This AVP contains an SGSN-Number in international number format as described in ITU-T Rec E.164 [41]
    // and shall be encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address.
      return parseTBCD("59899000208");
  }

  @Override
  protected String getSGSNName() {
    // The SGSN-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving SGSN.
    // For further details on the encoding of this AVP, see IETF RFC 6733.
    return "sgsn1B34.mnc001.mcc748.gprs";
  }

  @Override
  protected String getSGSNRealm() {
    // The SGSN-Realm AVP is of type DiameterIdentity, and it shall contain the Diameter Realm Identity of the serving SGSN.
    // For further details on the encoding of this AVP, see IETF RFC 6733.
    return "mnc001.mcc748.gprs";
  }

  @Override
  protected String getMMEName() {
    // The MME-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving MME.
    // For further details on the encoding of this AVP, see IETF RFC 6733.
    // Wireshark example taken from a real network capture:
    // AVP: MME-Name(2402) l=54 f=VM- vnd=TGPP val=KAUSNH01.epc.mnc035.mcc432.3gppnetwork.org
    //    AVP Code: 2402 MME-Name
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 54
    //    AVP Vendor Id: 3GPP (10415)
    //    MME-Name: KAUSNH01.epc.mnc035.mcc432.3gppnetwork.org
    //    Padding: 0000
    return "KAUSNH01.epc.mnc035.mcc432.3gppnetwork.org";
  }

  @Override
  protected String getMMERealm() {
    // The MME-Realm AVP is of type DiameterIdentity, and it shall contain the Diameter Realm Identity of the serving MME.
    // For further details on the encoding of this AVP, see IETF RFC 6733.
    // Wireshark example taken from a real network capture:
    // AVP: MME-Realm(2408) l=45 f=V-- vnd=TGPP val=epc.mnc035.mcc432.3gppnetwork.org
    //    AVP Code: 2408 MME-Realm
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 45
    //    AVP Vendor Id: 3GPP (10415)
    //    MME-Realm: epc.mnc035.mcc432.3gppnetwork.org
    //    Padding: 000000
    return "epc.mnc035.mcc432.3gppnetwork.org";
  }

  @Override
  protected byte[] getMSCNumber() {
    // The MSC-Number AVP is of type OctetString, and it shall contain the ISDN number of the serving MSC or MSC server
    // in international number format as described in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002 [3] for encoding of TBCD-strings.
    // Wireshark example taken from a real network capture:
    // AVP: MSC-Number(2403) l=18 f=VM- vnd=TGPP val=893905502001
    //    AVP Code: 2403 MSC-Number
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 18
    //    AVP Vendor Id: 3GPP (10415)
    //    MSC-Number: 893905502001
    //    Padding: 0000
    return new byte[] {(byte) 0x89, 0x39, 0x05, 0x50, 0x20, 0x01};
  }

  @Override
  protected String get3GPPAAAServerName() {
    // The 3GPP-AAA-Server-Name AVP is of type DiameterIdentity, and defines the Diameter address of the 3GPP AAA Server node.
    return "aaa.restcomm.com";
  }

  @Override
  protected long getLCSCapabilitiesSets() {
    // The LCS-Capabilities-Sets AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in 3GPP 29.002.
    // Wireshark example taken from a real network capture:
    // AVP: LCS-Capabilities-Sets(2404) l=16 f=VM- vnd=TGPP val=3
    //    AVP Code: 2404 LCS-Capabilities-Sets
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    LCS-Capabilities-Sets: 3
    return 3;
  }

  @Override
  protected java.net.InetAddress getGMLCAddress() {
    // The GMLC-Address AVP is of type Address and shall contain the IPv4 or IPv6 address of H-GMLC
    // or the V-GMLC associated with the serving node
    // Wireshark example taken from a real network capture:
    // AVP: GMLC-Address(2405) l=18 f=VM- vnd=TGPP val=10.219.113.252
    //    AVP Code: 2405 GMLC-Address
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 18
    //    AVP Vendor Id: 3GPP (10415)
    //    GMLC-Address: 00010adb71fc
    //        GMLC-Address Address Family: IPv4 (1)
    //        GMLC-Address Address: 10.219.113.252
    //    Padding: 0000
    InetAddress gmlcAddress = null;
    try {
      gmlcAddress = InetAddress.getByName("10.219.113.252");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return gmlcAddress;
  }

  @Override
  protected long getPLAFLags() {
    // The PLA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.4.53/1:
    // Table 7.4.53/1: PLA-Flags
    // Bit	Event Type                                    Description
    //  0   Deferred-MT-LR-Response-Indicator             This bit, when set, indicates that the message is sent in response to the deferred-MT location request.
    //                                                    This bit is applicable only when the message is sent over Lgd interface.
    //  1   MO-LR-ShortCircuit-Indicator                  This bit, when set, indicates that the MO-LR short circuit feature is accepted by the UE,
    //                                                    for periodic location reporting. This bit is applicable only when the message is sent over Lgd interface.
    //  2   Optimized-LCS-Proc-Performed                  This bit, when set, indicates that the combined MME/SGSN has performed the optimized LCS procedure
    //                                                    to retrieve the location of the target UE. This bit is applicable only when the message is sent for
    //                                                    the MT-LR procedure.
    //  3   UE-Transiently-Not-Reachable-Indicator        This bit, when set, indicates that the UE is transiently not reachable due to power saving
    //                                                    (e.g. UE is in extended idle mode DRX or in Power Saving Mode), and that the location information
    //                                                    will be returned in a subsequent Subscriber Location Report when the UE becomes reachable.
    return 16;
  }

  @Override
  protected long getCellPortionId() {
    // The Cell-Portion-ID AVP is of type Unsigned32. It indicates the current Cell Portion location of the target UE
    // as provided by the E-SMLC. It shall contain the value of the "Cell Portion ID" Information Element as defined
    // in 3GPP TS 29.171.
    // AVP: Cell-Portion-ID(2553) l=16 f=V-- vnd=TGPP val=000000c5
    //    AVP Code: 2553 Cell-Portion-ID
    //    AVP Flags: 0x80, Vendor-Specific: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    Cell-Portion-ID: 000000c5
    return 197;
  }

  @Override
  protected String getCivicAddress() {
    // The Civic-Address AVP is of type UTF8String.
    // It contains the XML document carried in the "Civic Address" Information Element as defined in 3GPP TS 29.171.
    // Example from IETF RFC 6848:
    return "<civicAddress xml:lang=\"en-US\"\n" +
        "        xmlns=\"urn:ietf:params:xml:ns:pidf:geopriv10:civicAddr\"\n" +
        "        xmlns:cae=\"urn:ietf:params:xml:ns:pidf:geopriv10:civicAddr:ext\">\n" +
        "     <country>US</country>\n" +
        "     <A1>CA</A1>\n" +
        "     <A2>Sacramento</A2>\n" +
        "     <RD>Colorado</RD>\n" +
        "     <HNO>223</HNO>\n" +
        "     <cae:STP>Boulevard</cae:STP>\n" +
        "     <cae:HNP>A</cae:HNP>\n" +
        "   </civicAddress>";
  }

  @Override
  protected long getBarometricPressure() {
    // The Barometric-Pressure AVP is of type Unsigned32.
    // It contains the "Barometric Pressure" Information Element as defined in 3GPP TS 29.171.
    // Barometric-Pressure ::= INTEGER (30000..115000)
    return 115000;
  }

  @Override
  protected String getLCSNameString() {
    // The LCS-Name-String AVP (AVP code 1238) is of type UTF8String and contains the LCS Client name.
    return "fernando@restcomm.org";
  }

  @Override
  protected int getLCSFormatIndicator() {
    // The LCS-Format-Indicator AVP (AVP code 1237) is of type Enumerated and contains the format of the LCS Client name. It can be one of the following values:
    // 0 LOGICAL_NAME
    // 1 EMAIL_ADDRESS
    // 2 MSISDN
    // 3 URL
    // 4 SIP_URL
    return 1;
  }

  @Override
  protected String getUserName() {
    // Information Element IMSI Mapped to AVP User-Name
    return "748039876543210";
  }

  @Override
  protected byte[] getMSISDN() {
    return parseTBCD("59899077937");
  }

  @Override
  protected String getIMEI() {
    return "011714004661057";
  }

  @Override
  protected long getDeferredLocationType() {
    // The Deferred-Location-Type AVP is of type Unsigned32, and it shall contain a bit mask.
    // Each bit indicates a type of event, until when the location estimation is deferred.
    // For details, please refer to 3GPP TS 23.271 clause 4.4.2.
    // The meaning of the bits shall be as defined in table 7.4.36/1:
    // Bit	Event Type            Description
    //    0   UE-Available        Any event in which the SGSN has established a contact with the UE.
    //    1   Entering-Into-Area  An event where the UE enters a pre-defined geographical area.
    //    2   Leaving-From-Area   An event where the UE leaves a pre-defined geographical area.
    //    3   Being-Inside-Area   An event where the UE is currently within the pre-defined geographical area.
    //    4   Periodic-LDR        An event where a defined periodic timer expires in the UE and activates a location report or a location request.
    //    5   Motion-Event        An event where the UE moves by more than a minimum linear distance. This event is applicable to a deferred EPC-MT-LR only.
    //    6   LDR-Activated       An event where deferred location reporting has been activated in the UE. This event is applicable to a deferred EPC-MT-LR only.
    //    7   Maximum-Interval-Expiration	An event where the maximum reporting interval has expired. This event is applicable to a deferred EPC-MT-LR only.
    return 1;
  }

  @Override
  protected byte[] getLCSReferenceNumber() {
    // The LCS-Reference-Number AVP is of type OctetString of length 1.
    // It shall contain the points number identifying the deferred location request.
    return new byte[] {0x21};
  }

  @Override
  protected long getReportingAmount() {
    // The Reporting-Amount AVP is of type Unsigned32, and it contains reporting frequency.
    // Its minimum value shall be 1 and maximum value shall be 8639999.
    return 8639999;
  }

  @Override
  protected long getReportingInterval() {
    // The Interval-Time AVP is of type Unsigned32, and it contains reporting interval in seconds.
    // Its minimum value shall be 1 and maximum value shall be 8639999.
    return 8639999;
  }

  @Override
  protected int getLocationEvent() {
    // The Location-Event AVP is of type Enumerated. The following values are defined:
    // EMERGENCY_CALL_ORIGINATION (0)
    // EMERGENCY_CALL_RELEASE (1)
    // MO_LR (2)
    // EMERGENCY_CALL_HANDOVER (3)
    // DEFERRED_MT_LR_RESPONSE (4)
    // DEFERRED_MO_LR_TTTP_INITIATION (5)
    // DELAYED_LOCATION_REPORTING (6)
    // HANDOVER_TO_5GC (7)
    return 4;
  }

  @Override
  protected long getLSCServiceTypeId() {
    // The LCS-Service-Type-ID is of type Unsigned32.
    // It defines the identifier associated to one of the Service Types for which the LCS client is allowed to locate
    // the particular UE.
    // Wireshark example taken from a real network capture:
    // AVP: LCS-Service-Type-ID(2520) l=16 f=VM- vnd=TGPP val=1
    //    AVP Code: 2520 LCS-Service-Type-ID
    //    AVP Flags: 0xc0, Vendor-Specific: Set, Mandatory: Set
    //    AVP Length: 16
    //    AVP Vendor Id: 3GPP (10415)
    //    LCS-Service-Type-ID: 1
    return 1;
  }

  @Override
  protected int getPseudonymIndicator() {
    // The Pseudonym-Indicator AVP is of type Enumerated.
    // It defines if a pseudonym is requested.
    // The following values are defined:
    //  PSEUDONYM_NOT_REQUESTED (0)
    //  PSEUDONYM_REQUESTED (1)
    return 1;
  }

  @Override
  protected int getLCSQoSClass() {
    // The LCS-QoS-Class AVP is of the type Enumerated. The following values are defined:
    // ASSURED (0)
    // BEST EFFORT (1)
    return 1;
  }

  @Override
  protected long getLRRFLags() {
    // The LRR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.4.35/1:
    // Table 7.4.35/1: LRR-Flags
    // Bit	Event Type                                    Description
    //  0   Lgd/SLg-Indicator                             This bit, when set, indicates that the Location Report Request message is sent on the Lgd interface,
    //                                                    i.e. the source node is an SGSN (or a combined MME/SGSN to which the UE is attached via UTRAN or GERAN).
    //                                                    This bit, when cleared, indicates that the Location Report Request message is sent on the SLg interface,
    //                                                    i.e. the source node is an MME (or a combined MME/SGSN to which the UE is attached via E-UTRAN).
    //  1   MO-LR-ShortCircuit-Indicator                  This bit, when set, indicates that the MO-LR short circuit feature is used by the UE for
    //                                                    location estimate. This bit is applicable only when for deferred MT-LR procedure and
    //                                                    when the message is sent over Lgd interface.
    //  2   MO-LR-ShortCircuit-Requested                  This bit, when set, indicates that the UE is requesting to use MO-LR short circuit feature
    //                                                    for location estimate.
    //                                                    This bit is applicable only when periodic MO-LR TTTP procedure is initiated by the UE and when the
    //                                                    message is sent over Lgd interface.
    return 1;
  }

  @Override
  protected long getTerminationCause() {
    // The Termination-Cause AVP is of type Unsigned32.
    // The following values are defined:
    //    "Normal"								     0
    //    "Error Undefined"					         1
    //    "Internal Timeout"						 2
    //    "Congestion"							     3
    //    "MT_LR_Restart"							 4
    //    "Privacy Violation"						 5
    //    "Shape of Location Estimate Not Supported" 6
    //    "Subscriber Termination"					 7
    //    "UE Termination"							 8
    //    "Network Termination"						 9
    // "MT_LR_Restart" cause code shall be used to trigger the GMLC to restart the location procedure,
    // either because the sending node knows that the terminal has moved under coverage of another SGSN or MME,
    // or because the subscriber has been deregistered due to a Cancel Location received from HSS.
    // Any unrecognized value of Termination-Cause shall be treated the same as value 1 ("Error Undefined").
    return 4;
  }

  @Override
  protected byte[] get1xRTTRCID() {
    // The 1xRTT-RCID AVP is of type OctetString.
    // It indicates the 1xRTT Reference Cell Id that consists of a Cell Identification Discriminator and
    // a Cell Identification and shall be formatted.
    // according to octets 3 through the end of the Cell Identifier element defined in subclause 4.2.17 in 3GPP2 A.S0014-D.
    // The allowable cell discriminator values are "0000 0010", and "0000 0111".
    return new byte[] {0x00, 0x00, 0x00, 0x10};
  }

}
