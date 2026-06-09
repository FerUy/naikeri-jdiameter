package org.jdiameter.api.validation;

import java.util.Map;

import org.jdiameter.api.AvpSet;
import org.jdiameter.api.Message;

/**
 * This class represents message/command in validation framework. It contains
 * basic info about command along with avp list - their multiplicity and
 * allowance.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @since 1.5.4.0-build404
 */
public interface MessageRepresentation {

  Map<AvpRepresentation, AvpRepresentation> getMessageAvps();

  int getCommandCode();

  long getApplicationId();

  boolean isRequest();

  String getName();

  /**
   * Fetches Avp representation for given code. If no such AVP is found,
   * <b>null</b> value is returned. AVP must be top level AVP to be fetched by
   * this operation.
   *
   * @param code
   *            - positive integer, equal to AVP code.
   * @return AvpRepresentation for given code or <b>null</b>
   */
  AvpRepresentation getAvp(int code);

  /**
   * Fetches Avp representation for given code and vendorId. If no such AVP is
   * found, <b>null</b> value is returned. AVP must be top level AVP to be
   * fetched by this operation.
   *
   * @param code
   *            - positive integer, equal to AVP code.
   * @param vendorId
   *            - positive long representing vendor code.
   * @return AvpRepresentation for given code/vendor pair or <b>null</b>
   */
  AvpRepresentation getAvp(int code, long vendorId);

  boolean isCountValidForMultiplicity(int code, int avpCount);

  boolean isCountValidForMultiplicity(int code, long vendorId, int avpCount);

  boolean isCountValidForMultiplicity(AvpSet destination, int code, long vendorId, int numberToAdd);

  boolean isCountValidForMultiplicity(AvpSet destination, int code, int numberToAdd);

  boolean isCountValidForMultiplicity(AvpSet destination, int code, long vendorId);

  boolean isCountValidForMultiplicity(AvpSet destination, int code);

  boolean isAllowed(int avpCode, long vendorId);

  boolean isAllowed(int avpCode);

  void validate(Message msg, ValidatorLevel validatorLevel) throws AvpNotAllowedException;

}
