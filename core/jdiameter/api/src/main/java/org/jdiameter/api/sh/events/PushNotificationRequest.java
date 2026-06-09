package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The Push-Notification-Request (PNR) command, indicated by the Command-Code field set to 309 and
 * the bit set in the Command Flags field, is sent by a Diameter server to a Diameter client
 * in order to notify changes in the user data in the server.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface PushNotificationRequest extends AppRequestEvent {

  String _SHORT_NAME = "PNR";
  String _LONG_NAME = "Push-Notification-Request";

  int code = 309;

}
