package org.jdiameter.common.impl.app.rx;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.rx.IRxSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class AppRxSessionImpl extends AppSessionImpl implements NetworkReqListener, StateMachine {

  protected Lock sendAndStateLock = new ReentrantLock();
  //FIXME: those must be recreated from local resources!
  //FIXME: change this to single ref!
  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  public AppRxSessionImpl(ISessionFactory sf, IRxSessionData sessionData) {
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
    super.release();
  }
}
