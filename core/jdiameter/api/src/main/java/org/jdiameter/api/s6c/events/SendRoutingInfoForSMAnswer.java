package org.jdiameter.api.s6c.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 <code>
  3GPP TS 29.338 V19.1.0 (2025-03) § 5.3.2.4

  The Send-Routing-Info-for-SM-Answer command (SRA) command,indicated by the Command-Code field set to 8388647
  and the 'R' bit cleared in the Command Flags field, is sent from HSS to SMS-GMSC or SMS Router or from SMS Router to SMS-GMSC.

  Message Format
  < Send-Routing-Info-for-SM-Answer > ::= < Diameter Header: 8388647, PXY, 16777312 >
                                   < Session-Id >
                                   [ DRMP ]
                                   [ Vendor-Specific-Application-Id ]
                                   [ Result-Code ]
                                   [ Experimental-Result ]
                                   { Auth-Session-State }
                                   { Origin-Host }
                                   { Origin-Realm }
                                   [ User-Name ]
                                  *[ Supported-Features ]
                                   [ Serving-Node ]
                                   [ Additional-Serving-Node ]
                                   [ SMSF-3GPP-Address ]
                                   [ SMSF-Non-3GPP-Address ]
                                   [ LMSI ]
                                   [ User-Identifier ]
                                   [ MWD-Status ]
                                   [ MME-Absent-User-Diagnostic-SM ]
                                   [ MSC-Absent-User-Diagnostic-SM ]
                                   [ SGSN-Absent-User-Diagnostic-SM ]
                                   [ SMSF-3GPP-Absent-User-Diagnostic-SM ]
                                   [ SMSF-Non-3GPP-Absent-User-Diagnostic-SM ]
                                  *[ AVP ]
                                   [ Failed-AVP ]
                                  *[ Proxy-Info ]
                                  *[ Route-Record ]
                                   [ MPS-Priority]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public interface SendRoutingInfoForSMAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "SRA";
  String _LONG_NAME = "Send-Routing-Info-for-SM-Answer";

  int code = 8388647;

}
