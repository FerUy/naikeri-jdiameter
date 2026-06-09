package org.jdiameter.api.app;

import org.jdiameter.api.AvpDataException;

/**
 * Basic class for application specific request event (Sx, Rx, Gx)
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AppRequestEvent extends AppEvent {

  /**
   * Return destination host avp value ( null if avp is empty )
   *
   * @return destination host avp value
   * @throws AvpDataException if avp is not string
   */
  String getDestinationHost() throws AvpDataException;

  /**
   * Return destination realm avp value ( null if avp is empty )
   *
   * @return origination realm avp value
   * @throws AvpDataException if avp is not string
   */
  String getDestinationRealm() throws AvpDataException;

}
