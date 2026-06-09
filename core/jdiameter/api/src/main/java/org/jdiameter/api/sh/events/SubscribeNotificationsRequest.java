package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The Subscribe-Notifications-Request (SNR) command, indicated by the Command-Code field set to
 * 308 and the bit set in the Command Flags field, is sent by a Diameter client to a Diameter
 * server in order to request notifications of changes in user data.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface SubscribeNotificationsRequest extends AppRequestEvent {

  String _SHORT_NAME = "SNR";
  String _LONG_NAME = "Subscribe-Notifications-Request";

  int code = 308;

}
