package org.jdiameter.api.gx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Credit-Control-Answer (CCA) messages, indicated by the Command-Code field set to 272 is sent
 * by the OCF to the CTF in order to reply to the CCR.
 *
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface GxReAuthAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RAA";
  String _LONG_NAME = "Re-Auth-Answer";
  int code = 258;
}
