package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JCancelLocationRequest extends AppRequestEvent {

  String _SHORT_NAME = "CLR";
  String _LONG_NAME = "Cancel-Location-Request";

  int code = 317;

}
