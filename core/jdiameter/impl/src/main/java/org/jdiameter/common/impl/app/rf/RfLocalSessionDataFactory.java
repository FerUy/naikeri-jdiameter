package org.jdiameter.common.impl.app.rf;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rf.ClientRfSession;
import org.jdiameter.api.rf.ServerRfSession;
import org.jdiameter.client.impl.app.rf.ClientRfSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.rf.IRfSessionData;
import org.jdiameter.server.impl.app.rf.ServerRfSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class RfLocalSessionDataFactory implements IAppSessionDataFactory<IRfSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IRfSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientRfSession.class)) {
      ClientRfSessionDataLocalImpl data = new ClientRfSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerRfSession.class)) {
      ServerRfSessionDataLocalImpl data = new ServerRfSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
