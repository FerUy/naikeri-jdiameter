package org.jdiameter.client.api;

import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Session;

/**
 * This interface describe extends methods of base class
 * Data: $Date: 2009/07/27 18:05:03 $
 * Revision: $Revision: 1.3 $
 * @version 1.5.0.1
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ISession extends Session {
  NetworkReqListener getReqListener();
}
