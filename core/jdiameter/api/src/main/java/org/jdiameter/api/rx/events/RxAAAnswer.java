package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The AAA command, indicated by the Command-Code field set to 265 and the 'R' bit cleared
 * in the Command Flags field, is sent by the PCRF to the AF in response to the AAR command.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxAAAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "AAA";
  String _LONG_NAME = "AA-Answer";

  int code = 265;
}
