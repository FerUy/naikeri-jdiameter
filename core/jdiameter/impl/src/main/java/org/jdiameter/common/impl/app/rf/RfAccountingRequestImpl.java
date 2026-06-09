package org.jdiameter.common.impl.app.rf;

import static org.jdiameter.api.Avp.ACC_RECORD_NUMBER;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.rf.events.RfAccountingRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RfAccountingRequestImpl extends AppRequestEventImpl implements RfAccountingRequest {

  private static final long serialVersionUID = 1L;

  public RfAccountingRequestImpl(AppSession session, int accountRecordType, int accReqNumber, String destRealm, String destHost) {
    super(session.getSessions().get(0).createRequest(code, session.getSessionAppId(), destRealm, destHost));
    try {
      getMessage().getAvps().addAvp(Avp.ACC_RECORD_TYPE, accountRecordType);
      getMessage().getAvps().addAvp(Avp.ACC_RECORD_NUMBER, accReqNumber);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  public RfAccountingRequestImpl(Request request) {
    super(request);
  }

  @Override
  public int getAccountingRecordType() throws AvpDataException {
    Avp accRecordTypeAvp = message.getAvps().getAvp(Avp.ACC_RECORD_TYPE);
    if (accRecordTypeAvp != null) {
      return accRecordTypeAvp.getInteger32();
    }
    else {
      throw new AvpDataException("Avp ACC_RECORD_TYPE not found");
    }
  }

  @Override
  public long getAccountingRecordNumber() throws AvpDataException {
    Avp accRecordNumberAvp = message.getAvps().getAvp(ACC_RECORD_NUMBER);
    if (accRecordNumberAvp != null) {
      return accRecordNumberAvp.getUnsigned32();
    }
    else {
      throw new AvpDataException("Avp ACC_RECORD_NUMBER not found");
    }
  }
}
