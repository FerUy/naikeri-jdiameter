package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Subscribe-Notifications-Answer command, indicated by the Command-Code field set to 308 and
 * the bit cleared in the Command Flags field, is sent by a server in response to the
 * Subscribe-Notifications-Request command.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface SubscribeNotificationsAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "SNA";
  String _LONG_NAME = "Subscribe-Notifications-Answer";

  int code = 308;

}
