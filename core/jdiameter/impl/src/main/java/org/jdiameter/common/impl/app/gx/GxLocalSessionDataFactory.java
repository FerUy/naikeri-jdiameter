package org.jdiameter.common.impl.app.gx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.gx.ClientGxSession;
import org.jdiameter.api.gx.ServerGxSession;
import org.jdiameter.client.impl.app.gx.ClientGxSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.gx.IGxSessionData;
import org.jdiameter.server.impl.app.gx.ServerGxSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class GxLocalSessionDataFactory implements IAppSessionDataFactory<IGxSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IGxSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientGxSession.class)) {
      ClientGxSessionDataLocalImpl data = new ClientGxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerGxSession.class)) {
      ServerGxSessionDataLocalImpl data = new ServerGxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
