package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The RAA command, indicated by the Command-Code field set to 258 and the 'R' bit cleared
 * in the Command Flags field, is sent by the AF to the PCRf in response to the RAR command.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxReAuthAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RAA";
  String _LONG_NAME = "Re-Auth-Answer";

  int code = 258;
}
