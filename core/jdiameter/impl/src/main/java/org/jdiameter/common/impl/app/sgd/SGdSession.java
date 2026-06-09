package org.jdiameter.common.impl.app.sgd;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.sgd.ISGdMessageFactory;
import org.jdiameter.common.api.app.sgd.ISGdSessionData;

import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class SGdSession extends AppSessionImpl implements NetworkReqListener, StateMachine {

  public static final int _TX_TIMEOUT = 30 * 1000;

  protected Lock sendAndStateLock = new ReentrantLock();

  @SuppressWarnings("rawtypes")
  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<>();
  protected transient ISGdMessageFactory messageFactory;

  protected static final String TIMER_NAME_MSG_TIMEOUT = "MSG_TIMEOUT";
  protected ISGdSessionData sessionData;

  public SGdSession(ISessionFactory sf, ISGdSessionData sessionData) {
    super(sf, sessionData);
    this.sessionData = sessionData;
  }

  @SuppressWarnings("rawtypes")
  public void addStateChangeNotification(StateChangeListener listener) {
    if (!stateListeners.contains(listener)) {
      stateListeners.add(listener);
    }
  }

  @SuppressWarnings("rawtypes")
  public void removeStateChangeNotification(StateChangeListener listener) {
    stateListeners.remove(listener);
  }

  public boolean isStateless() {
    return true;
  }

  protected void startMsgTimer() {
    try {
      sendAndStateLock.lock();
      sessionData.setTsTimerId(super.timerFacility.schedule(getSessionId(), TIMER_NAME_MSG_TIMEOUT, _TX_TIMEOUT));
    } finally {
      sendAndStateLock.unlock();
    }
  }

  protected void cancelMsgTimer() {
    try {
      sendAndStateLock.lock();
      final Serializable timerId = this.sessionData.getTsTimerId();
      if (timerId == null) {
        return;
      }
      super.timerFacility.cancel(timerId);
      this.sessionData.setTsTimerId(null);
    } finally {
      sendAndStateLock.unlock();
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((sessionData == null) ? 0 : sessionData.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SGdSession other = (SGdSession) obj;
    if (sessionData == null) {
      return other.sessionData == null;
    } else {
      return sessionData.equals(other.sessionData);
    }
  }
}
