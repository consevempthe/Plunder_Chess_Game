package server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRequestTest {
	private GameRequest game;
	Server server = new Server(4000);
	private ServerWorker worker;
	@BeforeEach
	void setup() {
		worker = new ServerWorker(null, null);
	}
	
	@Test
	void testBuildResponseSucceed() throws IllegalRequestException { //Not succeeding. Cathy, maybe you can take a look.
		game = new GameRequest("game user1 user2 101", worker, server);
		String res = game.buildResponse();
		assertEquals("game success user1 user2 101", res);
	}
	

}