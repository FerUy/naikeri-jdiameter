package org.jdiameter.common.api.app.sgd;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.sgd.ClientSGdSessionListener;
import org.jdiameter.api.sgd.ServerSGdSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public interface ISGdSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @return the ServerSGdSessionListener instance
   */
  ServerSGdSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @param serverSGdSessionListener the serverSessionListener to set
   */
  void setServerSessionListener(ServerSGdSessionListener serverSGdSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @return the ClientSGdSessionListener instance
   */
  ClientSGdSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect as setting this directly in app session.
   * However, clustered session use this value when recreated!
   *
   * @param clientSGdSessionListener the clientSessionListener to set
   */
  void setClientSessionListener(ClientSGdSessionListener clientSGdSessionListener);

  /**
   * @return the ISGdMessageFactory instance
   */
  ISGdMessageFactory getMessageFactory();

  /**
   * @param messageFactory the ISGdMessageFactory instance to set
   */
  void setMessageFactory(ISGdMessageFactory messageFactory);

  /**
   * @return the stateListener
   */
  StateChangeListener<AppSession> getStateListener();

  /**
   * @param stateListener the stateListener to set
   */
  void setStateListener(StateChangeListener<AppSession> stateListener);
}
