package org.jdiameter.common.api.app.rx;

import org.jdiameter.api.Message;
import org.jdiameter.api.rx.ClientRxSession;

/**
 * Diameter 3GPP IMS Rx Reference Point Client Additional listener.
 * Actions for FSM
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 */
public interface IClientRxSessionContext {

  void grantAccessOnDeliverFailure(ClientRxSession clientCCASessionImpl, Message request);

  void denyAccessOnDeliverFailure(ClientRxSession clientCCASessionImpl, Message request);

  void grantAccessOnFailureMessage(ClientRxSession clientCCASessionImpl);

  void denyAccessOnFailureMessage(ClientRxSession clientCCASessionImpl);

  void indicateServiceError(ClientRxSession clientCCASessionImpl);
}
