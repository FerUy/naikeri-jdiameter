package org.mobicents.diameter.stack;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import org.jdiameter.api.Configuration;
import org.jdiameter.server.api.IMetaData;
import org.jdiameter.server.impl.io.tcp.NetworkGuard;
import org.junit.Test;

/**
 * Regression tests for the TCP {@link NetworkGuard} binding its server socket on a separate thread.
 *
 * <p>The bind is scheduled with a configurable delay. If destroy() ran before that bind, the bind still
 * executed afterwards and left a listening socket that nothing owned or closed, so a later stack could
 * not bind the same port while its peers connected to the orphaned socket and never got a CEA.
 */
public class NetworkGuardBindTest {

  private static final InetAddress LOOPBACK = InetAddress.getLoopbackAddress();

  @Test
  public void destroyBeforeDelayedBindLeavesPortFree() throws Exception {
    int port = freePort();
    NetworkGuard guard = new NetworkGuard(LOOPBACK, port, null, null, metaDataWithBindDelay(1000));
    guard.destroy();

    // well past the bind delay: an unguarded bind would have opened the socket by now
    Thread.sleep(2500);

    assertTrue("Port " + port + " is bound after destroy(): the delayed bind ran after the guard was destroyed", isPortFree(port));
  }

  @Test
  public void destroyAfterBindReleasesPort() throws Exception {
    int port = freePort();
    NetworkGuard guard = new NetworkGuard(LOOPBACK, port, null, null, metaDataWithBindDelay(200));
    try {
      Thread.sleep(1500);
      assertFalse("The guard did not bind port " + port + " within 1.5s of a 200ms bind delay", isPortFree(port));
    }
    finally {
      guard.destroy();
    }
    assertTrue("Port " + port + " is still bound after destroy()", isPortFree(port));
  }

  private static int freePort() throws IOException {
    ServerSocket socket = new ServerSocket(0, 1, LOOPBACK);
    try {
      return socket.getLocalPort();
    }
    finally {
      socket.close();
    }
  }

  private static boolean isPortFree(int port) {
    ServerSocket probe = null;
    try {
      probe = new ServerSocket();
      probe.setReuseAddress(false);
      probe.bind(new InetSocketAddress(LOOPBACK, port));
      return true;
    }
    catch (IOException e) {
      return false;
    }
    finally {
      if (probe != null) {
        try {
          probe.close();
        }
        catch (IOException e) {
          // ignore
        }
      }
    }
  }

  private static IMetaData metaDataWithBindDelay(final long bindDelayMillis) {
    final Configuration configuration = proxy(Configuration.class, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) {
        return "getLongValue".equals(method.getName()) ? Long.valueOf(bindDelayMillis) : defaultValue(method.getReturnType());
      }
    });
    return proxy(IMetaData.class, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) {
        return "getConfiguration".equals(method.getName()) ? configuration : defaultValue(method.getReturnType());
      }
    });
  }

  private static <T> T proxy(Class<T> type, InvocationHandler handler) {
    return type.cast(Proxy.newProxyInstance(NetworkGuardBindTest.class.getClassLoader(), new Class<?>[] { type }, handler));
  }

  private static Object defaultValue(Class<?> type) {
    if (!type.isPrimitive() || type == void.class) {
      return null;
    }
    // a one-element primitive array holds that type's zero value, already boxed by Array.get
    return Array.get(Array.newInstance(type, 1), 0);
  }
}
