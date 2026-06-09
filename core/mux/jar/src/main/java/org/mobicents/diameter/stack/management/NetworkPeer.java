package org.mobicents.diameter.stack.management;

import java.io.Serializable;
import java.util.HashMap;

public interface NetworkPeer extends Serializable {

  String getName();

  void setName(String name);

  Boolean getAttemptConnect();

  void setAttemptConnect(Boolean attemptConnect);

  Integer getRating();

  void setRating(Integer rating);

  String getIp();

  void setIp(String ip);

  Integer getPortRangeLow();

  Integer getPortRangeHigh();

  void setPortRange(Integer portRangeLow, Integer portRangeHigh);

  String getSecurityRef();

  void setSecurityRef(String securityRef);

  HashMap<String, DiameterStatistic> getStatistics();

  void setStatistics(HashMap<String, DiameterStatistic> statistics);
}
