package org.mobicents.diameter.stack.functional.s6a.base;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.DisconnectCause;
import org.jdiameter.api.Mode;
import org.jdiameter.api.Peer;
import org.jdiameter.api.PeerState;
import org.jdiameter.api.PeerTable;
import org.jdiameter.api.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
@RunWith(Parameterized.class)
public class S6aNotifyTest {

  // TODO: add test on replicated nodes ?
  private ClientS6a clientNode;
  private ServerS6a serverNode1;
  private final URI clientConfigURI;
  private final URI serverNode1ConfigURI;

  public S6aNotifyTest(String clientConfigUrl, String serverNode1ConfigURL) throws Exception {
    super();
    this.clientConfigURI = new URI(clientConfigUrl);
    this.serverNode1ConfigURI = new URI(serverNode1ConfigURL);
  }

  @Before
  public void setUp() throws Exception {
    try {
      this.clientNode = new ClientS6a();
      this.serverNode1 = new ServerS6a();

      this.serverNode1.init(new FileInputStream(new File(this.serverNode1ConfigURI)), "SERVER1");
      this.serverNode1.start();

      this.clientNode.init(new FileInputStream(new File(this.clientConfigURI)), "CLIENT");
      this.clientNode.start(Mode.ANY_PEER, 10, TimeUnit.SECONDS);
      Stack stack = this.clientNode.getStack();
      List<Peer> peers = stack.unwrap(PeerTable.class).getPeerTable();
      if (peers.size() > 1) {
        // works better with replicated, since disconnected peers are also listed
        boolean foundConnected = false;
        for (Peer p : peers) {
          if (p.getState(PeerState.class).equals(PeerState.OKAY)) {
            if (foundConnected) {
              throw new Exception("Wrong number of connected peers: " + peers);
            }
            foundConnected = true;
          }
        }
      } else if (peers.size() != 1) {
        throw new Exception("Wrong number of connected peers: " + peers);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() {
    if (this.serverNode1 != null) {
      try {
        this.serverNode1.stop(DisconnectCause.REBOOTING);
      } catch (Exception e) {
        e.printStackTrace();
      }
      this.serverNode1 = null;
    }

    if (this.clientNode != null) {
      try {
        this.clientNode.stop(DisconnectCause.REBOOTING);
      } catch (Exception e) {
        e.printStackTrace();
      }
      this.clientNode = null;
    }
  }

  @Test
  public void testNotify() {
    try {
      // pain of parameter tests :) ?
      clientNode.sendNotifyRequest();
      waitForMessage();

      serverNode1.sendNotifyAnswer();
      waitForMessage();

    } catch (Exception e) {
      e.printStackTrace();
      fail(e.toString());
    }

    if (!serverNode1.isReceivedNOR()) {
      fail("Did not receive NOR! " + "Server ER:\n" + serverNode1.createErrorReport(this.serverNode1.getErrors()));
    }

    if (!clientNode.isReceivedNOA()) {
      fail("Did not receive NOA! " + "Client ER:\n" + clientNode.createErrorReport(this.clientNode.getErrors()));
    }

    if (!clientNode.isPassed()) {
      fail("Client ER:\n" + clientNode.createErrorReport(this.clientNode.getErrors()));
    }

    if (!serverNode1.isPassed()) {
      fail("Server ER:\n" + serverNode1.createErrorReport(this.serverNode1.getErrors()));
    }
  }

  @Parameters
  public static Collection<Object[]> data() {

    String client = "configurations/functional-s6a/config-client.xml";
    String server1 = "configurations/functional-s6a/config-server-node1.xml";

    //String replicatedClient = "configurations/functional-s6a/replicated-config-client.xml";
    //String replicatedServer1 = "configurations/functional-s6a/replicated-config-server-node1.xml";

    Class<S6aNotifyTest> t = S6aNotifyTest.class;
    client = Objects.requireNonNull(t.getClassLoader().getResource(client)).toString();
    server1 = Objects.requireNonNull(t.getClassLoader().getResource(server1)).toString();
    //replicatedClient = t.getClassLoader().getResource(replicatedClient).toString();
    //replicatedServer1 = t.getClassLoader().getResource(replicatedServer1).toString();

    return Arrays.asList(new Object[][] { { client, server1 }/*, { replicatedClient, replicatedServer1 } */});
  }

  private void waitForMessage() {
    try {
      Thread.sleep(2000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
