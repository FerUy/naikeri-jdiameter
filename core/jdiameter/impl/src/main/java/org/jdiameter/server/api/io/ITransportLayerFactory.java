package org.jdiameter.server.api.io;

import java.net.InetAddress;

import org.jdiameter.client.api.io.TransportException;

/**
 * Factory of Network Layer elements. This interface append to parent interface
 * additional method for creating INetWorkGuard guard instances.
 * Additional parameters (Configuration, Parsers and etc) injection to instance over constructor
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ITransportLayerFactory extends org.jdiameter.client.api.io.ITransportLayerFactory {

  /**
   * Create INetworkGuard instance with predefined parameters
   *
   * @param inetAddress address of server socket
   * @param port  port of server socket
   * @return INetWorkGuard instance
   * @throws TransportException
   */
  INetworkGuard createNetworkGuard(InetAddress inetAddress, int port) throws TransportException;

  /**
   * Create INetworkGuard instance with predefined parameters
   *
   * @param inetAddress address of server socket
   * @param port  port of server socket
   * @param listener event listener
   * @return INetWorkGuard instance
   * @throws TransportException
   */
  INetworkGuard createNetworkGuard(InetAddress inetAddress, int port, INetworkConnectionListener listener) throws TransportException;

  /**
   * Create INetworkGuard instance with predefined parameters
   *
   * @param inetAddress address of server socket
   * @param port  port of server socket
   * @return INetWorkGuard instance
   * @throws TransportException
   */
  INetworkGuard createNetworkGuard(InetAddress[] inetAddress, int port) throws TransportException;

  /**
   * Create INetworkGuard instance with predefined parameters
   *
   * @param inetAddress address of server socket
   * @param port  port of server socket
   * @param listener event listener
   * @return INetWorkGuard instance
   * @throws TransportException
   */
  INetworkGuard createNetworkGuard(InetAddress[] inetAddress, int port, INetworkConnectionListener listener) throws TransportException;

}
