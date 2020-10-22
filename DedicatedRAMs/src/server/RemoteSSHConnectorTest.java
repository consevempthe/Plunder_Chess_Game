package server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RemoteSSHConnectorTest {

	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");	
	
	@Test
	void testConnectedDisconnected() {
		boolean connected = connector.connect();
		assertTrue(connected);
		connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
		connected = connector.connect();
		assertFalse(connected); 
		connector.disconnect();
	}

}
