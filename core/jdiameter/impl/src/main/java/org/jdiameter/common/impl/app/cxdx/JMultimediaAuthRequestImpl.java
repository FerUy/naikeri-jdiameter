package org.jdiameter.common.impl.app.cxdx;

import org.jdiameter.api.Message;
import org.jdiameter.api.cxdx.events.JMultimediaAuthRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class JMultimediaAuthRequestImpl extends AppRequestEventImpl implements  JMultimediaAuthRequest {

  private static final long serialVersionUID = 1L;

  /**
   *
   * @param message
   */
  public JMultimediaAuthRequestImpl(Message message) {
    super(message);
    message.setRequest(true);
  }

}
