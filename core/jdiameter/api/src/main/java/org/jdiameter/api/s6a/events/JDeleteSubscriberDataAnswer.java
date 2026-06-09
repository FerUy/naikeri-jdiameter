package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JDeleteSubscriberDataAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "DSA";
  String _LONG_NAME = "Delete-Subscriber-Data-Answer";

  int code = 320;

}
