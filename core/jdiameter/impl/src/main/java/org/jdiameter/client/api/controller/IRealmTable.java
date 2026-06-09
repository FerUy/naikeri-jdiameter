package org.jdiameter.client.api.controller;

import java.util.Collection;
import java.util.List;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.LocalAction;
import org.jdiameter.api.Realm;
import org.jdiameter.api.RealmTable;
import org.jdiameter.client.api.IAnswer;
import org.jdiameter.client.api.IRequest;
import org.jdiameter.server.api.agent.IAgentConfiguration;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IRealmTable extends RealmTable {

  Realm matchRealm(IRequest request);

  Realm matchRealm(IAnswer message, String destRealm);

  Realm getRealm(String realmName, ApplicationId applicationId);

  Realm removeRealmApplicationId(String realmName, ApplicationId appId);

  Collection<Realm> removeRealm(String realmName);

  Collection<Realm> getRealms(String realm);

  Collection<Realm> getRealms();

  String getRealmForPeer(String fqdn);

  void addLocalApplicationId(ApplicationId ap);

  void removeLocalApplicationId(ApplicationId a);

  void addLocalRealm(String localRealm, String fqdn);
  /**
   * Method which accepts IAgentConfiguration to avoid decode, encode, decode sequences
   * @param name
   * @param appId
   * @param locAction
   * @param agentConfImpl
   * @param isDynamic
   * @param expirationTime
   * @param hosts
   * @return
   * @throws InternalException
   */
  Realm addRealm(String name, ApplicationId appId, LocalAction locAction, IAgentConfiguration agentConfImpl, boolean isDynamic, long expirationTime,
      String[] hosts) throws InternalException;

  List<String> getAllRealmSet();
}
