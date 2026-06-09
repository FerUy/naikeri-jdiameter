package org.jdiameter.client.impl.parser;

import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownServiceException;
import java.util.Date;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class AvpImpl implements Avp {

  private static final long serialVersionUID = 1L;
  private static final ElementParser parser = new ElementParser();
  int avpCode;
  long vendorID;

  boolean isMandatory;
  boolean isEncrypted;
  boolean isVendorSpecific;

  byte[] rawData = new byte[0];
  AvpSet groupedData;

  private static final Logger logger = LoggerFactory.getLogger(AvpImpl.class);

  AvpImpl(int code, int flags, long vnd, byte[] data) {
    avpCode  = code;
    //
    isMandatory = (flags & 0x40) != 0;
    isEncrypted = (flags & 0x20) != 0;
    isVendorSpecific = (flags & 0x80) != 0;
    //
    vendorID = vnd;
    rawData  = data;
  }

  AvpImpl(Avp avp) {
    avpCode     = avp.getCode();
    vendorID    = avp.getVendorId();
    isMandatory = avp.isMandatory();
    isEncrypted = avp.isEncrypted();
    isVendorSpecific = avp.isVendorId();
    try {
      rawData = avp.getRaw();
      if (rawData == null || rawData.length == 0) {
        groupedData = avp.getGrouped();
      }
    }
    catch (AvpDataException e) {
      logger.debug("Can not create Avp", e);
    }
  }

  AvpImpl (int newCode, Avp avp) {
    this(avp);
    avpCode = newCode;
  }

  @Override
  public int getCode() {
    return avpCode;
  }

  @Override
  public boolean isVendorId() {
    return isVendorSpecific;
  }

  @Override
  public boolean isMandatory() {
    return isMandatory;
  }

  @Override
  public boolean isEncrypted() {
    return isEncrypted;
  }

  @Override
  public long getVendorId() {
    return vendorID;
  }

  @Override
  public byte[] getRaw() throws AvpDataException {
    return rawData;
  }

  @Override
  public byte[] getOctetString() throws AvpDataException {
    return rawData;
  }

  @Override
  public String getUTF8String() throws AvpDataException {
    try {
      return parser.bytesToUtf8String(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public int getInteger32() throws AvpDataException {
    try {
      return parser.bytesToInt(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public long getInteger64() throws AvpDataException {
    try {
      return parser.bytesToLong(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public long getUnsigned32() throws AvpDataException {
    try {
      byte[] u32ext = new byte[8];
      System.arraycopy(rawData, 0, u32ext, 4, 4);
      return parser.bytesToLong(u32ext);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public long getUnsigned64() throws AvpDataException {
    try {
      return parser.bytesToLong(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public float getFloat32() throws AvpDataException {
    try {
      return parser.bytesToFloat(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public double getFloat64() throws AvpDataException {
    try {
      return parser.bytesToDouble(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public InetAddress getAddress() throws AvpDataException {
    try {
      return parser.bytesToAddress(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public Date getTime() throws AvpDataException {
    try {
      return parser.bytesToDate(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public String getDiameterIdentity() throws AvpDataException {
    try {
      return parser.bytesToOctetString(rawData);
    }
    catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public URI getDiameterURI() throws AvpDataException {
    try {
      return new URI(parser.bytesToOctetString(rawData));
    } catch (URISyntaxException | UnknownServiceException e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public AvpSet getGrouped() throws AvpDataException {
    try {
      if (groupedData == null) {
        groupedData = parser.decodeAvpSet(rawData);
        rawData = new byte[0];
      }
      return groupedData;
    } catch (Exception e) {
      throw new AvpDataException(e, this);
    }
  }

  @Override
  public boolean isWrapperFor(Class<?> aClass) throws InternalException {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> aClass) throws InternalException {
    return null;
  }

  @Override
  public byte[] getRawData() {
    return (rawData == null || rawData.length == 0) ? parser.encodeAvpSet(groupedData) : rawData;
  }

  // Caching toString... Avp shouldn't be modified once created.
  private String toString;

  @Override
  public String toString() {
    if (toString == null) {
      this.toString = "AvpImpl [avpCode=" + avpCode + ", vendorID=" + vendorID +
              ", len=" + ((rawData != null) ? rawData.length : null) + "]@" + super.hashCode();
    }

    return this.toString;
  }
}
