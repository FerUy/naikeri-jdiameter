package org.jdiameter.common.impl.app.acc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.Answer;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Request;
import org.jdiameter.api.acc.events.AccountAnswer;
import org.jdiameter.api.acc.events.AccountRequest;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.IAppSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class AppAccSessionImpl extends AppSessionImpl implements  NetworkReqListener, org.jdiameter.api.app.StateMachine {

  protected Lock sendAndStateLock = new ReentrantLock();

  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  public AppAccSessionImpl(ISessionFactory sf, IAppSessionData appSessionData) {
    super(sf, appSessionData);
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

  protected AccountRequest createAccountRequest(Request request) {
    return new AccountRequestImpl(request);
  }

  protected AccountAnswer createAccountAnswer(Answer answer) {
    return new AccountAnswerImpl(answer);
  }

  @Override
  public void release() {
    //stateListeners.clear();
    super.release();
  }

}