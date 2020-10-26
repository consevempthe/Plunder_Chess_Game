package client.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.LoginResponse;
import client.User;

class LoginResponseTest {

	private LoginResponse response;
	private User user = new User(null, null, null);
	
	@Test
	void testHandleResponse() {
		response = new LoginResponse("login success test test test", user, null);
		response.handleResponse();
		assertEquals("test", user.getNickname());
		assertEquals("test", user.getEmail());
		assertEquals("test", user.getPassword());
		user = new User(null, null, null);
		response = new LoginResponse("login failed", user, null);
		response.handleResponse();
		assertEquals(null, user.getNickname());
		assertEquals(null, user.getEmail());
		assertEquals(null, user.getPassword());
	}

}
