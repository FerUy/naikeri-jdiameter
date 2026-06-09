package org.jdiameter.api;

/**
 * The NoRouteException signals that no route exist for a given realm.
 *
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public class RouteException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor with reason string
   * @param message reason string
   */
  public RouteException(String message) {
    super(message);
  }

  /**
   * Constructor with reason string and parent exception
   * @param message message reason string
   * @param cause parent exception
   */
  public RouteException(String message, Throwable cause) {
    super(message, cause);
  }
}
