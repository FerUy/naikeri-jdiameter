package org.jdiameter.api.app;

import java.io.Serializable;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Message;

/**
 * Basic class for application specific event (Sx, Rx, Gx)
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AppEvent extends Serializable {

  /**
   * @return commCode of parent message
   */
  int getCommandCode();

  /**
   * @return set of associated message
   * @throws InternalException signals that internal message is not set.
   */
  Message getMessage() throws InternalException;

  /**
   * Return origination host avp value ( null if avp is empty )
   *
   * @return origination host avp value
   * @throws AvpDataException if avp is not string
   */
  String getOriginHost() throws AvpDataException;

  /**
   * Return origination realm avp value ( null if avp is empty )
   *
   * @return origination realm avp value
   * @throws AvpDataException if avp is not string
   */
  String getOriginRealm() throws AvpDataException;

}
