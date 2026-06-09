package org.jdiameter.client.api.controller;

import org.jdiameter.api.Realm;
import org.jdiameter.server.api.agent.IAgent;
import org.jdiameter.server.api.agent.IAgentConfiguration;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IRealm extends Realm {

  /**
   * Return list of real peers
   *
   * @return array of realm peers
   */
  String[] getPeerNames();

  /**
   * Append new host (peer) to this realm
   *
   * @param host
   *            name of peer host
   */
  void addPeerName(String name);

  /**
   * Remove peer from this realm
   *
   * @param host
   *            name of peer host
   */
  void removePeerName(String name);

  /**
   * Checks if a peer name belongs to this realm
   *
   * @param name name of peer host
   * @return true if the the peer belongs to this realm, false otherwise
   */
  boolean hasPeerName(String name);

  /**
   * Get the processing agent for this realm
   *
   * @return the agent for this realm, if any
   */
  IAgent getAgent();

  /**
   * Get agent configuration values for this realm.
   * @return
   */
  IAgentConfiguration getAgentConfiguration();

}
