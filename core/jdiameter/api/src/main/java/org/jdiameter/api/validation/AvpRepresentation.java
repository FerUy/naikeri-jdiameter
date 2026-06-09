package org.jdiameter.api.validation;

import java.util.List;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;

/**
 * Represents avp, it stores info about presence, multiplicity, avp
 * code, vendor.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @since 1.5.4.0-build404
 */
public interface AvpRepresentation {

  /**
   * <pre>
   * Represents multiplicity of AVP:
   * 0     The AVP MUST NOT be present in the message.
   * </pre>
   */
  String _MP_NOT_ALLOWED = "0";

  /**
   * <pre>
   * Represents multiplicity of AVP:
   * 0+    Zero or more instances of the AVP MAY be present in the message.
   * </pre>
   */
  String _MP_ZERO_OR_MORE = "0+";

  /**
   * <pre>
   * Represents multiplicity of AVP:
   * 0-1   Zero or one instance of the AVP MAY be present in the message.
   *       It is considered an error if there are more than one instance of the AVP.
   * </pre>
   */
  String _MP_ZERO_OR_ONE = "0-1";

  /**
   * <pre>
   * Represents multiplicity of AVP:
   * 1     One instance of the AVP MUST be present in the message.
   *       message.
   * </pre>
   */
  String _MP_ONE = "1";

  /**
   * <pre>
   * Represents multiplicity of AVP:
   * 1+    At least one instance of the AVP MUST be present in the
   *       message.
   * </pre>
   */
  String _MP_ONE_AND_MORE = "1+";

  String _DEFAULT_MANDATORY = "may";
  String _DEFAULT_PROTECTED = "may";
  String _DEFAULT_VENDOR = "mustnot";

  int _FIX_POSITION_INDEX = -1;

  enum Rule {
    must, may, mustnot, shouldnot
  };

  enum Type {
    OctetString, Integer32, Integer64, Unsigned32, Unsigned64, Float32, Float64, Grouped, Address,
    Time, UTF8String, DiameterIdentity, DiameterURI, Enumerated, IPFilterRule, QoSFilterRule
  };

  boolean isPositionFixed();

  //public void markFixPosition(int index);

  boolean isCountValidForMultiplicity(int avpCount);

  boolean isCountValidForMultiplicity(AvpSet destination, int numberToAdd);

  boolean isAllowed(int avpCode, long vendorId);

  boolean isAllowed(int avpCode);

  int getPositionIndex();

  int getCode();

  long getVendorId();

  boolean isAllowed();

  String getMultiplicityIndicator();

  String getName();

  boolean isGrouped();

  //public void setGrouped(boolean grouped);

  List<AvpRepresentation> getChildren();

  //public void setChildren(List<AvpRepresentation> children);

  //public void setCode(int code);

  //public void setVendorId(long vendor);

  //public void setMultiplicityIndicator(String multiplicityIndicator);

  //public void setName(String name);

  boolean isWeak();

  //public void markWeak(boolean isWeak);

  String getDescription();

  boolean isMayEncrypt();

  String getRuleMandatory();

  int getRuleMandatoryAsInt();

  String getRuleProtected();

  int getRuleProtectedAsInt();

  String getRuleVendorBit();

  int getRuleVendorBitAsInt();

  String getOriginalType();

  String getType();

  boolean isProtected();

  boolean isMandatory();

  /**
   * Validates passed avp.
   * @param avp - simply avp which should be confronted vs definition
   */
  void validate(Avp avp) throws AvpNotAllowedException;

  /**
   * Validates passed avp.
   * @param avpSet - AvpSet which represents internal content of this avp
   */
  void validate(AvpSet avpSet) throws AvpNotAllowedException;

  @Override
  String toString();

  @Override
  int hashCode();

  @Override
  boolean equals(Object obj);

  Object clone() throws CloneNotSupportedException;

}
