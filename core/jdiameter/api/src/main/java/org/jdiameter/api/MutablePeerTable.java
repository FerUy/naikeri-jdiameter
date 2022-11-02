package org.jdiameter.api;

/**
 * This interface extends PeerTable interface and
 * append some operation for controls peer and realm table
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:enmanuelcalero61@gmail.com"> Enmanuel Calero </a>
 * @version 1.5.1 Final
 */
public interface MutablePeerTable extends PeerTable {

  /**
   * Return peer statistics
   * @param peerHost host of peer
   * @return peer statistics
   */
  Statistic getStatistic(String peerHost);

  /**
   * Append peer table listener
   *
   * @param listener listener instance
   */
  void setPeerTableListener(PeerTableListener listener);

  /**
   * Add new peer to the peer table
   *
   * @param peer      URI of peer (host, port and other connection information)
   *                  for example: aaa://host.example.com:6666;transport=tcp;protocol=diameter
   * @param realmName   name of the realm
   * @param connecting  attempt connect
   * @param ip          the IP address of the peer
   * @return peer instance
   */
  Peer addPeer(URI peer, String realmName, boolean connecting, String ip);

  /**
   * Add new peer to the peer table
   *
   * @param peer        URI of peer (host, port and other connection information)
   *                    for example: aaa://host.example.com:6666;transport=tcp;protocol=diameter
   * @param realmName   name of the realm
   * @param connecting  attempt connect
   * @return peer instance
   */
  Peer addPeer(URI peer, String realmName, boolean connecting);

  /**
   * Remove peer from the peer table
   *
   * @param peerHost    host of peer
   * @return removed peer instance
   */
  Peer removePeer(String peerHost);

  /**
   * Remove peer from the peer table
   *
   * @param peerHost        host of the peer
   * @param disconnectCause the disconnect cause (REBOOTING, BUSY, DO_NOT_WANT_TO_TALK_TO_YOU)
   * @return removed peer instance
   */
  Peer removePeer(String peerHost, int disconnectCause, boolean connecting);
}
