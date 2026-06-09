package org.jdiameter.common.api.app.rf;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.rf.ClientRfSessionListener;
import org.jdiameter.api.rf.ServerRfSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IRfSessionFactory extends IAppSessionFactory {

  /**
   * @return the serverSessionListener
   */
  ServerRfSessionListener getServerSessionListener();

  /**
   * @param serverSessionListener
   *            the serverSessionListener to set
   */
  void setServerSessionListener(ServerRfSessionListener serverSessionListener);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener
   *            the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);

  /**
   * @return the clientSessionListener
   */
  ClientRfSessionListener getClientSessionListener();

  /**
   * @param clientSessionListener
   *            the clientSessionListener to set
   */
  void setClientSessionListener(ClientRfSessionListener clientSessionListener);

  /**
   * @return the clientContextListener
   */
  IClientRfActionContext getClientContextListener();

  /**
   * @param clientContextListener
   *            the clientContextListener to set
   */
  void setClientContextListener(IClientRfActionContext clientContextListener);

  /**
   * @return the serverContextListener
   */
  IServerRfActionContext getServerContextListener();

  /**
   * @param serverContextListener
   *            the serverContextListener to set
   */
  void setServerContextListener(IServerRfActionContext serverContextListener);

  /**
   * @return the messageTimeout
   */
  long getMessageTimeout();

  /**
   * @param messageTimeout
   *            the messageTimeout to set
   */
  void setMessageTimeout(long messageTimeout);

  ApplicationId getApplicationId();

  void setApplicationId(ApplicationId id);

}
