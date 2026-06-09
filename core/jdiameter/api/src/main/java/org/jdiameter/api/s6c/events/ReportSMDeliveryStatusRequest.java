package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.7

  The Report-SM-Delivery-Status-Request (RDR) command, indicated by the Command-Code field set to 8388649
  and the "R" bit set in the Command Flags field, is sent from SMS-GMSC or IP-SM-GW to HSS.

  Message Format:
  < Report-SM-Delivery-Status-Request > ::= < Diameter Header: 8388649, REQ, PXY, 16777312 >
                                     < Session-Id >
                                     [ DRMP ]
                                     [ Vendor-Specific-Application-Id ]
                                     { Auth-Session-State }
                                     { Origin-Host }
                                     { Origin-Realm }
                                     [ Destination-Host ]
                                     { Destination-Realm }
                                    *[ Supported-Features ]
                                     { User-Identifier }
                                     [ SMSMI-Correlation-ID ]
                                     { SC-Address }
                                     { SM-Delivery-Outcome }
                                     [ RDR-Flags ]
                                    *[ AVP ]
                                    *[ Proxy-Info ]
                                    *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface ReportSMDeliveryStatusRequest extends AppRequestEvent {

  String _SHORT_NAME = "RDR";
  String _LONG_NAME = "Report-SM-Delivery-Status-Request";

  int code = 8388649;
}
