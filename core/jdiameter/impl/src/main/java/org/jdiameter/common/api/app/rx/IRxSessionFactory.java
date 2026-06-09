package org.jdiameter.common.api.app.rx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.rx.ClientRxSessionListener;
import org.jdiameter.api.rx.ServerRxSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter 3GPP IMS Rx Reference Point.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 */
public interface IRxSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientRxSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param clientSessionListener
   *          the clientSessionListener to set
   */
  void setClientSessionListener(ClientRxSessionListener clientSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerRxSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param serverSessionListener
   *          the serverSessionListener to set
   */
  void setServerSessionListener(ServerRxSessionListener serverSessionListener);

  /**
   * @return the serverContextListener
   */
  IServerRxSessionContext getServerContextListener();

  /**
   * @param serverContextListener
   *          the serverContextListener to set
   */
  void setServerContextListener(IServerRxSessionContext serverContextListener);

  /**
   * @return the clientContextListener
   */
  IClientRxSessionContext getClientContextListener();

  /**
   * @return the messageFactory
   */
  IRxMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *          the messageFactory to set
   */
  void setMessageFactory(IRxMessageFactory messageFactory);

  /**
   * @param clientContextListener
   *          the clientContextListener to set
   */
  void setClientContextListener(IClientRxSessionContext clientContextListener);

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
