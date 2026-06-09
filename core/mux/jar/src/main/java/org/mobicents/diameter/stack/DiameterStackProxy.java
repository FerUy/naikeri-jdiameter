package org.mobicents.diameter.stack;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.BaseSession;
import org.jdiameter.api.Configuration;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.MetaData;
import org.jdiameter.api.Mode;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.SessionFactory;
import org.jdiameter.api.Stack;
import org.jdiameter.api.validation.Dictionary;
import org.jdiameter.client.api.IAssembler;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.StackState;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

public class DiameterStackProxy implements Stack, IContainer {

  protected Stack realStack = null;

  public DiameterStackProxy(Stack realStack) {
    super();
    this.realStack = realStack;
  }

  @Override
  public void destroy() {
    this.realStack.destroy();
  }

  @Override
  public Logger getLogger() {
    return this.realStack.getLogger();
  }

  @Override
  public MetaData getMetaData() {
    return this.realStack.getMetaData();
  }

  @Override
  public SessionFactory getSessionFactory() throws IllegalDiameterStateException {
    return this.realStack.getSessionFactory();
  }

  @Override
  public <T extends BaseSession> T getSession(String sessionId, Class<T> clazz) throws InternalException {
    return this.realStack.getSession(sessionId, clazz);
  }

  @Override
  public SessionFactory init( Configuration config ) throws IllegalDiameterStateException, InternalException {
    return this.realStack.init( config );
  }

  @Override
  public boolean isActive() {
    return this.realStack.isActive();
  }

  @Override
  public void start() throws IllegalDiameterStateException, InternalException {
    this.realStack.start();
  }

  @Override
  public void start(Mode mode, long timeout, TimeUnit unit) throws IllegalDiameterStateException, InternalException {
    this.realStack.start( mode, timeout, unit );
  }

  @Override
  public void stop( long timeout, TimeUnit unit, int disconnectCause ) throws IllegalDiameterStateException, InternalException {
    this.realStack.stop( timeout, unit, disconnectCause );
  }

  @Override
  public boolean isWrapperFor( Class<?> iface ) throws InternalException {
    return this.realStack.isWrapperFor( iface );
  }

  @Override
  public <T> T unwrap( Class<T> iface ) throws InternalException {
    return this.realStack.unwrap( iface );
  }

  @Override
  public void addSessionListener(String sessionId, NetworkReqListener listener) {
    ((IContainer) realStack).addSessionListener(sessionId, listener);
  }

  @Override
  public IConcurrentFactory getConcurrentFactory() {
    return ((IContainer) realStack).getConcurrentFactory();
  }

  @Override
  public Configuration getConfiguration() {
    return ((IContainer) realStack).getConfiguration();
  }

  @Override
  public ScheduledExecutorService getScheduledFacility() {
    return ((IContainer) realStack).getScheduledFacility();
  }

  @Override
  public StackState getState() {
    return ((IContainer) realStack).getState();
  }

  @Override
  public void removeSessionListener(String sessionId) {
    ((IContainer) realStack).removeSessionListener(sessionId);
  }

  @Override
  public void sendMessage(IMessage session) throws RouteException, AvpDataException, IllegalDiameterStateException, IOException {
    ((IContainer) realStack).sendMessage(session);
  }

  @Override
  public void sendMessage(IMessage session, Boolean useRealm) throws RouteException, AvpDataException, IllegalDiameterStateException, IOException {
    ((IContainer) realStack).sendMessage(session, useRealm);
  }

  @Override
  public IAssembler getAssemblerFacility() {
    return ((IContainer) realStack).getAssemblerFacility();
  }

  /* (non-Javadoc)
   * @see org.jdiameter.api.Stack#getDictionary()
   */
  @Override
  public Dictionary getDictionary() throws IllegalDiameterStateException {
    return realStack.getDictionary();
  }

}
