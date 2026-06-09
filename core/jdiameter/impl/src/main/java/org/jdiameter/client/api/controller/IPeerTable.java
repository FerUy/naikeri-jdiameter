package org.jdiameter.client.api.controller;

import java.io.IOException;
import java.util.Map;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.PeerTable;
import org.jdiameter.api.RouteException;
import org.jdiameter.client.api.IAssembler;
import org.jdiameter.client.api.IMessage;

/**
 *  This interface provide additional methods for PeerTable interface
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IPeerTable extends PeerTable {

  /**
   * Start peer manager ( start network activity )
   *
   * @throws IllegalDiameterStateException
   * @throws IOException
   */
  void start() throws IllegalDiameterStateException, IOException;

  /**
   * Run stopping procedure (unsynchronized)
   */
  void stopping(int disconnectCause);

  /**
   * Release resources
   */
  void stopped();

  /**
   *  Destroy all resources
   */
  void destroy();

  /**
   * Send message to diameter network ( routing procedure )
   *
   * @param message  message instance
   * @throws IllegalDiameterStateException
   * @throws IOException
   * @throws RouteException
   * @throws AvpDataException
   */
  void sendMessage(IMessage message) throws IllegalDiameterStateException, IOException, RouteException, AvpDataException;

  /**
   * Send message to diameter network ( routing procedure )
   *
   * @param message  message instance
   * @throws IllegalDiameterStateException
   * @throws IOException
   * @throws RouteException
   * @throws AvpDataException
   */
  void sendMessage(IMessage message, Boolean useRealm) throws IllegalDiameterStateException, IOException, RouteException, AvpDataException;

  /**
   * Register session lister
   *
   * @param sessionId session id
   * @param listener listener listener
   */
  void addSessionReqListener(String sessionId, NetworkReqListener listener);

  /**
   * Return peer from peer table by identity - FQDN host name.
   *
   * @param fqdn peer host
   * @return peer instance
   */
  @Override
  IPeer getPeer(String fqdn);

  /**
   * Return map of session event listeners
   *
   * @return map of session event listeners
   */
  Map<String, NetworkReqListener> getSessionReqListeners();

  /**
   * Remove session event listener
   *
   * @param sessionId id of session
   */
  void removeSessionListener(String sessionId);

  /**
   * Set instance assembler
   *
   * @param assembler assembler instance
   */
  void setAssembler(IAssembler assembler);
}
