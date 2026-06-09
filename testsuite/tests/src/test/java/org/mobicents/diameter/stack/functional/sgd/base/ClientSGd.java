package org.mobicents.diameter.stack.functional.sgd.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.api.sgd.events.MOForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer;
import org.jdiameter.api.sgd.events.MTForwardShortMessageRequest;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.sgd.AbstractSGdClient;

import java.util.Date;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientSGd extends AbstractSGdClient {

  protected boolean receivedTFA;
  protected boolean receivedOFR;
  protected boolean sentTFR;
  protected boolean sentOFA;

  protected MOForwardShortMessageRequest moForwardShortMessageRequest;

  public ClientSGd() {
  }

  public boolean isReceivedTFA() {
    return receivedTFA;
  }

  public boolean isReceivedOFR() {
    return receivedOFR;
  }

  public boolean isSentTFR() {
    return sentTFR;
  }

  public boolean isSentOFA() {
    return sentOFA;
  }

  public void sendMTForwardShortMessageRequest() throws Exception {
    MTForwardShortMessageRequest tfr = super.createTFR(super.clientSGdSession);
    super.clientSGdSession.sendMTForwardShortMessageRequest(tfr);
    this.sentTFR = true;
    Utils.printMessage(log, super.stack.getDictionary(), tfr.getMessage(), isSentTFR());
  }

  public void sendMOForwardShortMessageAnswer() throws Exception {
    if (!receivedOFR || moForwardShortMessageRequest == null) {
      fail("Did not receive OFR or answer already sent.", null);
      throw new Exception("Did not receive OFR or answer already sent. Request: " + this.moForwardShortMessageRequest);
    }

    MOForwardShortMessageAnswer ofa = super.createOFA(moForwardShortMessageRequest, 2001);

    this.clientSGdSession.sendMOForwardShortMessageAnswer(ofa);

    this.sentOFA = true;
    moForwardShortMessageRequest = null;
    Utils.printMessage(log, super.stack.getDictionary(), ofa.getMessage(), isSentOFA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.sgd.AbstractSGdClient#doSendRoutingInfoForSMAnswerEvent(
   *    org.jdiameter.api.sgd.ClientSGdSession, org.jdiameter.api.sgd.events.MTForwardShortMessageRequest, org.jdiameter.api.sgd.events.MTForwardShortMessageAnswer)
   */
  public void doMTForwardShortMessageAnswerEvent(ClientSGdSession session, MTForwardShortMessageRequest tfr, MTForwardShortMessageAnswer tfa)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    Utils.printMessage(log, super.stack.getDictionary(), tfa.getMessage(), isReceivedTFA());
    if (this.isReceivedTFA()) {
      fail("Received TFA more than once", null);
      return;
    }
    this.receivedTFA = true;
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.sgd.AbstractSGdClient#doMOForwardShortMessageRequestEvent(
   *    org.jdiameter.api.sgd.ClientSGdSession, org.jdiameter.api.sgd.events.MOForwardShortMessageRequest)
   */
  public void doMOForwardShortMessageRequestEvent(ClientSGdSession session, MOForwardShortMessageRequest ofr)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException {

    if (this.receivedOFR) {
      fail("Received OFR more than once", null);
      return;
    }
    this.receivedOFR = true;
    this.moForwardShortMessageRequest = ofr;
  }

  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != moForwardShortMessageRequest.code) {
      fail("Received Request with code not used by S6c!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.clientSGdSession.getSessionId().equals(request.getSessionId())) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      super.clientSGdSession.release();
      try {
        super.clientSGdSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ClientSGdSession.class, (Object) null);
        ((NetworkReqListener) this.clientSGdSession).processRequest(request);
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
  protected byte[] getSmRpUi() {
    // { SM-RP-UI }
    // The SM-RP-UI is of type OctetString, and it shall contain a short message transfer protocol data unit
    // (TPDU) which is defined in 3GPP TS 23.040 and represents the user data field
    // carried by the short message service relay sub-layer protocol.
    // Its maximum length is of 200 octets
    return new byte[] { (byte) 0xd3, (byte) 0xe6, 0x14, (byte) 0xc4, 0x7e, (byte) 0x87, (byte) 0xc9,
        0x20, 0x7a, 0x79, 0x4e, 0x07};
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
  protected long getTFRFlags() {
    // The TFR-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 6.3.3.4/1:
    // Table 6.3.3.4/1: TFR-Flags
    // Bit Name                   Description
    //  0  More-Messages-To-Send  This bit, when set, shall indicate that the service centre has more short messages to send.
    // NOTE 1: Bits not defined in this table shall be cleared by the sending entity and discarded by the receiving entity.
    return 1;
  }

  @Override
  protected int getSMDeliveryTimer() {
    // The SM-Delivery-Timer is of type Integer, and it shall contain the value in seconds of the timer for SM Delivery.
    return 600;
  }

  @Override
  protected Date getSMDeliveryStartTime() {
    // The SM-Delivery-Start-Time is of type Time and in shall contain the timestamp (in UTC)
    // at which the SM Delivery Supervision Timer was started
    return new Date();
  }

  @Override
  protected Date getMaximumRetransmissionTime() {
    // The Maximum-Retransmission-Time is of type Time and in shall contain the
    // maximum retransmission time (in UTC) until which the SMS-GMSC is capable to retransmit
    // the MT Short Message.
    return new Date();
  }

  @Override
  protected byte[] getSmsGMSCAddress() {
    // The SMS-GMSC-Address AVP is of type OctetString, and it shall contain the E.164 number
    // of the SMS-GMSC or SMS Router, in international number format as described in
    // ITU-T Recommendation E.164 and encoded as a TBCD-string.
    // See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000123");
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
    return 5;
  }

  @Override
  protected byte[] getSMDiagnosticInfo() {
    // The SM-Diagnostic-Info AVP is of type OctetString, and it shall contain
    // complementary information associated to the SM Delivery Failure cause.
    return new byte[] { 0x01 };
  }

  @Override
  protected String getExternalIdentifier() {
    // The External-Identifier AVP is of type UTF8String.
    // See 3GPP TS 23.003 for the definition and formatting of the External Identifier.
    return "748039876543210@restcomm.org";
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
