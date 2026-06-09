package org.jdiameter.client.api.parser;

/**
 * Signals that an parser exception has occurred in a during decoding message
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ParseException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Create instance of class
   */
  public ParseException() {
  }

  /**
   * Create instance of class with predefined parameters
   * @param message error message
   */
  public ParseException(String message) {
    super(message);
  }

  /**
   * Create instance of class with predefined parameters
   * @param message error message
   * @param cause error cause
   */
  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Create instance of class with predefined parameters
   * @param cause error cause
   */
  public ParseException(Throwable cause) {
    super(cause);
  }
}
