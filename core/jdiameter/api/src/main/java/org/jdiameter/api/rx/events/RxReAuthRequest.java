package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The RAR command, indicated by the Command-Code field set to 258 and the 'R' bit set in the
 * Command Flags field, is sent by an PCRF to the AF in order to provide it with the Session Information.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxReAuthRequest extends AppRequestEvent {

  String _SHORT_NAME = "RAR";
  String _LONG_NAME = "Re-Auth-Request";

  int code = 258;

}
