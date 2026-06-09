package org.jdiameter.common.api.app.s13;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.s13.ClientS13SessionListener;
import org.jdiameter.api.s13.ServerS13SessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

public interface IS13SessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerS13SessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use this value when recreated!
   *
   * @param serverSessionListener the serverSessionListener to set
   */
  void setServerSessionListener(ServerS13SessionListener serverSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientS13SessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use this value when recreated!
   *
   * @param clientSessionListener the clientSessionListener to set
   */
  void setClientSessionListener(ClientS13SessionListener clientSessionListener);

  /**
   * @return the messageFactory
   */
  IS13MessageFactory getMessageFactory();

  /**
   * @param messageFactory the messageFactory to set
   */
  void setMessageFactory(IS13MessageFactory messageFactory);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);
}
