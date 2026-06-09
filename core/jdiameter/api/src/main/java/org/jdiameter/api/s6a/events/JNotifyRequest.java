package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JNotifyRequest extends AppRequestEvent {

  String _SHORT_NAME = "NOR";
  String _LONG_NAME = "Notify-Request";

  int code = 323;

}
