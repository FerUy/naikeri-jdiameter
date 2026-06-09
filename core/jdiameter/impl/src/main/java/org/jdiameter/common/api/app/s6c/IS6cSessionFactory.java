package org.jdiameter.common.api.app.s6c;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.s6c.ClientS6cSessionListener;
import org.jdiameter.api.s6c.ServerS6cSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface IS6cSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @return the ServerS6cSessionListener instance
   */
  ServerS6cSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @param serverS6cSessionListener the serverSessionListener to set
   */
  void setServerSessionListener(ServerS6cSessionListener serverS6cSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @return the ClientS6cSessionListener instance
   */
  ClientS6cSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @param clientS6cSessionListener the clientSessionListener to set
   */
  void setClientSessionListener(ClientS6cSessionListener clientS6cSessionListener);

  /**
   * @return the IS6cMessageFactory instance
   */
  IS6cMessageFactory getMessageFactory();

  /**
   * @param messageFactory the IS6cMessageFactory instance to set
   */
  void setMessageFactory(IS6cMessageFactory messageFactory);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);

}
