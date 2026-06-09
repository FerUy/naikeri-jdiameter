package org.mobicents.diameter.stack.functional.slh.base;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.slh.AbstractSLhClient;

import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ClientSLh extends AbstractSLhClient {

  protected boolean receivedRIA;
  protected boolean sentRIR;

  public ClientSLh() {
  }

  public boolean isReceivedRIA() {
    return receivedRIA;
  }

  public boolean isSentRIR() {
    return sentRIR;
  }

  public void sendLCSRoutingInfoRequest() throws Exception {
    LCSRoutingInfoRequest rir = super.createRIR(super.clientSLhSession);
    super.clientSLhSession.sendLCSRoutingInfoRequest(rir);
    this.sentRIR = true;
    Utils.printMessage(log, super.stack.getDictionary(), rir.getMessage(), isSentRIR());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.slh.AbstractSLhClient#doLCSRoutingInfoAnswerEvent(
   *    org.jdiameter.api.slh.ClientSLhSession, org.jdiameter.api.slh.events.LCSRoutingInfoRequest, org.jdiameter.api.slh.events.LCSRoutingInfoAnswer)
   */
  @Override
  public void doLCSRoutingInfoAnswerEvent(ClientSLhSession session, LCSRoutingInfoRequest request, LCSRoutingInfoAnswer answer)
    throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    Utils.printMessage(log, super.stack.getDictionary(), answer.getMessage(), isReceivedRIA());

    if (this.receivedRIA) {
      fail("Received RIA more than once", null);
      return;
    }
    this.receivedRIA = true;
  }

  /*** Attributes for LCS-Routing-Info-Request (RIR)  ***/

  @Override
  protected String getUserName() {
    return "748039876543210";
  }

  @Override
  protected byte[] getMSISDN() {
    return parseTBCD("59899077937");
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

}