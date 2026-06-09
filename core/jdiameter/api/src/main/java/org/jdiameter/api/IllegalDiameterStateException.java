package org.jdiameter.api;

/**
 * Signals that a method has been invoked at an illegal or
 * inappropriate time.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public class IllegalDiameterStateException extends Exception {

  private static final long serialVersionUID = 1L;

  public IllegalDiameterStateException() {
  }

  public IllegalDiameterStateException(String message) {
    super(message);
  }

  public IllegalDiameterStateException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalDiameterStateException(Throwable cause) {
    super(cause);
  }
}
