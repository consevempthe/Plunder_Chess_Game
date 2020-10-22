package server;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegistrationRequestTest {

	private RegistrationRequest register;
	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
	@BeforeEach
	public void setup() {
		connector.connect();
	}
	
	@AfterEach
	public void tearDown() {
		connector.disconnect();
	}
	
	
	@Test
	void testBuildResponseSucceed() throws ClassNotFoundException, IllegalRequestException {
		register = new RegistrationRequest("register Sky_Breaker3 pretend lll");
		String response = register.buildResponse();
		assertEquals("register success Sky_Breaker3 pretend lll", response);
		DatabaseAccessor accessor = new DatabaseAccessor();
		accessor.changeDatabase("delete from registration where nickname='Sky_Breaker3';");
	}
	
	@Test
	void testFailOnConstuctor() {
		assertThrows(IllegalRequestException.class, () -> register = new RegistrationRequest("t Sky_Breaker3 pretend lll"));
		assertThrows(IllegalRequestException.class, () -> register = new RegistrationRequest("register Sky_Breaker3 pretend lll 5thArg"));
	}
	
	@Test
	void testBuildResponseFailed() throws IllegalRequestException {
		register = new RegistrationRequest("register NStrike pretend lll");
		String response = register.buildResponse();
		assertEquals("register failed", response);
	}
	

}
