package org.jdiameter.common.impl.statistic;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.jdiameter.api.StatisticRecord;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticRecord;
import org.jdiameter.common.api.statistic.IStatisticRecord.Counters;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class StatisticImpl implements IStatistic {

  protected boolean enable = true;
  protected ConcurrentLinkedQueue<StatisticRecord> records = new ConcurrentLinkedQueue<StatisticRecord>();
  protected String name;
  protected String description;
  protected IStatistic.Groups group;
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  StatisticImpl(String name) {
    this.name = name;
  }

  StatisticImpl(String name, IStatistic.Groups group) {
    this(group.name() + "." + name);
    this.group = group;
    this.description = group.getDescription();

  }
  //  public StatisticImpl(String name, String desctiprion, IStatisticRecord... rec) {
  //    this.name = name;
  //    this.description = desctiprion;
  //    for (IStatisticRecord r : rec) {
  //      records.add((IStatisticRecord) r);
  //    }
  //  }
  StatisticImpl(String name, IStatistic.Groups group, String desctiprion, IStatisticRecord... rec) {
    this(name, group);
    this.description = desctiprion;
    for (IStatisticRecord r : rec) {
      records.add(r);
    }
  }
  @Override
  public void appendCounter(IStatisticRecord... rec) {
    for (IStatisticRecord r : rec) {
      r.enable(this.enable);
      records.add(r);
    }
  }

  @Override
  public IStatisticRecord getRecordByName(String name) {
    for (StatisticRecord r : records) {
      if (r.getName().equals(name)) {
        return (IStatisticRecord) r;
      }
    }
    return null;
  }

  @Override
  public IStatisticRecord getRecordByName(Counters name) {
    for (StatisticRecord r : records) {
      if (r.getName().equals(name.toString())) {
        return (IStatisticRecord) r;
      }
    }
    return null;
  }

  @Override
  public void enable(boolean e) {
    for (StatisticRecord r : records) {
      r.enable(e);
    }
    enable = e;
  }

  @Override
  public boolean isEnabled() {
    return enable;
  }

  @Override
  public void reset() {
    for (StatisticRecord r : records) {
      r.reset();
    }
  }

  @Override
  public StatisticRecord[] getRecords() {
    return records.toArray(new StatisticRecord[0]);
  }

  @Override
  public String toString() {
    return "Statistic{" + " records=" + records + " }";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((group == null) ? 0 : group.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    StatisticImpl other = (StatisticImpl) obj;
    if (group != other.group) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

}
