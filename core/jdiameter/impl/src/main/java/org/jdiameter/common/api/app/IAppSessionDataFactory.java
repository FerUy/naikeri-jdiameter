package org.jdiameter.common.api.app;

import org.jdiameter.api.app.AppSession;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAppSessionDataFactory<T extends IAppSessionData> {

  T getAppSessionData(Class<? extends AppSession> clazz, String sessionId);

}
