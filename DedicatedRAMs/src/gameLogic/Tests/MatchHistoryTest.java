package gameLogic.Tests;

import gameLogic.Game;
import gameLogic.MatchHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
		assertNull(game);
	}

}
