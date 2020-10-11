package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

class KingTest {

	private ChessBoard board = new ChessBoard();
	private King white;
	private King black;

	@BeforeEach
	void setUp() {
		white = new King(board, Color.WHITE);
		black = new King(board, Color.BLACK);
	}
	
	
	@Test
	void testToString() {
		assertEquals("\u2654", white.toString(), "Expect White King's Unicode to match the toString return.");
		assertEquals("\u265A", black.toString(), "Expect Black King's Unicode to match the toString return.");
	}
	
	@Test
	void testLegalMovesWhite1() {
		ArrayList<String> moveW = white.legalMoves(true);
		assertEquals(3, moveW.size(), "Expect White King's number of moves.");

		assertEquals(true, moveW.contains("b1"), "Expect White King's move set.");
		assertEquals(true, moveW.contains("b2"), "Expect White King's move set.");
		assertEquals(true, moveW.contains("a2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesWhite2() throws IllegalPositionException {
		//Will succeed when other pieces are finished.
		board.initialize();
		white = (King) board.getPiece("e1");
		ArrayList<String> moveW = white.legalMoves(true);
		assertEquals(0, moveW.size(), "Expect no possible moves.");
		board.placePiece(new King(board, Color.BLACK), "e2");
		moveW = white.legalMoves(true);
		assertEquals(1,  moveW.size(), "Expect 1 possible move.");
		assertEquals(true, moveW.contains("e2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesBlack1() {
		ArrayList<String> moveB = black.legalMoves(true);
		assertEquals(3, moveB.size(), "Expect White King's number of moves.");
		assertEquals(true, moveB.contains("b1"), "Expect Black King's move set.");
		assertEquals(true, moveB.contains("b2"), "Expect Black King's move set.");
		assertEquals(true, moveB.contains("a2"), "Expect Black King's move set.");
	}

	@Test
	void testLegalMovesBlack2() throws IllegalPositionException {
		board.initialize();
		board.placePiece(black, "e3");
		ArrayList<String> moveB = black.legalMoves(true);
		assertEquals(8, moveB.size(), "Expect 8 possible moves.");
		board.placePiece(new King(board, Color.BLACK), "e2");
		moveB = black.legalMoves(true);
		assertEquals(7,  moveB.size(), "Expect 7 possible move.");
	}

}
