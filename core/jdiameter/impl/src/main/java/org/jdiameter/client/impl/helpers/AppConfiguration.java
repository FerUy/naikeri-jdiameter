package org.jdiameter.client.impl.helpers;

import org.jdiameter.api.Configuration;

/**
 * This interface provide methods for change configuration object
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AppConfiguration extends Configuration {

  /**
   * Add elements to configuration
   * @param e elements identifier
   * @param value array of elements
   * @return instance of configuration
   */
  AppConfiguration add(Ordinal e, Configuration... value);

  /**
   *
   * @param e element identifier
   * @param value parameter value
   * @return instance of configuration
   */
  AppConfiguration add(Ordinal e, Object value);
}
