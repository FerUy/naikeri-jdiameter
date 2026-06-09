package org.jdiameter.api;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * This interface append to base interface specific methods for
 * creating and send diameter requests and responses
 *
 * @version 1.5.1 Final
 *
 * @author erick.svenson@yahoo.com
 * @author artem.litvinov@gmail.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface Session extends BaseSession, Wrapper  {

  /**
   * Set session request listener
   * @param listener session request listener
   */
  void setRequestListener(NetworkReqListener listener);

  /**
   * Returns a new Request object with the specified command code, applicationId, realm
   * and predefined system AVPs.
   * @param commandCode code of message
   * @param appId applicationId of destination application
   * @param destRealm name of destination Realm
   * @return new message object
   */
  Request createRequest(int commandCode, ApplicationId appId, String destRealm);

  /**
   * Returns a new Request object with the specified command code, applicationId, realm, host
   * and predefined system AVPs.
   * @param commandCode code of message
   * @param appId applicationId of destination application
   * @param destRealm name of destination Realm
   * @param destHost name of destination Host
   * @return new Request object
   */
  Request createRequest(int commandCode, ApplicationId appId, String destRealm, String destHost);

  /**
   * Returns a new Request object base on previous request.(header is copied)
   * @param prevRequest previous request (header is copied)
   * @return new Request object
   */
  Request createRequest(Request prevRequest);

  /**
   * Sends and wait response message with default timeout,
   * @param message request/answer diameter message
   * @param listener event listener
   * @param useRealm in case of not found an available peer if this useRealm is true, it will route using realm and
   *                 if this is false it will throw a RouteException
   * @throws org.jdiameter.api.InternalException The InternalException signals that internal error is occurred.
   * @throws org.jdiameter.api.IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws org.jdiameter.api.RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws org.jdiameter.api.OverloadException The OverloadException signals that destination host is overloaded.
   */
  void send(Message message, EventListener<Request, Answer> listener, Boolean useRealm)
          throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Sends and wait response message with default timeout
   * @param message request/answer diameter message
   * @param listener event listener
   * @throws org.jdiameter.api.InternalException The InternalException signals that internal error is occurred.
   * @throws org.jdiameter.api.IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws org.jdiameter.api.RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws org.jdiameter.api.OverloadException The OverloadException signals that destination host is overloaded.
   */
  void send(Message message, EventListener<Request, Answer> listener)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Sends and wait response message with defined timeout
   * @param message  request/answer diameter message
   * @param listener  event listener
   * @param timeOut value of timeout
   * @param timeUnit type of timeOut value
   * @throws org.jdiameter.api.InternalException  The InternalException signals that internal error is occurred.
   * @throws org.jdiameter.api.IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws org.jdiameter.api.RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws org.jdiameter.api.OverloadException The OverloadException signals that destination host is overloaded.
   */
  void send(Message message, EventListener<Request, Answer> listener, long timeOut, TimeUnit timeUnit)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Sends and wait response message with default timeout
   * @param message request/answer diameter message
   * @return InFuture result of an asynchronous operation
   * @throws org.jdiameter.api.InternalException The InternalException signals that internal error is occurred.
   * @throws org.jdiameter.api.IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws org.jdiameter.api.RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws org.jdiameter.api.OverloadException The OverloadException signals that destination host is overloaded.
   */
  Future<Message> send(Message message) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Sends and wait response message with defined timeout
   * @param message  request/answer diameter message
   * @return InFuture result of an asynchronous operation
   * @param timeOut value of timeout
   * @param timeUnit type of timeOut value
   * @throws org.jdiameter.api.InternalException  The InternalException signals that internal error is occurred.
   * @throws org.jdiameter.api.IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws org.jdiameter.api.RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws org.jdiameter.api.OverloadException The OverloadException signals that destination host is overloaded.
   */
  Future<Message> send(Message message, long timeOut, TimeUnit timeUnit)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}