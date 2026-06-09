package org.jdiameter.common.impl.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.jdiameter.common.api.statistic.IStatisticRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
  private static final Logger log = LoggerFactory.getLogger(DefaultRejectedExecutionHandler.class);

  private IStatisticRecord rejectedCount;

  /**
   * @param rejectedCount
   */
  DefaultRejectedExecutionHandler(IStatisticRecord rejectedCount) {
    this.rejectedCount = rejectedCount;
  }

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    log.debug("Task rejected {}", r);
    if (rejectedCount.isEnabled()) {
      rejectedCount.inc();
    }
  }
}