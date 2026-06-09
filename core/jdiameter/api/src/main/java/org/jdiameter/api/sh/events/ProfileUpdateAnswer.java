package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Profile-Update-Answer (PUA) command, indicated by the Command-Code field set to 307 and the
 * bit cleared in the Command Flags field, is sent by a server in response to the
 * Profile-Update-Request command.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ProfileUpdateAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "PUA";
  String _LONG_NAME = "Profile-Update-Answer";

  int code = 307;

}
