package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Message;
import org.jdiameter.api.s6a.events.JDeleteSubscriberDataRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JDeleteSubscriberDataRequestImpl extends AppRequestEventImpl implements JDeleteSubscriberDataRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JDeleteSubscriberDataRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
