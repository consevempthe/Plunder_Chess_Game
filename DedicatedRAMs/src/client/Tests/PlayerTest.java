package client.Tests;

import client.ChessBoard;
import client.Player;
import org.junit.jupiter.api.BeforeEach;


class PlayerTest {

	private ChessBoard board;
	private Player p1;
	@BeforeEach
	public void setUp() {
		board  = new ChessBoard();
		p1 = new Player(client.Player.Color.BLACK, "Nicknamey");
	}

}
