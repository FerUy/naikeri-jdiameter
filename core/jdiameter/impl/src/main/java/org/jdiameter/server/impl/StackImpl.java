package org.jdiameter.server.impl;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.MetaData;
import org.jdiameter.api.MutablePeerTable;
import org.jdiameter.api.Network;
import org.jdiameter.api.OverloadManager;
import org.jdiameter.client.api.StackState;

/**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class StackImpl extends org.jdiameter.client.impl.StackImpl implements StackImplMBean {

  @Override
  public MetaData getMetaData() {
    if (state == StackState.IDLE) {
      throw new IllegalStateException("Meta data not defined");
    }
    return assembler.getComponentInstance(MetaDataImpl.class);
  }

  @Override
  public boolean isWrapperFor(Class<?> aClass) throws InternalException {
    if (aClass == MutablePeerTable.class) {
      return true;
    }
    else if (aClass == Network.class) {
      return true;
    }
    else if (aClass == OverloadManager.class) {
      return true;
    }
    else {
      return super.isWrapperFor(aClass);
    }
  }

  @Override
  public <T> T unwrap(Class<T> aClass) throws InternalException {
    if (aClass == MutablePeerTable.class) {
      return assembler.getComponentInstance(aClass);
    }
    if (aClass == Network.class) {
      return assembler.getComponentInstance(aClass);
    }
    if (aClass == OverloadManager.class) {
      return assembler.getComponentInstance(aClass);
    }
    else {
      return super.unwrap(aClass);
    }
  }
}
