package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The ASA command, indicated by the Command-Code field set to 274 and the 'R' bit cleared
 * in the Command Flags field, is sent by the AF to the PCRF in response to the ASR command.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxAbortSessionAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "ASA";
  String _LONG_NAME = "Abort-Session-Answer";

  int code = 274;
}
