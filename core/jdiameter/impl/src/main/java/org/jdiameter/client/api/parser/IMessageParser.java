package org.jdiameter.client.api.parser;

import java.nio.ByteBuffer;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.client.api.IMessage;

/**
 * Basic interface for diameter message parsers.
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IMessageParser {

  /**
   * Create message from bytebuffer
   * @param data message bytebuffer
   * @return instance of message
   * @throws AvpDataException
   */
  IMessage createMessage(ByteBuffer data) throws AvpDataException;

  /**
   * Create message from byte array
   * @param data message byte array
   * @return instance of message
   * @throws AvpDataException
   */
  IMessage createMessage(byte[] message) throws AvpDataException;

  /**
   * Created specified type of message
   * @param iface type of message
   * @param data message bytebuffer
   * @return instance of message
   * @throws AvpDataException
   */
  <T> T createMessage(java.lang.Class<?> iface, ByteBuffer data) throws AvpDataException;

  /**
   * Created empty message
   * @param commandCode message command code
   * @param headerAppId header applicatio id
   * @return instance of message
   */
  IMessage createEmptyMessage(int commandCode, long headerAppId);

  /**
   * Created specified type of message
   * @param iface type of message
   * @param commandCode message command code
   * @param headerAppId header applicatio id
   * @return instance of message
   */
  <T> T createEmptyMessage(Class<?> iface, int commandCode, long headerAppId);

  /**
   * Created new message with copied of header of parent message
   * @param parentMessage parent message
   * @return instance of message
   */
  IMessage createEmptyMessage(IMessage parentMessage);

  /**
   * Created new message with copied of header of parent message
   * @param parentMessage parent message
   * @param commandCode new command code value
   * @return instance of message
   */
  IMessage createEmptyMessage(IMessage parentMessage, int commandCode);

  /**
   * Created new message with copied of header of parent message
   * @param iface type of message
   * @param parentMessage parent message
   * @return  instance of message
   */
  <T> T createEmptyMessage(Class<?> iface, IMessage parentMessage);

  /**
   * Encode message to ByteBuffer
   * @param message diameter message
   * @return instance of message
   * @throws ParseException
   */
  ByteBuffer encodeMessage(IMessage message) throws ParseException;

}
