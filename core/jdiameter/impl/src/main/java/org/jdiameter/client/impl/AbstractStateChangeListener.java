package org.jdiameter.client.impl;

import org.jdiameter.api.app.StateChangeListener;

/**
 * Implementation of state change methods.
 * Developer can provide implementation of particular methods required.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class AbstractStateChangeListener<T> implements StateChangeListener<T> {

  /*
   * (non-Javadoc)
   * @see org.jdiameter.api.app.StateChangeListener#stateChanged(java.lang.Enum, java.lang.Enum)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void stateChanged(Enum oldState, Enum newState) {
    // Stub method
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.api.app.StateChangeListener#stateChanged(java.lang.Object, java.lang.Enum, java.lang.Enum)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void stateChanged(T source, Enum oldState, Enum newState) {
    // Stub method
  }

}
