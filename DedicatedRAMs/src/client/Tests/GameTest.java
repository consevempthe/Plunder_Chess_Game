package client.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Game;
import client.IllegalMoveException;
import client.IllegalPositionException;
import client.User;
import client.GameStatus.Status;


public class GameTest {

	private Game game;
	private User user;

	@BeforeEach
	public void setUp() {
		user = new User("nickname", "a@colo.edu", "pass");
		game  = new Game("100", user);
	}

	@Test
	void testStartGame(){
		game.startGame();
		assertEquals(Status.INPROGRESS, game.getGameStatus().getStatus(), "Game status");

	}
	
	void testIncrement(){
		game.incrementTurn();
		assertEquals(1, game.getTurnCount(), "Game turn count.");
		assertEquals(false, game.getGameBoard().getTurnWhite(), "Board TurnWhite false.");
		game.incrementTurn();
		assertEquals(2, game.getTurnCount(), "Game turn count.");
		assertTrue(game.getGameBoard().getTurnWhite(), "Board TurnWhite true.");

	}
}
