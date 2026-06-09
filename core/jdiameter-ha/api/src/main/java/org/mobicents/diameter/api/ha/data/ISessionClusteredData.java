package org.mobicents.diameter.api.ha.data;

import org.jdiameter.api.BaseSession;
import org.jdiameter.api.NetworkReqListener;

/**
 * Interface for Session Clustered Data.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface ISessionClusteredData {

  void setSession(BaseSession s);
  BaseSession getSession();

  NetworkReqListener getSessionListener();
  void setSessionListener(NetworkReqListener lst);
}
