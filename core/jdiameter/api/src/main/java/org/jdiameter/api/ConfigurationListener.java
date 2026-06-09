package org.jdiameter.api;

/**
 * The listener interface for receiving runtime configuration changes events.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface ConfigurationListener {

  /**
   * Invoked when an changes is occurs.
   * @param key index of changed element
   * @param newValue new value
   * @return true if new value is applied
   */
  boolean elementChanged(int key, Object newValue);

}
