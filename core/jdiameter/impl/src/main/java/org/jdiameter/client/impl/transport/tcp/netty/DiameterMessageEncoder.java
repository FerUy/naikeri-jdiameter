package org.jdiameter.client.impl.transport.tcp.netty;

import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author <a href="mailto:jqayyum@gmail.com"> Jehanzeb Qayyum </a>
 */
public class DiameterMessageEncoder extends MessageToByteEncoder<IMessage> {
  protected final IMessageParser parser;

  public DiameterMessageEncoder(IMessageParser parser) {
    this.parser = parser;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
    out.writeBytes(Unpooled.wrappedBuffer(parser.encodeMessage(msg)));
  }

}
