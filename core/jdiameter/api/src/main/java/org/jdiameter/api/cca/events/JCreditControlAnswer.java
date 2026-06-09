package org.jdiameter.api.cca.events;

import org.jdiameter.api.Avp;
import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Credit-Control-Answer message (CCA) is indicated by the command-code field being set to 272
 * and the 'R' bit being cleared in the Command Flags field.  It is used between the credit-control
 * server and the Diameter credit-control client to acknowledge a Credit-Control-Request command.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JCreditControlAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "CCA";
  String _LONG_NAME = "Credit-Control-Answer";

  int code = 272;

  boolean isCreditControlFailureHandlingAVPPresent();

  int getCredidControlFailureHandlingAVPValue();

  boolean isDirectDebitingFailureHandlingAVPPresent();

  int getDirectDebitingFailureHandlingAVPValue();

  boolean isRequestTypeAVPPresent();

  int getRequestTypeAVPValue();

  Avp getValidityTimeAvp();

}
