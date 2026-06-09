package org.jdiameter.client.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class VersionProperties {

  /**
   * The single instance.
   */
  public static final VersionProperties instance = new VersionProperties();

  /**
   * The version properties.
   */
  private Properties props;

  /**
   * Do not allow direct public construction.
   */
  private VersionProperties() {
    props = loadProperties();
  }

  /**
   * Returns an unmodifiable map of version properties.
   *
   * @return
   */
  public Map getProperties() {
    return Collections.unmodifiableMap(props);
  }

  /**
   * Returns the value for the given property name.
   *
   * @param name
   *          - The name of the property.
   * @return The property value or null if the property is not set.
   */
  public String getProperty(final String name) {
    return props.getProperty(name);
  }

  /**
   * Returns the version information as a string.
   *
   * @return Basic information as a string.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Object key : props.keySet()) {
      if (first) {
        first = false;
      }
      else {
        sb.append(" , ");
      }
      sb.append(key).append(" = ").append(props.get(key));
    }
    return sb.toString();
  }

  /**
   * Load the version properties from a resource.
   */
  private Properties loadProperties() {

    props = new Properties();

    try {
      InputStream in = VersionProperties.class.getResourceAsStream("/META-INF/version.properties");
      props.load(in);
      in.close();
    }
    catch (Exception e) {
      // failed to load version properties. go with defaults
      props.put("vendor", "Mobicents");
      props.put("version", "UN.DEFINED");
    }

    return props;
  }

}