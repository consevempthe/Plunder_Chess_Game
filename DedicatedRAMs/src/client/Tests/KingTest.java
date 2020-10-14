package client.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

class KingTest {

	private ChessBoard board = new ChessBoard();
	private King white;
	private King black;

	@BeforeEach
	void setUp() {
		board = new ChessBoard();
		white = new King(board, Color.WHITE);
		black = new King(board, Color.BLACK);
	}
	
	
	@Test
	void testAllowedVests() {
		ArrayList<Class<?>> types = white.getPlunderableTypes();
		assertTrue(types.contains(Rook.class));
		assertTrue(types.contains(Pawn.class));
		assertTrue(types.contains(Queen.class));
		assertTrue(types.contains(Bishop.class));
		assertTrue(types.contains(Knight.class));
	}
	
	@Test
	void testToString() {
		assertEquals("\u2654", white.toString(), "Expect White King's Unicode to match the toString return.");
		assertEquals("\u265A", black.toString(), "Expect Black King's Unicode to match the toString return.");
	}
	
	@Test
	void testLegalMovesWhite1() {
		board.placePiece(white, "a1");
		ArrayList<String> moveW = white.legalMoves(false, true);
		assertEquals(3, moveW.size(), "Expect White King's number of moves.");
		assertTrue(moveW.contains("b1"), "Expect White King's move set.");
		assertTrue(moveW.contains("b2"), "Expect White King's move set.");
		assertTrue(moveW.contains("a2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesWhite2() throws IllegalPositionException {
		board.initialize();
		white = (King) board.getPiece("e1");
		ArrayList<String> moveW = white.legalMoves(true, true);
		assertEquals(0, moveW.size(), "Expect no possible moves.");
		board.getHistory().addMoveToMoveHistory(new Move(null, "e2", "e2", null));
		board.placePiece(new King(board, Color.BLACK), "e2");

		moveW = white.legalMoves(true, true);
		assertEquals(1,  moveW.size(), "Expect 1 possible move.");
		assertTrue(moveW.contains("e2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesBlack1() {
		board.placePiece(black, "a1");
		ArrayList<String> moveB = black.legalMoves(true, false);
		assertEquals(3, moveB.size(), "Expect White King's number of moves.");
		assertTrue(moveB.contains("b1"), "Expect Black King's move set.");
		assertTrue(moveB.contains("b2"), "Expect Black King's move set.");
		assertTrue(moveB.contains("a2"), "Expect Black King's move set.");
	}

	@Test
	void testLegalMovesBlack2() {
		board.initialize();
		board.placePiece(black, "e3");
		ArrayList<String> moveB = black.legalMoves(false, true);
		assertEquals(8, moveB.size(), "Expect 8 possible moves.");
		board.getHistory().addMoveToMoveHistory(new Move(null, "e2", "e2", null));
		board.placePiece(new Queen(board, Color.BLACK), "e2");
		moveB = black.legalMoves(false, true);
		assertEquals(7,  moveB.size(), "Expect 7 possible move.");
	}
	
	@Test
	void testLegalMovesCheckSimulation() throws IllegalPositionException {
		board.initialize();
		white = (King) board.getPiece("e1");
		black = (King) board.getPiece("e8");
		board.placePiece(white, "e6");
		ArrayList<String> moves = white.legalMoves(true, true);
		assertEquals(3, moves.size());
	}
	
	@Test
	void testLegalMovesWithVest() throws IllegalPositionException {
		board.placePiece(black, "a1");
		black.setVest(new Rook(board, black.getColor()));
		ArrayList<String> moveB = black.legalMoves(true, true);
		assertEquals(17,  moveB.size(), "Expect 17 possible move, two are repeats.");
		assertTrue(moveB.contains("a2"));
		assertTrue(moveB.contains("a3"));
		assertTrue(moveB.contains("a4"));
		assertTrue(moveB.contains("a5"));
		assertTrue(moveB.contains("a6"));
		assertTrue(moveB.contains("a7"));
		assertTrue(moveB.contains("a8"));
		assertTrue(moveB.contains("b1"));
		assertTrue(moveB.contains("c1"));
		assertTrue(moveB.contains("d1"));
		assertTrue(moveB.contains("e1"));
		assertTrue(moveB.contains("f1"));
		assertTrue(moveB.contains("g1"));
		assertTrue(moveB.contains("h1"));
		assertTrue(moveB.contains("a2"));
	}
	


}
