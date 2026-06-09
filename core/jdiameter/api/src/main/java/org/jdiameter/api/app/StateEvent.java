package org.jdiameter.api.app;

/**
 * The Event class holds information about the different events that can be handled
 * by the state machine. Events are prioritized depending on the importance of the event.
 * The priority model tries to ensure that old messages are handled before any new ones.
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface StateEvent extends Comparable {

  /**
   * This method should be adapted by any subclass
   * to return the type corresponding to the actual event.
   * @return type of this StateEvent
   */
  <E> E encodeType(Class<E> enumType);

  /**
   * Return type of this StateEvent
   * @return type of this StateEvent
   */
  Enum getType();

  /**
   * Returns a negative value if the priority for this object
   * is higher than the priority for the supplied object.
   * @param obj the Event to compare to.
   * @return compare result
   */

  /**
   * Set information object to this StateEvent
   * @param data information object
   */
  void setData(Object data);

  /**
   * Return information object of this StateEvent
   * @return information object of this StateEvent
   */
  Object getData();

}
