package org.jdiameter.api.sh.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The User-Data-Request (UDR) command, indicated by the Command-Code field set to 306 and the
 * bit set in the Command Flags field, is sent by a Diameter client to a Diameter server in order
 * to request user data.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface UserDataRequest extends AppRequestEvent {

  String _SHORT_NAME = "UDR";
  String _LONG_NAME = "User-Data-Request";

  int code = 306;

}
