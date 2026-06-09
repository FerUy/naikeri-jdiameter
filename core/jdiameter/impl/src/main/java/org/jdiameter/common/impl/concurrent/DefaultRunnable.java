package org.jdiameter.common.impl.concurrent;

import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.BrokenTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.WorkingThread;

import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticRecord;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class DefaultRunnable extends AbstractTask<Runnable> implements Runnable {

  DefaultRunnable(Runnable task, IStatistic statistic, IStatisticRecord... statisticRecords) {
    super(task, statistic, statisticRecords);
  }

  @Override
  public void run() {
    if (getCounter(WorkingThread) != null) {
      getCounter(WorkingThread).inc();
    }
    long time = System.nanoTime();
    try {
      parentTask.run();
    }
    catch (RuntimeException e) {
      if (getCounter(BrokenTasks) != null) {
        getCounter(BrokenTasks).inc();
      }
      throw e;
    }
    finally {
      updateTimeStatistic(time, time - createdTime);
      if (getCounter(WorkingThread) != null) {
        getCounter(WorkingThread).dec();
      }
    }
  }
}
