package org.mobicents.diameter.stack.management;

import java.util.HashMap;
import java.util.Map;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.LocalAction;
import org.jdiameter.api.PeerTable;
import org.jdiameter.client.api.controller.IRealm;
import org.jdiameter.server.api.agent.IAgentConfiguration;
import org.jdiameter.server.impl.MutablePeerTableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class NetworkImpl implements Network {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(NetworkImpl.class);

  private Map<String, NetworkPeer> peers = new HashMap<String, NetworkPeer>();
  private Map<String, Realm> realms = new HashMap<String, Realm>();

  public NetworkImpl() {
  }

  @Override
  public Map<String, NetworkPeer> getPeers() {
    return peers;
  }

  @Override
  public NetworkPeer getPeer(String name) {
    return peers.get(name);
  }

  @Override
  public void addPeer(NetworkPeer peer) {
    this.peers.put(peer.getName(), peer);
  }

  @Override
  public void addPeerRuntime(NetworkPeer peer, String realm) {
    try {
      org.jdiameter.server.impl.NetworkImpl n = (org.jdiameter.server.impl.NetworkImpl) DiameterConfiguration.stack.unwrap(org.jdiameter.api.Network.class);
      n.addPeer(peer.getName(), realm, peer.getAttemptConnect());
    }
    catch (InternalException e) {
      logger.error("Failed to unwrap class.", e);
    }
  }

  @Override
  public void removePeer(String name) {
    try {
      MutablePeerTableImpl mpt = (MutablePeerTableImpl) DiameterConfiguration.stack.unwrap(PeerTable.class);
      mpt.removePeer(name);
    }
    catch (InternalException e) {
      logger.error("Failed to unwrap class.", e);
    }
  }

  @Override
  public Map<String, Realm> getRealms() {
    return realms;
  }

  @Override
  public Realm getRealm(String name) {
    return realms.get(name);
  }

  @Override
  public void addRealm(Realm realm) {
    realms.put(realm.getName(), realm);
  }

  @Override
  public void addRealmRuntime(Realm realm) {
    try {
      org.jdiameter.server.impl.NetworkImpl n = (org.jdiameter.server.impl.NetworkImpl) DiameterConfiguration.stack.unwrap(org.jdiameter.api.Network.class);
      for (ApplicationIdJMX appId : realm.getApplicationIds()) {
        IAgentConfiguration agentConfiguration = null;
        if (realm instanceof IRealm) {
          agentConfiguration = ((IRealm) realm).getAgentConfiguration();
        }
        //TODO: XXX
        /*org.jdiameter.api.Realm r =*/ n.addRealm(realm.getName(), appId.asApplicationId(), LocalAction.valueOf(realm.getLocalAction()), agentConfiguration,
                                            realm.getDynamic(), realm.getExpTime());
      }
    }
    catch (InternalException e) {
      logger.error("Failed to unwrap class.", e);
    }
  }

  @Override
  public void removeRealm(String name) {
    try {
      org.jdiameter.server.impl.NetworkImpl n = (org.jdiameter.server.impl.NetworkImpl) DiameterConfiguration.stack.unwrap(org.jdiameter.api.Network.class);
      /*org.jdiameter.api.Realm r =*/ n.remRealm(name);
    }
    catch (InternalException e) {
      logger.error("Failed to unwrap class.", e);
    }
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("  ## PEERS ##\r\n");
    for (NetworkPeer peer : peers.values()) {
      buf.append(peer.toString());
    }
    buf.append("  ## REALMS ##\r\n");
    for (Realm realm : realms.values()) {
      buf.append(realm.toString());
    }

    return buf.toString();
  }
}
