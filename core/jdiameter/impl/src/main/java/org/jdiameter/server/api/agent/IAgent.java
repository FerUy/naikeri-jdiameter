package org.jdiameter.server.api.agent;

import org.jdiameter.api.Answer;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.Request;
import org.jdiameter.client.api.IRequest;
import org.jdiameter.client.api.controller.IRealm;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAgent extends /*NetworkReqListener,*/ EventListener<Request, Answer> {

  /**
   * This method use for process new network requests.
   * @param request request message
   * @return answer immediate answer messsage. Method may return null and an
   * Answer will be sent later on
   */
  Answer processRequest(IRequest request, IRealm matchedRealm);  //realm should be matched for all agents iirc, soooo :)

}
