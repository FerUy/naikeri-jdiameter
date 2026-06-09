package org.mobicents.diameter.impl.ha.timer;

import java.io.Serializable;

import org.jdiameter.api.BaseSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.impl.BaseSessionImpl;
import org.jdiameter.common.api.data.ISessionDatasource;
import org.jdiameter.common.api.timer.ITimerFacility;
import org.jdiameter.common.impl.app.AppSessionImpl;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.restcomm.timers.FaultTolerantScheduler;
import org.restcomm.timers.TimerTask;
import org.restcomm.timers.TimerTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replicated implementation of {@link ITimerFacility}
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ReplicatedTimerFacilityImpl implements ITimerFacility {

  private static final Logger logger = LoggerFactory.getLogger(ReplicatedTimerFacilityImpl.class);

  private ISessionDatasource sessionDataSource;
  private TimerTaskFactory taskFactory;
  private FaultTolerantScheduler ftScheduler;

  public ReplicatedTimerFacilityImpl(IContainer container) {
    super();
    this.sessionDataSource = container.getAssemblerFacility().getComponentInstance(ISessionDatasource.class);
    this.taskFactory = new TimerTaskFactory();
    MobicentsCluster cluster = ((ReplicatedSessionDatasource) this.sessionDataSource).getMobicentsCluster();
    this.ftScheduler = new FaultTolerantScheduler("DiameterTimer", 5, cluster, (byte) 12, null, this.taskFactory);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.timer.ITimerFacility#cancel(java.io.Serializable)
   */
  @Override
  public void cancel(Serializable id) {
    logger.debug("Cancelling timer with id {}", id);
    this.ftScheduler.cancel(id);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.timer.ITimerFacility#schedule(java.lang.String, java.lang.String, long)
   */
  @Override
  public Serializable schedule(String sessionId, String timerName, long miliseconds) throws IllegalArgumentException {
    String id = sessionId + "/" + timerName;
    logger.debug("Scheduling timer with id {}", id);

    if (this.ftScheduler.getTimerTaskData(id) != null) {
      throw new IllegalArgumentException("Timer already running: " + id);
    }

    DiameterTimerTaskData data = new DiameterTimerTaskData(id, miliseconds, sessionId, timerName);
    TimerTask tt = this.taskFactory.newTimerTask(data);
    ftScheduler.schedule(tt);
    return id;
  }

  private final class TimerTaskFactory implements org.restcomm.timers.TimerTaskFactory {

    @Override
    public TimerTask newTimerTask(TimerTaskData data) {
      return new DiameterTimerTask(data);
    }
  }

  private final class DiameterTimerTask extends TimerTask {

    DiameterTimerTask(TimerTaskData data) {
      super(data);
    }

    @Override
    public void runTask() {
      try {
        DiameterTimerTaskData data = (DiameterTimerTaskData) getData();
        BaseSession bSession = sessionDataSource.getSession(data.getSessionId());
        if (bSession == null) {
          // FIXME: error ?
          logger.error("Base Session is null for sessionId: {}", data.getSessionId());
          return;
        }
        else {
          try {
            if (!bSession.isAppSession()) {
              BaseSessionImpl impl = (BaseSessionImpl) bSession;
              impl.onTimer(data.getTimerName());
            }
            else {
              AppSessionImpl impl = (AppSessionImpl) bSession;
              impl.onTimer(data.getTimerName());
            }
          }
          catch (Exception e) {
            logger.error("Caught exception from session object!", e);
          }
        }
      }
      catch (Exception e) {
        logger.error("Failure executing timer task", e);
      }
    }
  }

}
