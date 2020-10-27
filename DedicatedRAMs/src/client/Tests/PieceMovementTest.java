package client.Tests;

import client.*;
import client.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PieceMovementTest {

	PieceMovement movement;
	ChessBoard board;
	@BeforeEach
	void setUp() throws IllegalPositionException {
		String input = "n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		board  = new ChessBoard(in);
		board.initialize();
		movement = new PieceMovement(board.getHistory(), board, board.getPiece("d2"));
	}

	@Test
	void testPawnPlusOne() {
		String test = movement.pawnPlusOne();
		assertEquals("d3", test);
	}

	@Test
	void testPawnPlusTwo() {
		String test = movement.pawnPlusTwo();
		assertEquals("d4", test);
	}

	@Test
	void testPawnCapture() {
		ArrayList<String> moves = movement.pawnCapture();
		assertEquals(0,  moves.size());
		board.placePiece(new Pawn(board, Color.BLACK), "c3", false);
		moves = movement.pawnCapture();
		assertTrue(moves.contains("c3"));
	}

	@Test
	void testKingCircleOfMoves() {
		ArrayList<String> moves = movement.kingCircleOfMoves();
		assertEquals(3, moves.size());
		assertTrue(moves.contains("c3"));
		assertTrue(moves.contains("d3"));
		assertTrue(moves.contains("e3"));
	}

	@Test
	void testKnightJumpMove() {
		ArrayList<String> moves = movement.knightJumpMove();
		assertEquals(4, moves.size());
		assertTrue(moves.contains("b3"));
		assertTrue(moves.contains("c4"));
		assertTrue(moves.contains("e4"));
		assertTrue(moves.contains("f3"));
	}

	@Test
	void testLongRangeMoves() {
		ArrayList<String> moves = movement.longRangeMoves("Straight");
		assertEquals(5, moves.size());
		assertTrue(moves.contains("d4"));
		assertTrue(moves.contains("d3"));
		assertTrue(moves.contains("d5"));
		assertTrue(moves.contains("d6"));
		assertTrue(moves.contains("d7"));
		moves = movement.longRangeMoves("Diagonal");
		assertEquals(7, moves.size());
		assertTrue(moves.contains("c3"));
		assertTrue(moves.contains("b4"));
		assertTrue(moves.contains("a5"));
		assertTrue(moves.contains("e3"));
		assertTrue(moves.contains("f4"));
		assertTrue(moves.contains("g5"));
		assertTrue(moves.contains("h6"));
	}
	
	@Test
	void testEnPassantMove() throws IllegalMoveException, IllegalPositionException {
		board.placePiece(new Pawn(board, Color.WHITE), "b5", false);
		movement = new PieceMovement(board.getHistory(), board, board.getPiece("b5"));
		String move = movement.enPassantMove();
		assertNull(move);
		board.setTurnWhite(false);
    	board.move("a7", "a5");
        move = movement.enPassantMove();
        assertEquals("a6", move);
        board.move("e7", "e5");
        move = movement.enPassantMove();
		assertNull(move);
	}

}
