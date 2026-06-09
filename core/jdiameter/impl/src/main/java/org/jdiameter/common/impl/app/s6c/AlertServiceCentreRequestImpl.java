package org.jdiameter.common.impl.app.s6c;

import org.jdiameter.api.Message;
import org.jdiameter.api.s6c.events.AlertServiceCentreRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class AlertServiceCentreRequestImpl extends AppRequestEventImpl implements AlertServiceCentreRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(AlertServiceCentreRequestImpl.class);

  public AlertServiceCentreRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
