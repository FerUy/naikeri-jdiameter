package org.jdiameter.api.slg.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */

/*
 * As for 3GPP TS 29.172 v18.1.0, operation is used by an MME or SGSN to provide the location of a target UE to a GMLC,
 * when a request for location has been implicitly issued or when a Delayed Location Reporting is triggered
 * after receipt of a request for location for a UE transiently not reachable.
 *
 * The MME or SGSN initiates the procedure by sending a SUBSCRIBER LOCATION REPORT message to the GMLC.
 * The message may carry the identity of the UE, the location estimate and its age,
 * and the event causing the location report.
 *
 * The Location-Report-Request (LRR) command, indicated by the Command-Code field set to 8388621 and the "R" bit set in the
 * Command Flags field, is sent by the MME or SGSN in order to provide subscriber location data to the GMLC (Subscriber Location
 * Report operation request)
 */

public interface LocationReportRequest extends AppRequestEvent {

  String _SHORT_NAME = "LRR";
  String _LONG_NAME = "Location-Report-Request";

  int code = 8388621;

}