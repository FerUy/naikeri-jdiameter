package org.mobicents.diameter.stack.functional.slh.base;

import org.jdiameter.api.Answer;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.api.slh.events.LCSRoutingInfoAnswer;
import org.mobicents.diameter.stack.functional.Utils;
import org.mobicents.diameter.stack.functional.slh.AbstractSLhServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.mobicents.diameter.stack.TBCDUtil.parseTBCD;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class ServerSLh extends AbstractSLhServer {

  protected boolean receivedRIR;
  protected boolean sentRIA;

  protected LCSRoutingInfoRequest request;

  public ServerSLh() {
  }

  protected boolean isReceivedRIR() {
    return receivedRIR;
  }

  protected boolean isSentRIA() {
    return sentRIA;
  }

  public void sendLCSRoutingInfoAnswer() throws Exception {
    if (!receivedRIR || request == null) {
      fail("Did not receive RIR or answer already sent.", null);
      throw new Exception("Did not receive RIR or answer already sent. Request: " + this.request);
    }

    LCSRoutingInfoAnswer ria = super.createRIA(request, 2001);

    super.serverSLhSession.sendLCSRoutingInfoAnswer(ria);

    this.sentRIA = true;
    request = null;
    Utils.printMessage(log, super.stack.getDictionary(), ria.getMessage(), isSentRIA());
  }

  /* (non-Javadoc)
   * @see org.mobicents.diameter.stack.functional.TBase#processRequest(org.jdiameter.api.Request)
   */
  @Override
  public Answer processRequest(Request request) {
    int code = request.getCommandCode();
    if (code != LCSRoutingInfoRequest.code) {
      fail("Received Request with code not used by SLh!. Code[" + request.getCommandCode() + "]", null);
      return null;
    }
    if (super.serverSLhSession != null) {
      // do fail?
      fail("Received Request in base listener, not in app specific!" + code, null);
    } else {
      try {

        super.serverSLhSession = this.sessionFactory.getNewAppSession(request.getSessionId(), getApplicationId(), ServerSLhSession.class, (Object) null);
        ((NetworkReqListener) this.serverSLhSession).processRequest(request);

      } catch (Exception e) {
        e.printStackTrace();
        fail(null, e);
      }
    }
    return null;
  }
  @Override
  public void doLCSRoutingInfoRequestEvent(ServerSLhSession session, LCSRoutingInfoRequest request)
    throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    if (this.receivedRIR) {
      fail("Received RIR more than once", null);
      return;
    }
    this.receivedRIR = true;
    this.request = request;
  }


  /** Attributes for LCS-Routing-Info-Answer (RIA) **/

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
  protected byte[] getLMSI() {
    // The LMSI AVP is of type OctetString, and it shall contain the Local Mobile Station Identity (LMSI) allocated by the VLR,
    // as defined in 3GPP TS 23.003 .
    return new byte[] {114, 4, (byte) 233, (byte) 141};
  }

  /** [ Serving-Node ] **/
  @Override
  protected byte[] getSGSNNumber() {
    // sent in Additional-Serving-Node AVP
    return null;
  }

  @Override
  protected String getSGSNName() {
    // sent in Additional-Serving-Node AVP
    return null;
  }

  @Override
  protected String getSGSNRealm() {
    // sent in Additional-Serving-Node AVP
    return null;
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
  protected byte[] getAdditionalSGSNNumber() {
    // The SGSN-Number AVP is of type OctetString, and it shall contain the ISDN number of the SGSN.
    // For further details on the definition of this AVP, see 3GPP TS 23.003.
    // This AVP contains an SGSN-Number in international number format as described in ITU-T Rec E.164 [41]
    // and shall be encoded as a TBCD-string. See 3GPP TS 29.002 for encoding of TBCD-strings.
    // This AVP shall not include leading indicators for the nature of address and the numbering plan;
    // it shall contain only the TBCD-encoded digits of the address
    return parseTBCD("59899000208");
  }

  @Override
  protected String getAdditionalSGSNName() {
    // The SGSN-Name AVP is of type DiameterIdentity, and it shall contain the Diameter identity of the serving SGSN.
    return "sgsn1B34.mnc001.mcc748.gprs";
  }

  @Override
  protected String getAdditionalSGSNRealm() {
    // The SGSN-Realm AVP is of type DiameterIdentity, and it shall contain the Diameter Realm Identity of the serving SGSN.
    return "mnc001.mcc748.gprs";
  }

  @Override
  protected String getAdditionalMMEName() {
    // sent in Serving-Node AVP
    return null;
  }

  @Override
  protected String getAdditionalMMERealm() {
    // sent in Serving-Node AVP
    return null;
  }

  @Override
  protected byte[] getAdditionalMSCNumber() {
    return parseTBCD("59899001210");
  }

  @Override
  protected String getAdditional3GPPAAAServerName() {
    // The 3GPP-AAA-Server-Name AVP is of type DiameterIdentity, and defines the Diameter address of the 3GPP AAA Server node.
    return "aaa3.mnc002.mcc748.3gppnetwork.org";
  }

  @Override
  protected long getAdditionalLCSCapabilitiesSets() {
    // The LCS-Capabilities-Sets AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in 3GPP 29.002.
    return 3L;
  }

  @Override
  protected java.net.InetAddress getAdditionalGMLCAddress() {
    // The GMLC-Address AVP is of type Address and shall contain the IPv4 or IPv6 address of H-GMLC
    // or the V-GMLC associated with the serving node
    return null;
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
  protected java.net.InetAddress getPPRAddress() {
    // The PPR-Address AVP is of type Address and contains
    // the IPv4 or IPv6 address of the Privacy Profile Register for the targeted user.
    try {
      return InetAddress.getLocalHost();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected long getRIAFLags() {
    // The RIA-Flags AVP is of type Unsigned32, and it shall contain a bit mask.
    // The meaning of the bits shall be as defined in table 6.4.15/1:
    // Table 6.4.15/1: RIA-Flags
    // Bit	Event Type                                        Description
    //  0   Combined-MME/SGSN-Supporting-Optimized-LCS-Proc   This bit, when set, indicates that the UE
    //                                                        is served by the MME and the SGSN parts
    //                                                        of the same combined MME/SGSN and this combined MME/SGSN
    //                                                        supports the optimized LCS procedure.
    return 1;
  }

}