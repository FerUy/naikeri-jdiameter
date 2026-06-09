package org.jdiameter.common.impl.app.auth;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.auth.ClientAuthSession;
import org.jdiameter.api.auth.ServerAuthSession;
import org.jdiameter.client.impl.app.auth.ClientAuthSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.auth.IAuthSessionData;
import org.jdiameter.server.impl.app.auth.ServerAuthSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AuthLocalSessionDataFactory implements IAppSessionDataFactory<IAuthSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IAuthSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientAuthSession.class)) {
      ClientAuthSessionDataLocalImpl data = new ClientAuthSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerAuthSession.class)) {
      ServerAuthSessionDataLocalImpl data = new ServerAuthSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
