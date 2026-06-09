package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The ASR command, indicated by the Command-Code field set to 274 and the 'R' bit set in the
 * Command Flags field, is sent by a PCRF to the AF in order to abort a session.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxAbortSessionRequest extends AppRequestEvent {

  String _SHORT_NAME = "ASR";
  String _LONG_NAME = "Abort-Session-Request";

  int code = 274;

}
