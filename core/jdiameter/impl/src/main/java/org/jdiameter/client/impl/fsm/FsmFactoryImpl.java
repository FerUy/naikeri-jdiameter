package org.jdiameter.client.impl.fsm;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.client.api.fsm.IContext;
import org.jdiameter.client.api.fsm.IFsmFactory;
import org.jdiameter.client.api.fsm.IStateMachine;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.jdiameter.common.api.statistic.IStatisticManager;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class FsmFactoryImpl implements IFsmFactory { // TODO: please redesign this code "duplicate"

  protected IStatisticManager statisticFactory;

  public FsmFactoryImpl(IStatisticManager statisticFactory) {
    this.statisticFactory = statisticFactory;

  }

  @Override
  public IStateMachine createInstanceFsm(IContext context, IConcurrentFactory concurrentFactory, Configuration config) throws InternalException {
    return new org.jdiameter.client.impl.fsm.PeerFSMImpl(context, concurrentFactory, config, statisticFactory);
  }
}
