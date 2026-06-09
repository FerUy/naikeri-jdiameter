package org.jdiameter.common.api.data;

import org.jdiameter.api.BaseSession;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.common.api.app.IAppSessionData;
import org.jdiameter.common.api.app.IAppSessionDataFactory;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ISessionDatasource {

  void start();

  void stop();

  boolean exists(String sessionId);

  NetworkReqListener getSessionListener(String sessionId);

  void setSessionListener(String sessionId, NetworkReqListener data);

  NetworkReqListener removeSessionListener(String sessionId);

  void removeSession(String sessionId);

  void addSession(BaseSession session);

  BaseSession getSession(String sessionId);

  boolean isClustered();

  IAppSessionDataFactory<? extends IAppSessionData> getDataFactory(Class<? extends IAppSessionData> x);

}
