package org.jdiameter.server.api;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.OverloadManager;
import org.jdiameter.api.URI;

/**
 * This interface describe extends methods of base class
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IOverloadManager extends OverloadManager {

  /**
   * Return true if application has overload
   * @param appId application id
   * @return true if application has overload
   */
  boolean isParenAppOverload(final ApplicationId appId);

  /**
   * eturn true if application has overload by predefined type
   * @param appId application id
   * @param type type of overload (CPU, Memory... )
   * @return true if application has overload
   */
  boolean isParenAppOverload(final ApplicationId appId, int type);

  /**
   * Notification about overload
   * @param index overload entry index
   * @param uri peer uri
   * @param value overload value
   */
  void changeNotification(int index, URI uri, double value);
}
