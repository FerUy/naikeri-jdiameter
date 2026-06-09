package org.jdiameter.client.impl.transport.tcp.netty;

import java.util.List;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.client.api.parser.IMessageParser;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 *
 * @author <a href="mailto:jqayyum@gmail.com"> Jehanzeb Qayyum </a>
 */
public class DiameterMessageDecoder extends ByteToMessageDecoder {
  protected final IMessageParser parser;
  protected final TCPClientConnection parentConnection;

  public DiameterMessageDecoder(TCPClientConnection parentConnection, IMessageParser parser) {
    this.parser = parser;
    this.parentConnection = parentConnection;
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    if (in.readableBytes() >= 4) {
      int first = in.getInt(in.readerIndex());
      byte version = (byte) (first >> 24);
      if (version != 1) {
        return;
      }

      int messageLength = (first & 0xFFFFFF);
      if (in.readableBytes() < messageLength) {
        return;
      }

      byte[] bytes = new byte[messageLength];
      in.readBytes(bytes);

      try {
        out.add(this.parser.createMessage(bytes));
      } catch (AvpDataException e) {
        this.parentConnection.onAvpDataException(e);
      }
    }
  }

}
