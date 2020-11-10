package client.Tests;

import gameLogic.ChessBoard;
import gameLogic.Player;
import org.junit.jupiter.api.BeforeEach;


class PlayerTest {

	private ChessBoard board;
	private Player p1;
	@BeforeEach
	public void setUp() {
		board  = new ChessBoard();
		p1 = new Player(gameLogic.Player.Color.BLACK, "Nicknamey");
	}

}
