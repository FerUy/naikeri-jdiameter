package org.jdiameter.common.api.app;

import org.jdiameter.api.ApplicationId;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class AppSessionDataLocalImpl implements IAppSessionData {

  private String sessionId;
  private ApplicationId applicationId;

  @Override
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public ApplicationId getApplicationId() {
    return applicationId;
  }

  @Override
  public void setApplicationId(ApplicationId applicationId) {
    this.applicationId = applicationId;
  }

  @Override
  public boolean remove() {
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((applicationId == null) ? 0 : applicationId.hashCode());
    result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AppSessionDataLocalImpl other = (AppSessionDataLocalImpl) obj;
    if (applicationId == null) {
      if (other.applicationId != null) {
        return false;
      }
    } else if (!applicationId.equals(other.applicationId)) {
      return false;
    }
    if (sessionId == null) {
      if (other.sessionId != null) {
        return false;
      }
    }
    else if (!sessionId.equals(other.sessionId)) {
      return false;
    }

    return true;
  }

}
