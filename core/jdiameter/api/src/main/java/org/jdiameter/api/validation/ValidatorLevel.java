package org.jdiameter.api.validation;

/**
 * Represents possible levels for Diameter Validator
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @since 1.5.4.0-build404
 */
public class ValidatorLevel {

  public static final String _OFF = "OFF";
  public static final String _MESSAGE = "MESSAGE";
  public static final String _ALL = "ALL";

  public static final ValidatorLevel OFF = new ValidatorLevel(_OFF);
  public static final ValidatorLevel MESSAGE = new ValidatorLevel(_MESSAGE);
  public static final ValidatorLevel ALL = new ValidatorLevel(_ALL);

  private String name = null;

  private ValidatorLevel(String name) {
    super();
    this.name = name;
  }

  public static ValidatorLevel fromString(String s) throws IllegalArgumentException {
    if (s.toUpperCase().equals(_OFF)) {
      return OFF;
    }
    if (s.toUpperCase().equals(_MESSAGE)) {
      return MESSAGE;
    }
    if (s.toUpperCase().equals(_ALL)) {
      return ALL;
    }
    throw new IllegalArgumentException("No level for such value: " + s);
  }

  @Override
  public String toString() {
    return "ValidatorLevel [name=" + name + "]";
  }

}
