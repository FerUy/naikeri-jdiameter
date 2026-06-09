package org.jdiameter.api.sh;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateMachine;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationAnswer;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataRequest;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ClientShSession extends AppSession, StateMachine {

  /**
   * Send profile update request to server
   * @param request Authentication-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendProfileUpdateRequest(ProfileUpdateRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send subscription request to server
   * @param request Authentication-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendSubscribeNotificationsRequest(SubscribeNotificationsRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Send user data request to server
   * @param request Authentication-Request event instance
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   **/
  void sendUserDataRequest(UserDataRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void sendPushNotificationAnswer(PushNotificationAnswer answer)  throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;
}
