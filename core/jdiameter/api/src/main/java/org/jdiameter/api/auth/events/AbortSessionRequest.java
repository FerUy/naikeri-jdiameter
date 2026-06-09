package org.jdiameter.api.auth.events;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppRequestEvent;

/**
 * A Abort Session Request is a request from a client to a server
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AbortSessionRequest extends AppRequestEvent {

  String _SHORT_NAME = "ASR";
  String _LONG_NAME = "Abort-Session-Request";

  int code = 274;

  /**
   * Return Auth-Application-Id value of request
   * @return Auth-Application-Id value of request
   * @throws org.jdiameter.api.AvpDataException if avp is not integer
   */
  long getAuthApplicationId()  throws AvpDataException;
}
