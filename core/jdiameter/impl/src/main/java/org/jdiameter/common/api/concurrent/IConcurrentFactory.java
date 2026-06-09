package org.jdiameter.common.api.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.jdiameter.common.api.statistic.IStatistic;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IConcurrentFactory {

  enum ScheduledExecServices {
    ProcessingMessageTimer,
    RedirectMessageTimer,
    DuplicationMessageTimer,
    PeerOverloadTimer,
    ConnectionTimer,
    StatisticTimer,
    ApplicationSession
  }

  // Thread
  Thread getThread(Runnable runnuble);

  Thread getThread(String namePrefix, Runnable runnuble);

  List<Thread> getThreads();

  ThreadGroup getThreadGroup();

  // ScheduledExecutorService
  ScheduledExecutorService getScheduledExecutorService(String name);

  Collection<ScheduledExecutorService> getScheduledExecutorServices();

  void shutdownNow(ScheduledExecutorService service);

  // Common
  IStatistic getStatistic();

  List<IStatistic> getStatistics();

  void shutdownAllNow();
}
