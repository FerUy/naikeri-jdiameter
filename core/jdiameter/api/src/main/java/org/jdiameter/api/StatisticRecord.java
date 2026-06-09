package org.jdiameter.api;

/**
 * This class implements counter of statistic
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface StatisticRecord {

  /**
   * Return name of counter
   * @return  name of counter
   */
  String getName();

  /**
   * Return description of counter
   * @return description of counter
   */
  String getDescription();

  /**
   * Return value of counter as integer
   * @return value of counter
   */
  int getValueAsInt();

  /**
   *  Return value of counter as double
   * @return value of counter
   */
  double getValueAsDouble();

  /**
   *  Return value of counter as long
   * @return value of counter
   */
  long getValueAsLong();

  /**
   * Return child counters
   * @return array of child counters
   */
  StatisticRecord[] getChilds();

  /**
   * Reset counter and all child counters
   */
  void reset();

  /**
   * Enable/Disable counter
   *
   * @param e on/off parameter
   */
  void enable(boolean e);

  boolean isEnabled();
}
