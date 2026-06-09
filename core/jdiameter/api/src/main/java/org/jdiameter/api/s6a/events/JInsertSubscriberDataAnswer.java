package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JInsertSubscriberDataAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "IDA";
  String _LONG_NAME = "Insert-Subscriber-Data-Answer";

  int code = 319;

}
