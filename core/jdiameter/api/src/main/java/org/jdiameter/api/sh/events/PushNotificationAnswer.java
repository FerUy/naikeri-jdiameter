package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Push-Notifications-Answer (PNA) command, indicated by the Command-Code field set to 309 and
 * the bit cleared in the Command Flags field, is sent by a client in response to the
 * Push-Notification-Request command.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface PushNotificationAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "PNA";
  String _LONG_NAME = "Push-Notification-Answer";

  int code = 309;

}
