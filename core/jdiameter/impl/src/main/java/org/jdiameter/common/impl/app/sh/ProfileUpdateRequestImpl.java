package org.jdiameter.common.impl.app.sh;

import org.jdiameter.api.Request;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ProfileUpdateRequestImpl extends AppRequestEventImpl implements ProfileUpdateRequest {

  private static final long serialVersionUID = 1L;

  public ProfileUpdateRequestImpl(Request request) {
    super(request);
  }
}
