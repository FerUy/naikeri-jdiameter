package org.jdiameter.api.slg.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */

/*
 * As for 3GPP TS 29.172 v13.0.0, the Provide Subscriber Location operation is used by a GMLC
 * to request the location of a target UE from the MME or SGSN at any time, as part of EPC-MT-LR or PS-MT-LR positioning procedures.
 * The response contains a location estimate of the target UE and other additional information.
 *
 * The Provide Subscriber Location operation is also used by a GMLC to request the location of the target UE from the SGSN or MME at any time,
 * as part of deferred MT-LR procedure. The response contains the acknowledgment of the receipt of the request and other additional information.
 *
 * The GMLC initiates the procedure by sending a PROVIDE SUBSCRIBER LOCATION REQUEST message to the MME or SGSN.
 * This message carries the type of location information requested (e.g. current location and optionally, velocity),
 * the UE subscriber's IMSI, LCS QoS information (e.g. accuracy, response time),
 * an indication of whether the LCS client has the override capability,
 * and an indication of whether delayed location reporting for UEs transiently not reachable
 * (e.g. UEs in extended idle mode DRX or Power Saving Mode) is supported as specified in clauses 9.1.6 and 9.1.15 of 3GPP TS 23.271 [2].
 * The message also carries an LCS reference number, if delayed location reporting is supported.
 * For deferred MT-LR procedure, additionally, the message carries Deferred location type, LCS reference number,
 * H-GMLC address, periodic LDR info, triggered LDR info, etc.

 * The Provide-Location-Request (PLR) command, indicated by the Command-Code field set to 8388620 and the "R" bit set in the
 * Command Flags field, is sent by the GMLC in order to request subscriber location to the MME or SGSN (Provide Subscriber
 * Location operation request)
 */

public interface ProvideLocationRequest extends AppRequestEvent {

  String _SHORT_NAME = "PLR";
  String _LONG_NAME = "Provide-Location-Request";

  int code = 8388620;

}
