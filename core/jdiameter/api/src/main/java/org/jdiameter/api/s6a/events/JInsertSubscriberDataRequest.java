package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JInsertSubscriberDataRequest extends AppRequestEvent {

  String _SHORT_NAME = "IDR";
  String _LONG_NAME = "Insert-Subscriber-Data-Request";

  int code = 319;

}
