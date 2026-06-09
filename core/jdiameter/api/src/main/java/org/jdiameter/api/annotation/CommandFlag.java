package org.jdiameter.api.annotation;

/**
 * This enumerated class describe all flags of message
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum CommandFlag {
  /**
   * Request flag
   */
  R,
  /**
   * Proxiable flag
   */
  P,
  /**
   * Error flag
   */
  E,
  /**
   * Re-transmitted flag
   */
  T,
}