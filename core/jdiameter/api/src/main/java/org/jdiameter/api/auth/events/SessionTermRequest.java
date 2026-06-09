package org.jdiameter.api.auth.events;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppRequestEvent;

/**
 * A Session Termination Request is a request from a client to a server
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface SessionTermRequest extends AppRequestEvent {

  String _SHORT_NAME = "STR";
  String _LONG_NAME = "Session-Termination-Request";

  int code = 275;

  /**
   * @return Auth-Application-Id value of request
   * @throws AvpDataException if result code avp is not integer
   */
  long getAuthApplicationId() throws AvpDataException;

  /**
   * @return termination cause
   * @throws AvpDataException if result code avp is not integer
   */
  int getTerminationCause() throws AvpDataException;

}
