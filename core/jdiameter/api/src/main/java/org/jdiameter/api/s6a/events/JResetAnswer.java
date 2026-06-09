package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JResetAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RSA";
  String _LONG_NAME = "Reset-Answer";

  int code = 322;

}
