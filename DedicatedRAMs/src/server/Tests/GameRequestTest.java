package server.Tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import client.Client;
import server.GameRequest;
import exceptions.IllegalRequestException;
import server.Server;
import server.ServerWorker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

public class GameRequestTest {
	private GameRequest game;
	private static int port = 4456;
	static Server server = new Server(port);
	private ServerWorker worker1;
	private ServerWorker worker2;

	@BeforeAll
	static void setup() throws IllegalRequestException, IOException {
    	server.start();
    	Client client1 = new Client("localhost", port);
    	Client client2 = new Client("localhost", port);
    	client1.connect();
    	client2.connect();
    	
    	server.getWorkerList().get(0).setNickname("user1");
    	server.getWorkerList().get(1).setNickname("user2");
	}
	
	@Test
	void testBuildResponseSucceed() throws IllegalRequestException {
		worker1 = server.findWorker("user1");
		game = new GameRequest("game user1 user2 101", worker1, server);
		String res = game.buildResponse();
		assertEquals("game success user1 user2 101", res);
	}
	
	@Test
	void testBuildResponseFail() throws IllegalRequestException {
		game = new GameRequest("game user1 user2000 101", worker1, server);
		String res = game.buildResponse();
		assertEquals("User user2000 not online", res);
	}

}