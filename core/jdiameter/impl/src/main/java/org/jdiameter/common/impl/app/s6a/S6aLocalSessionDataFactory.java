package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6a.ClientS6aSession;
import org.jdiameter.api.s6a.ServerS6aSession;
import org.jdiameter.client.impl.app.s6a.ClientS6aSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.s6a.IS6aSessionData;
import org.jdiameter.server.impl.app.s6a.ServerS6aSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public class S6aLocalSessionDataFactory implements IAppSessionDataFactory<IS6aSessionData> {

  /*
   * (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IS6aSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientS6aSession.class)) {
      ClientS6aSessionDataLocalImpl data = new ClientS6aSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerS6aSession.class)) {
      ServerS6aSessionDataLocalImpl data = new ServerS6aSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else {
      throw new IllegalArgumentException("Invalid Session Class: " + clazz.toString());
    }
  }

}
