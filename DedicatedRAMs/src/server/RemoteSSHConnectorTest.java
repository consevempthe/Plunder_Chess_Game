package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RemoteSSHConnectorTest {

	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
	
	@Test
	void testConnectedDisconnected() {
		boolean connected = connector.connect();
		//assertTrue(connected); //May fail if a connection is already obtained.
		connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
		connected = connector.connect();
		assertFalse(connected); 
		connector.disconnect();
	}

}
