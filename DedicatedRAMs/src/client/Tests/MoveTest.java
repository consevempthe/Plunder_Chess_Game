package client.Tests;

import static org.junit.jupiter.api.Assertions.*;

import client.ChessPiece;
import client.King;
import client.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

class MoveTest {

	private Move move;
	private ChessPiece piece = new King(null, Color.WHITE);
	@BeforeEach
	void setUp() {
		move = new Move(piece, "a1", "a2", null);
	}

	@Test
	void testGetPiece() {
		ChessPiece test = move.getPieceMoved();
		assertEquals(piece, test);
	}

	@Test
	void testGetFrom() {
		String test = move.getFrom();
		assertEquals("a1", test);
	}

	@Test
	void testGetTo() {
		String test = move.getTo();
		assertEquals("a2", test);
	}

}
