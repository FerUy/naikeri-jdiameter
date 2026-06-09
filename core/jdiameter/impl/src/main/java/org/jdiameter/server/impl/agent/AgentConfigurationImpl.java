package org.jdiameter.server.impl.agent;

import static org.jdiameter.client.impl.helpers.Parameters.Properties;
import static org.jdiameter.client.impl.helpers.Parameters.PropertyName;
import static org.jdiameter.client.impl.helpers.Parameters.PropertyValue;

import java.util.Properties;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.server.api.agent.IAgentConfiguration;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AgentConfigurationImpl implements IAgentConfiguration {

  private static final long serialVersionUID = 1L;

  protected Properties properties;

  /*
   * (non-Javadoc)
   * @see org.jdiameter.server.api.agent.IAgentConfiguration#getProperties()
   */
  @Override
  public Properties getProperties() {
    return this.properties;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.api.agent.IAgentConfiguration#parse(java.lang.String )
   */
  @Override
  public IAgentConfiguration parse(String agentConfiguration) throws InternalException {
    if (agentConfiguration == null) {
      return null;
    }
    AgentConfigurationImpl conf = new AgentConfigurationImpl();
    try {

      conf.properties = new Properties();
      String[] split = agentConfiguration.split(";");
      for (String s : split) {
        String[] data = s.split("=");
        conf.properties.put(data[0], data[1]);
      }
    }
    catch (Exception e) {
      throw new InternalException(e);
    }
    return conf;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.server.api.agent.IAgentConfiguration#parse(org.jdiameter .api.Configuration)
   */
  @Override
  public IAgentConfiguration parse(Configuration agentConfiguration) throws InternalException {
    if (agentConfiguration == null) {
      return null;
    }
    AgentConfigurationImpl conf = new AgentConfigurationImpl();
    try {

      conf.properties = new Properties();
      Configuration[] propConfs = agentConfiguration.getChildren(Properties.ordinal());
      for (Configuration c : propConfs) {

        conf.properties.put(c.getStringValue(PropertyName.ordinal(), ""), c.getStringValue(PropertyValue.ordinal(), ""));
      }
    }
    catch (Exception e) {
      throw new InternalException(e);
    }
    return conf;
  }

}
