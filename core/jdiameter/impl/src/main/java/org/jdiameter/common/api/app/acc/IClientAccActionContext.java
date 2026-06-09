package org.jdiameter.common.api.app.acc;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.Request;
import org.jdiameter.api.acc.ClientAccSession;

/**
 * Diameter Accounting Client Additional listener
 * Actions for FSM
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IClientAccActionContext {

  /**
   * Filling nested avp into interim message
   * @param interimRequest instance of interim message which will be sent to server
   */
  void interimIntervalElapses(ClientAccSession appSession, Request interimRequest) throws InternalException;

  /**
   * Call back for failed_send_record event
   * @param accRequest accounting request record
   * @return true if you want put message to buffer and false if you want to stop processing
   */
  boolean failedSendRecord(ClientAccSession appSession, Request accRequest) throws InternalException;

  /**
   * Filling nested avp into STR
   * @param sessionTermRequest instance of STR which will be sent to server
   */
  void disconnectUserOrDev(ClientAccSession appSession, Request sessionTermRequest) throws InternalException;
}
