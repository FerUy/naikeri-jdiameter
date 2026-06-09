package org.mobicents.diameter.stack.management;

import java.io.Serializable;
import java.util.Collection;

public interface Realm extends Serializable {

  Collection<ApplicationIdJMX> getApplicationIds();

  void addApplicationId(ApplicationIdJMX applicationId);

  void removeApplicationId(ApplicationIdJMX applicationId);

  String getName();

  void setName(String name);

  Collection<String> getPeers();

  void setPeers(Collection<String> peers);

  void addPeer(String peer);

  void removePeer(String peer);

  String getLocalAction();

  void setLocalAction(String localAction);

  Boolean getDynamic();

  void setDynamic(Boolean dynamic);

  Long getExpTime();

  void setExpTime(Long expTime);

}
