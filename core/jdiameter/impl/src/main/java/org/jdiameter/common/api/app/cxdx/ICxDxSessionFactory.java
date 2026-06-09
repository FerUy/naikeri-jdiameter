package org.jdiameter.common.api.app.cxdx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.cxdx.ClientCxDxSessionListener;
import org.jdiameter.api.cxdx.ServerCxDxSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Session Factory interface for Diameter CxDx application.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ICxDxSessionFactory extends IAppSessionFactory {

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @return the clientSessionListener
   */
  ClientCxDxSessionListener getClientSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @param clientSessionListener
   *            the clientSessionListener to set
   */
  void setClientSessionListener(ClientCxDxSessionListener clientSessionListener);

  /**
   * Get stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @return the serverSessionListener
   */
  ServerCxDxSessionListener getServerSessionListener();

  /**
   * Set stack wide listener for sessions. In local mode it has similar effect
   * as setting this directly in app session. However clustered session use
   * this value when recreated!
   *
   * @param serverSessionListener
   *            the serverSessionListener to set
   */
  void setServerSessionListener(ServerCxDxSessionListener serverSessionListener);

  /**
   * @return the messageFactory
   */
  ICxDxMessageFactory getMessageFactory();

  /**
   * @param messageFactory
   *            the messageFactory to set
   */
  void setMessageFactory(ICxDxMessageFactory messageFactory);

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
