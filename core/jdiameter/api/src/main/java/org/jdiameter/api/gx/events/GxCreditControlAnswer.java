package org.jdiameter.api.gx.events;

import org.jdiameter.api.Avp;
import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Credit-Control-Answer (CCA) messages, indicated by the Command-Code field set to 272 is sent
 * by the OCF to the CTF in order to reply to the CCR.
 *
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface GxCreditControlAnswer extends AppAnswerEvent {

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
