package org.jdiameter.api.s6a.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:paul.carter-brown@smilecoms.com"> Paul Carter-Brown </a>
 */
public interface JPurgeUEAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "PUA";
  String _LONG_NAME = "Purge-UE-Answer";

  int code = 321;

}
