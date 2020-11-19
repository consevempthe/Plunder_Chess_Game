package gameLogic.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Game;
import client.MatchHistory;

class MatchHistoryTest {

	private MatchHistory history;
	
	@BeforeEach
	void setup() {
		history = new MatchHistory();
	}
	
	@Test
	void testAddGame() {
		history.addGame(new Game("test", null));
		assertEquals(1, history.getGames().size());
	}

	@Test
	void testGetGames() {
		assertEquals(0, history.getGames().size());
	}
	
	@Test
	void testGetGame() {
		history.addGame(new Game("test", null));
		Game game = history.getGame("test");
		assertEquals("test", game.getGameID());
		game = history.getGame("test1");
		assertEquals(null, game);
	}

}
