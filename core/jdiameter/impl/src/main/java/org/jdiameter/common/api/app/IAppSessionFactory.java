package org.jdiameter.common.api.app;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.app.AppSession;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAppSessionFactory {

  AppSession getNewSession(String sessionId, Class<? extends AppSession> aClass, ApplicationId applicationId, Object[] args);

  AppSession getSession(String sessionId, Class<? extends AppSession> aClass);
}
