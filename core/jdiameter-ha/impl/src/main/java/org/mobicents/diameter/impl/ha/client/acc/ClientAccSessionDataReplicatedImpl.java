package org.mobicents.diameter.impl.ha.client.acc;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.restcomm.cache.FqnWrapper;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.acc.ClientAccSession;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.api.parser.ParseException;
import org.jdiameter.client.impl.app.acc.IClientAccSessionData;
import org.jdiameter.common.api.app.acc.ClientAccSessionState;
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
public class ClientAccSessionDataReplicatedImpl extends AppSessionDataReplicatedImpl implements IClientAccSessionData {

  private static final Logger logger = LoggerFactory.getLogger(ClientAccSessionDataReplicatedImpl.class);

  private static final String STATE = "STATE";
  private static final String INTERIM_TIMERID = "INTERIM_TIMERID";
  private static final String DEST_HOST = "DEST_HOST";
  private static final String DEST_REALM = "DEST_REALM";
  private static final String BUFFER = "BUFFER";

  private IMessageParser messageParser;

  /**
   * @param nodeFqnWrapper
   * @param mobicentsCluster
   * @param container
   */
  public ClientAccSessionDataReplicatedImpl(FqnWrapper nodeFqnWrapper, MobicentsCluster mobicentsCluster, IContainer container) {
    super(nodeFqnWrapper, mobicentsCluster);

    if (super.create()) {
      setAppSessionIface(this, ClientAccSession.class);
      setClientAccSessionState(ClientAccSessionState.IDLE);
    }

    this.messageParser = container.getAssemblerFacility().getComponentInstance(IMessageParser.class);
  }

  /**
   * @param sessionId
   * @param mobicentsCluster
   * @param container
   */
  public ClientAccSessionDataReplicatedImpl(String sessionId, MobicentsCluster mobicentsCluster, IContainer container) {
    this(
      FqnWrapper.fromRelativeElementsWrapper(ReplicatedSessionDatasource.SESSIONS_FQN, sessionId),
      mobicentsCluster, container
    );
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#setClientAccSessionState
   * (org.jdiameter.common.api.app.acc.ClientAccSessionState)
   */
  @Override
  public void setClientAccSessionState(ClientAccSessionState state) {
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
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#getClientAccSessionState()
   */
  @Override
  public ClientAccSessionState getClientAccSessionState() {
    if (exists()) {
      return (ClientAccSessionState) getNodeValue(STATE);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#setInterimTimerId(java.io.Serializable)
   */
  @Override
  public void setInterimTimerId(Serializable tid) {
    if (exists()) {
      putNodeValue(INTERIM_TIMERID, tid);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#getInterimTimerId()
   */
  @Override
  public Serializable getInterimTimerId() {
    if (exists()) {
      return (Serializable) getNodeValue(INTERIM_TIMERID);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String destHost) {
    if (exists()) {
      putNodeValue(DEST_HOST, destHost);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    if (exists()) {
      return (String) getNodeValue(DEST_HOST);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String destRealm) {
    if (exists()) {
      putNodeValue(DEST_REALM, destRealm);
    }
    else {
      throw new IllegalStateException();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.jdiameter.client.impl.app.acc.IClientAccSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    if (exists()) {
      return (String) getNodeValue(DEST_REALM);
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
