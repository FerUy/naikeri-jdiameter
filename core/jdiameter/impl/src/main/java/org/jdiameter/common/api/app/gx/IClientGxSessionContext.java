package org.jdiameter.common.api.app.gx;

import org.jdiameter.api.Message;
import org.jdiameter.api.gx.ClientGxSession;

/**
 * Diameter Gx Application Client Additional listener.
 * Actions for FSM
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface IClientGxSessionContext {

  long getDefaultTxTimerValue();

  void txTimerExpired(ClientGxSession session);

  int getDefaultCCFHValue();

  int getDefaultDDFHValue();

  void grantAccessOnDeliverFailure(ClientGxSession clientGxSessionImpl, Message request);

  void denyAccessOnDeliverFailure(ClientGxSession clientGxSessionImpl, Message request);

  void grantAccessOnTxExpire(ClientGxSession clientGxSessionImpl);

  void denyAccessOnTxExpire(ClientGxSession clientGxSessionImpl);

  void grantAccessOnFailureMessage(ClientGxSession clientGxSessionImpl);

  void denyAccessOnFailureMessage(ClientGxSession clientGxSessionImpl);

  void indicateServiceError(ClientGxSession clientGxSessionImpl);
}
