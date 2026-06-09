package org.jdiameter.api;

/**
 * This interface is used to inform about changes in the state for a given peer.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface PeerStateListener {

  /**
   * A change of state has occurred for a peer.
   * @param oldState old state of peer
   * @param newState new state of peer
   */
  void stateChanged (PeerState oldState, PeerState newState);

}
