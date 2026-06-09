package org.jdiameter.common.impl.app.slg;

import org.jdiameter.api.Message;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public class ProvideLocationRequestImpl extends AppRequestEventImpl implements ProvideLocationRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(ProvideLocationRequestImpl.class);

  public ProvideLocationRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}