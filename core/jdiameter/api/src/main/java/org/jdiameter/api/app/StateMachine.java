package org.jdiameter.api.app;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;

/**
 * The StateMachine lets you organize event handling,
 * if the order of the events are important to you.
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface StateMachine {

  /**
   * Add a new state change listener
   * @param listener a points to the listener that will get information about state changes.
   */
  void addStateChangeNotification(StateChangeListener listener);

  /**
   * Remove a state change listener
   * @param listener a points to the listener that will get information about state changes.
   */
  void removeStateChangeNotification(StateChangeListener listener);

  /**
   * Handle an event in the current state.
   * @param event processing event
   * @return true if staterocessed
   * @throws OverloadException if queue of state mashine is full
   * @throws InternalException if FSM has internal error
   */
  boolean handleEvent(StateEvent event) throws InternalException, OverloadException;

  /**
   * Get the current state
   * @param stateType type of state
   * @return current state
   */
  <E> E getState(Class<E> stateType);
}
