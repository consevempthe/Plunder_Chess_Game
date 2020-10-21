package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import client.Client;

public class GamesRequestTest{
	String request = "games test";
	GamesRequest games;
	Server server = new Server(4000);
	Client client = new Client("localhost", 4000);

	DatabaseAccessor accessor = new DatabaseAccessor();
	
	@Test
	void testBuildResponse() throws IllegalRequestException {
		games = new GamesRequest(request);
		String response = games.buildResponse();
		assertEquals("games success gamename gamename1 gamename2", response, "Game ids using nickname test");
	}
	
	@Test
	void testBadConstructor() {
		assertThrows(IllegalRequestException.class, () -> games = new GamesRequest("games test user1"));
		assertThrows(IllegalRequestException.class, () -> games = new GamesRequest("games"));
	}
}
