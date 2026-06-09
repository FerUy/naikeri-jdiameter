package org.jdiameter.api.app;

/**
 * This interface must be extended by any class that should implement a certain state in the state machine.
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface State {

  /**
   *  Action that should be taken each time this state is entered
   */
  void entryAction();

  /**
   * Action that should be taken each time this state is exited
   */
  void exitAction();

  /**
   * This method processed received event.
   * @param event the event to process.
   * @return true if event is processed
   */
  boolean processEvent(StateEvent event);
}
