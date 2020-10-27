package client.Tests;

import client.Client;
import client.LoginResponse;
import client.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoginResponseTest {

	private LoginResponse response;
	private User user = new User(null, null, null);
	private Client client = new Client("localhost", 8818);

	@Test
	void testHandleResponse() {

		response = new LoginResponse("login success test test test", user, client);
		response.handleResponse();
		assertEquals("test", user.getNickname());
		assertEquals("test", user.getEmail());
		assertEquals("test", user.getPassword());
		user = new User(null, null, null);
		response = new LoginResponse("login failed", user, client);
		response.handleResponse();
		assertNull(user.getNickname());
		assertNull(user.getEmail());
		assertNull(user.getPassword());
	}

}
