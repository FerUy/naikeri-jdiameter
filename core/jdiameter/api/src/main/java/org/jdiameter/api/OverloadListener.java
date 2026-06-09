package org.jdiameter.api;

/**
 * This class allows processed stack overloaded notification
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface OverloadListener {

  /**
   * Notifies this OverloadListener that the stack has overload.
   * @param peer listening peer
   * @param value value of overload
   */
  void overloadDetected(URI peer, double value);

  /**
   * Notifies this OverloadListener that the stack has overload cased
   * @param peer listening peer
   */
  void overloadCeased(URI peer);
}
