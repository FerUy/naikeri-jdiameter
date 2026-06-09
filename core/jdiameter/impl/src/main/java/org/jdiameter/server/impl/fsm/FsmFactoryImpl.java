package org.jdiameter.server.impl.fsm;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.client.api.fsm.IContext;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.jdiameter.common.api.statistic.IStatisticManager;
import org.jdiameter.server.api.IFsmFactory;
import org.jdiameter.server.api.IStateMachine;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class FsmFactoryImpl extends org.jdiameter.client.impl.fsm.FsmFactoryImpl implements IFsmFactory {

  public FsmFactoryImpl(IStatisticManager statisticFactory) {
    super(statisticFactory);
  }

  @Override
  public IStateMachine createInstanceFsm(IContext context, IConcurrentFactory concurrentFactory, Configuration config) throws InternalException {
    return new org.jdiameter.server.impl.fsm.PeerFSMImpl(context, concurrentFactory, config, statisticFactory);
  }
}
