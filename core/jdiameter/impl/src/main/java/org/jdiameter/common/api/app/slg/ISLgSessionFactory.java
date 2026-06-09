package org.jdiameter.common.api.app.slg;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.slg.ClientSLgSessionListener;
import org.jdiameter.api.slg.ServerSLgSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;
// import org.jdiameter.common.api.app.slg.ISLgMessageFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ISLgSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerSLgSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param serverSessionListener the serverSessionListener to set
   */
  void setServerSessionListener(ServerSLgSessionListener serverSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientSLgSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param clientSessionListener the clientSessionListener to set
   */
  void setClientSessionListener(ClientSLgSessionListener clientSessionListener);

  /**
   * @return the messageFactory
   */
  ISLgMessageFactory getMessageFactory();

  /**
   * @param messageFactory the messageFactory to set
   */
  void setMessageFactory(ISLgMessageFactory messageFactory);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);

}