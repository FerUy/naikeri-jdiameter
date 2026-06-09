package org.jdiameter.common.impl.app.cca;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.cca.CCASession;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.IAppSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class AppCCASessionImpl extends AppSessionImpl implements CCASession, NetworkReqListener {

  protected Lock sendAndStateLock = new ReentrantLock();

  //FIXME: those must be recreated from local resources!
  //FIXME: change this to single ref!
  //FIXME: use FastList ?
  protected List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  public AppCCASessionImpl(ISessionFactory sf, IAppSessionData data)  {
    super(sf, data);
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