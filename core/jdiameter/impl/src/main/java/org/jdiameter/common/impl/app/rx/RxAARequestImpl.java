package org.jdiameter.common.impl.app.rx;

import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RxAARequestImpl extends AppRequestEventImpl implements RxAARequest {

  private static final long serialVersionUID = 1L;
  protected static final Logger logger = LoggerFactory.getLogger(RxAARequestImpl.class);

  public RxAARequestImpl(AppSession session, String destRealm, String destHost) {
    super(session.getSessions().get(0).createRequest(code, session.getSessionAppId(), destRealm, destHost));
  }

  public RxAARequestImpl(Request request) {
    super(request);
  }
}
