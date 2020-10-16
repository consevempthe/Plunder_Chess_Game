package server;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoginRequestTest {

	private LoginRequest login1;
	private ServerWorker worker;
	@BeforeEach
	void setup() {
		worker = new ServerWorker(null, null);
	}

	@Test
	void testBuildResponseSucceed() throws IllegalRequestException {
		login1 = new LoginRequest("login test test", worker);
		String response = login1.buildResponse();
		assertEquals("login success test test test", response);
		assertEquals("test", worker.getNickname());
	}
	
	@Test
	void testBuildResponseException() {
		assertThrows(IllegalRequestException.class, () -> login1 = new LoginRequest(" ", worker));
		assertThrows(IllegalRequestException.class, () -> login1 = new LoginRequest("ll ll ll", worker));
	}
	
	@Test
	void testBuildResponseFail1() throws IllegalRequestException {
		login1 = new LoginRequest("login NStrike doyoureallywanttohack", worker);
		String response = login1.buildResponse();
		assertEquals("login failed", response);
		assertEquals(null, worker.getNickname());
	}	
	
	@Test
	void testBuildResponseFail2() throws IllegalRequestException {
		login1 = new LoginRequest("login NotInDatabase doyoureallywanttohackme", worker);
		String response = login1.buildResponse();
		assertEquals("login failed", response);
		assertEquals(null, worker.getNickname());
	}

}