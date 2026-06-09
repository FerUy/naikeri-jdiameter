package org.jdiameter.common.impl.app.ro;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.ro.ClientRoSession;
import org.jdiameter.api.ro.ServerRoSession;
import org.jdiameter.client.impl.app.ro.ClientRoSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.ro.IRoSessionData;
import org.jdiameter.server.impl.app.ro.ServerRoSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class RoLocalSessionDataFactory implements IAppSessionDataFactory<IRoSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IRoSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientRoSession.class)) {
      ClientRoSessionDataLocalImpl data = new ClientRoSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerRoSession.class)) {
      ServerRoSessionDataLocalImpl data = new ServerRoSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
