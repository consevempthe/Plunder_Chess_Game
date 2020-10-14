package server;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegistrationRequestTest {

	private RegistrationRequest register;
	
	@Test
	void testBuildResponseSucceed() throws ClassNotFoundException, IllegalRequestException {
		register = new RegistrationRequest("register Sky_Breaker3 pretend lll");
		String response = register.buildResponse();
		assertEquals("register success", response);
		DatabaseAccessor accessor = new DatabaseAccessor();
		accessor.changeDatabase("delete from registration where nickname='Sky_Breaker3';");
	}
	
	@Test
	void testFailOnConstuctor() throws ClassNotFoundException, IllegalRequestException {
		assertThrows(IllegalRequestException.class, () -> {register = new RegistrationRequest("t Sky_Breaker3 pretend lll");});
		assertThrows(IllegalRequestException.class, () -> {register = new RegistrationRequest("register Sky_Breaker3 pretend lll 5thArg");});
	}
	
	@Test
	void testBuildResponseFailed() throws ClassNotFoundException, IllegalRequestException {
		register = new RegistrationRequest("register NStrike pretend lll");
		String response = register.buildResponse();
		assertEquals("register failed", response);
	}
	

}
