package org.jdiameter.common.api.app.cca;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.cca.ClientCCASessionListener;
import org.jdiameter.api.cca.ServerCCASessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter Credit-Control Application (CCA).
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ICCASessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientCCASessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param clientSessionListener
   *          the clientSessionListener to set
   */
  void setClientSessionListener(ClientCCASessionListener clientSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerCCASessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param serverSessionListener
   *          the serverSessionListener to set
   */
  void setServerSessionListener(ServerCCASessionListener serverSessionListener);

  /**
   * @return the serverContextListener
   */
  IServerCCASessionContext getServerContextListener();

  /**
   * @param serverContextListener
   *          the serverContextListener to set
   */
  void setServerContextListener(IServerCCASessionContext serverContextListener);

  /**
   * @return the clientContextListener
   */
  IClientCCASessionContext getClientContextListener();

  /**
   * @return the messageFactory
   */
  ICCAMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *          the messageFactory to set
   */
  void setMessageFactory(ICCAMessageFactory messageFactory);

  /**
   * @param clientContextListener
   *          the clientContextListener to set
   */
  void setClientContextListener(IClientCCASessionContext clientContextListener);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener
   *          the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);
}
