package org.mobicents.diameter.stack.functional.s6c.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.s6c.AbstractS6cServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ServerS6c extends AbstractS6cServer {

  protected boolean receivedSRR;
  protected boolean receivedRDR;
  protected boolean receivedALA;
  protected boolean sentSRA;
  protected boolean sentRDA;
  protected boolean sentALR;

  protected SendRoutingInfoForSMRequest sendRoutingInfoForSMRequest;
  protected ReportSMDeliveryStatusRequest reportSMDeliveryStatusRequest;

  public ServerS6c() {
  }

  public boolean isReceivedSRR() {
    return receivedSRR;
  }

  public boolean isReceivedRDR() {
    return receivedRDR;
  }

  public boolean isReceivedALA() {
    return receivedALA;
  }

  public boolean isSentSRA() {
    return sentSRA;
  }

  public boolean isSentRDA() {
    return sentRDA;
  }

  public boolean isSentALR() {
    return sentALR;
  }

  public void sendSendRoutingInfoForSMAnswer() throws Exception {
    if (!receivedSRR || sendRoutingInfoForSMRequest == null) {
      fail("Did not receive SRR or answer already sent.", null);
      throw new Exception("Did not receive SRR or answer already sent. Request: " + this.sendRoutingInfoForSMRequest);
    }

    SendRoutingInfoForSMAnswer sra = super.createSRA(sendRoutingInfoForSMRequest, 2001);

    super.serverS6cSession.sendSendRoutingInfoForSMAnswer(sra);

    this.sentSRA = true;
    sendRoutingInfoForSMRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), sra.getMessage(), isSentSRA());
  }

  public void sendReportSMDeliveryStatusAnswer() throws Exception {
    if (!receivedRDR || reportSMDeliveryStatusRequest == null) {
      fail("Did not receive RDR or answer already sent.", null);
      throw new Exception("Did not receive RDR or answer already sent. Request: " + this.reportSMDeliveryStatusRequest);
    }

    ReportSMDeliveryStatusAnswer rda = super.createRDA(reportSMDeliveryStatusRequest, 2001);

    super.serverS6cSession.sendReportSMDeliveryStatusAnswer(rda);

    this.sentRDA = true;
    reportSMDeliveryStatusRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), rda.getMessage(), isSentRDA());
  }

  public void sendAlertServiceCentreRequest() throws Exception {
    try {
      super.serverS6cSession = this.sessionFactory.getNewAppSession(this.sessionFactory.getSessionId("xx-S6c-TESTxx"), getApplicationId(),
          ServerS6cSession.class, (Object) null);
    } catch (Exception e) {
      e.printStackTrace();
      fail(null, e);
    }
    AlertServiceCentreRequest alr = super.createALR(super.serverS6cSession);
    this.serverS6cSession.sendAlertServiceCentreRequest(alr);
    this.sentALR = true;
    Utils.printMessage(log, super.stack.getDictionary(), alr.getMessage(), isSentALR());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cServer#doSendRoutingInfoForSMRequestEvent(
   *    org.jdiameter.api.s6c.ServerS6cSession, org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest)
   */
  @Override
  public void doSendRoutingInfoForSMRequestEvent(ServerS6cSession session, SendRoutingInfoForSMRequest srr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    if (this.receivedSRR) {
      fail("Received SRR more than once", null);
      return;
    }
    this.receivedSRR = true;
    this.sendRoutingInfoForSMRequest = srr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cServer#doReportSMDeliveryStatusRequestEvent(
   *    org.jdiameter.api.s6c.ServerS6cSession, org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest)
   */
  @Override
  public void doReportSMDeliveryStatusRequestEvent(ServerS6cSession session, ReportSMDeliveryStatusRequest rdr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {
    if (this.receivedRDR) {
      fail("Received RDR more than once", null);
      return;
    }
    this.receivedRDR = true;
    this.reportSMDeliveryStatusRequest = rdr;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cServer#doAlertServiceCentreAnswerEvent(
   *    org.jdiameter.api.s6c.ServerS6cSession, org.jdiameter.api.s6c.events.AlertServiceCentreRequest, org.jdiameter.api.s6c.events.AlertServiceCentreAnswer)
   */
  @Override
  public void doAlertServiceCentreAnswerEvent(ServerS6cSession session, AlertServiceCentreRequest alr, AlertServiceCentreAnswer ala)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    Utils.printMessage(log, super.stack.getDictionary(), ala.getMessage(), isReceivedALA());
    if (this.receivedALA) {
      fail("Received ALA more than once", null);
      return;
    }
    this.receivedALA = true;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != SendRoutingInfoForSMRequest.code && code != ReportSMDeliveryStatusRequest.code) {
      fail("Received Request with code not used by S6c!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverS6cSession != null) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      try {
        super.serverS6cSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerS6cSession.class, (Object) null);
        ((NetworkReqListener) this.serverS6cSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }

  @Override
  protected String getUserName() {
    // [ User-Name ]
    return "748039876543210";
  }

  /** [ Serving-Node ] **/
  @Override
  protected byte[] getSGSNNumber() {
    // The SGSN-Number AVP is of type OctetString, and it shall contain the ISDN number of the SGSN.
    // For further details on the definition of this AVP, see 3GPP TS 23.003.
    // This AVP contains an SGSN-Number in international number format as described in ITU-T Rec E.164 [41]
    // and shall be encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000208");
  }

  @Override
  protected String getSGSNName() {
    // The SGSN-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving SGSN.
    return "sgsn1B34.mnc001.mcc748.gprs";
  }

  @Override
  protected String getSGSNRealm() {
    // The SGSN-Realm AVP is of type DiameterIdentity and it shall contain the Diameter Realm Identity of the serving SGSN.
    return "mnc001.mcc748.gprs";
  }

  @Override
  protected String getMMEName() {
    // The MME-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving MME.
    return "mmec03.mmegi3000.mme.epc.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected String getMMERealm() {
    // The MME-Realm AVP is of type DiameterIdentity, and it shall contain the Diameter Realm Identity of the serving MME.
    return "epc.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected byte[] getMSCNumber() {
    // The MSC-Number AVP is of type OctetString, and it shall contain the ISDN number of the serving MSC or MSC server
    // in international number format as described in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings
    return null;
  }

  @Override
  protected String get3GPPAAAServerName() {
    // The 3GPP-AAA-Server-Name AVP is of type DiameterIdentity, and defines the Diameter address of the 3GPP AAA Server node.
    return null;
  }

  @Override
  protected long getLCSCapabilitiesSets() {
    // The LCS-Capabilities-Sets AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in 3GPP 29.002.
    return -1;
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

  @Override
  protected byte[] getAdditionalSGSNNumber() {
    return null;
  }

  @Override
  protected String getAdditionalSGSNName() {
    return null;
  }

  @Override
  protected String getAdditionalSGSNRealm() {
    return null;
  }

  @Override
  protected String getAdditionalMMEName() {
    return null;
  }

  @Override
  protected String getAdditionalMMERealm() {
    return null;
  }

  @Override
  protected byte[] getAdditionalMSCNumber() {
    // The MSC-Number AVP is of type OctetString, and it shall contain the ISDN number of the serving MSC or MSC server
    // in international number format as described in ITU-T Rec E.164 and shall be encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings
    return parseTBCD("59899000007");
  }

  @Override
  protected String getAdditional3GPPAAAServerName() {
    // The 3GPP-AAA-Server-Name AVP is of type DiameterIdentity, and defines the Diameter address of the 3GPP AAA Server node.
    return "aaa3.mnc002.mcc748.3gppnetwork.org";
  }

  protected long getAdditionalLCSCapabilitiesSets() {
    // The LCS-Capabilities-Sets AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in 3GPP 29.002.
    return 4L;
  }

  @Override
  protected InetAddress getAdditionalGMLCAddress() {
    return null;
  }

  /** [ SMSF-3GPP-Address ] **/
  @Override
  protected byte[] getSmsf3gppNumber() {
    // The SMSF-3GPP-Number AVP is of type OctetString, and it shall contain the ISDN number of the SMSF registered for 3GPP access.
    // For further details on the definition of this AVP, see 3GPP TS 23.003.
    // This AVP contains an SMSF-3GPP-Number in international number format as described in ITU-T Rec E.164
    // and shall be encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000510");
  }

  @Override
  protected String getSmsf3gppName() {
    // The SMSF-3GPP-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving SMSF registered for 3GPP access.
    return "smsf1.mnc002.mcc748.3gppnetwork.org";
  }

  protected String getSmsf3gppRealm() {
    // The SMSF-3GPP-Realm AVP is of type DiameterIdentity, and it shall contain the Diameter Realm Identity of the serving SMSF registered for 3GPP access.
    return "mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected int getSmsf3gppSbiSupportIndicator() {
    // The SMSF-3GPP-SBI-Support-Indicator AVP is of type Enumerated and shall indicate
    // whether the SMSF support service-based-interface or not, with the following values:
    // NOT_SUPPORT_SBI (0)
    // SUPPORT_SBI (1)
    return 1;
  }

  @Override
  protected byte[] getSmsfNon3gppNumber() {
    return null;
  }

  @Override
  protected String getSmsfNon3gppName() {
    return "smsf1";
  }

  @Override
  protected String getSmsfNon3gppRealm() {
    return null;
  }

  @Override
  protected int getSmsfNon3gppSbiSupportIndicator() {
    return 0;
  }

  @Override
  protected byte[] getLMSI() {
    // The LMSI AVP is of type OctetString, and it shall contain the Local Mobile Station Identity (LMSI) allocated by the VLR,
    // as defined in 3GPP TS 23.003 .
    return new byte[] {114, 4, (byte) 233, (byte) 141};
  }

  /** [ User-Identifier ] **/
  @Override
  protected byte[] getMSISDN() {
    // [ MSISDN ]
    return parseTBCD("59899077937");
  }

  @Override
  protected String getExternalIdentifier() {
    // The External-Identifier AVP is of type UTF8String.
    // See 3GPP TS 23.003 for the definition and formatting of the External Identifier.
    return "748039876543210@restcomm.org";
  }

  @Override
  protected long getMWDStatus() {
    // The MWD-Status AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 5.3.3.8/1:
    // Table 5.3.3.8/1: MWD Status
    // bit  name                    Description
    //  0   SC-Address Not included This bit when set shall indicate that the SC Address has not been added to the Message Waiting Data in the HSS.
    //  1   MNRF-Set                This bit, when set, shall indicate that the MNRF flag is set in the HSS
    //  2   MCEF-Set                This bit, when set, shall indicate that the MCEF flag is set in the HSS.
    //  3   MNRG-Set                This bit, when set, shall indicate that the MNRG flag is set in the HSS
    //  4   MNR5G-Set               This bit, when set, shall indicate that the HSS/UDM is waiting for a reachability
    //                              notification/registration from 5G serving nodes.
    // Bits not defined in this table shall be cleared by the sending HSS and discarded by the receiving MME.
    return 2L;
  }

  @Override
  protected long getMMEAbsentUserDiagnosticSM() {
    // The MME-Absent-User-Diagnostic-SM AVP is of type Unsigned32 and shall indicate the diagnostic explaining
    // the absence of the user given by the MME. The values are defined in 3GPP TS 23.040 [3] clause 3.3.2.
    return 1;
  }

  @Override
  protected long getMSCAbsentUserDiagnosticSM() {
    // The MSC-Absent-User-Diagnostic-SM AVP is of type Unsigned32 and shall indicate the diagnostic explaining
    // the absence of the user given by the MSC. The values are defined in 3GPP TS 23.040 [3] clause 3.3.2.
    return 0;
  }

  @Override
  protected long getSGSNAbsentUserDiagnosticSM() {
    // The SGSN-Absent-User-Diagnostic-SM AVP is of type Unsigned32 and shall indicate the diagnostic explaining
    // the absence of the user given by the SGSN. The values are defined in 3GPP TS 23.040 [3] clause 3.3.2.
    return 10;
  }

  @Override
  protected long getSMSF3gppAbsentUserDiagnosticSM() {
    return 12;
  }

  @Override
  protected long getSMSFNon3gppAbsentUserDiagnosticSM() {
    return 13;
  }

  /** [ SMSMI-Correlation-ID ] **/
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

  @Override
  protected Time getMaximumUEAvailabilityTime() {
    // The Maximum-UE-Availability-Time is of type Time and in shall contain the timestamp (in UTC)
    // until which a UE using a power saving mechanism (such as extended idle mode DRX)
    // is expected to be reachable for SM Delivery
    return null;
  }

  @Override
  protected long getSMSGMSCAlertEvent() {
    // The SMS-GMSC-Alert-Event AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 5.3.3.23/1:
    // Table 5.3.3.23/1: SMS-GMSC-Alert-Event
    // Bit  Name                       Description
    //  0   UE-Available-For-MT-SMS	   This bit, when set, shall indicate that the UE is now available for MT SMS
    //  1   UE-Under-New-Serving-Node  This bit, when set, shall indicate that the UE has moved under the coverage of another MME or SGSN.
    // Bits not defined in this table shall be cleared by the sending entity and discarded by the receiving entity.
    return 1;
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

}
