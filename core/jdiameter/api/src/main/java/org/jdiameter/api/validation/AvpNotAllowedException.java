package org.jdiameter.api.validation;

/**
 * Class to indicate error in AVP add operation.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AvpNotAllowedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private int avpCode = -1;
  private long vendorId = -1L;

  /**
   *
   */
  public AvpNotAllowedException(int code, long vendor) {
    this.avpCode = code;
    this.vendorId = vendor;
  }

  /**
   *
   * @param message
   */
  public AvpNotAllowedException(String message, int code, long vendor) {
    super(message);
    this.avpCode = code;
    this.vendorId = vendor;
  }

  /**
   *
   * @param cause
   */
  public AvpNotAllowedException(Throwable cause, int code, long vendor) {
    super(cause);
    this.avpCode = code;
    this.vendorId = vendor;
  }

  /**
   *
   * @param message
   * @param cause
   */
  public AvpNotAllowedException(String message, Throwable cause, int code, long vendor) {
    super(message, cause);
    this.avpCode = code;
    this.vendorId = vendor;
  }

  public int getAvpCode() {
    return avpCode;
  }

  public long getVendorId() {
    return vendorId;
  }

  @Override
  public String toString() {
    return "AvpNotAllowedException [avpCode=" + avpCode + ", vendorId=" + vendorId + ", toString()=" + super.toString() + "]";
  }

}
