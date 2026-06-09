package org.jdiameter.common.api.app.s6a;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.s6a.ClientS6aSessionListener;
import org.jdiameter.api.s6a.ServerS6aSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter S6a application.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface IS6aSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerS6aSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @param serverSessionListener
   *            the serverSessionListener to set
   */
  void setServerSessionListener(ServerS6aSessionListener serverSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientS6aSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @param clientSessionListener
   *            the clientSessionListener to set
   */
  void setClientSessionListener(ClientS6aSessionListener clientSessionListener);

  /**
   * @return the messageFactory
   */
  IS6aMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *            the messageFactory to set
   */
  void setMessageFactory(IS6aMessageFactory messageFactory);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener
   *            the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);

}
