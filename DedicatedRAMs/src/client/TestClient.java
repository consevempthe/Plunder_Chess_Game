package client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.Server;

class TestClient {

	static Server server;
	@BeforeAll
	static void setUp() {
		server = new Server(8188);
		server.start();
	}
	
	@Test
	void testInitialValues() {
		Client client = new Client("localhost", 8188);
		assertEquals("localhost", client.getServerName());
		assertEquals(8188, client.getServerPort());
	}
	
	@Test
	void testConnectionSucceed() {
		Client client = new Client("localhost", 8188);
		boolean connected = client.connect();
		assertEquals(true, connected, "Should properly connect.");
	}

	@Test
	void testConnectionFailed() {
		Client client1 = new Client("localhost", 8888);
		boolean connected = client1.connect();
		assertEquals(false, connected, "Should fail to properly connect.");
	}
	

	@Test
	void testResponseWithNothing() throws IOException {
		Client client = new Client("localhost", 8188);
		assertThrows(NullPointerException.class, ()->client.response(), "Expect Null pointer.");
	}
	
}
