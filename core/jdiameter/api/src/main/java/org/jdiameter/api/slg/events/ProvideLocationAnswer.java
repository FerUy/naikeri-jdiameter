package org.jdiameter.api.slg.events;

import org.jdiameter.api.app.AppAnswerEvent;

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
 * Upon reception of PROVIDE SUBSCRIBER LOCATION REQUEST message,
 * the MME or SGSN shall perform authentication privacy verification on the location request.
 * After that, for EPC-MT-LR or PS-MT-LR procedures the MME or SGSN shall retrieve the location information of the target UE
 * from E-UTRAN or UTRAN/GERAN according to the procedures described in 3GPP TS 23.271 [2].
 *
 * For a deferred EPC-MT-LR procedure, the MME shall wait until the UE becomes reachable
 * before performing authentication privacy verification on the location request and instigating periodic or triggered location in the UE.
 *
 * The MME or SGSN returns a PROVIDE SUBSCRIBER LOCATION RESPONSE to the GMLC.
 * For EPC-MT-LR or PS-MT-LR procedures, if the target UE is transiently not reachable
 * and delayed location reporting for UEs transiently not reachable is supported,
 * the message shall contain an indication that the UE is transiently not reachable;
 * otherwise the message shall contain the location estimate, its age and obtained accuracy.
 *
 * If the MME or SGSN failed to get the current location and the LCS client is requesting the current or last known location,
 * the MME or SGSN may return the last known location of the target UE if this is known.
 *
 * For a deferred EPC-MT-LR procedure for periodic or triggered location,
 * the MME returns a PROVIDE SUBSCRIBER LOCATION RESPONSE to the GMLC
 * to indicate that the MME is able and willing to support the procedure.
 * After the MME has performed successful authentication privacy verification on the location request
 * and successfully instigated periodic or triggered location in the UE,
 * the MME returns a SUBSCRIBER LOCATION REPORT to the GMLC to indicate that periodic
 * or triggered location has been activated in the UE. The MME (or a different MME)
 * returns additional SUBSCRIBER LOCATION REPORT messages to the GMLC (or possibly to a different GMLC if not the HGMLC)
 * for each periodic or triggered location event detected by the UE.
 * For details, refer to 3GPP TS 23.271 [2].
 *
 * The Provide-Location-Answer (PLA) command, indicated by the Command-Code field set to 8388620 and the "R" bit cleared in the
 * Command Flags field, is sent by the MME or SGSN to the GMLC in response to the Provide-Location-Request command (Provide
 * Subscriber Location operation answer)
 */

public interface ProvideLocationAnswer extends AppAnswerEvent{

  String _SHORT_NAME = "PLA";
  String _LONG_NAME = "Provide-Location-Answer";

  int code = 8388620;

}
