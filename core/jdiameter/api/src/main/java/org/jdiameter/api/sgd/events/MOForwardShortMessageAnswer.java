package org.jdiameter.api.sgd.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 <code>
 3GPP TS 29.338 V19.1.0 (2025-03) § 6.3.2.4

 The MO-Forward-Short-Message-Answer Command (OFA) command, indicated by the Command-Code field set to 8388645
 and the 'R' bit cleared in the Command Flags field, is sent from the SMS-IWMSC to the MME / SGSN
 and it is also sent from the MTC-IWF to the SMS-IWMSC.

 Message Format
 < MO-Forward-Short-Message-Answer > ::= < Diameter Header: 8388645, PXY, 16777313 >
                                  < Session-Id >
                                  [ DRMP ]
                                  [ Vendor-Specific-Application-Id ]
                                  [ Result-Code ]
                                  [ Experimental-Result ]
                                  { Auth-Session-State }
                                  { Origin-Host }
                                 *[ Supported-Features ]
                                  [ SM-Delivery-Failure-Cause ]
                                  [ SM-RP-UI ]
                                  [ External-Identifier ]
                                 *[ AVP ]
                                  [ Failed-AVP ]
                                 *[ Proxy-Info ]
                                 *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface MOForwardShortMessageAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "OFA";
  String _LONG_NAME = "MO-Forward-Short-Message-Answer";

  int code = 8388645;

}
