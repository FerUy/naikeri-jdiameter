package org.jdiameter.common.impl.app.rx;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rx.ClientRxSession;
import org.jdiameter.api.rx.ServerRxSession;
import org.jdiameter.client.impl.app.rx.ClientRxSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.rx.IRxSessionData;
import org.jdiameter.server.impl.app.rx.ServerRxSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RxLocalSessionDataFactory implements IAppSessionDataFactory<IRxSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IRxSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientRxSession.class)) {
      ClientRxSessionDataLocalImpl data = new ClientRxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerRxSession.class)) {
      ServerRxSessionDataLocalImpl data = new ServerRxSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
