package org.jdiameter.common.api.statistic;

import org.jdiameter.api.Statistic;

/**
 * This interface describe extends methods of base class
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IStatistic extends Statistic {

  enum Groups {
    Peer("Peer statistic"),
    PeerFSM("Peer FSM statistic"),
    Network("Network statistic"),
    Concurrent(" Concurrent factory statistics"),
    ScheduledExecService("ScheduledExecutorService statistic");

    private String description;

    Groups(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * Merge statistic
   *
   * @param rec external statistic
   */
  void appendCounter(IStatisticRecord... rec);

  IStatisticRecord getRecordByName(String name);

  IStatisticRecord getRecordByName(IStatisticRecord.Counters name);

}
