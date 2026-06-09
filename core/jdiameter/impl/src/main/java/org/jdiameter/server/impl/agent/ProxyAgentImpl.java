package org.jdiameter.server.impl.agent;

import org.apache.log4j.Logger;
import org.jdiameter.api.Answer;
import org.jdiameter.api.Request;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IRequest;
import org.jdiameter.client.api.controller.IRealm;
import org.jdiameter.client.api.controller.IRealmTable;
import org.jdiameter.server.api.agent.IProxy;

/**
 *
 *
 * @author babass
 */
public class ProxyAgentImpl extends AgentImpl implements IProxy {

  private static Logger logger = Logger.getLogger(ProxyAgentImpl.class);

  /**
   * @param container
   * @param realmTable
   */
  public ProxyAgentImpl(IContainer container, IRealmTable realmTable) {
    super(container, realmTable);
    logger.info("proxy agent: created");
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.server.api.agent.IAgent#processRequest(org.jdiameter.client
   * .api.IRequest, org.jdiameter.client.api.controller.IRealm)
   */
  @Override
  public Answer processRequest(IRequest request, IRealm matchedRealm) {
    logger.info("proxy agent: processRequest executed");
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.api.EventListener#receivedSuccessMessage(org.jdiameter.
   * api.Message, org.jdiameter.api.Message)
   */
  @Override
  public void receivedSuccessMessage(Request request, Answer answer) {
    logger.info("proxy agent: receivedSuccessMessage");
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.jdiameter.api.EventListener#timeoutExpired(org.jdiameter.api.Message)
   */
  @Override
  public void timeoutExpired(Request request) {
    logger.info("proxy agent: timeoutExpired");
  }

}
