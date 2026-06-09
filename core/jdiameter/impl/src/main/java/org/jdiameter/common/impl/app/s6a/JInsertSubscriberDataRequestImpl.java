package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Message;
import org.jdiameter.api.s6a.events.JInsertSubscriberDataRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JInsertSubscriberDataRequestImpl extends AppRequestEventImpl implements JInsertSubscriberDataRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JInsertSubscriberDataRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
