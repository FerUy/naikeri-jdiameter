package org.mobicents.diameter.stack.management;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public interface LocalPeer extends Serializable {

  String getUri();

  Collection<String> getIpAddresses();

  String getRealm();

  Long getVendorId();

  String getProductName();

  Long getFirmwareRev();

  Collection<ApplicationIdJMX> getDefaultApplications();

  void setUri(String uri);

  void addIpAddress(String ipAddress);

  void removeIpAddress(String ipAddress);

  void setRealm(String realm);

  void setVendorId(Long vendorId);

  void setProductName(String productName);

  void setFirmwareRev(Long firmwareRev);

  void addDefaultApplication(ApplicationIdJMX defaultApplication);

  void removeDefaultApplication(ApplicationIdJMX defaultApplication);

  HashMap<String, DiameterStatistic> getStatistics();

  void setStatistics(HashMap<String, DiameterStatistic> statistics);
}
