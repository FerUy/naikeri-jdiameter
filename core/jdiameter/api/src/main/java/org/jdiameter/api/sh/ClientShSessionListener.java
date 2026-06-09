package org.jdiameter.api.sh;

import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.sh.events.ProfileUpdateAnswer;
import org.jdiameter.api.sh.events.ProfileUpdateRequest;
import org.jdiameter.api.sh.events.PushNotificationRequest;
import org.jdiameter.api.sh.events.SubscribeNotificationsAnswer;
import org.jdiameter.api.sh.events.SubscribeNotificationsRequest;
import org.jdiameter.api.sh.events.UserDataAnswer;
import org.jdiameter.api.sh.events.UserDataRequest;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ClientShSessionListener {

  void doSubscribeNotificationsAnswerEvent(ClientShSession session, SubscribeNotificationsRequest request, SubscribeNotificationsAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doProfileUpdateAnswerEvent(ClientShSession session, ProfileUpdateRequest request, ProfileUpdateAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doPushNotificationRequestEvent(ClientShSession session, PushNotificationRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doUserDataAnswerEvent(ClientShSession session, UserDataRequest request, UserDataAnswer answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  /**
   * Notifies this ShSessionEventListener that the ClientShSession has received not Sh message.
   * @param session parent application session (FSM)
   * @param request request object
   * @param answer answer object
   * @throws InternalException The InternalException signals that internal error is occurred.
   * @throws IllegalDiameterStateException The IllegalStateException signals that session has incorrect state (invalid).
   * @throws RouteException The NoRouteException signals that no route exist for a given realm.
   * @throws OverloadException The OverloadException signals that destination host is overloaded.
   */
  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;


}
