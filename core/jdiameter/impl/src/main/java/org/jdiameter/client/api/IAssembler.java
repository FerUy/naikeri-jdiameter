package org.jdiameter.client.api;

/**
 * This interface provide IOC functionality
 * Data: $Date: 2009/10/10 20:17:57 $
 * Revision: $Revision: 1.2 $
 *
 * @version 1.5.0.1
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IAssembler {
  /**
   * Return parent IOC
   *
   * @return IOC instance
   */
  IAssembler getParent();

  /**
   * Return all children
   *
   * @return all children
   */
  IAssembler[] getChilds();

  /**
   * Register new component
   *
   * @param aClass class of component
   * @return instance of component
   */
  <T> T getComponentInstance(Class<T> aClass);

  /**
   * Register new component
   *
   * @param object instance of component
   */
  void registerComponentInstance(Object object);

  /**
   * Register new component
   *
   * @param aClass class of component
   * @param object instance of component
   */
  void registerComponentImplementation(Class<?> aClass, Object object);

  /**
   * Release all attached resources
   */
  void destroy();
}
