package org.jdiameter.client.impl;

import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.Message;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RawSession;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.Session;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RawSessionImpl extends BaseSessionImpl implements RawSession {

  RawSessionImpl(IContainer stack) {
    container = stack;
    this.parser = container.getAssemblerFacility().
        getComponentInstance(IMessageParser.class);
  }

  @Override
  public  Message createMessage(int commandCode, ApplicationId appId, Avp... avps) {
    if ( isValid ) {
      setLastAccessTime();
      IMessage m = parser.createEmptyMessage(commandCode, getAppId(appId));
      m.getAvps().addAvp(avps);
      appendAppId(appId, m);
      return m;
    } else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public Message createMessage(int commandCode, ApplicationId appId, long hopByHopIdentifier, long endToEndIdentifier, Avp... avps) {
    if ( isValid ) {
      setLastAccessTime();
      IMessage m = parser.createEmptyMessage(commandCode, getAppId(appId));
      if (hopByHopIdentifier >= 0) {
        m.setHopByHopIdentifier(-hopByHopIdentifier);
      }
      if (endToEndIdentifier >= 0) {
        m.setEndToEndIdentifier(endToEndIdentifier);
      }
      m.getAvps().addAvp(avps);
      appendAppId(appId, m);
      return m;
    } else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public Message createMessage(Message message, boolean copyAvps) {
    if ( isValid ) {
      setLastAccessTime();
      IMessage newMessage = null;
      IMessage inner = (IMessage) message;
      if (copyAvps) {
        newMessage = parser.createEmptyMessage(inner);
        MessageUtility.addOriginAvps(newMessage, container.getMetaData());
      } else {
        newMessage = (IMessage) createMessage(
            inner.getCommandCode(),
            inner.getSingleApplicationId(),
            -1,
            -1
            );
      }
      newMessage.setRequest(message.isRequest());
      newMessage.setProxiable(message.isProxiable());
      newMessage.setError(message.isError());
      newMessage.setReTransmitted(message.isReTransmitted());
      return newMessage;

    } else {
      throw new IllegalStateException("Session already released");
    }
  }

  @Override
  public void send(Message message, EventListener<Message, Message> listener)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    genericSend(message,  listener, true);
  }

  @Override
  public void send(Message message, EventListener<Message, Message> listener, long timeOut, TimeUnit timeUnit)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    genericSend(message,  listener, timeOut, timeUnit, true);
  }

  @Override
  public void release() {
    isValid = false;
    container = null;
    parser = null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws InternalException {
    return iface == Session.class;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws InternalException {
    return (T) (iface == Session.class ?  new SessionImpl(container) : null);
  }
}
