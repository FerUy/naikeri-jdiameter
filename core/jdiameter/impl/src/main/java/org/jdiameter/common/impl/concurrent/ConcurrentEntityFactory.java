package org.jdiameter.common.impl.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

import org.jdiameter.common.api.concurrent.IConcurrentEntityFactory;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticRecord;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ConcurrentEntityFactory implements IConcurrentEntityFactory {

  // TODO: get rid of that?
  public ConcurrentEntityFactory() {
  }

  @Override
  public ThreadFactory newThreadFactory(String threadPoolName) {
    return new BaseThreadFactory(threadPoolName);
  }

  @Override
  public RejectedExecutionHandler newRejectedExecutionHandler(IStatisticRecord rejectedCount) {
    return new DefaultRejectedExecutionHandler(rejectedCount);
  }

  @Override
  public <L> Callable<L> newDefaultCallable(Callable<L> runnable, IStatistic statistic, IStatisticRecord... statisticRecords) {
    return new DefaultCallable<L>(runnable, statistic, statisticRecords);
  }

  @Override
  public Runnable newDefaultRunnable(Runnable runnable, IStatistic statistic, IStatisticRecord... statisticRecords) {
    return new DefaultRunnable(runnable, statistic, statisticRecords);
  }
}
