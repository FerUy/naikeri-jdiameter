package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.Message;
import org.jdiameter.api.s6c.events.SendRoutingInfoForSMRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class SendRoutingInfoForSMRequestImpl extends AppRequestEventImpl implements SendRoutingInfoForSMRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(SendRoutingInfoForSMRequestImpl.class);

  public SendRoutingInfoForSMRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
