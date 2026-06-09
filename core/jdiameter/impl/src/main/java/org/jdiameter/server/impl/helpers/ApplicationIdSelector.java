package org.jdiameter.server.impl.helpers;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Message;
import org.jdiameter.api.Selector;
import org.jdiameter.client.api.IMessage;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ApplicationIdSelector implements Selector<Message, ApplicationId> {

  private ApplicationId applicationId;

  public ApplicationIdSelector(ApplicationId applicationId) {
    if (applicationId == null) {
      throw new IllegalArgumentException("Please set application id");
    }

    this.applicationId = applicationId;
  }

  @Override
  public boolean checkRule(Message message) {
    return message != null && ((IMessage) message).getSingleApplicationId().equals(applicationId);
  }

  @Override
  public ApplicationId getMetaData() {
    return applicationId;
  }
}
