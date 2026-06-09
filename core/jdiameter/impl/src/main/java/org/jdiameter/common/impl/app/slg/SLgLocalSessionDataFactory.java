package org.jdiameter.common.impl.app.slg;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slg.ClientSLgSession;
import org.jdiameter.api.slg.ServerSLgSession;
import org.jdiameter.client.impl.app.slg.ClientSLgSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.slg.ISLgSessionData;
import org.jdiameter.server.impl.app.slg.ServerSLgSessionDataLocalImpl;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class SLgLocalSessionDataFactory implements IAppSessionDataFactory<ISLgSessionData> {

  public ISLgSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientSLgSession.class)) {
      ClientSLgSessionDataLocalImpl data = new ClientSLgSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else if (clazz.equals(ServerSLgSession.class)) {
      ServerSLgSessionDataLocalImpl data = new ServerSLgSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else {
      throw new IllegalArgumentException("Invalid Session Class: " + clazz.toString());
    }
  }
}