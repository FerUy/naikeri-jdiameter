package org.jdiameter.common.impl.app.rx;

import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rx.events.RxReAuthRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RxReAuthRequestImpl extends AppRequestEventImpl implements RxReAuthRequest {

  private static final long serialVersionUID = 1L;
  protected static final Logger logger = LoggerFactory.getLogger(RxReAuthRequestImpl.class);

  public RxReAuthRequestImpl(AppSession session, String destRealm, String destHost) {
    super(session.getSessions().get(0).createRequest(code, session.getSessionAppId(), destRealm, destHost));
  }

  public RxReAuthRequestImpl(Request request) {
    super(request);
  }
}
