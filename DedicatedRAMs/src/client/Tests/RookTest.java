package client.Tests;

import client.ChessBoard;
import client.Player.Color;
import client.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RookTest {
	
	private ChessBoard board;
	private Rook whiteRook;
	private Rook blackRook;

	@BeforeEach
	void init() {
		board  = new ChessBoard();
		whiteRook = new Rook(board, Color.WHITE);
		blackRook = new Rook(board, Color.BLACK);
	}
	
	@Test
	void testToString() {
		String whiteRookUnicode = "\u2656";
		assertEquals(whiteRookUnicode, whiteRook.toString());
		String blackRookUnicode = "\u265C";
		assertEquals(blackRookUnicode, blackRook.toString());
	}
	
	// This test simply has an empty board with a single white rook in location e5
	@Test
	void simpleLegalMovesTest() {
		board.placePiece(whiteRook, "e5", false);
		ArrayList<String> legalRookMoves = whiteRook.legalMoves(true, true);
		assertTrue(legalRookMoves.contains("e6"));
		assertTrue(legalRookMoves.contains("e7"));
		assertTrue(legalRookMoves.contains("e8"));
		assertTrue(legalRookMoves.contains("f5"));
		assertTrue(legalRookMoves.contains("g5"));
		assertTrue(legalRookMoves.contains("h5"));
		assertTrue(legalRookMoves.contains("d5"));
		assertTrue(legalRookMoves.contains("c5"));
		assertTrue(legalRookMoves.contains("b5"));
		assertTrue(legalRookMoves.contains("a5"));
		assertTrue(legalRookMoves.contains("e4"));
		assertTrue(legalRookMoves.contains("e3"));
		assertTrue(legalRookMoves.contains("e2"));
		assertTrue(legalRookMoves.contains("e1"));
		assertEquals(14, legalRookMoves.size());
	}
	
	// puts rook in corner of board at location a8
	@Test
	void cornerLegalMovesTest() {
		board.placePiece(whiteRook, "a8", false);
		ArrayList<String> legalRookMoves = whiteRook.legalMoves(true, true);
		assertTrue(legalRookMoves.contains("h8"));
		assertTrue(legalRookMoves.contains("b8"));
		assertTrue(legalRookMoves.contains("c8"));
		assertTrue(legalRookMoves.contains("d8"));
		assertTrue(legalRookMoves.contains("e8"));
		assertTrue(legalRookMoves.contains("f8"));
		assertTrue(legalRookMoves.contains("g8"));
		assertTrue(legalRookMoves.contains("a7"));
		assertTrue(legalRookMoves.contains("a6"));
		assertTrue(legalRookMoves.contains("a5"));
		assertTrue(legalRookMoves.contains("a4"));
		assertTrue(legalRookMoves.contains("a3"));
		assertTrue(legalRookMoves.contains("a2"));
		assertTrue(legalRookMoves.contains("a1"));
		assertEquals(14, legalRookMoves.size());
	}
	
	// surround white rook with black pieces
	@Test
	void surroundedLegalMovesTest1() {
		board.placePiece(whiteRook, "d4", false);
		board.placePiece(new Rook(board, Color.BLACK), "e4", false);
		board.placePiece(new Rook(board, Color.BLACK), "d5", false);
		board.placePiece(new Rook(board, Color.BLACK), "c4", false);
		board.placePiece(new Rook(board, Color.BLACK), "d3", false);
		ArrayList<String> legalRookMoves = whiteRook.legalMoves(true, true);
		assertTrue(legalRookMoves.contains("e4"));
		assertTrue(legalRookMoves.contains("d5"));
		assertTrue(legalRookMoves.contains("c4"));
		assertTrue(legalRookMoves.contains("d3"));
		assertEquals(4, legalRookMoves.size());
	}
	
	// surround white rook with white pieces
	@Test
	void surroundedLegalMovesTest2() {
		board.placePiece(whiteRook, "d4", false);
		board.placePiece(new Rook(board, Color.WHITE), "e4", false);
		board.placePiece(new Rook(board, Color.WHITE), "d5", false);
		board.placePiece(new Rook(board, Color.WHITE), "c4", false);
		board.placePiece(new Rook(board, Color.WHITE), "d3", false);
		ArrayList<String> legalRookMoves = whiteRook.legalMoves(true, true);
		assertTrue(legalRookMoves.isEmpty());
	}

}
