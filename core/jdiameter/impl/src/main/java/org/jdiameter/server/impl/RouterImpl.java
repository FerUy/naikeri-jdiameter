package org.jdiameter.server.impl;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.MetaData;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.controller.IRealmTable;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.jdiameter.server.api.IRouter;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class RouterImpl extends org.jdiameter.client.impl.router.RouterImpl implements IRouter {

  public RouterImpl(IContainer container, IConcurrentFactory concurrentFactory, IRealmTable realmTable, Configuration config, MetaData metaData) {
    super(container, concurrentFactory, realmTable, config, metaData);
  }

}
