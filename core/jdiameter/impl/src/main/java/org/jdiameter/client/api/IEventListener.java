package org.jdiameter.client.api;

import org.jdiameter.api.Answer;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.Request;

/**
 * This interface describe extends methods of base class
 * Data: $Date: 2008/07/03 19:43:10 $
 * Revision: $Revision: 1.1 $
 * @version 1.5.0.1
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IEventListener extends EventListener<Request, Answer> {

  /**
   * Set value of valid field
   * @param value true is listener yet valid
   */
  void setValid(boolean value);

  /**
   * Return rue if event listener valid(session is not released)
   * @return rue if event listener valid
   */
  boolean isValid();
}
