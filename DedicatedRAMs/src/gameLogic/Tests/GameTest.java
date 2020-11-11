package gameLogic.Tests;

import client.User;
import gameLogic.Game;
import gameLogic.GameStatus.Status;
import gameLogic.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {

	private Game game;

	@BeforeEach
	public void setUp() {
		User user = new User("nickname", "a@colo.edu", "pass");
		game  = new Game("100", user);
		game.setPlayers(new Player(Player.Color.WHITE,  "nickname"), new Player(Player.Color.BLACK,  "nickname2"));
	}

	@Test
	void testStartGame(){
		game.startGame();
		assertEquals(Status.INPROGRESS, game.getGameStatus().getStatus(), "Game status");

	}

	@Test
	void testIncrement(){
		game.incrementTurn();
		assertEquals(1, game.getTurnCount(), "Game turn count.");
		assertEquals(game.getCurrentPlayerColor(), Player.Color.BLACK);
		game.incrementTurn();
		assertEquals(2, game.getTurnCount(), "Game turn count.");
		assertEquals(game.getCurrentPlayerColor(), Player.Color.WHITE);

	}
}
