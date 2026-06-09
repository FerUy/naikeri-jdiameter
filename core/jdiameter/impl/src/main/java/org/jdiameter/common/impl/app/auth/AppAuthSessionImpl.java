package org.jdiameter.common.impl.app.auth;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.auth.IAuthSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class AppAuthSessionImpl extends AppSessionImpl implements NetworkReqListener, org.jdiameter.api.app.StateMachine {

  protected Lock sendAndStateLock = new ReentrantLock();
  protected ApplicationId appId;

  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  // protected SessionFactory sf = null;

  public AppAuthSessionImpl(ISessionFactory sf, IAuthSessionData sessionData) {
    super(sf, sessionData);
  }

  @Override
  public void addStateChangeNotification(StateChangeListener listener) {
    if (!stateListeners.contains(listener)) {
      stateListeners.add(listener);
    }
  }

  @Override
  public void removeStateChangeNotification(StateChangeListener listener) {
    stateListeners.remove(listener);
  }

  @Override
  public void release() {
    //stateListeners.clear();
    super.release();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((appId == null) ? 0 : appId.hashCode());
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
    AppAuthSessionImpl other = (AppAuthSessionImpl) obj;
    if (appId == null) {
      if (other.appId != null) {
        return false;
      }
    }
    else if (!appId.equals(other.appId)) {
      return false;
    }
    return true;
  }


}