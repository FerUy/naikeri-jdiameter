package org.jdiameter.common.impl.app.gx;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.gx.IGxSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public abstract class AppGxSessionImpl extends AppSessionImpl implements NetworkReqListener, StateMachine {

  protected Lock sendAndStateLock = new ReentrantLock();
  //FIXME: those must be recreated from local resources!
  //FIXME: change this to single ref!
  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  public AppGxSessionImpl(ISessionFactory sf, IGxSessionData sessionData) {
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
}
