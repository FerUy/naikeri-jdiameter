package org.jdiameter.server.api.io;

/**
 * This interface describe INetWorkConnectionListener consumer
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface INetworkGuard {

  /**
   * Append new listener
   *
   * @param listener listener instance
   */
  void addListener(INetworkConnectionListener listener);

  /**
   * Remove listener
   *
   * @param listener listener instance
   */
  void remListener(INetworkConnectionListener listener);

  /**
   * Release all attached resources (socket and etc)
   */
  void destroy();

}