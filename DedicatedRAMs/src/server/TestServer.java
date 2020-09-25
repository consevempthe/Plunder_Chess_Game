package server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestServer {
	private Server server;
	
	@BeforeEach
	void setUp() {
		server = new Server(8188);
	}
	
	@Test
	void testInitialValues() {
		assertEquals(true, server.getWorkerList().isEmpty(), "Test Worker List Initial Values Upon Constructor.");
		assertEquals(8188, server.getServerPort(), "Test server port value upon construction.");
	}
}