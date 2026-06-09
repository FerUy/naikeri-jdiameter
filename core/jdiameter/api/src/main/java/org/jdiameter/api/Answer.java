package org.jdiameter.api;

/**
 * An Answer message is sent by a recipient of Request once it has received and
 * interpreted the Request.
 * Answers contain a Result-Code AVP and other AVPs in message body.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface Answer extends Message {

  /**
   * @return ResultCode Avp from message
   */
  Avp getResultCode();

}
