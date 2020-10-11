package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

class MoveTest {

	private Move move;
	private ChessPiece piece = new King(null, Color.WHITE);
	@BeforeEach
	void setUp() {
		move = new Move(piece, "a1", "a2");
	}

	@Test
	void testGetPiece() {
		ChessPiece test = move.getPiece();
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