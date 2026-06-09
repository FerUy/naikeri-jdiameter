package org.mobicents.diameter.impl.ha.common.cxdx;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.api.parser.ParseException;
import org.jdiameter.common.api.app.cxdx.CxDxSessionState;
import org.jdiameter.common.api.app.cxdx.ICxDxSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public abstract class CxDxSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements ICxDxSessionData {

  private static final Logger logger = LoggerFactory.getLogger(CxDxSessionDataReplicatedImpl.class);

  private static final String STATE = "STATE";
  private static final String BUFFER = "BUFFER";
  private static final String TS_TIMERID = "TS_TIMERID";

  private IMessageParser messageParser;

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public CxDxSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster);
    this.messageParser = container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#setCxDxSessionState(org.jdiameter.common.api.app.cxdx.CxDxSessionState)
   */
  @Override
  public void setCxDxSessionState(CxDxSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#getCxDxSessionState()
   */
  @Override
  public CxDxSessionState getCxDxSessionState() {
    if (exists()) {
      return (CxDxSessionState) getNodeValue(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#getTsTimerId()
   */
  @Override
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TS_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.cxdx.ICxDxSessionData#setTsTimerId(java.io.Serializable)
   */
  @Override
  public void setTsTimerId(Serializable tid) {
    if (exists()) {
      putNodeValue(TS_TIMERID, tid);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public Request getBuffer() {
    byte[] data = (byte[]) getNodeValue(BUFFER);
    if (data != null) {
      try {
        return this.messageParser.createMessage(ByteBuffer.wrap(data));
      }
      catch (AvpDataException e) {
        logger.error("Unable to recreate message from buffer.");
        return null;
      }
    }
    else {
      return null;
    }
  }

  @Override
  public void setBuffer(Request buffer) {
    if (buffer != null) {
      try {
        byte[] data = this.messageParser.encodeMessage((IMessage) buffer).array();
        putNodeValue(BUFFER, data);
      }
      catch (ParseException e) {
        logger.error("Unable to encode message to buffer.");
      }
    }
    else {
      removeNodeValue(BUFFER);
    }
  }
}
