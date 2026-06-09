package org.jdiameter.api.cxdx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Push-Profile-Answer (PPA) command, indicated by the Command-Code field set to 305 and the bit cleared in the
 * Command Flags field, is sent by a client in response to the Push-Profile-Request command.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JPushProfileAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "PPA";
  String _LONG_NAME = "Push-Profile-Answer";

  int code = 305;

}
