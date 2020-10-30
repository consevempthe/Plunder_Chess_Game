package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestServer {
	private Server server;
	
	@BeforeEach
	void setUp() {
		server = new Server(8188);
	}
	
	@Test
	void testInitialValues() {
		assertTrue(server.getWorkerList().isEmpty(), "Test Worker List Initial Values Upon Constructor.");
		assertEquals(8188, server.getServerPort(), "Test server port value upon construction.");
	}
}
