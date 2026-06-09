package org.jdiameter.api.slg.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */

/*
 * As for 3GPP TS 29.172 v18.1.0, operation is used by an MME or SGSN to provide the location of a target UE to a GMLC,
 * when a request for location has been implicitly issued or when a Delayed Location Reporting is triggered
 * after receipt of a request for location for a UE transiently not reachable.
 *
 * Upon reception of SUBSCRIBER LOCATION REPORT message, the GMLC shall return a SUBSCRIBER LOCATION REPORT ACK
 * to the MME or SGSN and process the location report accordingly,
 * e.g. transfer of the location estimate to an external LCS Client according to procedure described in 3GPP TS 23.271
 *
 * The Location-Report-Answer (LRA) command, indicated by the Command-Code field set to 8388621 and the "R" bit cleared in the
 * Command Flags field, is sent by the GMLC to the MME or SGSN in response to the Location-Report-Request command (Subscriber
 * Location Report operation answer).
 */

public interface LocationReportAnswer extends AppAnswerEvent{

  String _SHORT_NAME = "LRA";
  String _LONG_NAME = "Location-Report-Answer";

  int code = 8388621;

}
