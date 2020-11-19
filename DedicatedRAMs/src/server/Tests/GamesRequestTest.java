package server.Tests;

import client.Client;
import server.DatabaseAccessor;
import server.GamesRequest;
import exceptions.IllegalRequestException;
import server.Server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GamesRequestTest{
	String request = "games test";
	GamesRequest games;
	Server server = new Server(4001);
	Client client = new Client("localhost", 4001);

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
