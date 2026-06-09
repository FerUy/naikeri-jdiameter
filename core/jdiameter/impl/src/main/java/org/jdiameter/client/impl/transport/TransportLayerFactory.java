package org.jdiameter.client.impl.transport;

import static java.lang.Class.forName;

import java.lang.reflect.Constructor;
import java.net.InetAddress;

import org.jdiameter.api.Configuration;
import org.jdiameter.api.InternalException;
import org.jdiameter.client.api.io.IConnection;
import org.jdiameter.client.api.io.IConnectionListener;
import org.jdiameter.client.api.io.ITransportLayerFactory;
import org.jdiameter.client.api.io.TransportError;
import org.jdiameter.client.api.io.TransportException;
import org.jdiameter.client.api.parser.IMessageParser;
import org.jdiameter.client.impl.helpers.AppConfiguration;
import org.jdiameter.client.impl.helpers.ExtensionPoint;
import org.jdiameter.client.impl.helpers.Parameters;
import org.jdiameter.common.api.concurrent.DummyConcurrentFactory;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;

 /**
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class TransportLayerFactory implements ITransportLayerFactory {

  private Class<IConnection> connectionClass;
  private Constructor<IConnection> constructorIAi, constructorIAiCL, constructorIAiCLEha;
  protected IMessageParser parser;
  protected Configuration config = null;

  public TransportLayerFactory(Configuration config, IMessageParser parser) throws TransportException {
    this.config = config;
    Configuration[] children = config.getChildren(Parameters.Extensions.ordinal());

    AppConfiguration internalExtensions = (AppConfiguration) children[ExtensionPoint.Internal.id()];
    String implName = internalExtensions.getStringValue(ExtensionPoint.InternalConnectionClass.ordinal(),
        ExtensionPoint.InternalConnectionClass.defValue());
    try {
      // TODO: this should be enough to check if class has interface!?
      this.connectionClass = (Class<IConnection>) forName(implName);

      if (!IConnection.class.isAssignableFrom(this.connectionClass)) {
        throw new TransportException("Specified class does not inherit IConnection interface " + this.connectionClass, TransportError.Internal);
      }
    } catch (Exception e) {
      throw new TransportException("Cannot prepare specified connection class " + this.connectionClass, TransportError.Internal, e);
    }
    try {
      //TODO: this is bad practice, IConnection is interface and this code enforces constructor type to be present!
      constructorIAiCLEha = connectionClass.getConstructor(
          Configuration.class, IConcurrentFactory.class, InetAddress.class, Integer.TYPE, InetAddress.class,
          Integer.TYPE, String[].class, String.class, IConnectionListener.class, IMessageParser.class, String.class);
      constructorIAiCL = connectionClass.getConstructor(
          Configuration.class, IConcurrentFactory.class, InetAddress.class, Integer.TYPE, InetAddress.class,
          Integer.TYPE, IConnectionListener.class, IMessageParser.class, String.class);
      constructorIAi = connectionClass.getConstructor(
          Configuration.class, IConcurrentFactory.class, InetAddress.class, Integer.TYPE, InetAddress.class,
          Integer.TYPE, IMessageParser.class, String.class);
    } catch (Exception e) {
      throw new TransportException("Cannot find required constructor", TransportError.Internal, e);
    }
    this.parser = parser;
  }

  @Override
  public IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort,
                                      InetAddress localAddress, int localPort, String ref) throws TransportException {
    try {
      factory = factory == null ? new DummyConcurrentFactory() : factory;
      return constructorIAi.newInstance(config, factory, remoteAddress, remotePort, localAddress, localPort, parser, ref);
    } catch (Exception e) {
      throw new TransportException("Cannot create an instance of " + connectionClass, TransportError.Internal, e);
    }
  }

  @Override
  public IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort, InetAddress localAddress,
                                      int localPort, IConnectionListener listener, String ref) throws TransportException {
    try {
      factory = factory == null ? new DummyConcurrentFactory() : factory;
      return constructorIAiCL.newInstance(config, factory, remoteAddress, remotePort, localAddress, localPort, listener, parser, ref);
    } catch (Exception e) {
      throw new TransportException("Cannot create an instance of " + connectionClass, TransportError.Internal, e);
    }
  }

  @Override
  public IConnection createConnection(InetAddress remoteAddress, IConcurrentFactory factory, int remotePort, InetAddress localAddress,
                                      int localPort, String[] extraHostAddresses, String standbyRemoteAddresses,
                                      IConnectionListener listener, String ref) throws TransportException {
    try {
      factory = factory == null ? new DummyConcurrentFactory() : factory;
      return constructorIAiCLEha.newInstance(config, factory, remoteAddress, remotePort, localAddress, localPort,
          extraHostAddresses, standbyRemoteAddresses, listener, parser, ref);
    } catch (Exception e) {
      throw new TransportException("Cannot create an instance of " + connectionClass, TransportError.Internal, e);
    }
  }

  @Override
  public boolean isWrapperFor(Class<?> aClass) throws InternalException {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> aClass) throws InternalException {
    return null;
  }
}
