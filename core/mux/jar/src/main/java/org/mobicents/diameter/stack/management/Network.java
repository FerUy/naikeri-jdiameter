package org.mobicents.diameter.stack.management;

import java.io.Serializable;
import java.util.Map;

public interface Network extends Serializable {

  Map<String, NetworkPeer> getPeers();

  NetworkPeer getPeer(String name);

  void addPeer(NetworkPeer peer);

  void addPeerRuntime(NetworkPeer peer, String realm);

  void removePeer(String name);

  Map<String, Realm> getRealms();

  Realm getRealm(String name);

  void addRealm(Realm realm);

  void addRealmRuntime(Realm realm);

  void removeRealm(String name);
}
