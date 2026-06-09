package org.jdiameter.common.impl.app.cxdx;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.cxdx.ICxDxMessageFactory;
import org.jdiameter.common.api.app.cxdx.ICxDxSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class CxDxSession extends AppSessionImpl implements NetworkReqListener, StateMachine {

  public static final int _TX_TIMEOUT = 30 * 1000;

  protected Lock sendAndStateLock = new ReentrantLock();

  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();
  protected transient ICxDxMessageFactory messageFactory;

  //protected CxDxSessionState state = CxDxSessionState.IDLE;
  // protected Future timeoutTaskFuture;
  // this can be weird
  //protected Serializable timerId_timeout;
  protected static final String TIMER_NAME_MSG_TIMEOUT = "MSG_TIMEOUT";
  //protected Message buffer;
  protected ICxDxSessionData sessionData;

  public CxDxSession(ISessionFactory sf, ICxDxSessionData sessionData) {
    super(sf, sessionData);
    this.sessionData = sessionData;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void addStateChangeNotification(StateChangeListener listener) {
    if (!stateListeners.contains(listener)) {
      stateListeners.add(listener);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void removeStateChangeNotification(StateChangeListener listener) {
    stateListeners.remove(listener);
  }

  @Override
  public boolean isStateless() {
    // Cx/Dx is always stateless
    return true;
  }

  /* (non-Javadoc)
   * @see org.jdiameter.common.impl.app.AppSessionImpl#isReplicable()
   */
  @Override
  public boolean isReplicable() {
    // Cx/Dx is event based..
    return false;
  }

  @Override
  public void release() {
    //stateListeners.clear();
    super.release();
  }

  protected void startMsgTimer() {
    try {
      sendAndStateLock.lock();
      sessionData.setTsTimerId(super.timerFacility.schedule(getSessionId(), TIMER_NAME_MSG_TIMEOUT, _TX_TIMEOUT));

    }
    finally {
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

    }
    finally {
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
    CxDxSession other = (CxDxSession) obj;
    if (sessionData == null) {
      if (other.sessionData != null) {
        return false;
      }
    }
    else if (!sessionData.equals(other.sessionData)) {
      return false;
    }
    return true;
  }



}
