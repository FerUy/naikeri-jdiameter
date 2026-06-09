package org.jdiameter.server.api;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.client.api.fsm.IContext;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

/**
 * Peer FSM factory
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IFsmFactory extends org.jdiameter.client.api.fsm.IFsmFactory { // todo move to common api

  @Override
  IStateMachine createInstanceFsm(IContext context, IConcurrentFactory concurrentFactory, Configuration config) throws InternalException;
}
