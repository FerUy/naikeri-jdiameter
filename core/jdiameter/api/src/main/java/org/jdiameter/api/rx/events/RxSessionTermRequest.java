package org.jdiameter.api.rx.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The STR command, indicated by the Command-Code field set to 275 and the 'R' bit set in the
 * Command Flags field, is sent by an AF to the PCRF in order to terminate a session.
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface RxSessionTermRequest extends AppRequestEvent {

  String _SHORT_NAME = "STR";
  String _LONG_NAME = "Session-Termination-Request";

  int code = 275;

}
