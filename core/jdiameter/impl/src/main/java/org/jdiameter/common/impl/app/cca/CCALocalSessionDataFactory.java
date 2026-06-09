package org.jdiameter.common.impl.app.cca;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.cca.ClientCCASession;
import org.jdiameter.api.cca.ServerCCASession;
import org.jdiameter.client.impl.app.cca.ClientCCASessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.cca.ICCASessionData;
import org.jdiameter.server.impl.app.cca.ServerCCASessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class CCALocalSessionDataFactory implements IAppSessionDataFactory<ICCASessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public ICCASessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientCCASession.class)) {
      ClientCCASessionDataLocalImpl data = new ClientCCASessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerCCASession.class)) {
      ServerCCASessionDataLocalImpl data = new ServerCCASessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
