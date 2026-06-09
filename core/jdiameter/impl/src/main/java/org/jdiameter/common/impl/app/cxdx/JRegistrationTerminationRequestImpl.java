package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Message;
import org.jdiameter.api.cxdx.events.JRegistrationTerminationRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JRegistrationTerminationRequestImpl extends AppRequestEventImpl implements JRegistrationTerminationRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JRegistrationTerminationRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
