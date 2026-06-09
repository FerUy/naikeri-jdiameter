package org.jdiameter.api;

/**
 * A Diameter Request is a request from a client to a server (or server to client - network request).
 *
 * @author erick.svenson@yahoo.com
 * @author artem.litvinov@gmail.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface Request extends Message {

  /**
   * @return true if it is network request
   */
  boolean isNetworkRequest();

  /**
   * Creates an answer for this request with the specified result code.
   * Header and system AVPs from request are copied to answer.
   * @param resultCode result code of answer
   * @return answer object instance
   */
  Answer createAnswer(long resultCode);

  /**
   * Creates an answer for this request with the specified experimental result code.
   * Header and system AVPs from request are copied to answer.
   * @param vendorId vendorId
   * @param experimentalResultCode experimental result code of answer
   * @return answer object instance
   */
  Answer createAnswer(long vendorId, long experementalResultCode);

  /**
   * Creates answer for this request. Header and system AVPs from request are copied to answer.
   * @return
   */
  Answer createAnswer();

}
