package org.jdiameter.client.api;

import org.jdiameter.api.MetaData;

/**
 *  This interface describe extends methods of base class
 * Data: $Date: 2008/07/03 19:43:10 $
 * Revision: $Revision: 1.1 $
 * @version 1.5.0.1
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IMetaData extends MetaData {

  /**
   * Set new value of host state
   */
  void updateLocalHostStateId();

  /**
   * Return host state value
   * @return host state value
   */
  long getLocalHostStateId();
}
