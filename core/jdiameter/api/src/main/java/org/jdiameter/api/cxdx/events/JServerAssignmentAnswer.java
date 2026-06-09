package org.jdiameter.api.cxdx.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Server-Assignment-Answer (SAA) command, indicated by the Command-Code field set to 301 and the bit cleared
 * in the Command Flags field, is sent by a server in response to the Server-Assignment-Request command.
 * If Result-Code or Experimental-Result does not inform about an error, the User-Data AVP shall contain the
 * information that the S-CSCF needs to give service to the user.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JServerAssignmentAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "SAA";
  String _LONG_NAME = "Server-Assignment-Answer";

  int code = 301;

}
