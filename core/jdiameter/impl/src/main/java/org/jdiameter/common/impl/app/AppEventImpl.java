package org.jdiameter.common.impl.app;

import static org.jdiameter.api.Avp.ORIGIN_HOST;
import static org.jdiameter.api.Avp.ORIGIN_REALM;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Message;
import org.jdiameter.api.app.AppEvent;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class AppEventImpl implements AppEvent {

  private static final long serialVersionUID = 1L;
  protected Message message;

  public AppEventImpl(Message message) {
    this.message = message;
  }

  @Override
  public int getCommandCode() {
    return message.getCommandCode();
  }

  @Override
  public Message getMessage() throws InternalException {
    return message;
  }

  @Override
  public String getOriginHost() throws AvpDataException {
    Avp originHostAvp = message.getAvps().getAvp(ORIGIN_HOST);
    if (originHostAvp != null) {
      return originHostAvp.getDiameterIdentity();
    }
    else {
      throw new AvpDataException("Avp ORIGIN_HOST not found");
    }
  }

  @Override
  public String getOriginRealm() throws AvpDataException {
    Avp originRealmAvp = message.getAvps().getAvp(ORIGIN_REALM);
    if (originRealmAvp != null) {
      return originRealmAvp.getDiameterIdentity();
    }
    else {
      throw new AvpDataException("Avp ORIGIN_REALM not found");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AppEventImpl that = (AppEventImpl) o;

    return message.equals(that.message);
  }

  @Override
  public int hashCode() {
    return message.hashCode();
  }

  @Override
  public String toString() {
    return message != null ? message.toString() : "empty";
  }
}
