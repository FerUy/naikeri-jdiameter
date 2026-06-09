package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.s6c.ClientS6cSession;
import org.jdiameter.api.s6c.ServerS6cSession;
import org.jdiameter.client.impl.app.s6c.ClientS6cSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.s6c.IS6cSessionData;
import org.jdiameter.server.impl.app.s6c.ServerS6cSessionDataLocalImpl;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class S6cLocalSessionDataFactory implements IAppSessionDataFactory<IS6cSessionData> {

  public IS6cSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientS6cSession.class)) {
      ClientS6cSessionDataLocalImpl data = new ClientS6cSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else if (clazz.equals(ServerS6cSession.class)) {
      ServerS6cSessionDataLocalImpl data = new ServerS6cSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else {
      throw new IllegalArgumentException("Invalid Session Class: " + clazz);
    }
  }
}
