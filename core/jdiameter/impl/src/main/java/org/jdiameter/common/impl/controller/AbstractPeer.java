package org.jdiameter.common.impl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Peer;
import org.jdiameter.api.URI;
import org.jdiameter.client.impl.helpers.UIDGenerator;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticManager;
import org.jdiameter.common.api.statistic.IStatisticRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AbstractPeer implements Comparable<Peer> {

  private static final Logger logger = LoggerFactory.getLogger(AbstractPeer.class);

  public static final int INT_COMMON_APP_ID = 0xffffffff;
  protected static UIDGenerator uid = new UIDGenerator();

  // Statistic
  protected IStatistic statistic;
  protected List<IStatisticRecord> perSecondRecords = new ArrayList<IStatisticRecord>();
  protected URI uri;
  protected IStatisticManager statisticFactory;

  //Added locks to make statistics creation and removal thread safe
  private Lock statisticsLock = new ReentrantLock();

  public AbstractPeer(URI uri, IStatisticManager statisticFactory) {
    this.uri = uri;
    this.statisticFactory = statisticFactory;
  }

  protected void createPeerStatistics() {
    logger.debug("Creating Peer Statistics for URI {}", this.uri);
    // PCB - Added locking around this so that the stats cannot be attempted
    // to be initialized twice by 2 threads entering this function at the same time
    try {
      statisticsLock.lock();
      if (this.statistic != null) {
        return;
      }
      String uriString = uri == null ? "local" : uri.toString();
      IStatisticRecord appGenRequestCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.AppGenRequest);
      IStatisticRecord appGenCPSRequestCounter =
          statisticFactory.newPerSecondCounterRecord(uriString, IStatisticRecord.Counters.AppGenRequestPerSecond, appGenRequestCounter);
      IStatisticRecord appGenRejectedRequestCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.AppGenRejectedRequest);

      perSecondRecords.add(appGenCPSRequestCounter);

      IStatisticRecord appGenResponseCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.AppGenResponse);
      IStatisticRecord appGenCPSResponseCounter =
          statisticFactory.newPerSecondCounterRecord(uriString, IStatisticRecord.Counters.AppGenResponsePerSecond, appGenResponseCounter);
      IStatisticRecord appGenRejectedResponseCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.AppGenRejectedResponse);

      perSecondRecords.add(appGenCPSResponseCounter);

      IStatisticRecord netGenRequestCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.NetGenRequest);
      IStatisticRecord netGenCPSRequestCounter =
          statisticFactory.newPerSecondCounterRecord(uriString, IStatisticRecord.Counters.NetGenRequestPerSecond, netGenRequestCounter);
      IStatisticRecord netGenRejectedRequestCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.NetGenRejectedRequest);

      perSecondRecords.add(netGenCPSRequestCounter);

      IStatisticRecord netGenResponseCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.NetGenResponse);
      IStatisticRecord netGenCPSResponseCounter =
          statisticFactory.newPerSecondCounterRecord(uriString, IStatisticRecord.Counters.NetGenResponsePerSecond, netGenResponseCounter);
      IStatisticRecord netGenRejectedResponseCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.NetGenRejectedResponse);

      perSecondRecords.add(netGenCPSResponseCounter);

      IStatisticRecord sysGenResponseCounter = statisticFactory.newCounterRecord(IStatisticRecord.Counters.SysGenResponse);

      this.statistic = statisticFactory.newStatistic(uriString, IStatistic.Groups.Peer,
          appGenRequestCounter, appGenCPSRequestCounter, appGenRejectedRequestCounter,
          appGenResponseCounter, appGenCPSResponseCounter, appGenRejectedResponseCounter,
          netGenRequestCounter, netGenCPSRequestCounter, netGenRejectedRequestCounter,
          netGenResponseCounter, netGenCPSResponseCounter, netGenRejectedResponseCounter,
          sysGenResponseCounter
          );
    }
    finally {
      logger.debug("Completed creating Peer Statistics for URI {}: {}", this.uri, this.statistic);
      if (this.statistic == null) {
        logger.warn("Failed to create Peer Statistics for URI {}, creating dummy and setting to disabled", this.uri);
        this.statistic = statisticFactory.newStatistic("local", IStatistic.Groups.Peer);
        this.statistic.enable(false);
      }
      statisticsLock.unlock();
    }
  }

  protected void removePeerStatistics() {
    logger.debug("Removing Peer Statistics for URI {}", this.uri);
    //RG edited this to make removal of peer stats thread safe
    try {
      statisticsLock.lock();
      if (this.statistic == null) {
        return;
      }
      for (IStatisticRecord rec : this.perSecondRecords) {
        this.statisticFactory.removePerSecondCounterRecord(rec);
      }

      this.statisticFactory.removeStatistic(this.statistic);
      this.perSecondRecords.clear();
      this.statistic = null;
    }
    finally {
      logger.debug("Completed removing Peer Statistics for URI {}: {}", this.uri, this.statistic);
      statisticsLock.unlock();
    }
  }

  @Override
  public int compareTo(Peer o) {
    return uri.compareTo(o.getUri());
  }

  /**
   * @throws IllegalDiameterStateException
   * @throws InternalException
   *
   */
  protected void disconnect(int disconnectCause) throws InternalException, IllegalDiameterStateException {
  }
}
