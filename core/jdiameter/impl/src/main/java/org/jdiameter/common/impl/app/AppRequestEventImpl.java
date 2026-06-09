package org.jdiameter.common.impl.app;

import static org.jdiameter.api.Avp.DESTINATION_HOST;
import static org.jdiameter.api.Avp.DESTINATION_REALM;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Message;
import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AppRequestEventImpl extends AppEventImpl implements AppRequestEvent {

  private static final long serialVersionUID = 1L;

  public AppRequestEventImpl(Message message) {
    super(message);
  }

  @Override
  public String getDestinationHost() throws AvpDataException {
    Avp destHostAvp = message.getAvps().getAvp(DESTINATION_HOST);
    if (destHostAvp  != null) {
      return destHostAvp.getDiameterIdentity();
    }
    else {
      throw new AvpDataException("Avp DESTINATION_HOST not found");
    }
  }

  @Override
  public String getDestinationRealm() throws AvpDataException {
    Avp destRealmAvp = message.getAvps().getAvp(DESTINATION_REALM);
    if (destRealmAvp != null) {
      return destRealmAvp.getDiameterIdentity();
    }
    else {
      throw new AvpDataException("Avp DESTINATION_REALM not found");
    }
  }
}
