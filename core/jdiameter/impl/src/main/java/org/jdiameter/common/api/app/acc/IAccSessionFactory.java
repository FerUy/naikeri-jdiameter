package org.jdiameter.common.api.app.acc;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.acc.ClientAccSessionListener;
import org.jdiameter.api.acc.ServerAccSessionListener;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAccSessionFactory extends IAppSessionFactory {

  /**
   * @return the serverSessionListener
   */
  ServerAccSessionListener getServerSessionListener();

  /**
   * @param serverSessionListener
   *            the serverSessionListener to set
   */
  void setServerSessionListener(ServerAccSessionListener serverSessionListener);

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
  ClientAccSessionListener getClientSessionListener();

  /**
   * @param clientSessionListener
   *            the clientSessionListener to set
   */
  void setClientSessionListener(ClientAccSessionListener clientSessionListener);

  /**
   * @return the clientContextListener
   */
  IClientAccActionContext getClientContextListener();

  /**
   * @param clientContextListener
   *            the clientContextListener to set
   */
  void setClientContextListener(IClientAccActionContext clientContextListener);

  /**
   * @return the serverContextListener
   */
  IServerAccActionContext getServerContextListener();

  /**
   * @param serverContextListener
   *            the serverContextListener to set
   */
  void setServerContextListener(IServerAccActionContext serverContextListener);

  void setMessageFactory(IAccMessageFactory messageFactory);

  IAccMessageFactory getMessageFactory();

  ApplicationId getApplicationId();

  void setApplicationId(ApplicationId id);

}
