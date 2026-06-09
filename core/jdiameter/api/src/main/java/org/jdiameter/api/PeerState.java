package org.jdiameter.api;

/**
 * This enumerated class define Peer states. More information you can read on document
 * "Authentication, Authorization and Accounting (AAA) Transport Profile"
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public enum PeerState {
  OKAY, SUSPECT, DOWN, REOPEN, INITIAL
}
