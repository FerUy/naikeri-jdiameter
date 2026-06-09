package org.jdiameter.api.cxdx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Location-Info-Answer (LIA) command, indicated by the Command-Code field set to 302 and the  bit cleared in
 * the Command Flags field, is sent by a server in response to the Location-Info-Request command.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JLocationInfoAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "LIA";
  String _LONG_NAME = "Location-Info-Answer";

  int code = 302;

}
