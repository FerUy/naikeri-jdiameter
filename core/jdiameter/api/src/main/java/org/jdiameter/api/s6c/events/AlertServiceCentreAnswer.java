package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.6

  The Alert-Service-Centre-Answer (ALA) command, indicated by the Command-Code field set to 8388648
  and the 'R' bit cleared in the Command Flags field,
  is sent from the SMS-IWMSC to the HSS and from the SMS-GMSC to the MME or SGSN (possibly via an SMS Router).

  Message Format
  < Alert-Service-Centre-Answer > ::= < Diameter Header: 8388648, PXY, 16777312 >
                               < Session-Id >
                               [ DRMP ]
                               [ Vendor-Specific-Application-Id ]
                               [ Result-Code ]
                               [ Experimental-Result ]
                               { Auth-Session-State }
                               { Origin-Host }
                               { Origin-Realm }
                              *[ Supported-Features ]
                              *[ AVP ]
                               [ Failed-AVP ]
                              *[ Proxy-Info ]
                              *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface AlertServiceCentreAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "ALA";
  String _LONG_NAME = "Alert-Service-Centre-Answer";

  int code = 8388648;
}
