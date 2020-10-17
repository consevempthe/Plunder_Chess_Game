package client.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import client.Client;
import client.Game;
import client.GameResponse;
import client.Player;
import client.Response;
import client.User;
import client.Player.Color;

public class GameResponseTest {

	private GameResponse response;
	private User user = new User(null, null, null);
	private Client client = new Client("localhost", 8818);

	
	@Test
	void testHandleResponse() {
		client.game = new Game("1001", user);
		response = new GameResponse("game success user1 user2 1001", user, client);
		response.handleResponse();
		assertEquals("1001", client.game.getGameID(), "Game ID");
		assertEquals("user1", client.game.getPlayers()[0].getNickname(), "Player1 nickname.");
		assertEquals(Color.WHITE, client.game.getPlayers()[0].getColor(), "Player1 color.");
		assertEquals(true, client.game.getPlayers()[0].getTurn(), "Player1 turn true.");
		assertEquals("user2", client.game.getPlayers()[1].getNickname(), "Player2 nickname.");
		assertEquals(Color.BLACK, client.game.getPlayers()[1].getColor(), "Player2 color.");
		assertEquals(false, client.game.getPlayers()[1].getTurn(), "Player2 turn false.");
	}


}
