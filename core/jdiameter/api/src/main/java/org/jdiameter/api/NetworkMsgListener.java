package org.jdiameter.api;

/**
 *
 * @author joram.herrera2@gmail.com
 * @version 1.5.1 Final
 */
public interface NetworkMsgListener {

  /**
   * Interface for message pre-processing
   * @param message
   * @return message
   */
  Message processMessage(Message message);
}
