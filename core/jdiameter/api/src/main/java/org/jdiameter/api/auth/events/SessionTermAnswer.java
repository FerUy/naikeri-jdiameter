package org.jdiameter.api.auth.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * A Answer message is sent by a recipient of Request once it has received and
 * interpreted the Request.
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface SessionTermAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "STA";
  String _LONG_NAME = "Session-Termination-Answer";

  int code = 275;

}