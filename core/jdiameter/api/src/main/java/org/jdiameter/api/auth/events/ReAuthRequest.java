package org.jdiameter.api.auth.events;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppRequestEvent;

/**
 * A ReAuthentication Request is a request from a client to a server
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ReAuthRequest extends AppRequestEvent {

  String _SHORT_NAME = "RAR";
  String _LONG_NAME = "Re-Auth-Request";

  int code = 258;

  /**
   * Return re-authentication request type
   * @return re-authentication request type
   * @throws org.jdiameter.api.AvpDataException if avp is not integer
   */
  int getReAuthRequestType() throws AvpDataException;

  /**
   * Return Auth-Application-Id value of request
   * @return Auth-Application-Id value of request
   * @throws AvpDataException if avp is not integer
   */
  long getAuthApplicationId()  throws AvpDataException;

}
