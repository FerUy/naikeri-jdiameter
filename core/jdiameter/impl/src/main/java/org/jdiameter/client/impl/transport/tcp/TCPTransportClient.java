package org.jdiameter.client.impl.transport.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.client.api.io.NotInitializedException;
import org.jdiameter.client.impl.parser.MessageParser;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class TCPTransportClient implements Runnable {

  private TCPClientConnection parentConnection;
  private IConcurrentFactory concurrentFactory;

  public static final String BYTE_BUFFER_SIZE_KEY = "org.restcomm.tcp.byteBufferSize";
  public static final String BYTE_BUFFER_SIZE_DEFAULT = "8192";
  public static final String BYTE_STORAGE_SIZE_KEY = "org.restcomm.tcp.byteStorageSize";
  public static final String BYTE_STORAGE_SIZE_DEFAULT = "16384";
  protected int bufferSize = 8192;
  protected int storageSize = 16384;
  protected ByteBuffer buffer;
  protected ByteBuffer storage;

  protected boolean stop = false;
  protected Thread selfThread;

  protected InetSocketAddress destAddress;
  protected InetSocketAddress origAddress;

  protected SocketChannel socketChannel;
  protected Lock lock = new ReentrantLock();

  private String socketDescription = null;

  private static final Logger logger = LoggerFactory.getLogger(TCPTransportClient.class);

  //PCB - allow non-blocking IO
  private static final boolean BLOCKING_IO = false;
  private static final long SELECT_TIMEOUT = 500; // milliseconds

  public TCPTransportClient() {
  }

  /**
   * Default constructor
   *
   * @param concurrentFactory factory for create threads
   * @param parentConnection connection created this transport
   */
  TCPTransportClient(IConcurrentFactory concurrentFactory, TCPClientConnection parentConnection) {
    this.parentConnection = parentConnection;
    this.concurrentFactory = concurrentFactory;
    this.bufferSize = Integer.parseInt(System.getProperty(BYTE_BUFFER_SIZE_KEY, BYTE_BUFFER_SIZE_DEFAULT));
    this.storageSize = Integer.parseInt(System.getProperty(BYTE_STORAGE_SIZE_KEY, BYTE_STORAGE_SIZE_DEFAULT));
    buffer = ByteBuffer.allocate(bufferSize);
    storage = ByteBuffer.allocate(storageSize);
  }

  /**
   *  Network init socket
   */
  public void initialize() throws IOException, NotInitializedException {
    logger.debug("Initialising TCPTransportClient. Origin address is [{}] and destination address is [{}]", origAddress, destAddress);
    if (destAddress == null) {
      throw new NotInitializedException("Destination address is not set");
    }
    socketChannel = SelectorProvider.provider().openSocketChannel();

    try {
      if (origAddress != null) {
        socketChannel.socket().bind(origAddress);
      }

      socketChannel.connect(destAddress);
      //PCB added logging
      socketChannel.configureBlocking(BLOCKING_IO);
      getParent().onConnected();
    }
    catch (IOException e) {
      if (origAddress != null) {
        socketChannel.socket().close();
      }
      socketChannel.close();
      throw e;
    }
  }

  public TCPClientConnection getParent() {
    return parentConnection;
  }

  public void initialize(Socket socket) throws IOException, NotInitializedException  {
    logger.debug("Initialising TCPTransportClient for a socket on [{}] with bufferSize [{}] and storageSize[{}]", socket, bufferSize, storageSize);
    socketDescription = socket.toString();
    socketChannel = socket.getChannel();
    //PCB added logging
    socketChannel.configureBlocking(BLOCKING_IO);
    destAddress = new InetSocketAddress(socket.getInetAddress(), socket.getPort());
  }

  public void start() throws NotInitializedException {
    // for client
    if (socketDescription == null && socketChannel != null) {
      socketDescription = socketChannel.socket().toString();
    }
    logger.debug("Starting transport. Socket is {}", socketDescription);
    if (socketChannel == null) {
      throw new NotInitializedException("Transport is not initialized");
    }
    if (!socketChannel.isConnected()) {
      throw new NotInitializedException("Socket channel is not connected");
    }
    if (getParent() == null) {
      throw new NotInitializedException("No parent connection is set is set");
    }
    if (selfThread == null || !selfThread.isAlive()) {
      selfThread = concurrentFactory.getThread("TCPReader", this);
    }

    if (!selfThread.isAlive()) {
      selfThread.setDaemon(true);
      selfThread.start();
    }
  }

  //PCB added logging
  @Override
  public void run() {
    // Workaround for Issue #4 (http://code.google.com/p/jdiameter/issues/detail?id=4)
    // BEGIN WORKAROUND // Give some time to initialization...
    int sleepTime = 250;
    logger.debug("Sleeping for {}ms before starting transport so that listeners can all be added and ready for messages", sleepTime);
    try {
      Thread.sleep(sleepTime);
    }
    catch (InterruptedException e) {
      // ignore
    }
    logger.debug("Finished sleeping for {}ms. By now, MutablePeerTableImpl should have added its listener", sleepTime);

    logger.debug("Transport is started. Socket is [{}]", socketDescription);
    Selector selector = null;
    try {
      selector = Selector.open();
      socketChannel.register(selector, SelectionKey.OP_READ);
      while (!stop) {
        selector.select(SELECT_TIMEOUT);
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        while (it.hasNext()) {
          // Get the selection key
          SelectionKey selKey = it.next();
          // Remove it from the list to indicate that it is being processed
          it.remove();
          if (selKey.isValid() && selKey.isReadable()) {
            // Get channel with bytes to read

            // The IDE shows a warning on the next line, DO NOT CHANGE IT!!!
            // The "Surround with try-with-resources block" suggestion from the IDE is a bug
            SocketChannel sChannel = (SocketChannel) selKey.channel();
            int dataLength = sChannel.read(buffer);
            logger.debug("Just read [{}] bytes on [{}]", dataLength, socketDescription);
            if (dataLength == -1) {
              stop = true;
              break;
            }
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            append(data);
            buffer.clear();
          }
        }
      }
    }
    catch (ClosedByInterruptException e) {
      logger.error("Transport exception ", e);
    }
    catch (AsynchronousCloseException e) {
      logger.error("Transport is closed");
    }
    catch (Throwable e) {
      logger.error("Transport exception ", e);
    }
    finally {
      try {
        clearBuffer();
        if (selector != null) {
          selector.close();
        }
        if (socketChannel != null && socketChannel.isOpen()) {
          socketChannel.close();
        }
        getParent().onDisconnect();
      }
      catch (Exception e) {
        logger.error("Error", e);
      }
      stop = false;
      logger.info("Read thread is stopped for socket [{}]", socketDescription);
    }
  }

  public void stop() throws Exception {
    logger.debug("Stopping transport. Socket is [{}]", socketDescription);
    stop = true;
    if (socketChannel != null && socketChannel.isOpen()) {
      socketChannel.close();
    }
    if (selfThread != null) {
      selfThread.join(100);
    }
    clearBuffer();
    logger.debug("Transport is stopped. Socket is [{}]", socketDescription);
  }

  public void release() throws Exception {
    stop();
    destAddress = null;
  }

  private void clearBuffer() {
    buffer = ByteBuffer.allocate(bufferSize);
  }

  public InetSocketAddress getDestAddress() {
    return this.destAddress;
  }

  public void setDestAddress(InetSocketAddress address) {
    this.destAddress = address;
    if (logger.isDebugEnabled()) {
      logger.debug("Destination address is set to [{}] : [{}]", destAddress.getHostName(), destAddress.getPort());
    }
  }

  public void setOrigAddress(InetSocketAddress address) {
    this.origAddress = address;
    if (logger.isDebugEnabled()) {
      logger.debug("Origin address is set to [{}] : [{}]", origAddress.getHostName(), origAddress.getPort());
    }
  }

  public InetSocketAddress getOrigAddress() {
    return this.origAddress;
  }

  public void sendMessage(ByteBuffer bytes) throws IOException {
    if (logger.isDebugEnabled()) {
      if (logger.isTraceEnabled()) {
        String hex = MessageParser.byteArrayToHexString(bytes.array());
        logger.trace("About to send a byte buffer of size [{}] over the TCP nio socket [{}]\n{}",
                bytes.array().length, socketDescription, hex);
      }
      else {
        logger.debug("About to send a byte buffer of size [{}] over the TCP nio socket [{}]", bytes.array().length, socketDescription);
      }
    }
    int rc = 0;
    // PCB - removed locking
    // ZhixiaoLuo: Fix #28, without the lock the data in the socketChannel will get mixed in multi-threads.
    lock.lock();
    try {
      while (rc < bytes.array().length) {
        rc += socketChannel.write(bytes);
      }
    }
    catch (Exception e) {
      logger.error("Unable to send message", e);
      throw new IOException("Error while sending message: " + e);
    }
    finally {
      lock.unlock();
    }
    if (rc == 0) {
      logger.error("socketChannel.write(bytes) - returned zero indicating that perhaps the write buffer is full");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Sent a byte buffer of size [{}] over the TCP nio socket [{}]", bytes.array().length, socketDescription);
    }
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("Transport to ");
    if (this.destAddress != null) {
      buffer.append(this.destAddress.getHostName());
      buffer.append(":");
      buffer.append(this.destAddress.getPort());
    }
    else {
      buffer.append("null");
    }
    buffer.append("@");
    buffer.append(super.toString());
    return buffer.toString();
  }

  boolean isConnected() {
    return socketChannel != null && socketChannel.isOpen() && socketChannel.isConnected();
  }

  /**
   * Adds data to storage
   *
   * @param data data to add
   */
  private void append(byte[] data) {
    if (storage.position() + data.length >= storage.capacity()) {
      ByteBuffer tmp = ByteBuffer.allocate(storage.limit() + data.length * 2);
      byte[] tmpData = new byte[storage.position()];
      storage.flip();
      storage.get(tmpData);
      tmp.put(tmpData);
      storage = tmp;
      logger.warn("Increase storage size. Current size is {}", storage.array().length);
    }

    try {
      storage.put(data);
    }
    catch (BufferOverflowException boe) {
      logger.error("Buffer overflow occurred", boe);
    }
    boolean messageReceived;
    do {
      messageReceived = seekMessage();
    } while (messageReceived);
  }

  private boolean seekMessage() {
    // make sure there's actual data written on the buffer
    if (storage.position() == 0) {
      return false;
    }

    storage.flip();
    try {
      // get first four bytes for version and message length
      // 0                   1                   2                   3
      // 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      // |    Version    |                 Message Length                |
      // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      int tmp = storage.getInt();
      // reset position so we can now read whole message
      storage.position(0);

      // check that version is 1, as per RFC 3588 - Section 3:
      // This Version field MUST be set to 1 to indicate Diameter Version 1
      byte version = (byte) (tmp >> 24);
      if (version != 1) {
        // ZhixiaoLuo: fix #28, if unlucky storage.limit < data.length(1024), then always failed to do storage.put(data)
        // ZhixiaoLuo: and get BufferOverflowException in append(data)
        storage.clear();
        logger.error("Invalid message version detected [" + version + "]");
        return false;
      }
      // extract the message length, so we know how much to read
      int messageLength = (tmp & 0xFFFFFF);

      // verify that we do have the whole message in the storage
      if (storage.limit() < messageLength) {
        // we don't have it all... let's restore buffer to receive more
        storage.position(storage.limit());
        storage.limit(storage.capacity());
        logger.debug("Received partial message, waiting for remaining (expected: {} bytes, got {} bytes).", messageLength, storage.position());
        return false;
      }

      // read the complete message
      byte[] data = new byte[messageLength];
      storage.get(data);
      storage.compact();

      try {
        // make a message out of data and process it
        logger.debug("Passing message on to parent");
        getParent().onMessageReceived(ByteBuffer.wrap(data));
        logger.debug("Finished passing message on to parent");
      }
      catch (AvpDataException e) {
        logger.debug("Garbage was received. Discarding.");
        storage.clear();
        getParent().onAvpDataException(e);
      }
    }
    catch (BufferUnderflowException bue) {
      // we don't have enough data to read message length... wait for more
      storage.position(storage.limit());
      storage.limit(storage.capacity());
      logger.debug("Buffer underflow occurred, waiting for more data.", bue);
      return false;
    }
    return true;
  }
}