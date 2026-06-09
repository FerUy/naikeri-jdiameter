package org.jdiameter.common.api.app.ro;

import org.jdiameter.api.Message;
import org.jdiameter.api.ro.ClientRoSession;

/**
 * Diameter Ro Application Client Additional listener
 * Actions for FSM
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IClientRoSessionContext {

  long getDefaultTxTimerValue();

  void txTimerExpired(ClientRoSession session);

  int getDefaultCCFHValue();

  int getDefaultDDFHValue();

  void grantAccessOnDeliverFailure(ClientRoSession clientCCASessionImpl, Message request);

  void denyAccessOnDeliverFailure(ClientRoSession clientCCASessionImpl, Message request);

  void grantAccessOnTxExpire(ClientRoSession clientCCASessionImpl);

  void denyAccessOnTxExpire(ClientRoSession clientCCASessionImpl);

  void grantAccessOnFailureMessage(ClientRoSession clientCCASessionImpl);

  void denyAccessOnFailureMessage(ClientRoSession clientCCASessionImpl);

  void indicateServiceError(ClientRoSession clientCCASessionImpl);

}
