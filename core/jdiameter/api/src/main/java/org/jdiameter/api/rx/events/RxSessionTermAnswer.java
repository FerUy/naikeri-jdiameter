package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The STA command, indicated by the Command-Code field set to 275 and the 'R' bit cleared
 * in the Command Flags field, is sent by the PCRF to the AF in response to the STR command.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxSessionTermAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "STA";
  String _LONG_NAME = "Session-Termination-Answer";

  int code = 275;
}
