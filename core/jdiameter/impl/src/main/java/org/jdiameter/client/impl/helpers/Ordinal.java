package org.jdiameter.client.impl.helpers;

import java.io.Serializable;

/**
 * This class provide ordinality properties to childs
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class Ordinal implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String name;
  protected int ordinal;

  /**
   * Return name of element
   * @return name of element
   */
  public String name() {
    return name;
  }

  /**
   * Return id of element
   * @return id of element
   */
  public int ordinal() {
    return ordinal;
  }
}
