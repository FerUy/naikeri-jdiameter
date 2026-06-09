package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JResetRequest extends AppRequestEvent {

  String _SHORT_NAME = "RSR";
  String _LONG_NAME = "Reset-Request";

  int code = 322;

}
