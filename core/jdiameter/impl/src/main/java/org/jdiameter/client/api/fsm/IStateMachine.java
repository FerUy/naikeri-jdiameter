package org.jdiameter.client.api.fsm;

import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.common.api.statistic.IStatistic;

/**
 * This interface extends StateMachine interface
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IStateMachine extends StateMachine {

  /**
   * This method returns occupancy of event queue
   * @return occupancy of event queue
   */
  double getQueueInfo();

  void remStateChangeNotification(StateChangeListener listener);

  IStatistic getStatistic();
}
