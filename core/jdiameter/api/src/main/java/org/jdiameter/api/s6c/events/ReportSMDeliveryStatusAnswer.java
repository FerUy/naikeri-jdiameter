package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.8

  The Report-SM-Delivery-Status-Answer (RDA) command, indicated by the Command-Code field set to 8388649
  and the 'R' bit cleared in the Command Flags field, is sent from HSS to SMS-GMSC or IP-SM-GW.

  Message Format
  < Report-SM-Delivery-Status-Answer > ::=< Diameter Header: 8388649, PXY, 16777312 >
                                    < Session-Id >
                                    [ DRMP ]
                                    [ Vendor-Specific-Application-Id ]
                                    [ Result-Code ]
                                    [ Experimental-Result ]
                                    { Auth-Session-State }
                                    { Origin-Host }
                                    { Origin-Realm }
                                   *[ Supported-Features ]
                                    [ User-Identifier ]
                                   *[ AVP ]
                                    [ Failed-AVP ]
                                   *[ Proxy-Info ]
                                   *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface ReportSMDeliveryStatusAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RDA";
  String _LONG_NAME = "Report-SM-Delivery-Status-Answer";

  int code = 8388649;
}
