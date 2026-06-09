package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The User-Data-Answer (UDA) command, indicated by the Command-Code field set to 306 and the
 * bit cleared in the Command Flags field, is sent by a server in response to the
 * User-Data-Request command.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface UserDataAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "UDA";
  String _LONG_NAME = "User-Data-Answer";

  int code = 306;

}
