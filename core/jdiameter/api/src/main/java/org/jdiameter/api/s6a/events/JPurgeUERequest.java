package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface JPurgeUERequest extends AppRequestEvent {

  String _SHORT_NAME = "PUR";
  String _LONG_NAME = "Purge-UE-Request";

  int code = 321;

}
