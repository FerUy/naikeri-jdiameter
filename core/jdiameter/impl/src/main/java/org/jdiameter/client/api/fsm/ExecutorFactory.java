package org.jdiameter.client.api.fsm;

import java.util.concurrent.ExecutorService;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ExecutorFactory {

  ExecutorService getExecutor();

}
