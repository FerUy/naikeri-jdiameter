package org.jdiameter.client.impl.app.rx;

import org.jdiameter.common.api.app.rx.ClientRxSessionState;
import org.jdiameter.common.api.app.rx.IRxSessionData;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IClientRxSessionData extends IRxSessionData {

  boolean isEventBased();

  void setEventBased(boolean b);

  boolean isRequestTypeSet();

  void setRequestTypeSet(boolean b);

  ClientRxSessionState getClientRxSessionState();

  void setClientRxSessionState(ClientRxSessionState state);

}
