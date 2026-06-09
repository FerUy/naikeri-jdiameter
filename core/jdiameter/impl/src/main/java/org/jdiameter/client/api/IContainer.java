package org.jdiameter.client.api;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Configuration;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.Stack;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

/**
 * This interface extends behavior of stack interface
 * Data: $Date: 2008/07/03 19:43:10 $
 * Revision: $Revision: 1.1 $
 * @version 1.5.0.1
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IContainer extends Stack {

  /**
   * Return state of stack
   * @return Return state of stack
   */
  StackState getState();

  /**
   * Return configuration instance
   * @return configuration instance
   */
  Configuration getConfiguration();

  /**
   * Return root IOC
   * @return root IOC
   */
  IAssembler getAssemblerFacility();

  /**
   * Return common Scheduled Executor Service
   * @return common Scheduled Executor Service
   */
  ScheduledExecutorService getScheduledFacility();

  /**
   * Return common concurrent factory
   * @return
   */
  IConcurrentFactory getConcurrentFactory();
  /**
   * Send message
   * @param session session instance
   * @throws RouteException
   * @throws AvpDataException
   * @throws IllegalDiameterStateException
   * @throws IOException
   */
  void sendMessage(IMessage session) throws RouteException, AvpDataException, IllegalDiameterStateException, IOException;

  void sendMessage(IMessage session, Boolean useRealm) throws RouteException, AvpDataException, IllegalDiameterStateException, IOException;

  /**
   * Add session listener
   * @param sessionId session id
   * @param listener listener instance
   */
  void addSessionListener(String sessionId, NetworkReqListener listener);

  /**
   * Remove session event listener by sessionId
   * @param sessionId session identifier
   */
  void removeSessionListener(String sessionId);
}
