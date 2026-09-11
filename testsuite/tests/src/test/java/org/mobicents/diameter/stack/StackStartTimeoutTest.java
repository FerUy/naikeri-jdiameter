package org.mobicents.diameter.stack;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.Mode;
import org.jdiameter.server.impl.StackImpl;
import org.jdiameter.server.impl.helpers.XMLConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Regression tests for start(Mode, timeout, unit) timing out.
 *
 * <p>The timed start launches the peer manager (network guard, schedulers, peer state machines) and then
 * waits for the peers. On timeout it threw without changing the state, so destroy() saw a stack that was
 * never started and did not stop any of it: the local port stayed bound and the peer state machine kept
 * reconnecting to its peer long after the stack was gone.
 *
 * <p>Uses jdiameter-client-two.xml: local peer 127.0.0.2:13868, one peer to connect to at 127.0.0.2:3868,
 * where nothing listens during the start.
 */
public class StackStartTimeoutTest {

  private static final String CLIENT_CONFIG = "configurations/jdiameter-client-two.xml";
  private static final InetSocketAddress LOCAL_PEER = new InetSocketAddress(address("127.0.0.2"), 13868);
  private static final InetSocketAddress REMOTE_PEER = new InetSocketAddress(address("127.0.0.2"), 3868);

  // the peer state machine retried every 10s in the logs of this failure; wait longer than one retry
  private static final int RECONNECT_WATCH_MILLIS = 15000;

  @Test
  public void timedOutStartReleasesLocalPort() throws Exception {
    failTimedStartAndDestroy();

    assertTrue("Local peer port " + LOCAL_PEER + " is still bound after a timed-out start and destroy()", isPortFree(LOCAL_PEER));
  }

  @Test
  public void timedOutStartStopsReconnecting() throws Exception {
    failTimedStartAndDestroy();

    ServerSocket remotePeer = new ServerSocket();
    try {
      remotePeer.bind(REMOTE_PEER);
      remotePeer.setSoTimeout(RECONNECT_WATCH_MILLIS);
      Socket connection = remotePeer.accept();
      connection.close();
      fail("A destroyed stack connected to " + REMOTE_PEER + ": its peer state machine was never stopped");
    }
    catch (SocketTimeoutException expected) {
      // nothing reconnected
    }
    finally {
      remotePeer.close();
    }
  }

  private void failTimedStartAndDestroy() throws Exception {
    StackImpl stack = new StackImpl();
    try {
      InputStream is = StackStartTimeoutTest.class.getClassLoader().getResourceAsStream(CLIENT_CONFIG);
      Assert.assertNotNull("InputStream for configuration file should not be null", is);
      stack.init(new XMLConfiguration(is));
      try {
        stack.start(Mode.ALL_PEERS, 2000, TimeUnit.MILLISECONDS);
        fail("start() should have timed out: nothing listens on " + REMOTE_PEER);
      }
      catch (InternalException expected) {
        // TimeOut
      }
    }
    finally {
      stack.destroy();
    }
  }

  private static boolean isPortFree(InetSocketAddress address) {
    ServerSocket probe = null;
    try {
      probe = new ServerSocket();
      probe.setReuseAddress(false);
      probe.bind(address);
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

  private static InetAddress address(String ip) {
    try {
      return InetAddress.getByName(ip);
    }
    catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
