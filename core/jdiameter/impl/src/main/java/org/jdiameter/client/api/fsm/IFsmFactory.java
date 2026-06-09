package org.jdiameter.client.api.fsm;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

/**
 * Peer FSM factory
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface IFsmFactory {

  /**
   * Create instance of Peer FSM
   *
   * @param context FSM context object
   * @param concurrentFactory executor facility
   * @param config configuration
   * @return State machine instance
   * @throws InternalException
   */
  IStateMachine createInstanceFsm(IContext context, IConcurrentFactory concurrentFactory, Configuration config) throws InternalException;
}
