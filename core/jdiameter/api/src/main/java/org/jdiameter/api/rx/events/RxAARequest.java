package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The AAR command, indicated by the Command-Code field set to 265 and the 'R' bit set in the
 * Command Flags field, is sent by an AF to the PCRF in order to provide it with the Session Information.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxAARequest extends AppRequestEvent {

  String _SHORT_NAME = "AAR";
  String _LONG_NAME = "AA-Request";

  int code = 265;

}
