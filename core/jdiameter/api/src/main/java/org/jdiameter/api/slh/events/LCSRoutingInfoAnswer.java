package org.jdiameter.api.slh.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

/*
 * As for 3GPP TS 29.173 v13.0.0, the LCS-Routing-Info-Answer (RIA) command, indicated by the Command-Code field set to 8388622
 * and the "R" bit cleared in the Command Flags field, is sent from HSS to GMLC. The procedure invoked by the GMLC is used for
 * retrieving routing information for LCS (Location Services) for a specified user from the HSS via a LCS-Routing-Info-Request
 * (RIR) command.
 *
 */
public interface LCSRoutingInfoAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RIA";
  String _LONG_NAME = "LCS-Routing-Info-Answer";

  int code = 8388622;

}
