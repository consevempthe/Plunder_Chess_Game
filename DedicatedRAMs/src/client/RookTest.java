package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RookTest {
	
	private ChessBoard board;
	private Rook whiteRook;
	private Rook blackRook;
	
	private final String whiteRookUnicode = "\u2656";
	private final String blackRookUnicode = "\u265C";

	@BeforeEach
	void init() {
		board = new ChessBoard();
		whiteRook = new Rook(board, ChessPiece.Color.WHITE);
		blackRook = new Rook(board, ChessPiece.Color.BLACK);
	}
	
	@Test
	void testToString() {
		assertEquals(whiteRookUnicode, whiteRook.toString());
		assertEquals(blackRookUnicode, blackRook.toString());
	}
	
	// This test simply has an empty board with a single white rook in location e5
	@Test
	void simpleLegalMovesTest() {
		board.placePiece(whiteRook, "e5");
		ArrayList<String> legalRookMoves = whiteRook.legalMoves();
		assertEquals(true, legalRookMoves.contains("e6"));
		assertEquals(true, legalRookMoves.contains("e7"));
		assertEquals(true, legalRookMoves.contains("e8"));
		assertEquals(true, legalRookMoves.contains("f5"));
		assertEquals(true, legalRookMoves.contains("g5"));
		assertEquals(true, legalRookMoves.contains("h5"));
		assertEquals(true, legalRookMoves.contains("d5"));
		assertEquals(true, legalRookMoves.contains("c5"));
		assertEquals(true, legalRookMoves.contains("b5"));
		assertEquals(true, legalRookMoves.contains("a5"));
		assertEquals(true, legalRookMoves.contains("e4"));
		assertEquals(true, legalRookMoves.contains("e3"));
		assertEquals(true, legalRookMoves.contains("e2"));
		assertEquals(true, legalRookMoves.contains("e1"));
		assertEquals(14, legalRookMoves.size());
	}
	
	// puts rook in corner of board at location a8
	@Test
	void cornerLegalMovesTest() {
		board.placePiece(whiteRook, "a8");
		ArrayList<String> legalRookMoves = whiteRook.legalMoves();
		assertEquals(true, legalRookMoves.contains("h8"));
		assertEquals(true, legalRookMoves.contains("b8"));
		assertEquals(true, legalRookMoves.contains("c8"));
		assertEquals(true, legalRookMoves.contains("d8"));
		assertEquals(true, legalRookMoves.contains("e8"));
		assertEquals(true, legalRookMoves.contains("f8"));
		assertEquals(true, legalRookMoves.contains("g8"));
		assertEquals(true, legalRookMoves.contains("a7"));
		assertEquals(true, legalRookMoves.contains("a6"));
		assertEquals(true, legalRookMoves.contains("a5"));
		assertEquals(true, legalRookMoves.contains("a4"));
		assertEquals(true, legalRookMoves.contains("a3"));
		assertEquals(true, legalRookMoves.contains("a2"));
		assertEquals(true, legalRookMoves.contains("a1"));
		assertEquals(14, legalRookMoves.size());
	}
	
	// surround white rook with black pieces
	@Test
	void surroundedLegalMovesTest1() {
		board.placePiece(whiteRook, "d4");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "e4");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "d5");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "c4");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "d3");
		ArrayList<String> legalRookMoves = whiteRook.legalMoves();
		assertEquals(true, legalRookMoves.contains("e4"));
		assertEquals(true, legalRookMoves.contains("d5"));
		assertEquals(true, legalRookMoves.contains("c4"));
		assertEquals(true, legalRookMoves.contains("d3"));
		assertEquals(4, legalRookMoves.size());
	}
	
	// surround white rook with white pieces
	@Test
	void surroundedLegalMovesTest2() {
		board.placePiece(whiteRook, "d4");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "e4");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "d5");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "c4");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "d3");
		ArrayList<String> legalRookMoves = whiteRook.legalMoves();
		assertEquals(true, legalRookMoves.isEmpty());
	}

}
