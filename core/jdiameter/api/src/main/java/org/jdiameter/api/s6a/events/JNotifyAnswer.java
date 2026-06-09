package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JNotifyAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "NOA";
  String _LONG_NAME = "Notify-Answer";

  int code = 323;

}
