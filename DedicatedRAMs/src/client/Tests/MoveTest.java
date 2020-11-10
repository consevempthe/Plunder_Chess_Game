package client.Tests;

import gameLogic.ChessPiece;
import gameLogic.King;
import gameLogic.Move;
import gameLogic.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

	private Move move;
	private ChessPiece piece = new King(null, Color.WHITE);
	@BeforeEach
	void setUp() {
		move = new Move(piece, "a1", "a2");
	}

	@Test
	void testGetPiece() {
		ChessPiece test = move.getPieceMoved();
		assertEquals(piece, test);
	}

	@Test
	void testGetFrom() {
		String test = move.getCurrentPos();
		assertEquals("a1", test);
	}

	@Test
	void testGetTo() {
		String test = move.getNewPos();
		assertEquals("a2", test);
	}

}
