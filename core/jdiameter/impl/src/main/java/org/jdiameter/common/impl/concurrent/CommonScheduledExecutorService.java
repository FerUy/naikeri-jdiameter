package org.jdiameter.common.impl.concurrent;

import static org.jdiameter.common.api.statistic.IStatistic.Groups.ScheduledExecService;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.BrokenTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.CanceledTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.RejectedTasks;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.WaitTimeTask;
import static org.jdiameter.common.api.statistic.IStatisticRecord.Counters.WorkingThread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.Configuration;
import org.jdiameter.client.impl.helpers.Parameters;
import org.jdiameter.common.api.concurrent.IConcurrentEntityFactory;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticManager;
import org.jdiameter.common.api.statistic.IStatisticRecord;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class CommonScheduledExecutorService extends ScheduledThreadPoolExecutor {

  private IStatistic statistic;
  private IConcurrentEntityFactory entityFactory;
  private IStatisticRecord execTimeSumm;
  private IStatisticRecord execTimeCount;
  private IStatisticRecord waitTimeSumm;
  private IStatisticRecord waitTimeCount;
  private IStatisticManager statisticFactory;

  CommonScheduledExecutorService(String name, Configuration config, final IConcurrentEntityFactory entityFactory, IStatisticManager statisticFactory) {
    super(config == null ? (Integer) Parameters.ConcurrentEntityPoolSize.defValue() : config.getIntValue(Parameters.ConcurrentEntityPoolSize.ordinal(),
        (Integer) Parameters.ConcurrentEntityPoolSize.defValue()));
    this.statisticFactory = statisticFactory;
    this.entityFactory = entityFactory;
    final IStatisticRecord rejectedCount = statisticFactory.newCounterRecord(RejectedTasks);
    execTimeSumm = statisticFactory.newCounterRecord("TimeSumm", "TimeSumm");
    execTimeCount = statisticFactory.newCounterRecord("TimeCount", "TimeCount");
    waitTimeSumm = statisticFactory.newCounterRecord("TimeSumm", "TimeSumm");
    waitTimeCount = statisticFactory.newCounterRecord("TimeCount", "TimeCount");
    //XXX: YYY: no need to remove? it lives as long stack does.
    statistic = statisticFactory.newStatistic(name, ScheduledExecService, rejectedCount);

    final IStatisticRecord execTimeCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.ExecTimeTask, new AbstractTask.AverageValueHolder(
        statistic, IStatisticRecord.Counters.ExecTimeTask), execTimeSumm, execTimeCount);

    final IStatisticRecord waitTimeCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.WaitTimeTask, new AbstractTask.AverageValueHolder(
        statistic, IStatisticRecord.Counters.WaitTimeTask), waitTimeSumm, waitTimeCount);

    statistic.appendCounter(statisticFactory.newCounterRecord(WorkingThread), statisticFactory.newCounterRecord(CanceledTasks),
        statisticFactory.newCounterRecord(BrokenTasks), execTimeCounter, waitTimeCounter, statisticFactory.newCounterRecord(WaitTimeTask));

    if (config == null) {
      this.setThreadFactory(entityFactory.newThreadFactory(name));
    } else {
      this.setThreadFactory(entityFactory.newThreadFactory(config.getStringValue(Parameters.ConcurrentEntityDescription.ordinal(), name)));
    }

    super.setRejectedExecutionHandler(entityFactory.newRejectedExecutionHandler(rejectedCount));
  }


  @Override
  public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
    return super.schedule(this.entityFactory.newDefaultRunnable(runnable, statistic, execTimeSumm, execTimeCount, waitTimeSumm, waitTimeCount), delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return super.schedule(this.entityFactory.newDefaultCallable(callable, statistic, execTimeSumm, execTimeCount, waitTimeSumm, waitTimeCount), delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
    return super.scheduleAtFixedRate(this.entityFactory.newDefaultRunnable(runnable, statistic, execTimeSumm, execTimeCount, waitTimeSumm, waitTimeCount),
        initialDelay, period, unit);
  }

  public IStatistic getStatistic() {
    return statistic;
  }

  @Override
  public void shutdown() {
    this.statisticFactory.removeStatistic(statistic);
    super.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    this.statisticFactory.removeStatistic(statistic);
    return super.shutdownNow();
  }

}
