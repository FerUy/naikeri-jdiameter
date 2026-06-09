package org.jdiameter.api.rf.events;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Accounting Answer (ACA) messages, indicated by the Command-Code field set to 271 is sent by
 * the CDF to the CTF in order to reply to the ACR.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface RfAccountingAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "ACA";
  String _LONG_NAME = "Accounting-Answer";

  int code = 271;

  /**
   * @return Record type of answer
   * @throws org.jdiameter.api.AvpDataException if result code avp is not integer
   */
  int getAccountingRecordType() throws AvpDataException;

  /**
   * @return record number
   * @throws AvpDataException if result code avp is not integer
   */
  long getAccountingRecordNumber() throws AvpDataException;

}
