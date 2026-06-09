package org.jdiameter.common.api.app.slh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.slh.ClientSLhSessionListener;
import org.jdiameter.api.slh.ServerSLhSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ISLhSessionFactory extends IAppSessionFactory {

  /**
    * Get stack wide listener for sessions. In local mode it has similar effect
    * as setting this directly in app session. However clustered session use this value when recreated!
    *
    * @return the serverSessionListener
    */
  ServerSLhSessionListener getServerSessionListener();

  /**
    * Set stack wide listener for sessions. In local mode it has similar effect
    * as setting this directly in app session. However clustered session use this value when recreated!
    *
    * @param serverSessionListener the serverSessionListener to set
    */
  void setServerSessionListener(ServerSLhSessionListener serverSessionListener);

  /**
    * Get stack wide listener for sessions. In local mode it has similar effect
    * as setting this directly in app session. However clustered session use this value when recreated!
    *
    * @return the clientSessionListener
    */
  ClientSLhSessionListener getClientSessionListener();

  /**
    * Set stack wide listener for sessions. In local mode it has similar effect
    * as setting this directly in app session. However clustered session use this value when recreated!
    *
    * @param clientSessionListener the clientSessionListener to set
    */
  void setClientSessionListener(ClientSLhSessionListener clientSessionListener);

  /**
    * @return the messageFactory
    */
  ISLhMessageFactory getMessageFactory();

  /**
    * @param messageFactory the messageFactory to set
    */
  void setMessageFactory(ISLhMessageFactory messageFactory);

  /**
    * @return the stateListener
    */
  StateChangeListener<AppSession> getStateListener();

  /**
    * @param stateListener the stateListener to set
    */
  void setStateListener(StateChangeListener<AppSession> stateListener);

}