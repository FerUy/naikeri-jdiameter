package org.jdiameter.common.impl.app.sh;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sh.ClientShSession;
import org.jdiameter.api.sh.ServerShSession;
import org.jdiameter.client.impl.app.sh.ShClientSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.sh.IShSessionData;
import org.jdiameter.server.impl.app.sh.ShServerSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ShLocalSessionDataFactory implements IAppSessionDataFactory<IShSessionData> {

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IShSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if (clazz.equals(ClientShSession.class)) {
      ShClientSessionDataLocalImpl data = new ShClientSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if (clazz.equals(ServerShSession.class)) {
      ShServerSessionDataLocalImpl data = new ShServerSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
