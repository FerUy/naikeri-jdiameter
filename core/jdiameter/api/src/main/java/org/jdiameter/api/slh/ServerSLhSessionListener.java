package org.jdiameter.api.slh;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slh.events.LCSRoutingInfoRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ServerSLhSessionListener {

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doLCSRoutingInfoRequestEvent(ServerSLhSession session, LCSRoutingInfoRequest request)
          throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

}
