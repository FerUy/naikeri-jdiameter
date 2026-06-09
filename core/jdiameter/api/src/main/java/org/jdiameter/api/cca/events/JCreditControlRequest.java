package org.jdiameter.api.cca.events;

import org.jdiameter.api.app.AppRequestEvent;

/**
 * The Credit-Control-Request message (CCR) is indicated by the command-code field being set to
 * 272 and the 'R' bit being set in the Command Flags field.  It is used between the Diameter
 * credit-control client and the credit-control server to request credit authorization for a given
 * service.
 *
 * The Auth-Application-Id MUST be set to the value 4, indicating the Diameter credit-control
 * application.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface JCreditControlRequest extends AppRequestEvent {

  String _SHORT_NAME = "CCR";
  String _LONG_NAME = "Credit-Control-Request";

  int code = 272;

  boolean isRequestedActionAVPPresent();

  int getRequestedActionAVPValue();

  boolean isRequestTypeAVPPresent();

  int getRequestTypeAVPValue();

}
