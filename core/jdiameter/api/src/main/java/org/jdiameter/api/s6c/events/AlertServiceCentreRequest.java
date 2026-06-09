package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.5

  The Alert-Service-Centre-Request (ALR) command, indicated by the Command-Code field set to 8388648
  and the "R" bit set in the Command Flags field,
  is sent from the HSS to the SMS-IWMSC and from the MME or SGSN to the SMS-GMSC (possibly via an SMS Router).

  Message Format
  < Alert-Service-Centre-Request > ::= < Diameter Header: 8388648, REQ, PXY, 16777312 >
                                < Session-Id >
                                [ DRMP ]
                                [ Vendor-Specific-Application-Id ]
                                { Auth-Session-State }
                                { Origin-Host }
                                { Origin-Realm }
                                [ Destination-Host ]
                                { Destination-Realm }
                                { SC-Address }
                                { User-Identifier }
                                [ SMSMI-Correlation-ID ]
                                [ Maximum-UE-Availability-Time ]
                                [ SMS-GMSC-Alert-Event ]
                                [ Serving-Node ]
                               *[ Supported-Features ]
                               *[ AVP ]
                               *[ Proxy-Info ]
                               *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface AlertServiceCentreRequest extends AppRequestEvent {

  String _SHORT_NAME = "ALR";
  String _LONG_NAME = "Alert-Service-Centre-Request";

  int code = 8388648;
}
