package org.jdiameter.client.impl.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.LocalAction;
import org.jdiameter.client.api.controller.IRealm;
import org.jdiameter.server.api.agent.IAgent;
import org.jdiameter.server.api.agent.IAgentConfiguration;

/**
 * The Realm class implements rows in the Diameter Realm routing table.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RealmImpl implements IRealm {

  protected String name;
  protected ApplicationId appId;
  protected LocalAction action;
  protected boolean dynamic;
  protected long expirationTime;
  protected Collection<String> hosts = new ConcurrentLinkedQueue<String>();
  protected IAgent agent;
  protected IAgentConfiguration agentConfiguration;
  public RealmImpl(String name, ApplicationId applicationId, LocalAction localAction,
      IAgent agent, IAgentConfiguration agentConfiguration, boolean dynamic, long expirationTime, String... hosts) {
    this.hosts.addAll(Arrays.asList(hosts));
    this.name = name;
    this.appId = applicationId;
    this.action = localAction;
    this.dynamic = dynamic;
    this.expirationTime = expirationTime;
    this.agent = agent;
    this.agentConfiguration = agentConfiguration;
  }

  /**
   * Return name of this realm
   *
   * @return name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Return applicationId associated with this realm
   *
   * @return applicationId
   */
  @Override
  public ApplicationId getApplicationId() {
    return appId;
  }

  /**
   * Return realm local action for this realm
   *
   * @return realm local action
   */
  @Override
  public LocalAction getLocalAction() {
    return action;
  }

  /**
   * Return list of real peers
   *
   * @return array of realm peers
   */
  @Override
  public String[] getPeerNames() {
    return hosts.toArray(new String[hosts.size()]);
  }

  /**
   * Append new host (peer) to this realm
   *
   * @param host
   *          name of peer host
   */
  @Override
  public void addPeerName(String name) {
    if (!hasPeerName(name)) {
      hosts.add(name);
    }
  }

  /**
   * Remove peer from this realm
   *
   * @param host
   *          name of peer host
   */
  @Override
  public void removePeerName(String s) {
    hosts.remove(name);
  }

  /**
   * Return true if this realm is dynamic updated
   *
   * @return true if this realm is dynamic updated
   */
  @Override
  public boolean isDynamic() {
    return dynamic;
  }

  /**
   * Return expiration time for this realm in milisec
   *
   * @return expiration time
   */
  @Override
  public long getExpirationTime() {
    return expirationTime;
  }

  @Override
  public boolean hasPeerName(String name) {
    return this.hosts.contains(name);
  }

  @Override
  public IAgent getAgent() {
    return agent;
  }

  @Override
  public IAgentConfiguration getAgentConfiguration() {
    return this.agentConfiguration;
  }

  @Override
  public boolean isLocal() {
    return false;
  }

  @Override
  public String toString() {
    return "RealmImpl [name=" + name + ", appId=" + appId + ", action=" + action + ", dynamic=" + dynamic +
        ", expirationTime=" + expirationTime + ", hosts=" + hosts + ", agent=" + agent + "]";
  }

}
