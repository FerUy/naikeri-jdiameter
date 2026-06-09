package org.mobicents.diameter.stack.functional.s6c.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.events.AlertServiceCentreAnswer;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer;
import org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.s6c.AbstractS6cClient;

import java.util.HashMap;


/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientS6c extends AbstractS6cClient {

  protected boolean receivedSRA;
  protected boolean receivedRDA;
  protected boolean receivedALR;
  protected boolean sentSRR;
  protected boolean sentRDR;
  protected boolean sentALA;

  protected AlertServiceCentreRequest alertServiceCentreRequest;

  public ClientS6c() {
  }

  public boolean isSentSRR() {
    return sentSRR;
  }

  public boolean isReceivedSRA() {
    return receivedSRA;
  }

  public boolean isSentRDR() {
    return sentRDR;
  }

  public boolean isReceivedRDA() {
    return receivedRDA;
  }

  public boolean isReceivedALR() {
    return receivedALR;
  }

  public boolean isSentALA() {
    return sentALA;
  }

  public void sendSendRoutingInfoForSMRequest() throws Exception {
    SendRoutingInfoForSMRequest srr = super.createSRR(super.clientS6cSession);
    super.clientS6cSession.sendSendRoutingInfoForSMRequest(srr);
    this.sentSRR = true;
    Utils.printMessage(log, super.stack.getDictionary(), srr.getMessage(), isSentSRR());
  }

  public void sendReportSMDeliveryStatusRequest() throws Exception {
    ReportSMDeliveryStatusRequest rdr = super.createRDR(super.clientS6cSession);
    super.clientS6cSession.sendReportSMDeliveryStatusRequest(rdr);
    this.sentRDR = true;
    Utils.printMessage(log, super.stack.getDictionary(), rdr.getMessage(), isSentRDR());
  }

  public void sendAlertServiceCentreAnswer() throws Exception {
    if (!receivedALR || alertServiceCentreRequest == null) {
      fail("Did not receive ALR or answer already sent.", null);
      throw new Exception("Did not receive ALR or answer already sent. Request: " + this.alertServiceCentreRequest);
    }

    AlertServiceCentreAnswer ala = super.createALA(alertServiceCentreRequest, 2001);

    this.clientS6cSession.sendAlertServiceCentreAnswer(ala);

    this.sentALA = true;
    alertServiceCentreRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), ala.getMessage(), isSentALA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cClient#doSendRoutingInfoForSMAnswerEvent(
   *    org.jdiameter.api.s6c.ClientS6cSession, org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest, org.jdiameter.api.s6c.events.SendRoutingInfoForSMAnswer)
   */
  @Override
  public void doSendRoutingInfoForSMAnswerEvent(ClientS6cSession session, SendRoutingInfoForSMRequest srr, SendRoutingInfoForSMAnswer sra)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    Utils.printMessage(log, super.stack.getDictionary(), sra.getMessage(), isReceivedSRA());
    if (this.receivedSRA) {
      fail("Received SRA more than once", null);
      return;
    }
    this.receivedSRA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cClient#doReportSMDeliveryStatusAnswerEvent(
   *    org.jdiameter.api.s6c.ClientS6cSession, org.jdiameter.api.s6c.events.ReportSMDeliveryStatusRequest, org.jdiameter.api.s6c.events.ReportSMDeliveryStatusAnswer)
   */
  @Override
  public void doReportSMDeliveryStatusAnswerEvent(ClientS6cSession session, ReportSMDeliveryStatusRequest rdr, ReportSMDeliveryStatusAnswer rda)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    Utils.printMessage(log, super.stack.getDictionary(), rda.getMessage(), isReceivedRDA());
    if (this.receivedRDA) {
      fail("Received RDA more than once", null);
      return;
    }
    this.receivedRDA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.s6c.AbstractS6cClient#doAlertServiceCentreRequestEvent(
   *    org.jdiameter.api.s6c.ClientS6cSession, org.jdiameter.api.s6c.events.AlertServiceCentreRequest)
   */
  @Override
  public void doAlertServiceCentreRequestEvent(ClientS6cSession session, AlertServiceCentreRequest alr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    if (this.receivedALR) {
      fail("Received ALR more than once", null);
      return;
    }
    this.receivedALR = true;
    this.alertServiceCentreRequest = alr;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != AlertServiceCentreRequest.code) {
      fail("Received Request with code not used by S6c!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientS6cSession.getSessionId().equals(request.getSessionId())) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      super.clientS6cSession.release();
      try {
        super.clientS6cSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientS6cSession.class, (Object) null);
        ((NetworkReqListener) this.clientS6cSession).processRequest(request);
      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
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
  protected int getSM_RP_MTI() {
    // [ SM-RP-MTI ]
    // The SM-RP-MTI AVP is of type Enumerated and shall contain the
    // RP-Message Type Indicator of the Short Message.
    // The following values are defined:
    // SM_DELIVER (0)
    // SM_STATUS_REPORT (1)
    return 0;
  }

  @Override
  protected byte[] getSM_RP_SMEA() {
    // [ SM-RP-SMEA ]
    // The SM-RP-SMEA AVP is of type OctetString and shall contain the RP-Originating SME-address
    // of the Short Message Entity that has originated the SM. It shall be formatted according
    // to the formatting rules of the address fields described in 3GPP TS 23.040.

    // From jSS7 MAP load example:
    // TypeOfNumber ton = TypeOfNumber.InternationalNumber;
    // NumberingPlanIdentification npi = NumberingPlanIdentification.ISDNTelephoneNumberingPlan;
    // String addressValue = "491710460020";
    // AddressField addressField = new AddressFieldImpl(ton, npi, addressValue);
    // SM_RP_SMEA sM_RP_SMEA = new SM_RP_SMEAImpl(addressField);
    return new byte[] { (byte) 0x94, 0x71, 0x01, 0x64, 0x00, 0x02};
  }

  @Override
  protected long getSRRFlags() {
    // [ SRR-Flags ]
    // The SRR-Flags AVP is of type Unsigned32, and it shall contain a bit mask. The meaning of the bits shall be as defined in table 5.3.3.4./1:
    // Table 5.3.3.4/1: SRR-Flags
    //Bit Name                      Description
    // 0  GPRS-Indicator            This bit shall be set if the SMS-GMSC supports receiving of two serving nodes addresses from the HSS.
    // 1  SM-RP-PRI                 This bit shall be set if the delivery of the short message shall be attempted when
    //                              a service centre address is already contained in the Message Waiting Data file
    // 2  Single-Attempt-Delivery   This bit if set indicates that only one delivery attempt shall be performed for this particular SM.
    // NOTE 1: Bits not defined in this table shall be cleared by the sending entity and discarded by the receiving entity.
    return 7L;
  }

  @Override
  protected int getSMDeliveryNotIntended() {
    // [ SM-Delivery-Not-Intended ]
    // The SM-Delivery-Not-Intended AVP is of type Enumerated and shall indicate by its presence that
    // delivery of a short message is not intended.
    // It further indicates whether only IMSI or only MCC+MNC with the following values:
    //  ONLY_IMSI_REQUESTED (0),
    //  ONLY_MCC_MNC_REQUESTED (1).
    return 0;
  }

  /** { User-Identifier } **/
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
    return new byte[] {114, 2, (byte) 233, (byte) 140};
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
  protected long getRDRFlags() {
    // [ RDR-Flags ]
    // The RDR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 5.3.3.21/1:
    // Table 5.3.3.21/1: RDR-Flags
    // Bit Name                     Description
    //  0  Single-Attempt-Delivery  This bit if set indicates that only one delivery attempt shall be performed for this particular SM.
    // NOTE 1: Bits not defined in this table shall be cleared by the sending entity and discarded by the receiving entity.
    return 1L;
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
