package org.jdiameter.common.impl.app.rf;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.rf.events.RfAccountingAnswer;
import org.jdiameter.api.rf.events.RfAccountingRequest;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.rf.IRfSessionData;
import org.jdiameter.common.impl.app.AppSessionImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public abstract class AppRfSessionImpl extends AppSessionImpl implements  NetworkReqListener, org.jdiameter.api.app.StateMachine {

  protected Lock sendAndStateLock = new ReentrantLock();
  protected ApplicationId appId;

  protected transient List<StateChangeListener> stateListeners = new CopyOnWriteArrayList<StateChangeListener>();

  public AppRfSessionImpl(ISessionFactory sf, IRfSessionData sessionData) {
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

  protected RfAccountingRequest createAccountRequest(Request request) {
    return new RfAccountingRequestImpl(request);
  }

  protected RfAccountingAnswer createAccountAnswer(Answer answer) {
    return new RfAccountingAnswerImpl(answer);
  }

  @Override
  public void release() {
    //stateListeners.clear();
    super.release();
  }
}