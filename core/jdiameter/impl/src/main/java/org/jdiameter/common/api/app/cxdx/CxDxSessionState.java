package org.jdiameter.common.api.app.cxdx;

/**
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public enum CxDxSessionState {

  //FIXME: should we distinguish types of messages?
  IDLE, MESSAGE_SENT_RECEIVED, TERMINATED, TIMEDOUT

}
