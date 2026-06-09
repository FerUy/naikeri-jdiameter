package org.mobicents.diameter.impl.ha.client.rf;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.rf.ClientRfSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.api.parser.ParseException;
import org.jdiameter.client.impl.app.rf.IClientRfSessionData;
import org.jdiameter.common.api.app.rf.ClientRfSessionState;
import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.diameter.impl.ha.common.AppSessionDataReplicatedImpl;
import org.mobicents.diameter.impl.ha.data.ReplicatedSessionDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientRfSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IClientRfSessionData {

  private static final Logger logger = LoggerFactory.getLogger(ClientRfSessionDataReplicatedImpl.class);

  private static final String STATE = "STATE";
  private static final String BUFFER = "BUFFER";
  private static final String TS_TIMERID = "TS_TIMERID";
  private static final String DESTINATION_HOST = "DESTINATION_HOST";
  private static final String DESTINATION_REALM = "DESTINATION_REALM";

  private IMessageParser messageParser;

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ClientRfSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ClientRfSession.class);
      setClientRfSessionState(ClientRfSessionState.IDLE);
    }

    this.messageParser = container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ClientRfSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster, container
    );
  }

  @Override
  public ClientRfSessionState getClientRfSessionState() {
    if (exists()) {
      return (ClientRfSessionState) getNodeValue(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setClientRfSessionState(ClientRfSessionState state) {
    if (exists()) {
      putNodeValue(STATE, state);
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

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getTsTimerId()
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
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setTsTimerId(java.io.Serializable)
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

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    if (exists()) {
      return (String) getNodeValue(DESTINATION_HOST);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String destinationHost) {
    if (exists()) {
      putNodeValue(DESTINATION_HOST, destinationHost);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    if (exists()) {
      return (String) getNodeValue(DESTINATION_REALM);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String destinationRealm) {
    if (exists()) {
      putNodeValue(DESTINATION_REALM, destinationRealm);
    }
    else {
      throw new IllegalStateException();
    }
  }
}
