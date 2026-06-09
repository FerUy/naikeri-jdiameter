package org.jdiameter.api.slh.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

/*
 * As for 3GPP TS 29.173 v13.0.0, the LCS-Routing-Info-Request (RIR) command, indicated by the Command-Code field set to 8388622
 * and the "R" bit set in the Command Flags field, is sent from GMLC to HSS. The procedure invoked by the GMLC is used for
 * retrieving routing information for LCS (Location Services) for a specified user from the HSS.
 *
 */
public interface LCSRoutingInfoRequest extends AppRequestEvent {

  String _SHORT_NAME = "RIR";
  String _LONG_NAME = "LCS-Routing-Info-Request";

  int code = 8388622;

}