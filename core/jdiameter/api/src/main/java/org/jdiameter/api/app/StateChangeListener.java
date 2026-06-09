package org.jdiameter.api.app;

/**
 * Interface used to inform about changes in the state for a FSM.
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface StateChangeListener<T> {

  /**
   * @deprecated
   * A change of state has occurred for a FSM.
   * @param oldState Old state of FSM
   * @param newState New state of FSM
   */
  @Deprecated
  @SuppressWarnings("unchecked")
  void stateChanged(Enum oldState, Enum newState);

  /**
   * A change of state has occurred for a FSM.
   *
   * @param source the App Session that generated the change.
   * @param oldState Old state of FSM
   * @param newState New state of FSM
   */
  @SuppressWarnings("unchecked")
  void stateChanged(T source, Enum oldState, Enum newState);
}
