package org.jdiameter.common.api.app.gx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.gx.ClientGxSessionListener;
import org.jdiameter.api.gx.ServerGxSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter Gx Application (Gx).
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface IGxSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientGxSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has
   * similar effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param clientSessionListener
   *          the clientSessionListener to set
   */
  void setClientSessionListener(ClientGxSessionListener clientSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerGxSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar
   * effect as setting this directly in app session.
   * However clustered session use this value when recreated!
   *
   * @param serverSessionListener
   *          the serverSessionListener to set
   */
  void setServerSessionListener(ServerGxSessionListener serverSessionListener);

  /**
   * @return the serverContextListener
   */
  IServerGxSessionContext getServerContextListener();

  /**
   * @param serverContextListener
   *          the serverContextListener to set
   */
  void setServerContextListener(IServerGxSessionContext serverContextListener);

  /**
   * @return the clientContextListener
   */
  IClientGxSessionContext getClientContextListener();

  /**
   * @return the messageFactory
   */
  IGxMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *          the messageFactory to set
   */
  void setMessageFactory(IGxMessageFactory messageFactory);

  /**
   * @param clientContextListener
   *          the clientContextListener to set
   */
  void setClientContextListener(IClientGxSessionContext clientContextListener);

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
