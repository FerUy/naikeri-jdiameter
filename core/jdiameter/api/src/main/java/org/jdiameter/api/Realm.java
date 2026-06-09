package org.jdiameter.api;

/**
 * Base Realm interface.
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface Realm {

  /**
   * Return name of this realm
   *
   * @return name
   */
  String getName();

  /**
   * Return applicationId associated with this realm
   *
   * @return applicationId
   */
  ApplicationId getApplicationId();

  /**
   * Return realm local action for this realm
   *
   * @return realm local action
   */
  LocalAction getLocalAction();

  /**
   * Return true if this realm is dynamic updated
   *
   * @return true if this realm is dynamic updated
   */
  boolean isDynamic();

  /**
   * Return expiration time for this realm in milisec
   *
   * @return expiration time
   */
  long getExpirationTime();

  /**
   * Returns true if realm is local. Local means that it is defined as local(not action) realm for this peer.
   * @return
   */
  boolean isLocal();

}
