package org.jdiameter.client.api.io;

/**
 * This enumeration describe types on network errors
 * These types help to more details define behaviour of high layers
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum TransportError {
  /**
   * Internal error (network layer exceptions)
   */
  Internal,
  /**
   * Overload errors (overload netowrk queues)
   */
  Overload,
  /**
   * Error during send message procedure (special type)
   */
  FailedSendMessage,
  /**
   * Received broken message (special type)
   */
  ReceivedBrokenMessage,
  /**
   * Network error (io exceptions)
   */
  NetWorkError
}
