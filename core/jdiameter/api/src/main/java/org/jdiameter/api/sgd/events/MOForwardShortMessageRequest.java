package org.jdiameter.api.sgd.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 <code>
 3GPP TS 29.338 V19.1.0 (2025-03) § 6.3.2.3

 The MO-Forward-Short-Message-Request (OFR) command, indicated by the Command-Code field set to 8388645
 and the "R" bit set in the Command Flags field, is sent from the MME / SGSN to the SMS-IWMSC
 and it is also sent from the SMS-IWMSC to the MTC-IWF.

 Message Format
 < MO-Forward-Short-Message-Request > ::= < Diameter Header: 8388645, REQ, PXY, 16777313 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ Destination-Host ]
                                   { Destination-Realm }
                                   { SC-Address }
                                   [ OFR-Flags ]
                                  *[ Supported-Features ]
                                   { User-Identifier }
                                   [ EPS-Location-Information ]
                                   [ NR-Cell-Global-Identity ]
                                   { SM-RP-UI }
                                   [ SMSMI-Correlation-ID ]
                                   [ SM-Delivery-Outcome ]
                                   [ MPS-Priority ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface MOForwardShortMessageRequest extends AppRequestEvent {

  String _SHORT_NAME = "OFR";
  String _LONG_NAME = "MO-Forward-Short-Message-Request";

  int code = 8388645;
}
