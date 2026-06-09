package org.jdiameter.common.impl.app.s6a;

import org.jdiameter.api.Message;
import org.jdiameter.api.s6a.events.JAuthenticationInformationRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public class JAuthenticationInformationRequestImpl extends AppRequestEventImpl implements  JAuthenticationInformationRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JAuthenticationInformationRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
