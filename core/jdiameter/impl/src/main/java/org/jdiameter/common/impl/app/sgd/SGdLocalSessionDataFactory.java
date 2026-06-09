package org.jdiameter.common.impl.app.sgd;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sgd.ClientSGdSession;
import org.jdiameter.api.sgd.ServerSGdSession;
import org.jdiameter.client.impl.app.sgd.ClientSGdSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.sgd.ISGdSessionData;
import org.jdiameter.server.impl.app.sgd.ServerSGdSessionDataLocalImpl;


/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SGdLocalSessionDataFactory implements IAppSessionDataFactory<ISGdSessionData> {

  public ISGdSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientSGdSession.class)) {
      ClientSGdSessionDataLocalImpl data = new ClientSGdSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else if (clazz.equals(ServerSGdSession.class)) {
      ServerSGdSessionDataLocalImpl data = new ServerSGdSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    } else {
      throw new IllegalArgumentException("Invalid Session Class: " + clazz);
    }
  }

}
