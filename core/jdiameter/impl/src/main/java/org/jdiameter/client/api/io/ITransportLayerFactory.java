package org.jdiameter.client.api.io;

import java.net.InetAddress;

import org.jdiameter.api.Wrapper;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

/**
 * Factory of Network Layer elements.
 * Configuration and message parser instances injection by constructor
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ITransportLayerFactory extends Wrapper {

  /**
   * Create new IConnection instance with predefined parameters
   *
   * @param remoteAddress destination host address
   * @param factory       concurrent factory
   * @param remotePort    destination port address
   * @param localAddress  local network adapter address
   * @param localPort     local socket port
   * @param ref           points to additional parameters
   * @return IConnection instance
   * @throws TransportException
   */
  IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort,
                               InetAddress localAddress, int localPort, String ref) throws TransportException;

  /**
   * Create new IConnection instance with predefined parameters
   *
   * @param remoteAddress destination host address
   * @param factory       concurrent factory
   * @param remotePort    destination port address
   * @param localAddress  local network adapter address
   * @param localPort     local socket port
   * @param listener      connection listener instance
   * @param ref           points to additional parameters
   * @return IConnection instance
   * @throws TransportException
   */
  IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort,
                               InetAddress localAddress, int localPort, IConnectionListener listener,
                               String ref) throws TransportException;

  /**
   * Create new IConnection instance with predefined parameters
   *
   * @param remoteAddress          destination host address
   * @param factory                concurrent factory
   * @param remotePort             destination port address
   * @param localAddress           local network adapter address
   * @param localPort              local socket port
   * @param listener               connection listener instance
   * @param extraHostAddresses     sctp extra host addresses for multihoming
   * @param standbyRemoteAddresses remote IP strings for secondary/standby connection
   * @param ref                    points to additional parameters
   * @return IConnection instance
   * @throws TransportException
   */
  IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort,
                               InetAddress localAddress, int localPort, String[] extraHostAddresses,
                               String standbyRemoteAddresses, IConnectionListener listener,
                               String ref) throws TransportException;
}
