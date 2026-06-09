package org.jdiameter.api;

/**
 * The AvpDataException signals invalid operations on Avp data.
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class AvpDataException extends Exception {

  private static final long serialVersionUID = -5898449417016355792L;

  protected Avp avp;

  /**
   * Default constructor
   */
  public AvpDataException(Avp avp) {
    super();
    this.avp = avp;
  }

  /**
   * Constructor with reason string
   * @param message reason string
   */
  public AvpDataException(String message, Avp avp) {
    super(message);
    this.avp = avp;
  }

  /**
   * Constructor with reason string and parent exception
   * @param message message reason string
   * @param cause parent exception
   */
  public AvpDataException(String message, Throwable cause, Avp avp) {
    super(message, cause);
    this.avp = avp;
  }

  /**
   * Constructor with parent exception
   * @param cause  parent exception
   */
  public AvpDataException(Throwable cause, Avp avp) {
    super(cause);
    this.avp = avp;
  }

  /**
   * Default constructor
   */
  public AvpDataException() {
    super();
  }

  /**
   * Constructor with reason string
   * @param message reason string
   */
  public AvpDataException(String message) {
    super(message);
  }

  /**
   * Constructor with reason string and parent exception
   * @param message message reason string
   * @param cause parent exception
   */
  public AvpDataException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with parent exception
   * @param cause  parent exception
   */
  public AvpDataException(Throwable cause) {
    super(cause);
  }

  public Avp getAvp() {
    return avp;
  }

}
