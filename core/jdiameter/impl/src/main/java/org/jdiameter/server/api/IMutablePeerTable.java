package org.jdiameter.server.api;

import org.jdiameter.api.MutablePeerTable;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.client.api.controller.IPeerTable;

/**
 * This interface describe extends methods of base class
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IMutablePeerTable extends MutablePeerTable, IPeerTable {


  /**
   * Check message on duplicate
   * @param request checked message
   * @return true if messahe has duplicate into storage
   */
  IMessage isDuplicate(IMessage request);

  /**
   * Save message to duplicate storage
   * @param key key of message
   * @param answer message
   */
  void saveToDuplicate(String key, IMessage answer);

  /**
   * Return instance of session factory
   * @return instance of session factory
   */
  ISessionFactory getSessionFactory();
}
