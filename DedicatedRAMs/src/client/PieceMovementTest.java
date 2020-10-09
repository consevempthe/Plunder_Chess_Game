package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

class PieceMovementTest {

	PieceMovement movement;
	ChessBoard board = new ChessBoard();
	@BeforeEach
	void setUp() throws IllegalPositionException{
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
		board.placePiece(new Pawn(board, Color.BLACK), "c3");
		moves = movement.pawnCapture();
		assertEquals(true, moves.contains("c3"));
	}

	@Test
	void testKingCircleOfMoves() {
		ArrayList<String> moves = movement.kingCircleOfMoves();
		assertEquals(3, moves.size());
		assertEquals(true, moves.contains("c3"));
		assertEquals(true, moves.contains("d3"));
		assertEquals(true, moves.contains("e3"));
	}

	@Test
	void testKnightJumpMove() {
		ArrayList<String> moves = movement.knightJumpMove();
		assertEquals(4, moves.size());
		assertEquals(true, moves.contains("b3"));
		assertEquals(true, moves.contains("c4"));
		assertEquals(true, moves.contains("e4"));
		assertEquals(true, moves.contains("f3"));
	}

	@Test
	void testLongRangeMoves() {
		ArrayList<String> moves = movement.longRangeMoves("Straight");
		assertEquals(5, moves.size());
		assertEquals(true, moves.contains("d4"));
		assertEquals(true, moves.contains("d3"));
		assertEquals(true, moves.contains("d5"));
		assertEquals(true, moves.contains("d6"));
		assertEquals(true, moves.contains("d7"));
		moves = movement.longRangeMoves("Diagonal");
		assertEquals(7, moves.size());
		assertEquals(true, moves.contains("c3"));
		assertEquals(true, moves.contains("b4"));
		assertEquals(true, moves.contains("a5"));
		assertEquals(true, moves.contains("e3"));
		assertEquals(true, moves.contains("f4"));
		assertEquals(true, moves.contains("g5"));
		assertEquals(true, moves.contains("h6"));
	}
	
	@Test
	void testEnPassantMove() throws IllegalMoveException, IllegalPositionException {
		board.placePiece(new Pawn(movement.board, Color.WHITE), "b5");
		movement = new PieceMovement(board.getHistory(), board, board.getPiece("b5"));
		String move = movement.enPassantMove();
    	assertEquals(null, move);
    	board.move("a7", "a5");
        move = movement.enPassantMove();
        assertEquals("a6", move);
        board.move("e7", "e5");
        move = movement.enPassantMove();
        assertEquals(null, move);
	}

}
