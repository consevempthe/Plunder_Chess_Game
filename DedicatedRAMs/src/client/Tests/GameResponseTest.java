package client.Tests;

import client.Client;
import client.Game;
import client.GameResponse;
import client.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameResponseTest {

	private GameResponse response;
	private User user = new User("ethan", "test@mail.com", "password");
	private Client client = new Client("localhost", 8818);

	
	@Test
	void testHandleResponse() {
		client.setGame(new Game("1001", user));
		response = new GameResponse("game success user1 user2 1001", user, client);
		response.handleResponse();
		assertEquals("1001", client.getGame().getGameID(), "Game ID");
	}


}
