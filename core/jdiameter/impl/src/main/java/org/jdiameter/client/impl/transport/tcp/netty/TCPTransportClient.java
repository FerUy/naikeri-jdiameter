package org.jdiameter.client.impl.transport.tcp.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 *
 * @author <a href="mailto:jqayyum@gmail.com"> Jehanzeb Qayyum </a>
 */
public class TCPTransportClient {
  protected EventLoopGroup workerGroup;
  protected EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(1);
  protected Channel channel;
  protected TCPClientConnection parentConnection;
  protected InetSocketAddress destAddress;
  protected InetSocketAddress sourceAddress; // TODO: what?
  protected String socketDescription;
  protected static final Logger logger = LoggerFactory.getLogger(TCPTransportClient.class);
  protected IMessageParser parser;

  protected static final int CONNECT_TIMEOUT = 500; // mills

  protected TCPTransportClient(TCPClientConnection parentConnection, IMessageParser parser) {
    if (parentConnection == null) {
      throw new IllegalArgumentException("Parent connection cannot be null");
    }
    this.parentConnection = parentConnection;

    if (parser == null) {
      throw new IllegalArgumentException("Parser cannot be null");
    }
    this.parser = parser;
  }

  public TCPTransportClient(TCPClientConnection parentConnection, IMessageParser parser, InetSocketAddress destAddress,
      InetSocketAddress sourceAddress) {
    this(parentConnection, parser);

    logger.debug("Client only connection");

    if (destAddress == null && sourceAddress == null) {
      throw new IllegalArgumentException("Either Destination or Source address is required");
    }

    if (sourceAddress != null) {
      this.sourceAddress = sourceAddress;
    }

    if (destAddress != null) {
      this.destAddress = destAddress;
      this.socketDescription = destAddress.toString();
    }
  }

  public TCPTransportClient(TCPClientConnection parentConnection, IMessageParser parser, Channel channel) {
    this(parentConnection, parser);
    logger.debug("Server only connection");

    if (channel == null) {
      throw new IllegalArgumentException("Channel cannot be null");
    }
    this.channel = channel;
    ChannelPipeline pipeline = this.channel.pipeline();
    pipeline.addLast("decoder", new DiameterMessageDecoder(parentConnection, parser));
    pipeline.addLast("encoder", new DiameterMessageEncoder(parser));
    pipeline.addLast(eventExecutorGroup, "msgHandler", new DiameterMessageHandler(parentConnection));

    this.destAddress = (InetSocketAddress) this.channel.remoteAddress();
  }

  public void start() throws InterruptedException {
    logger.debug("Starting TCP Transport on [{}]", socketDescription);
    if (isConnected()) {
      logger.debug("TCP Transport already started, [{}]", socketDescription);
      return;
    }
    this.workerGroup = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap().group(workerGroup).channel(NioSocketChannel.class)
              .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast("decoder", new DiameterMessageDecoder(parentConnection, parser));
                  pipeline.addLast("encoder", new DiameterMessageEncoder(parser));
                  pipeline.addLast(eventExecutorGroup, "msgHandler", new DiameterMessageHandler(parentConnection));
                }
              });

      this.channel = bootstrap.localAddress(sourceAddress).remoteAddress(destAddress).connect().sync().channel();
      logger.debug("TCP Transport connected successfully, [{}]", socketDescription);
      parentConnection.onConnected();
    } catch (Exception e) {
      logger.error("Error while starting TCP Transport on [{}]", socketDescription);
      if (this.channel != null && this.channel.isOpen()) {
        this.channel.close();
      }
      workerGroup.shutdownGracefully();
      throw e;
    }
  }

  public void stop() {
    logger.debug("Stopping TCP Transport, [{}]", socketDescription);
    if (!isConnected()) {
      logger.debug("Already stoppped TCP Transport, [{}]", socketDescription);
      return;
    }
    closeChannel();
    closeWorkerGroup();
    closeEventExecutorGroup();
    logger.debug("Transport is stopped [{}]", socketDescription);
  }

  private void closeEventExecutorGroup() {
    if (eventExecutorGroup != null) {
      eventExecutorGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS)
              .addListener(future -> {
                if (!future.isSuccess()) {
                  logger.warn("Error shutting down eventExecutorGroup", future.cause());
                }
              });
      eventExecutorGroup = null;
    }
  }

  private void closeWorkerGroup() {
    if (workerGroup != null) {
      workerGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS)
              .addListener(future -> {
                if (!future.isSuccess()) {
                  logger.warn("Error shutting down workerGroup", future.cause());
                }
              });
      workerGroup = null;
    }
  }

  private void closeChannel() {
    if (channel != null) {
      channel.close().addListener(future -> {
        if (!future.isSuccess()) {
          logger.warn("Error closing channel for [{}]", socketDescription, future.cause());
        }
      });
      channel = null;
    }
  }

  public void release() {
    logger.debug("Releasing TCP Transport, [{}]", socketDescription);
    stop();
    destAddress = null;
    sourceAddress = null;
  }

  public void sendMessage(IMessage message) {
    if (!isConnected()) {
      throw new IllegalStateException("TCP transport is stopped on socket " + socketDescription);
    }
    channel.writeAndFlush(message);
  }

  public String toString() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("Transport to ");
    if (this.destAddress != null) {
      buffer.append(this.destAddress.getHostName());
      buffer.append(":");
      buffer.append(this.destAddress.getPort());
    } else {
      buffer.append("null");
    }
    buffer.append("@");
    buffer.append(super.toString());
    return buffer.toString();
  }

  public TCPClientConnection getParent() {
    return parentConnection;
  }

  public InetSocketAddress getDestAddress() {
    return this.destAddress;
  }

  boolean isConnected() {
    return channel != null && channel.isActive();
  }
}