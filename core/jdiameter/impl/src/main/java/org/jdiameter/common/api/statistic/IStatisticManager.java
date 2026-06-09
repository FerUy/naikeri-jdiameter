package org.jdiameter.common.api.statistic;

import java.util.List;
import java.util.Set;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IStatisticManager {

  IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription);

  IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription, IStatisticRecord.ValueHolder counters);

  IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription, IStatisticRecord.ValueHolder counters, IStatisticRecord... rec);

  IStatisticRecord newCounterRecord(String name, String description);

  IStatisticRecord newCounterRecord(String name, String description, IStatisticRecord.ValueHolder counter);

  IStatisticRecord newPerSecondCounterRecord(String name, IStatisticRecord.Counters recordDescription, IStatisticRecord record);

  IStatistic newStatistic(String name, IStatistic.Groups group, IStatisticRecord... rec);

  //IStatistic newStatistic(String name, String description, IStatisticRecord... rec);

  //void removePerSecondCounterRecord(String name, IStatisticRecord.Counters recordDescription);

  void removePerSecondCounterRecord(IStatisticRecord rec);

  //void removeStatistic(String name);

  //void removeStatistic(String name, IStatistic.Groups group);

  void removeStatistic(IStatistic stat);

  // --- non factory methods, metadata access
  boolean isOn();

  long getPause();

  long getDelay();

  Set<String> getEnabled();

  // --- access method

  List<IStatisticRecord> getPSStatisticRecord();

  List<IStatistic> getStatistic();

}
