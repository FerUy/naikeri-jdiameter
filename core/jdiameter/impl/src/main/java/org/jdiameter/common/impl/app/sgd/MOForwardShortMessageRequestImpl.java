package org.jdiameter.common.impl.app.sgd;

import org.jdiameter.api.Message;
import org.jdiameter.api.sgd.events.MOForwardShortMessageRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class MOForwardShortMessageRequestImpl extends AppRequestEventImpl implements MOForwardShortMessageRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(MOForwardShortMessageRequestImpl.class);

  public MOForwardShortMessageRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }
}
