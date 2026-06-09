package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JCancelLocationAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "CLA";
  String _LONG_NAME = "Cancel-Location-Answer";

  int code = 317;

}
