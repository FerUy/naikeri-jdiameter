package org.jdiameter.api;

/**
 * This interface is used to inform about changes in the peer table.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface PeerTableListener {

  /**
   * This method notified about adding a new peer to peer table
   * @param peer peer instance
   */
  void peerAccepted(Peer peer);

  /**
   * This method notified about removing a peer from peer table
   * @param peer peer instance
   */
  void peerRemoved(Peer peer);
}
