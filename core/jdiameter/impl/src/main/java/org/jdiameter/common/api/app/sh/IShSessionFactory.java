package org.jdiameter.common.api.app.sh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.sh.ClientShSessionListener;
import org.jdiameter.api.sh.ServerShSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Diameter Sh Session Factory
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IShSessionFactory extends IAppSessionFactory {

  void setClientShSessionListener(ClientShSessionListener v);

  ClientShSessionListener getClientShSessionListener();

  void setServerShSessionListener(ServerShSessionListener v);

  ServerShSessionListener getServerShSessionListener();

  void setStateChangeListener(StateChangeListener<AppSession> v);

  StateChangeListener<AppSession> getStateChangeListener();

  void setMessageFactory(IShMessageFactory factory);

  IShMessageFactory getMessageFactory();

}
