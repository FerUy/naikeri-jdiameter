package org.jdiameter.common.api.app;

import org.jdiameter.api.ApplicationId;

/**
 * Interface for Application Session Data
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAppSessionData {

  int NON_INITIALIZED = Integer.MIN_VALUE;

  /**
   * Returns the session-id of the session to which this data belongs to.
   * @return a string representing the session-id
   */
  String getSessionId();

  /**
   * Sets the Application-Id of this Session Data session to which this data belongs to.
   * @param applicationId the Application-Id
   */
  void setApplicationId(ApplicationId applicationId);

  /**
   * Returns the Application-Id of this Session Data session to which this data belongs to.
   *
   * @return the Application-Id
   */
  ApplicationId getApplicationId();

  /**
   * Removes this session data from storage
   *
   * @return true if removed, false otherwise
   */
  boolean remove();

}
