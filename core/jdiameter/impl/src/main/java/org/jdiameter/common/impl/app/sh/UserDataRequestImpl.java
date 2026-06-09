package org.jdiameter.common.impl.app.sh;

import org.jdiameter.api.Request;
import org.jdiameter.api.sh.events.UserDataRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class UserDataRequestImpl extends AppRequestEventImpl implements UserDataRequest {

  private static final long serialVersionUID = 1L;

  public UserDataRequestImpl(Request request) {
    super(request);
  }

}