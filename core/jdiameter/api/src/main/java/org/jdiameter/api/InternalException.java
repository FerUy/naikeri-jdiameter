package org.jdiameter.api;

/**
 * An exception that provides information on a stack  error or other errors.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public class InternalException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public InternalException() {
    super();
  }

  /**
   * Constructor with reason string
   * @param message reason string
   */
  public InternalException(String message) {
    super(message);
  }

  /**
   * Constructor with reason string and parent exception
   * @param message message reason string
   * @param cause parent exception
   */
  public InternalException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with parent exception
   * @param cause  parent exception
   */
  public InternalException(Throwable cause) {
    super(cause);
  }

}
