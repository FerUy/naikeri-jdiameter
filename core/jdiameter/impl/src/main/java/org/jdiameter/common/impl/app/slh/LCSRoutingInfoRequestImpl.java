package org.jdiameter.common.impl.app.slh;

import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdiameter.api.Message;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class LCSRoutingInfoRequestImpl extends AppRequestEventImpl implements LCSRoutingInfoRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(LCSRoutingInfoRequestImpl.class);

  public LCSRoutingInfoRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}