package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.3

  The Send-Routing-Info-for-SM-Request (SRR) command, indicated by the Command-Code field set to 8388647
  and the "R" bit set in the Command Flags field, is sent from SMS-GMSC to HSS or SMS Router or from SMS Router to HSS

  Message Format
  < Send-Routing-Info-for-SM-Request > ::= < Diameter Header: 8388647, REQ, PXY, 16777312 >
                                    < Session-Id >
                                    [ DRMP ]
                                    [ Vendor-Specific-Application-Id ]
                                    { Auth-Session-State }
                                    { Origin-Host }
                                    { Origin-Realm }
                                    [ Destination-Host ]
                                    { Destination-Realm }
                                    [ MSISDN ]
                                    [ User-Name ]
                                    [ SMSMI-Correlation-ID ]
                                   *[ Supported-Features ]
                                    [ SC-Address ]
                                    [ SM-RP-MTI ]
                                    [ SM-RP-SMEA ]
                                    [ SRR-Flags ]
                                    [ SM-Delivery-Not-Intended ]
                                   *[ AVP ]
                                   *[ Proxy-Info ]
                                   *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface SendRoutingInfoForSMRequest extends AppRequestEvent {

  String _SHORT_NAME = "SRR";
  String _LONG_NAME = "Send-Routing-Info-for-SM-Request";

  int code = 8388647;
}
