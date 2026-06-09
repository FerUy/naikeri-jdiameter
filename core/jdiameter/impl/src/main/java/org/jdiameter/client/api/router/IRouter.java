package org.jdiameter.client.api.router;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.RouteException;
import org.jdiameter.client.api.IAnswer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.IRequest;
import org.jdiameter.client.api.controller.IPeer;
import org.jdiameter.client.api.controller.IPeerTable;
import org.jdiameter.client.api.controller.IRealmTable;

/**
 * This class describe Router functionality
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IRouter  {

  /**
   * Return peer from inner peer table by predefined parameters. Fetches peer based on message content, that is HBH or realm/host avp contents.
   * Takes into consideration ApplicationId present in message to pick correct realm definition from RealmTable.
   * This method should be called after {@link #updateRoute}.
   * @param message message with routed avps
   * @param manager instance of peer manager
   * @return peer instance
   * @throws RouteException
   * @throws AvpDataException
   */
  IPeer getPeer(IMessage message, IPeerTable manager) throws RouteException, AvpDataException;

  /**
   * Return peer from inner peer table by predefined parameters. Fetches peer based on message content, that is HBH or realm/host avp contents.
   * Takes into consideration ApplicationId present in message to pick correct realm definition from RealmTable.
   * This method should be called after {@link #updateRoute}.
   * @param message message with routed avps
   * @param manager instance of peer manager
   * @param useRealm in case of not found an available peer if this useRealm is true, it will route using realm and
   *                 if this is false it will throw a RouteException
   * @return peer instance
   * @throws RouteException
   * @throws AvpDataException
   */
  IPeer getPeer(IMessage message, IPeerTable manager, Boolean useRealm) throws RouteException, AvpDataException;

  /**
   * Return realm table
   *
   * @return object representing realm table
   */
  IRealmTable getRealmTable();

  /**
   * Register route information by received request. This information will be used
   * during answer routing.
   * @param request request
   */
  void registerRequestRouteInfo(IRequest request);

  // PCB - Changed to use a better routing mechanism as hopbyhop was not always unique and the table could also grow too big
  /**
   * Return Request route info
   * @param hopByHopIndentifier Hop-by-Hop Identifier
   * @return Array (host and realm)
   */
  String[] getRequestRouteInfo(IMessage message);

  //PCB added
  void garbageCollectRequestRouteInfo(IMessage message);

  /**
   * Start inner time facilities
   */
  void start();

  /**
   * Stop inner time facilities
   */
  void stop();

  /**
   * Release all resources
   */
  void destroy();

  /**
   * Called when redirect answer is received for request. This method update redirect host information and routes to new destination.
   * @param request
   * @param answer
   * @param table
   */
  void processRedirectAnswer(IRequest request, IAnswer answer, IPeerTable table) throws InternalException, RouteException;

  /**
   * Based on Redirect entries or any other factors, this method changes route information.
   * @param message
   * @return
   * @throws RouteException
   * @throws AvpDataException
   */
  boolean updateRoute(IRequest message) throws RouteException, AvpDataException;

}
