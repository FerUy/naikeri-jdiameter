package org.jdiameter.api.s13.events;

import org.jdiameter.api.Avp;
import org.jdiameter.api.app.AppRequestEvent;

/**
 * The ECR command, indicated by the Command-Code field set to 324 and the 'R'
 * bit set in the Command Flags field, is sent by MME or SGSN to EIR to check
 * the Mobile Equipment's identity status (e.g. to check that it has not been
 * stolen, or, to verify that it does not have faults).
 *
 */
public interface JMEIdentityCheckRequest extends AppRequestEvent {

  String _SHORT_NAME = "ECR";
  String _LONG_NAME = "ME-Identity-Check-Request";
  int code = 324;

  Avp getTerminalInformationAvp();

  boolean hasIMEI();
  String getIMEI();

  boolean hasTgpp2MEID();
  byte[] getTgpp2MEID();

  boolean hasSoftwareVersion();
  String getSoftwareVersion();

  boolean isUserNameAVPPresent();

  String getUserName();
}
