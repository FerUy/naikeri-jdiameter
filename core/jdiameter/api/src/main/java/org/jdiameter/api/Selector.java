package org.jdiameter.api;

/**
 *
 * @author erick.svenson@yahoo.com
 */
public interface Selector<T, A> {

  /**
   * Return true if rule is true
   * @param object check object
   * @return true if rule is true
   */
  boolean checkRule(T object);

  /**
   * Return metainformation object
   * @return  metainformation object
   */
  A getMetaData();
}