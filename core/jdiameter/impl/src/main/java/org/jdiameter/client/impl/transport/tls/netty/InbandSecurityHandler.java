package org.jdiameter.client.impl.transport.tls.netty;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.client.api.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 *
 * @author <a href="mailto:jqayyum@gmail.com"> Jehanzeb Qayyum </a>
 */
public class InbandSecurityHandler extends ChannelOutboundHandlerAdapter {
  protected static final Logger logger = LoggerFactory.getLogger(InbandSecurityHandler.class);

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    logger.debug("InbandSecurityHandler");
    IMessage message = (IMessage) msg;
    if (message.getCommandCode() == IMessage.CAPABILITIES_EXCHANGE_REQUEST
        || message.getCommandCode() == IMessage.CAPABILITIES_EXCHANGE_ANSWER) {
      logger.debug("Writing inband security for CER/CEA msg {}", message.getCommandCode());
      AvpSet set = message.getAvps();
      set.removeAvp(Avp.INBAND_SECURITY_ID);
      set.addAvp(Avp.INBAND_SECURITY_ID, 1);
    }
    ctx.write(msg, promise);
  }
}
