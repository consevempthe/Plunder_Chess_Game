package server;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginRequestTest {

	LoginRequest login1;
	LoginRequest login2;


	@Test
	void testBuildResponseSucceed() throws IllegalRequestException {
		login1 = new LoginRequest("login NStrike doyoureallywanttohackme");
		String response = login1.buildResponse();
		assertEquals("login success", response);
	}
	
	@Test
	void testBuildResponseException() throws IllegalRequestException {
		assertThrows(IllegalRequestException.class, () -> {login2 = new LoginRequest(" ");});
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
