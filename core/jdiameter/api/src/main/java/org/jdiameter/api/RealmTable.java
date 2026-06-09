package org.jdiameter.api;

/**
 * This interface introduces a capability to work with a network.
 * You can get instance of this interface over stack instance:
 * <code>
 * if (stack.isWrapperFor(RealmTable.class)) {
 *       RealmTable realmTabke = stack.unwrap(RealmTable.class);
 *       .....
 * }
 * </code>
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface RealmTable extends Wrapper {

  /**
   * Return different network statistics
   * @param realmName realmName
   * @return network statistics
   */
  Statistic getStatistic(String realmName);

  /**
   * Add new realm to realm table
   * @param realmName name of realm
   * @param applicationId application id of realm
   * @param action action of realm
   * @param agentConfiguration resource for configuration of action dependent agent, may be null.
   * @param dynamic commCode of realm
   * @param expTime expiration time of realm
   * @param extraConf - additional configuration which may be used by implementation
   * @return instance of created realm
   * @throws InternalException - when realm definition under pKey and sKey exist
   */
  Realm addRealm(String realmName, ApplicationId applicationId, LocalAction action, String agentConfiguration, boolean dynamic, long expTime, String[] hosts)
      throws InternalException;

  /**
   * Checks if there is such realm entry.
   * @param realmName
   * @return
   */
  boolean realmExists(String realmName);

}
