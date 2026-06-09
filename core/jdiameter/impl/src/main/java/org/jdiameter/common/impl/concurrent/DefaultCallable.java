package org.jdiameter.common.impl.concurrent;

import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.BrokenTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.CanceledTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.WorkingThread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticRecord;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class DefaultCallable<L> extends AbstractTask<Callable<L>> implements Callable<L> {

  DefaultCallable(Callable<L> task, IStatistic statistic, IStatisticRecord... statisticRecords) {
    super(task, statistic, statisticRecords);
  }

  @Override
  public L call() throws Exception {
    long time = 0;
    if (statistic.isEnabled()) {
      getCounter(WorkingThread).inc();
      time = System.nanoTime();
    }
    try {
      return parentTask.call();
    }
    catch (CancellationException e) {
      if (statistic.isEnabled()) {
        getCounter(CanceledTasks).inc();
      }
      throw e;
    }
    catch (Exception e) {
      if (statistic.isEnabled()) {
        getCounter(BrokenTasks).inc();
      }
      throw e;
    }
    finally {
      if (statistic.isEnabled()) {
        updateTimeStatistic(time, time - createdTime);
        getCounter(WorkingThread).dec();
      }

    }
  }
}
