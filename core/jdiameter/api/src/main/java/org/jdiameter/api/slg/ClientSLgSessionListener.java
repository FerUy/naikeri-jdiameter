package org.jdiameter.api.slg;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.slg.events.ProvideLocationRequest;
import org.jdiameter.api.slg.events.ProvideLocationAnswer;
// import org.jdiameter.api.slg.events.LocationReportAnswer;
import org.jdiameter.api.slg.events.LocationReportRequest;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */

public interface ClientSLgSessionListener {

  void doOtherEvent(AppSession session, AppRequestEvent request, AppAnswerEvent answer)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

  void doProvideLocationAnswerEvent(ClientSLgSession session, ProvideLocationRequest request, ProvideLocationAnswer answer)
          throws InternalException, IllegalDiameterStateException, RouteException, OverloadException, AvpDataException;

  void doLocationReportRequestEvent(ClientSLgSession session, LocationReportRequest request)
      throws InternalException, IllegalDiameterStateException, RouteException, OverloadException;

}
