package org.jdiameter.common.impl.app.acc;

import static org.jdiameter.api.Avp.ACC_RECORD_NUMBER;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.acc.events.AccountAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;

/**
 *
 * erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AccountAnswerImpl extends AppAnswerEventImpl implements AccountAnswer {

  private static final long serialVersionUID = 1L;

  public AccountAnswerImpl(Request request, int accountRecordType, int accReqNumber, long resultCode) {
    this(request.createAnswer(resultCode));
    try {
      getMessage().getAvps().addAvp(Avp.ACC_RECORD_TYPE, accountRecordType);
      getMessage().getAvps().addAvp(Avp.ACC_RECORD_NUMBER, accReqNumber);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * @param answer
   */
  public AccountAnswerImpl(Answer answer) {
    super(answer);
  }

  /**
   * @param request
   * @param vendorId
   * @param resultCode
   */
  public AccountAnswerImpl(Request request, long vendorId, long resultCode) {
    super(request, vendorId, resultCode);
  }

  /**
   * @param request
   * @param resultCode
   */
  public AccountAnswerImpl(Request request, long resultCode) {
    super(request, resultCode);
  }

  /**
   * @param request
   */
  public AccountAnswerImpl(Request request) {
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
    Avp accRecordNumberAvp =  message.getAvps().getAvp(ACC_RECORD_NUMBER);
    if (accRecordNumberAvp != null) {
      return accRecordNumberAvp.getUnsigned32();
    }
    else {
      throw new AvpDataException("Avp ACC_RECORD_NUMBER not found");
    }
  }
}
