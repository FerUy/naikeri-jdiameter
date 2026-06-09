package org.jdiameter.common.impl.statistic;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jdiameter.api.Configuration;
import org.jdiameter.client.impl.helpers.Parameters;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticManager;
import org.jdiameter.common.api.statistic.IStatisticRecord;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class StatisticManagerImpl implements IStatisticManager {

  //TODO: remove CopyOnWrite....
  private List<IStatistic> allStatistic = new CopyOnWriteArrayList<IStatistic>();
  private List<IStatisticRecord> allPSStatisticRecord = new CopyOnWriteArrayList<IStatisticRecord>();

  private List<IStatistic> frozenAllStatistic = Collections.unmodifiableList(allStatistic);
  private List<IStatisticRecord> frozenAllPSStatisticRecord = Collections.unmodifiableList(allPSStatisticRecord);

  private boolean enabled;
  private long pause, delay;
  private Set<String> activeRecords; //list of stats enabled on start


  public StatisticManagerImpl(Configuration config) {
    long pause = (Long) Parameters.StatisticsLoggerPause.defValue();
    long delay = (Long) Parameters.StatisticsLoggerDelay.defValue();
    boolean enabled = (Boolean) Parameters.StatisticsEnabled.defValue();
    String activeRecords = (String) Parameters.Statistics.defValue();
    Configuration[] loggerParams = config.getChildren(Parameters.Statistics.ordinal());
    if (loggerParams != null && loggerParams.length > 0) {
      pause = loggerParams[0].getLongValue(Parameters.StatisticsLoggerPause.ordinal(), pause);
      delay = loggerParams[0].getLongValue(Parameters.StatisticsLoggerDelay.ordinal(), delay);
      enabled = loggerParams[0].getBooleanValue(Parameters.StatisticsEnabled.ordinal(), enabled);
      activeRecords = loggerParams[0].getStringValue(Parameters.StatisticsActiveList.ordinal(), activeRecords);
    }
    this.pause = pause;
    this.delay = delay;
    this.enabled = enabled;
    Set<String> enabledSet = new HashSet<String>();
    if (activeRecords != null && activeRecords.length() > 0) {
      for (String s : activeRecords.split(",")) {
        enabledSet.add(s);
      }
    }
    this.activeRecords = Collections.unmodifiableSet(enabledSet);

  }

  @Override
  public IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription) {
    StatisticRecordImpl statisticRecord = new StatisticRecordImpl(recordDescription.name(), recordDescription.getDescription());
    statisticRecord.enable(this.isEnabled(recordDescription.name()));

    return statisticRecord;
  }

  @Override
  public IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription, IStatisticRecord.ValueHolder counters) {
    StatisticRecordImpl statisticRecord = new StatisticRecordImpl(recordDescription.name(), recordDescription.getDescription(), counters);
    statisticRecord.enable(this.isEnabled(recordDescription.name()));
    return statisticRecord;
  }

  @Override
  public IStatisticRecord newCounterRecord(IStatisticRecord.Counters recordDescription, IStatisticRecord.ValueHolder counter, IStatisticRecord... rec) {
    StatisticRecordImpl statisticRecord = new StatisticRecordImpl(recordDescription.name(), recordDescription.getDescription(), counter, rec);
    statisticRecord.enable(this.isEnabled(recordDescription.name()));
    return statisticRecord;
  }

  @Override
  public IStatisticRecord newCounterRecord(String name, String description) {
    StatisticRecordImpl statisticRecord = new StatisticRecordImpl(name, description);
    statisticRecord.enable(this.isEnabled(name));
    return statisticRecord;
  }

  @Override
  public IStatisticRecord newCounterRecord(String name, String description, IStatisticRecord.ValueHolder counters) {
    StatisticRecordImpl statisticRecord = new StatisticRecordImpl(name, description, counters);
    statisticRecord.enable(this.isEnabled(name));
    return statisticRecord;
  }

  @Override
  public IStatisticRecord newPerSecondCounterRecord(String name, IStatisticRecord.Counters recordDescription, IStatisticRecord child) {
    IStatisticRecord prevValue = new StatisticRecordImpl(name, recordDescription.getDescription());
    IStatisticRecord psStatistic = new StatisticRecordImpl(recordDescription.name() + "." + name, recordDescription.getDescription(), child, prevValue);
    if (allPSStatisticRecord.contains(psStatistic)) {
      throw new IllegalArgumentException("Statistic already defined: " + psStatistic);
    }
    allPSStatisticRecord.add(psStatistic);
    return psStatistic;
  }

  @Override
  public IStatistic newStatistic(String name, IStatistic.Groups group, IStatisticRecord... rec) {
    IStatistic statistic = new StatisticImpl(name, group, group.getDescription(), rec);
    statistic.enable(this.isEnabled(statistic.getName()));
    if (allStatistic.contains(statistic)) {
      throw new IllegalArgumentException("Statistic already defined: " + statistic);
    }
    allStatistic.add(statistic);
    return statistic;
  }

//  public IStatistic newStatistic(String name, String description, IStatisticRecord... rec) {
//    //FIXME: remove this?
//    IStatistic statistic = new StatisticImpl(name, description, rec);
//    statistic.enable(this.isEnabled(statistic.getName()));
//    if (allStatistic.contains(statistic)) {
//      throw new IllegalArgumentException("Statistic already defined: "+statistic);
//    }
//    allStatistic.add(statistic);
//    return statistic;
//  }
//
//  public void removePerSecondCounterRecord(String name, IStatisticRecord.Counters recordDescription) {
//    IStatisticRecord record = new StatisticRecordImpl(recordDescription+"."+name, recordDescription.getDescription());
//    this.allPSStatisticRecord.remove(record);
//  }
//
//  public void removeStatistic(String name) {
//    IStatistic statistic = new StatisticImpl(name);
//    this.allStatistic.remove(statistic);
//  }
//
//  public void removeStatistic(String name, IStatistic.Groups group) {
//    IStatistic statistic = new StatisticImpl(name, group);
//    this.allStatistic.remove(statistic);
//  }

  @Override
  public void removePerSecondCounterRecord(IStatisticRecord rec) {
    this.allPSStatisticRecord.remove(rec);
  }

  @Override
  public void removeStatistic(IStatistic stat) {
    this.allStatistic.remove(stat);
  }

  private boolean isEnabled(String name) {

    if (this.activeRecords.contains(name)) {
      return true;
    }

    //else lets check prefixes.
    while (name.indexOf(".") > 0) {
      name = name.substring(0, name.lastIndexOf("."));
      if (this.activeRecords.contains(name)) {
        return true;
      }
    }
    return this.activeRecords.contains(name);
  }


  @Override
  public boolean isOn() {
    return enabled;
  }

  @Override
  public long getPause() {
    return pause;
  }

  @Override
  public long getDelay() {
    return delay;
  }

  @Override
  public Set<String> getEnabled() {
    return activeRecords;
  }

  @Override
  public List<IStatisticRecord> getPSStatisticRecord() {
    return this.frozenAllPSStatisticRecord;
  }

  @Override
  public List<IStatistic> getStatistic() {
    return this.frozenAllStatistic;
  }

}
