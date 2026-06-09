package org.jdiameter.common.impl.app.acc;

import org.jdiameter.api.acc.ClientAccSession;
import org.jdiameter.api.acc.ServerAccSession;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.client.impl.app.acc.ClientAccSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.acc.IAccSessionData;
import org.jdiameter.server.impl.app.acc.ServerAccSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AccLocalSessionDataFactory implements IAppSessionDataFactory<IAccSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IAccSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientAccSession.class)) {
      ClientAccSessionDataLocalImpl data = new ClientAccSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerAccSession.class)) {
      ServerAccSessionDataLocalImpl data = new ServerAccSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
