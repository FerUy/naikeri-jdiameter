package org.jdiameter.api.acc.events;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppAnswerEvent;

/**
 * An Answer message is sent by a recipient of Request once it has received and
 * interpreted the Request.
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface AccountAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "ACA";
  String _LONG_NAME = "Accounting-Answer";

  int code = 271;

  /**
   * @return Record type of answer
   * @throws org.jdiameter.api.AvpDataException
   *           if result code avp is not integer
   */
  int getAccountingRecordType() throws AvpDataException;

  /**
   * @return record number
   * @throws AvpDataException
   *           if result code avp is not integer
   */
  long getAccountingRecordNumber() throws AvpDataException;

}
