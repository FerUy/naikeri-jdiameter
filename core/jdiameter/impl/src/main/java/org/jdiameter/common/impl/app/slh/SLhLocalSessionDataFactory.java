package org.jdiameter.common.impl.app.slh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slh.ClientSLhSession;
import org.jdiameter.api.slh.ServerSLhSession;
import org.jdiameter.client.impl.app.slh.ClientSLhSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.slh.ISLhSessionData;
import org.jdiameter.server.impl.app.slh.ServerSLhSessionDataLocalImpl;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public class SLhLocalSessionDataFactory implements IAppSessionDataFactory<ISLhSessionData> {

  public ISLhSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientSLhSession.class)) {
      ClientSLhSessionDataLocalImpl data = new ClientSLhSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else if (clazz.equals(ServerSLhSession.class)) {
      ServerSLhSessionDataLocalImpl data = new ServerSLhSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else {
      throw new IllegalArgumentException("Invalid Session Class: " + clazz.toString());
    }
  }
}