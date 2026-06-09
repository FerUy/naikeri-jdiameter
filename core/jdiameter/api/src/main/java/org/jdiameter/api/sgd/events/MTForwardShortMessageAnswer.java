package org.jdiameter.api.sgd.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 <code>
 3GPP TS 29.338 V19.1.0 (2025-03) § 6.3.2.6

 The MT-Forward-Short-Message-Answer Command (TFA) command, indicated by the Command-Code field set to 8388646
 and the 'R' bit cleared in the Command Flags field, is sent from the MME / SGSN to the SMS-GMSC (
 transiting an SMS Router, if present)

 Message Format
 < MT-Forward-Short-Message-Answer > ::= < Diameter Header: 8388646, PXY, 16777313 >
                                  < Session-Id >
                                  [ DRMP ]
                                  [ Vendor-Specific-Application-Id ]
                                  [ Result-Code ]
                                  [ Experimental-Result ]
                                  { Auth-Session-State }
                                  { Origin-Host }
                                  { Origin-Realm }
                                 *[ Supported-Features ]
                                  [ Absent-User-Diagnostic-SM ]
                                  [ SM-Delivery-Failure-Cause ]
                                  [ SM-RP-UI ]
                                  [ Requested-Retransmission-Time ]
                                  [ User-Identifier ]
                                  [ EPS-Location-Information ]
                                  [ NR-Cell-Global-Identity ]
                                 *[ AVP ]
                                  [ Failed-AVP ]
                                 *[ Proxy-Info ]
                                 *[ Route-Record ]
 </code>
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface MTForwardShortMessageAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "TFA";
  String _LONG_NAME = "MT-Forward-Short-Message-Answer";

  int code = 8388646;
}
