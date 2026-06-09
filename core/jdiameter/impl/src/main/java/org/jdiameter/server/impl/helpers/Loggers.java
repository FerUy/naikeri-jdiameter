package org.jdiameter.server.impl.helpers;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class Loggers extends org.jdiameter.client.impl.helpers.Loggers {

  private static final long serialVersionUID = 1L;

  /**
   * Logs for network operations
   */
  public static final Loggers NetWork = new Loggers("NetWork", "netWork", "Logs the NetWork watcher");

  public Loggers(String name, String fullName, String desc) {
    super(name, fullName, desc);
  }
}
