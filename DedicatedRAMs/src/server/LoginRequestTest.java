package server;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginRequestTest {

	private LoginRequest login1;

	@Test
	void testBuildResponseSucceed() throws IllegalRequestException {
		login1 = new LoginRequest("login test test");
		String response = login1.buildResponse();
		assertEquals("login success test test test", response);
	}
	
	@Test
	void testBuildResponseException() {
		assertThrows(IllegalRequestException.class, () -> login1 = new LoginRequest(" "));
		assertThrows(IllegalRequestException.class, () -> login1 = new LoginRequest("ll ll ll"));
	}
	
	@Test
	void testBuildResponseFail1() throws IllegalRequestException {
		login1 = new LoginRequest("login NStrike doyoureallywanttohack");
		String response = login1.buildResponse();
		assertEquals("login failed", response);
	}	
	
	@Test
	void testBuildResponseFail2() throws IllegalRequestException {
		login1 = new LoginRequest("login NotInDatabase doyoureallywanttohackme");
		String response = login1.buildResponse();
		assertEquals("login failed", response);
	}

}
