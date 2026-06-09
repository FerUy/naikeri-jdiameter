package org.jdiameter.api;

/**
 * Interface defining disconnect cause codes
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface DisconnectCause {

  /**
   * A scheduled reboot is imminent.
   */
  int REBOOTING = 0;

  /**
   * The peer's internal resources are constrained, and it has
   * determined that the transport connection needs to be closed.
   */
  int BUSY = 1;

  /**
   * The peer has determined that it does not see a need for the
   * transport connection to exist, since it does not expect any
   * messages to be exchanged in the near future.
   */
  int DO_NOT_WANT_TO_TALK_TO_YOU = 2;
}
