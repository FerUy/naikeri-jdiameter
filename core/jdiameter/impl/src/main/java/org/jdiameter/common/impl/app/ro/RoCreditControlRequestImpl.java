package org.jdiameter.common.impl.app.ro;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.ro.events.RoCreditControlRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ...
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class RoCreditControlRequestImpl extends AppRequestEventImpl implements RoCreditControlRequest {

  private static final long serialVersionUID = 1L;

  protected static final Logger logger = LoggerFactory.getLogger(RoCreditControlRequestImpl.class);

  private static final int REQUESTED_ACTION_AVP_CODE = 436;
  private static final int CC_REQUEST_TYPE_AVP_CODE = 416;

  public RoCreditControlRequestImpl(AppSession session, String destRealm, String destHost) {
    super(session.getSessions().get(0).createRequest(code, session.getSessionAppId(), destRealm, destHost));
  }

  public RoCreditControlRequestImpl(Request request) {
    super(request);
  }

  @Override
  public boolean isRequestedActionAVPPresent() {
    return super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE) != null;
  }

  @Override
  public int getRequestedActionAVPValue() {
    Avp requestedActionAvp = super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE);
    if (requestedActionAvp != null) {
      try {
        return requestedActionAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Requested-Action AVP value", e);
      }
    }

    return -1;
  }

  @Override
  public boolean isRequestTypeAVPPresent() {
    return super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE) != null;
  }

  @Override
  public int getRequestTypeAVPValue() {
    Avp requestTypeAvp = super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE);
    if (requestTypeAvp != null) {
      try {
        return requestTypeAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain CC-Request-Type AVP value", e);
      }
    }

    return -1;
  }

}
