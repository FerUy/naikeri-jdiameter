package org.jdiameter.server.api;

import org.jdiameter.api.Network;
import org.jdiameter.client.api.IMessage;

 /**
 * This interface append to base interface some
 * special methods.
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="joram.herrera2@gmail.com"> Joram Herrera </a>
 */
public interface INetwork extends Network {


  /**
   * Return NetworkListener instance for specified application-id
   * @param message message
   * @return  NetworkListener instance for specified selector
   * @see org.jdiameter.api.NetworkReqListener || org.jdiameter.api.NetworkMsgListener
   */
  <T> T getListener(IMessage message, String... peerApplications);

  /**
   * This method set peer manager for addPeer/remPeer methods
   * @param manager PeerTable instance
   */
  void setPeerManager(IMutablePeerTable manager);

}
