package org.mobicents.diameter.impl.ha.common.s6c;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.api.parser.ParseException;
import org.jdiameter.common.api.app.s6c.S6cSessionState;
import org.jdiameter.common.api.app.s6c.IS6cSessionData;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public abstract class S6cSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IS6cSessionData {

  private static final Logger logger = LoggerFactory.getLogger(S6cSessionDataReplicatedImpl.class);

  private static final String STATE = "STATE";
  private static final String BUFFER = "BUFFER";
  private static final String TS_TIMERID = "TS_TIMERID";

  private IMessageParser messageParser;

  /**
   * @param nodeFqnWrapper    FQN wrapper
   * @param mobicentsCluster  Cluster
   * @param container         Container
   */
  public S6cSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster);
    this.messageParser = container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.s6c.IS6cSessionData#setS6cSessionState(org.jdiameter.common.api.app.s6c.S6cSessionState)
   */
  public void setS6cSessionState(S6cSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.s6c.IS6cSessionData#getS6cSessionState()
   */
  public S6cSessionState getS6cSessionState() {
    if (exists()) {
      return (S6cSessionState) getNodeValue(STATE);
    } else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.common.api.app.s6c.IS6cSessionData#getTsTimerId()
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
   * @see org.jdiameter.common.api.app.s6c.IS6cSessionData#setTsTimerId(java.io.Serializable)
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
        return this.messageParser.createMessage(ByteBuffer.wrap(data));
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
      } catch (ParseException e) {
        logger.error("Unable to encode message to buffer.");
      }
    } else {
      removeNodeValue(BUFFER);
    }
  }

}
