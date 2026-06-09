package org.jdiameter.api.annotation;

/**
 * This enumerated class describe all flags of avp header
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum AvpFlag {
  /**
   * Mandatory flag
   */
  M,
  /**
   * Vendor-Id flag
   */
  V,
  /**
   * Encryption flag
   */
  P
}