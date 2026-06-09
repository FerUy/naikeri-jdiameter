package org.jdiameter.client.impl;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpSet;
import org.jdiameter.api.Message;
import org.jdiameter.api.MetaData;

/**
 * Small util class to separate AVP manipulation code from MessageParser class.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class MessageUtility {

  private MessageUtility() {
  }

  /**
   * Used to set origin, previously done in MessageParser.
   *
   * @param m
   * @param md
   */
  public static void addOriginAvps(Message m, MetaData md) {
    // FIXME: check for "userFqnAsUri" ?
    AvpSet set = m.getAvps();
    if (set.getAvp(Avp.ORIGIN_HOST) == null) {
      m.getAvps().addAvp(Avp.ORIGIN_HOST, md.getLocalPeer().getUri().getFQDN(), true, false, true);
    }
    if (set.getAvp(Avp.ORIGIN_REALM) == null) {
      m.getAvps().addAvp(Avp.ORIGIN_REALM, md.getLocalPeer().getRealmName(), true, false, true);
    }
  }

}
