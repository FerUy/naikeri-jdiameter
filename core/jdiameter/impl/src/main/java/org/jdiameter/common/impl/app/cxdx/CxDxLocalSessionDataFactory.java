package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.cxdx.ClientCxDxSession;
import org.jdiameter.api.cxdx.ServerCxDxSession;
import org.jdiameter.client.impl.app.cxdx.ClientCxDxSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.cxdx.ICxDxSessionData;
import org.jdiameter.server.impl.app.cxdx.ServerCxDxSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class CxDxLocalSessionDataFactory implements IAppSessionDataFactory<ICxDxSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public ICxDxSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientCxDxSession.class)) {
      ClientCxDxSessionDataLocalImpl data = new ClientCxDxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerCxDxSession.class)) {
      ServerCxDxSessionDataLocalImpl data = new ServerCxDxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
