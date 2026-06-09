package org.jdiameter.common.api.app.rf;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.Request;
import org.jdiameter.api.rf.ClientRfSession;

/**
 * Diameter Accounting Client Additional listener
 * Actions for FSM
 *
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IClientRfActionContext {

  /**
   * Filling nested avp into interim message
   * @param interimRequest instance of interim message which will be sent to server
   */
  void interimIntervalElapses(ClientRfSession appSession, Request interimRequest) throws InternalException;

  /**
   * Call back for failed_send_record event
   * @param accRequest accounting request record
   * @return true if you want put message to buffer and false if you want to stop processing
   */
  boolean failedSendRecord(ClientRfSession appSession, Request accRequest) throws InternalException;

  /**
   * Filling nested avp into STR
   * @param sessionTermRequest instance of STR which will be sent to server
   */
  void disconnectUserOrDev(ClientRfSession appSession, Request sessionTermRequest) throws InternalException;
}
