package org.jdiameter.common.api.app.auth;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.auth.ClientAuthSessionListener;
import org.jdiameter.api.auth.ServerAuthSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Interface for Diameter Authentication Session Factories
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAuthSessionFactory extends IAppSessionFactory {

  IAuthMessageFactory getMessageFactory();

  void setMessageFactory(IAuthMessageFactory v);

  ServerAuthSessionListener getServerSessionListener();

  void setServerSessionListener(ServerAuthSessionListener v);

  StateChangeListener<AppSession> getStateListener();

  void setStateListener(StateChangeListener<AppSession> v);

  ClientAuthSessionListener getClientSessionListener();

  void setClientSessionListener(ClientAuthSessionListener v);

  IServerAuthActionContext getServerSessionContext();

  void setServerSessionContext(IServerAuthActionContext v);

  IClientAuthActionContext getClientSessionContext();

  void setClientSessionContext(IClientAuthActionContext v);

  boolean isStateles();

  void setStateles(boolean stateless);

  /**
   * @return the messageTimeout
   */
  long getMessageTimeout();

  /**
   * @param messageTimeout
   *            the messageTimeout to set
   */
  void setMessageTimeout(long messageTimeout);

}
