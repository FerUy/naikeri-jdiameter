package org.jdiameter.api.sgd.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 <code>
 3GPP TS 29.338 V19.1.0 (2025-03) § 6.3.2.5

 The MT-Forward-Short-Message-Request (TFR) command, indicated by the Command-Code field set to 8388646
 and the "R" bit set in the Command Flags field, is sent from the SMS-GMSC to the MME / SGSN
 (transiting an SMS Router, if present).

 Message Format
 < MT-Forward-Short-Message-Request > ::= < Diameter Header: 8388646, REQ, PXY, 16777313 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   { Destination-Host }
                                   { Destination-Realm }
                                   { User-Name }
                                  *[ Supported-Features ]
                                  *[ SMSMI-Correlation-ID ]
                                   { SC-Address }
                                   { SM-RP-UI }
                                   [ MME-Number-for-MT-SMS ]
                                   [ SGSN-Number ]
                                   [ TFR-Flags ]
                                   [ SM-Delivery-Timer ]
                                   [ SM-Delivery-Start-Time ]
                                   [ Maximum-Retransmission-Time ]
                                   [ SMS-GMSC-Address ]
                                   [ MPS-Priority ]
                                  *[ AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface MTForwardShortMessageRequest extends AppRequestEvent {

  String _SHORT_NAME = "TFR";
  String _LONG_NAME = "MT-Forward-Short-Message-Request";

  int code = 8388646;
}
