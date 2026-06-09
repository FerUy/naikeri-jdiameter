package org.jdiameter.common.api.app.ro;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.ro.ClientRoSessionListener;
import org.jdiameter.api.ro.ServerRoSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter Ro Application.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IRoSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientRoSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param clientSessionListener
   *          the clientSessionListener to set
   */
  void setClientSessionListener(ClientRoSessionListener clientSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerRoSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param serverSessionListener
   *          the serverSessionListener to set
   */
  void setServerSessionListener(ServerRoSessionListener serverSessionListener);

  /**
   * @return the serverContextListener
   */
  IServerRoSessionContext getServerContextListener();

  /**
   * @param serverContextListener
   *          the serverContextListener to set
   */
  void setServerContextListener(IServerRoSessionContext serverContextListener);

  /**
   * @return the clientContextListener
   */
  IClientRoSessionContext getClientContextListener();

  /**
   * @return the messageFactory
   */
  IRoMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *          the messageFactory to set
   */
  void setMessageFactory(IRoMessageFactory messageFactory);

  /**
   * @param clientContextListener
   *          the clientContextListener to set
   */
  void setClientContextListener(IClientRoSessionContext clientContextListener);

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
