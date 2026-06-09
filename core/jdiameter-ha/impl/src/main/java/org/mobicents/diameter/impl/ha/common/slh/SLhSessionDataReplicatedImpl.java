package org.mobicents.diameter.impl.ha.common.slh;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.api.parser.ParseException;
import org.jdiameter.common.api.app.slh.SLhSessionState;
import org.jdiameter.common.api.app.slh.ISLhSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 *
 */
public abstract class SLhSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements ISLhSessionData {

  private static final Logger logger = LoggerFactory.getLogger(SLhSessionDataReplicatedImpl.class);

  private static final String STATE = "STATE";
  private static final String BUFFER = "BUFFER";
  private static final String TS_TIMERID = "TS_TIMERID";

  private IMessageParser messageParser;

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
  */
  public SLhSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster);
    this.messageParser = container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.slh.ISLhSessionData#setSLhSessionState(org.jdiameter.common.api.app.slh.SLhSessionState)
  */
  public void setSLhSessionState(SLhSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.slh.ISLhSessionData#getSLhSessionState()
   */
  public SLhSessionState getSLhSessionState() {
    if (exists()) {
      return (SLhSessionState) getNodeValue(STATE);
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.slh.ISLhSessionData#getTsTimerId()
  */
  public Serializable getTsTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(TS_TIMERID);
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.slh.ISLhSessionData#setTsTimerId(java.io.Serializable)
   */
  public void setTsTimerId(Serializable tid) {
    if (exists()) {
      putNodeValue(TS_TIMERID, tid);
    } else {
      throw new IllegalStateException();
    }
  }

  public Request getBuffer() {
    byte[] data = (byte[]) getNodeValue(BUFFER);
    if (data != null) {
      try {
        return (Request) this.messageParser.createMessage(ByteBuffer.wrap(data));
      } catch (AvpDataException e) {
        logger.error("Unable to recreate message from buffer.");
        return null;
      }
    } else {
      return null;
    }
  }

  public void setBuffer(Request buffer) {
    if (buffer != null) {
      try {
        byte[] data = this.messageParser.encodeMessage((IMessage) buffer).array();
        putNodeValue(BUFFER, data);
      }
      catch (ParseException e) {
        logger.error("Unable to encode message to buffer.");
      }
    } else {
      removeNodeValue(BUFFER);
    }
  }

}