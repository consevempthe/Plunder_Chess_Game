package client.Tests;

import client.Bishop;
import client.ChessBoard;
import client.IllegalPositionException;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;

public class BishopTest {
	
	private ChessBoard board = new ChessBoard();
	private Bishop white;
	private Bishop black;
	
	@BeforeEach
	void setUp() {
		white = new Bishop(board, Color.WHITE);
		black = new Bishop(board, Color.BLACK);
	}

	@Test
	public void testBishopLegalMoves() throws IllegalPositionException {
		black.setPosition("b6");

		ArrayList<String> legalMoves = black.legalMoves(true, true);

		assertTrue(legalMoves.contains("a5"), "Legal moves should contain a5");
		assertTrue(legalMoves.contains("a7"), "Legal moves should contain a7");
		assertTrue(legalMoves.contains("c7"), "Legal moves should contain c7");
		assertTrue(legalMoves.contains("d8"), "Legal moves should contain d8");
		assertTrue(legalMoves.contains("c5"), "Legal moves should contain c5");
		assertTrue(legalMoves.contains("d4"), "Legal moves should contain d4");
		assertTrue(legalMoves.contains("e3"), "Legal moves should contain e3");
		assertTrue(legalMoves.contains("f2"), "Legal moves should contain f2");
		assertTrue(legalMoves.contains("g1"), "Legal moves should contain g1");

		assertFalse(legalMoves.contains("b6"), "Legal moves should not contain b6");
		assertEquals(9, legalMoves.size(), "There are only 9 legal moves for position b6");

	}

	@Test
	public void testBishopToString() {
		assertEquals(white.toString(), "\u2657");
		assertEquals(black.toString(), "\u265D");
	}

	@Test
	public void testBishopSetPosition() throws IllegalPositionException {
		assertThrows(IllegalPositionException.class, () -> black.setPosition("gg"), "gg is an invalid position");
		assertThrows(IllegalPositionException.class, () -> black.setPosition("123"), "123 is an invalid position");
		assertThrows(IllegalPositionException.class, () -> black.setPosition(""), " is an invalid position");

		black.setPosition("f2");
		assertEquals(black.getPosition(), "f2");
	}
}

