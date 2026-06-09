package org.jdiameter.api.cxdx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Registration-Termination-Answer (RTA) command, indicated by the Command-Code field set to 304 and the bit
 * cleared in the Command Flags field, is sent by a client in response to the Registration-Termination-Request command.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JRegistrationTerminationAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "RTA";
  String _LONG_NAME = "Registration-Termination-Answer";

  int code = 304;

}
