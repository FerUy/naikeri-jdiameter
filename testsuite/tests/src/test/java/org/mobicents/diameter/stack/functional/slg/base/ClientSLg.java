package org.mobicents.diameter.stack.functional.slg.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.slg.ClientSLgSession;
import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.slg.AbstractSLgClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientSLg extends AbstractSLgClient {

  protected boolean receivedPLA;
  protected boolean receivedLRR;
  protected boolean sentPLR;
  protected boolean sentLRA;

  protected LocationReportRequest locationReportRequest;

  public ClientSLg() {
  }

  public boolean isReceivedPLA() {
    return receivedPLA;
  }

  public boolean isSentPLR() {
    return sentPLR;
  }

  public boolean isReceivedLRR() {
    return receivedLRR;
  }

  public boolean isSentLRA() {
    return sentLRA;
  }

  public void sendProvideLocationRequest() throws Exception {
    ProvideLocationRequest plr = super.createPLR(super.clientSLgSession);
    super.clientSLgSession.sendProvideLocationRequest(plr);
    this.sentPLR = true;
    Utils.printMessage(log, super.stack.getDictionary(), plr.getMessage(), isSentPLR());
  }

  public void sendLocationReportAnswer() throws Exception {
    if (!receivedLRR || locationReportRequest == null) {
      fail("Did not receive LRR or answer already sent.", null);
      throw new Exception("Did not receive LRR or answer already sent. Request: " + this.locationReportRequest);
    }

    LocationReportAnswer lra = super.createLRA(locationReportRequest, 2001);

    this.clientSLgSession.sendLocationReportAnswer(lra);

    this.sentLRA = true;
    locationReportRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), lra.getMessage(), isSentLRA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.slg.AbstractSLgClient#doProvideLocationAnswerEvent(
   *    org.jdiameter.api.slg.ClientSLgSession, org.jdiameter.api.slg.events.ProvideLocationRequest, org.jdiameter.api.slg.events.ProvideLocationAnswer)
   */
  @Override
  public void doProvideLocationAnswerEvent(ClientSLgSession session, ProvideLocationRequest request, ProvideLocationAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {

    Utils.printMessage(log, super.stack.getDictionary(), answer.getMessage(), isReceivedPLA());
    if (this.receivedPLA) {
      fail("Received PLA more than once", null);
      return;
    }
    this.receivedPLA = true;
  }

  @Override
  public void doLocationReportRequestEvent(ClientSLgSession session, LocationReportRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedLRR) {
      fail("Received LRR more than once", null);
      return;
    }
    this.receivedLRR = true;
    this.locationReportRequest = request;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != LocationReportRequest.code) {
      fail("Received Request with code not used by SLg!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientSLgSession.getSessionId().equals(request.getSessionId())) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      super.clientSLgSession.release();
      try {
        super.clientSLgSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientSLgSession.class, (Object) null);
        ((NetworkReqListener) this.clientSLgSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  /*** Attributes for Provide-Location-Request (PLR), Location-Report-Answer (LRA) ***/

  // { SLg-Location-Type }
  @Override
  protected int getSLgLocationType() {
    // The LCS-Client-Type AVP (AVP code 1241) is of type Enumerated and contains the type of services requested by the LCS Client.
    // It can be one of the following values:
    // 0 EMERGENCY_SERVICES
    // 1 VALUE_ADDED_SERVICES
    // 2 PLMN_OPERATOR_SERVICES
    // 3 LAWFUL_INTERCEPT_SERVICES
    return 1;
  }

  @Override
  protected String getLCSNameString() {
    // The LCS-Name-String AVP (AVP code 1238) is of type UTF8String and contains the LCS Client name.
    return "fernando@restcomm.org";
  }

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
  protected int getLCSClientType() {
    // The LCS-Client-Type AVP (AVP code 1241) is of type Enumerated and contains the type of services
    // requested by the LCS Client.
    // It can be one of the following values:
    // 0 EMERGENCY_SERVICES
    // 1 VALUE_ADDED_SERVICES
    // 2 PLMN_OPERATOR_SERVICES
    // 3 LAWFUL_INTERCEPT_SERVICES
    return 1;
  }

  @Override
  protected String getLCSRequestorIdString() {
    // The LCS-Requestor-ID-String AVP (AVP code 1240) is of type UTF8String and contains
    // the identification of the Requestor and can be e.g. MSISDN or logical name
    return "Restcomm Geolocation API";
  }

  @Override
  protected int getReqLCSFormatIndicator() {
    // The LCS-Format-Indicator AVP (AVP code 1237) is of type Enumerated and contains the format of the LCS Client name. It can be one of the following values:
    // 0 LOGICAL_NAME
    // 1 EMAIL_ADDRESS
    // 2 MSISDN
    // 3 URL
    // 4 SIP_URL
    return 0;
  }

  @Override
  protected long getLCSPriority() {
    // The LCS-Priority AVP is of type Unsigned32. It indicates the priority of the location request.
    // The value 0 shall indicate the highest priority, and the value 1 shall indicate normal priority.
    // All other values shall be treated as 1 (normal priority). For details, refer to 3GPP TS 22.071.
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
  protected long getHorizontalAccuracy() {
    // The Horizontal-Accuracy AVP is of type Unsigned32. Bits 6-0 corresponds to Uncertainty Code defined in 3GPP TS 23.032 [3].
    // The horizontal location error should be less than the error indicated by the uncertainty code with 67% confidence.
    // Bits 7 to 31 shall be ignored
    return 127;
  }

  @Override
  protected long getVerticalAccuracy() {
    // The Vertical-Accuracy AVP is of type Unsigned32. Bits 6-0 corresponds to Uncertainty Code defined in 3GPP TS 23.032.
    // The Vertical location error should be less than the error indicated by the uncertainty code with 67% confidence.
    // Bits 7 to 31 shall be ignored
    return 3;
  }

  @Override
  protected int getVerticalRequested() {
    // The Vertical-Requested AVP is of type Enumerated. The following values are defined:
    // VERTICAL_COORDINATE_IS_NOT REQUESTED (0)
    // VERTICAL_COORDINATE_IS_REQUESTED (1)
    return 1;
  }

  @Override
  protected int getResponseTime() {
    // The Response-Time AVP is of type Enumerated. The following values are defined:
    // LOW_DELAY (0)
    // DELAY_TOLERANT (1)
    return 1;
  }

  @Override
  protected int getVelocityRequested() {
    // The Velocity-Requested AVP is of type Enumerated. The following values are defined:
    // VELOCITY_IS_NOT_REQUESTED (0)
    // VELOCITY_IS_REQUESTED (1)
    return 1;
  }

  @Override
  protected long getLCSSupportedGADShapes() {
    // The Supported-GAD-Shapes AVP is of type Unsigned32, and it shall contain a bitmask.
    // A node shall mark in the BIT STRING all Shapes defined in 3GPP TS 23.032 it supports.
    // Bits 10-0 in shall indicate the supported Shapes defined in 3GPP TS 23.032. Bits 11 to 31 shall be ignored.
    // ellipsoidPoint (0)
    // ellipsoidPointWithUncertaintyCircle (1)
    // ellipsoidPointWithUncertaintyEllipse (2)
    // polygon (3)
    // ellipsoidPointWithAltitude (4)
    // ellipsoidPointWithAltitudeAndUncertaintyEllipsoid (5)
    // ellipsoidArc (6)
    // highAccuracyEllipsoidPointWithUncertaintyEllipse (7)
    // highAccuracyEllipsoidPointWithAltitudeAndUncertaintyEllipsoid (8)
    // highAccuracyEllipsoidPointWithScalableUncertaintyEllipse (9)
    // highAccuracyEllipsoidPointWithAltitudeAndScalableUncertaintyEllipsoid (10)
    return 127L;
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
  protected String getLCSCodeword() {
    // The LCS-Codeword AVP is of type UTF8String.
    // It indicates the potential codeword string to send in a notification message to the UE
    return "restcomm49f9f$#ERSD";
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
  protected int getLCSPrivacyCheckSession() {
    // The LCS-Privacy-Check-Session AVP is of type Grouped.
    // AVP format:
    // LCS-Privacy-Check-Session ::= <AVP header: 2522 10415>
    //                   { LCS-Privacy-Check }
    // The LCS-Privacy-Check AVP is of type Enumerated. The following values are defined:
    // ALLOWED_WITHOUT_NOTIFICATION (0)
    // ALLOWED_WITH_NOTIFICATION (1)
    // ALLOWED_IF_NO_RESPONSE (2)
    // RESTRICTED_IF_NO_RESPONSE (3)
    // NOT_ALLOWED (4)
    return 2;
  }

  @Override
  protected int getLCSPrivacyCheckNonSession() {
    // The LCS-Privacy-Check-Non-Session AVP is of type Grouped.
    // AVP format:
    // LCS-Privacy-Check-Non-Session ::= <AVP header: 2521 10415>
    //                    { LCS-Privacy-Check }
    // The LCS-Privacy-Check AVP is of type Enumerated. The following values are defined:
    // ALLOWED_WITHOUT_NOTIFICATION (0)
    // ALLOWED_WITH_NOTIFICATION (1)
    // ALLOWED_IF_NO_RESPONSE (2)
    // RESTRICTED_IF_NO_RESPONSE (3)
    // NOT_ALLOWED (4)
    return 4;
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
  protected int getOccurrenceInfo() {
    // The Occurrence-Info AVP is of type Enumerated. The following values are defined:
    // ONE_TIME_EVENT (0)
    // MULTIPLE_TIME_EVENT (1)
    return 1;
  }

  @Override
  protected long getIntervalTime() {
    // The Interval-Time AVP is of type Unsigned32, and it contains
    // the minimum time interval between area reports or motion reports, in seconds.
    // The minimum value shall be 1 second and the maximum value 32767 seconds
    return 32767;
  }

  @Override
  protected long getMaximumInterval() {
    // The Maximum-Interval AVP is of type Unsigned32, and it contains the maximum time interval
    // between consecutive event reports, in seconds.
    // The minimum value shall be 1 second and the maximum value 86400 seconds.
    // The Maximum-Interval AVP is only applicable to a deferred EPC-MT-LR.
    return 86400;
  }

  @Override
  protected long getSamplingInterval() {
    // The Sampling-Interval AVP is of type Unsigned32, and it contains the maximum time interval
    // between consecutive evaluations by a UE of an area event or motion event, in seconds.
    // The minimum value shall be 1 second and the maximum value 3600 seconds.
    // The Sampling-Interval AVP is only applicable to a deferred EPC-MT-LR.
    return 3600;
  }

  @Override
  protected long getReportingDuration() {
    // The Reporting-Duration AVP is of type Unsigned32, and it contains the maximum duration of event reporting, in seconds.
    // Its minimum value shall be 1 and maximum value shall be 8640000.
    // The Reporting-Duration AVP is only applicable to a deferred EPC-MT-LR.
    return 8640000;
  }

  @Override
  protected long getReportingLocationRequirements() {
    // The Reporting-Location-Requirements AVP is of type Unsigned32, and it shall contain a bit string
    // indicating requirements on location provision for a deferred EPC-MT-LR.
    // When a bit is set to one, the corresponding requirement is present.
    // When a bit is set to zero or when the AVP is omitted, the corresponding requirement is not present.
    // For support of backward compatibility, a receiver shall ignore any bits that are set to one but are not supported.
    // The meaning of the bits shall be as defined in table 7.4.69/1:
    // Table 7.4.69/1: Reporting-Location-Requirements
    // Bit     Requirement        Description
    //  0      Location-Estimate  A location estimate is required for each area event,
    //                            motion event report or expiration of the maximum time interval between event reports.
    //  1-31   None               Spare
    return 1;
  }

  @Override
  protected long getAreaType() {
    // The Area-Type AVP is of type Unsigned32. The following values are defined:
    // "Country Code"            0
    // "PLMN ID"                 1
    // "Location Area ID"        2
    // "Routing Area ID"         3
    // "Cell Global ID"          4
    // "UTRAN Cell ID"           5
    // "Tracking Area ID"        6
    // "E-UTRAN Cell Global ID"  7
    return 7;
  }

  @Override
  protected byte[] getAreaIdentification() {
    // The Area-Identification AVP is of type OctetString and shall contain
    // the identification of the area applicable for the change of area event based deferred location reporting.
    // For Area-Type 0 to 5, octets are coded as described in 3GPP TS 29.002.
    // For Area-Type 6, octets are coded as defined for the Tracking Area Identity area identification in 3GPP TS 24.080.
    // For Area-Type 7, octets are coded as defined for the ECGI area identification in 3GPP TS 24.080.
    // For a deferred EPC-MT-LR for the area event, only Area-Type 6 and 7 are applicable.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x09, 0x5f, 0x02};
  }

  @Override
  protected long getAdditionalAreaType() {
    // The Area-Type AVP is of type Unsigned32. The following values are defined:
    // "Country Code"            0
    // "PLMN ID"                 1
    // "Location Area ID"        2
    // "Routing Area ID"         3
    // "Cell Global ID"          4
    // "UTRAN Cell ID"           5
    // "Tracking Area ID"        6
    // "E-UTRAN Cell Global ID"  7
    return 6;
  }

  @Override
  protected byte[] getAdditionalAreaIdentification() {
    // The Area-Identification AVP is of type OctetString and shall contain
    // the identification of the area applicable for the change of area event based deferred location reporting.
    // For Area-Type 0 to 5, octets are coded as described in 3GPP TS 29.002.
    // For Area-Type 6, octets are coded as defined for the Tracking Area Identity area identification in 3GPP TS 24.080.
    // For Area-Type 7, octets are coded as defined for the ECGI area identification in 3GPP TS 24.080.
    // For a deferred EPC-MT-LR for the area event, only Area-Type 6 and 7 are applicable.
    return new byte[] {0x47, (byte) 0xf8, 0x10, 0x00, 0x6d};
  }

  @Override
  protected java.net.InetAddress getGMLCAddress() {
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

  @Override
  protected long getPLRFLags() {
    // The PLR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.4.52/1:
    // Table 7.4.52/1: PLR-Flags
    // Bit  Name                                            Description
    // 0    MO-LR-ShortCircuit-Indicator                    This bit, when set, indicates that
    //                                                      the MO-LR short circuit feature is requested
    //                                                      for the periodic location.
    //                                                      This bit is applicable only when the
    //                                                      deferred MT-LR procedure is initiated for
    //                                                      a periodic location event and when
    //                                                      the message is sent over Lgd interface.
    // 1    Optimized-LCS-Proc-Req	                        This bit, when set, indicates that the GMLC
    //                                                      is requesting the optimized LCS procedure
    //                                                      for the combined MME/SGSN.
    //                                                      This bit is applicable only when the MT-LR procedure
    //                                                      is initiated by the GMLC over the Lgd interface.
    //                                                      The GMLC shall set this bit only when
    //                                                      the HSS indicates the combined MME/SGSN node
    //                                                      supporting the optimized LCS procedure.
    // 2    Delayed-Location-Reporting-Support-Indicator    This bit, when set, indicates that the
    //                                                      GMLC supports delayed location reporting for
    //                                                      UEs transiently not reachable
    //                                                      (e.g. UEs in extended idle mode DRX or
    //                                                      Power Saving Mode) as specified in clauses
    //                                                      9.1.6 and 9.1.15 of 3GPP TS 23.271,
    //                                                      i.e. that the GMLC supports receiving a
    //                                                      PROVIDE SUBSCRIBER LOCATION RESPONSE with
    //                                                      the UE-Transiently-Not-Reachable-Indicator set in the PLA-Flags IE;
    //                                                      and receiving the location information in a
    //                                                      subsequent SUBSCRIBER LOCATION REPORT
    //                                                      when the UE becomes reachable.
    // NOTE1: Bits not defined in this table shall be cleared by the sending GMLC and discarded by the receiving MME or SGSN.
    return 4;
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
  protected int getPrioritizedListIndicator() {
    // The Prioritized-List-Indicator AVP is of type Enumerated, and it indicates if the PLMN-ID-List is provided in prioritized order or not.
    //  NOT_PRIORITIZED  (0)
    //  PRIORITIZED (1)
    return 0;
  }

  @Override
  protected byte[] getVisitedPLMNId() {
    // The PLMN-ID-List AVP is of type Grouped.
    // AVP format:
    // PLMN-ID-List ::= <AVP header: 2544 10415>
    //        { Visited-PLMN-Id }
    //        [ Periodic-Location-Support-Indicator ]
    //        *[ AVP ]
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
  protected int getPeriodicLocationSupportIndicator() {
    // The Periodic-Location-Support-Indicator AVP is of type Enumerated, and it indicates if the given PLMN-ID
    // (indicated by Visited-PLMN-Id) supports periodic location or not.
    // NOT_SUPPORTED (0)
    // SUPPORTED (1)
    return 1;
  }

  @Override
  protected long getLinearDistance() {
    // The Linear-Distance AVP is of type Unsigned32, and it contains the minimum linear (straight line) distance
    // for motion event reports, in meters.
    // The minimum value shall be 1 and maximum value shall be 10,000.
    // The Linear-Distance AVP is only applicable to a deferred EPC-MT-LR
    return 10000;
  }

  @Override
  protected long getLRAFLags() {
    // The LRA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 7.4.56/1:
    // Table 7.4.56/1: LRA-Flags
    // Bit Name                            Description
    //  0  MO-LR-ShortCircuit-Indicator    This bit, when set, indicates that the MO-LR short circuit feature
    //                                     is used for obtaining location estimate.
    //                                     This bit is applicable only when the message is sent over Lgd interface.
    return 0;
  }

}
