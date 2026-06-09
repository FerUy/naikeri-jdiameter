package org.jdiameter.common.api.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jdiameter.common.api.statistic.IStatistic;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class DummyConcurrentFactory implements IConcurrentFactory {

  @Override
  public Thread getThread(Runnable runnuble) {
    return new Thread(runnuble);
  }

  @Override
  public Thread getThread(String namePrefix, Runnable runnuble) {
    return new Thread(runnuble, namePrefix);
  }

  @Override
  public List<Thread> getThreads() {
    return new ArrayList<Thread>();
  }

  @Override
  public ThreadGroup getThreadGroup() {
    return null;
  }

  @Override
  public ScheduledExecutorService getScheduledExecutorService(String name) {
    return Executors.newScheduledThreadPool(4);
  }

  @Override
  public Collection<ScheduledExecutorService> getScheduledExecutorServices() {
    return new ArrayList<ScheduledExecutorService>();
  }

  @Override
  public void shutdownNow(ScheduledExecutorService service) {
  }

  @Override
  public IStatistic getStatistic() {
    return null;
  }

  @Override
  public List<IStatistic> getStatistics() {
    // TODO Auto-generated method stub
    return new ArrayList<IStatistic>();
  }

  @Override
  public void shutdownAllNow() {
  }
}
