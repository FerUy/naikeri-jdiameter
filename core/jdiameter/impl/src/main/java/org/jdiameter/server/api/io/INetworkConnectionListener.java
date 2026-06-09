package org.jdiameter.server.api.io;

import org.jdiameter.client.api.io.IConnection;

/**
 * This interface allow notifies consumers about created connections
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface INetworkConnectionListener {

  /**
   * Invoked when an new connection created.
   *
   * @param connection created connections
   */
  void newNetworkConnection(IConnection connection);

}