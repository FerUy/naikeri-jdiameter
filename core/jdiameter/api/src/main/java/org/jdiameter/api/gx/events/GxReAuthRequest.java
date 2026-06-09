package org.jdiameter.api.gx.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The CCR messages, indicated by the Command-Code field set to 272 is sent by the CTF to the OCF
 * in order to request credits for the request bearer / subsystem / service.
 *
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface GxReAuthRequest extends AppRequestEvent {

  String _SHORT_NAME = "RAR";
  String _LONG_NAME = "Re-Auth-Request";
  int code = 258;
}
