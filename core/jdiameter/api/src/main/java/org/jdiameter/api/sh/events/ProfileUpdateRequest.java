package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The Profile-Update-Request (PUR) command, indicated by the Command-Code field set to 307 and the
 * bit set in the Command Flags field, is sent by a Diameter client to a Diameter server in
 * order to update user data in the server.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ProfileUpdateRequest extends AppRequestEvent {

  String _SHORT_NAME = "PUR";
  String _LONG_NAME = "Profile-Update-Request";

  int code = 307;
}
