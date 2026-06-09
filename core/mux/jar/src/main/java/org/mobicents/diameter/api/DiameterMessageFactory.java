package org.mobicents.diameter.api;

import org.jdiameter.api.Message;

/**
 *
 * DiameterMessageFactory.java
 *
 * @version 1.0
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface DiameterMessageFactory {
  /**
   * Creates a new Diameter Message (request or answer, depending on boolean flag)
   * with the specified Command Code and Application Id
   *
   * @param isRequest
   * @param commandCode
   * @param applicationId
   * @return
   */
  Message createMessage(boolean isRequest, int commandCode, long applicationId);

  /**
   * Creates a new Diameter Message (Request) with the specified Command Code and
   * Application Id
   *
   * @param commandCode
   * @param applicationId
   * @return
   */
  Message createRequest(int commandCode, long applicationId);

  /**
   * Creates a new Diameter Message (Answer) with the specified Command Code and
   * Application Id
   *
   * @param commandCode
   * @param applicationId
   * @return
   */
  Message createAnswer(int commandCode, long applicationId);

}
