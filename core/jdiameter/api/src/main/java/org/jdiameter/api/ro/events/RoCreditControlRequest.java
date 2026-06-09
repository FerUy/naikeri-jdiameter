package org.jdiameter.api.ro.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The CCR messages, indicated by the Command-Code field set to 272 is sent by the CTF to the OCF
 * in order to request credits for the request bearer / subsystem / service.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface RoCreditControlRequest extends AppRequestEvent {

  String _SHORT_NAME = "CCR";
  String _LONG_NAME = "Credit-Control-Request";

  int code = 272;

  boolean isRequestedActionAVPPresent();

  int getRequestedActionAVPValue();

  boolean isRequestTypeAVPPresent();

  int getRequestTypeAVPValue();

}
