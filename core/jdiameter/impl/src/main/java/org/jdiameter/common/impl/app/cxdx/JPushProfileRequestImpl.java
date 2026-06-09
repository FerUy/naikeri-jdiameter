package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Message;
import org.jdiameter.api.cxdx.events.JPushProfileRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JPushProfileRequestImpl extends AppRequestEventImpl implements JPushProfileRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JPushProfileRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
