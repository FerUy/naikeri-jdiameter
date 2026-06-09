package org.jdiameter.server.api.agent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IRedirect extends IAgent {

  /**
   * Default property name for redirect host usage.
   */
  String RHU_PROPERTY = "rdr.host.usage";

  int RHU_DONT_CACHE = 0;
  int RHU_ALL_SESSION = 1;
  int RHU_ALL_REALM = 2;
  int RHU_REALM_AND_APPLICATION = 3;
  int RHU_ALL_APPLICATION = 4;
  int RHU_ALL_HOST = 5;
  int RHU_ALL_USER = 6;
}
