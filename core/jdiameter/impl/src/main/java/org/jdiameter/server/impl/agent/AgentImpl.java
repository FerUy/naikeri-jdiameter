package org.jdiameter.server.impl.agent;

import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.controller.IRealmTable;
import org.jdiameter.server.api.agent.IAgent;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class AgentImpl implements IAgent {

  protected IContainer container;
  protected IRealmTable realmTable;

  /**
   * @param container
   * @param peerTable
   * @param realmTable
   */
  public AgentImpl(IContainer container, IRealmTable realmTable) {
    super();
    this.container = container;
    this.realmTable = realmTable;
  }

}
