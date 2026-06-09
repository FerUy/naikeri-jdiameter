package org.jdiameter.server.api.agent;

import java.io.Serializable;
import java.util.Properties;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;

/**
 * Interface through which agent can access configuration options for realm.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAgentConfiguration extends Serializable {

  Properties getProperties();

  /**
   * Parse resource and return implementation. May return null if pased argument is null.
   * @param agentConfiguration
   * @return
   * @throws InternalException
   */
  IAgentConfiguration parse(String agentConfiguration) throws InternalException;

  /**
   * @param agentConfiguration
   * @return
   */
  IAgentConfiguration parse(Configuration agentConfiguration) throws InternalException;

}
