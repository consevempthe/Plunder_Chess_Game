package client.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;


class ChessBoardTest {

	private ChessBoard board;
	private King whiteKing;
	private King blackKing;

	@BeforeEach
	public void setUp() {
		board  = new ChessBoard();
		whiteKing = new King(board, Color.WHITE);
		blackKing = new King(board, Color.BLACK);
		board.setWhiteKing(whiteKing);
		board.setBlackKing(blackKing);
	}

	@Test
	void testPlacePieceCorrect(){
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1");
		assertTrue(placed, "Place Piece Failed When It Should Have Succeeded!");
	}

	@Test
	void testPlacePiecePositionLength() {
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1d");
		boolean placed2 = board.placePiece(new Queen(board, Color.WHITE), "a");
		assertFalse(placed, "Position should be too long!");
		assertFalse(placed2, "Position should be too short!");
	}
	@Test
	void testPlacePiecePositionIllegalCharacter() {
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "^1");
		boolean placed2 = board.placePiece(new Queen(board, Color.WHITE), "g*");
		assertFalse(placed, "Position ^1 should return false!");
		assertFalse(placed2, "Position g* should return false!");
	}
	@Test
	void testPlacePieceAlreadyFilledSameColor() {
		board.placePiece(new Queen(board, Color.WHITE), "a1");
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1");
		assertFalse(placed, "Position is already filled by White and should return false!");
	}
	@Test
	void testPlacePieceAlreadyFilledDifferentColor() {
		board = new ChessBoard();
		board.placePiece(new Queen(board, Color.BLACK), "a1");
        board.getHistory().addMoveToMoveHistory(new Move(new Rook(null, Color.BLACK), "e2", "e2", null));
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1");
		assertTrue(placed, "Piece is captured and should return true!");
	}


	@Test
	void testGetPieceNull() throws IllegalPositionException {
		ChessPiece piece = board.getPiece("a1");
		assertNull(piece, "Position a1 should return null!");
	}

	@Test
	void testGetPieceNotNull() throws IllegalPositionException {
		board.initialize();
		ChessPiece piece = board.getPiece("d1");
		assertNotEquals(null, piece, "Position d1 should return a piece.");
	}
	@Test
	void testGetPiecePositionLength() {
		assertThrows(IllegalPositionException.class, ()->board.getPiece("a1d"),
				"IllegelPositionException expected");
	}
	@Test
	void testGetPiecePositionIllegalCharacter() {
		assertThrows(IllegalPositionException.class, ()->board.getPiece("i1"),
				"IllegelPositionException expected");
		assertThrows(IllegalPositionException.class, ()->board.getPiece("g9"),
				"IllegelPositionException expected");
	}

	@Test
	void testRemovePiece() throws Exception {
		board.placePiece(new Queen(board, Color.WHITE), "c4");
		board.move("c4", "c8");
		assertNull(board.getPiece("c4"));
	}

	@Test
	void testMove() throws Exception {
		board.initialize();
		assertNull(board.getPiece("c4"));
		board.move("c2", "c4");
		assertNull(board.getPiece("c2"));
		assertNotNull(board.getPiece("c4"));

		board.move("g1", "f3");
		assertEquals(new Knight(board, Color.WHITE).toString(), board.getPiece("f3").toString());
	}

	@Test
	void testIllegalMoves() {
		board.initialize();
		assertThrows(IllegalMoveException.class, () -> board.move("d(", "d4"));
		assertThrows(IllegalMoveException.class, () -> board.move("d1", "d4"));
		assertThrows(IllegalMoveException.class, () -> board.move("e1", "e2"));
		assertThrows(IllegalMoveException.class, () -> board.move("d5", "d6"));
	}

	@Test
	void testInitializedBoard() throws IllegalPositionException {
		board.initialize();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				String position = (char)('a'+j) +"" +(char)('1' + i);
				ChessPiece piece = board.getPiece(position);
				if(i < 2 || i > 5) {
					assertNotEquals(null, piece, "Position should return a piece.");
				}
				else
					assertNull(piece, "Position should be empty.");
			}
		}
	}
	
	@Test
	void testReplacePiece() {
		board.placePiece(new Pawn(board, ChessPiece.Color.WHITE), "d5");
		try {
			ChessPiece piece = board.getPiece("d5");
			boolean instance = piece instanceof Pawn;
			assertTrue(instance);
			assertEquals("\u2659", piece.toString());
			board.replacePiece(new Queen(board, ChessPiece.Color.WHITE), "d5");
			piece = board.getPiece("d5");
			instance = piece instanceof Queen;
			assertEquals("\u2655", piece.toString());
			assertTrue(instance);
		} catch (IllegalPositionException e) {
			fail("testReplacePiece test failed");
		}
	}
	
	@Test
	void testCheckMate1() {
		board.setWhiteKing(whiteKing);
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "c6");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "d6");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "e6");
		board.placePiece(whiteKing, "d4");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.WHITE));
		
	}
	
	@Test
	void testCheckMate2() {
		board.setWhiteKing(whiteKing);
		board.placePiece(new King(board, ChessPiece.Color.WHITE), "d6");
		assertEquals(false, board.isCheckMate(ChessPiece.Color.WHITE));
	}
	
	@Test
	void testKingDoesNotMoveFromCheckmateAlgorithm() {
		ChessPiece piece = null;
		board.placePiece(new King(board, ChessPiece.Color.WHITE), "d6");
		try {
			piece = board.getPiece("d6");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		assertEquals(false, board.isCheckMate(ChessPiece.Color.WHITE));
		ChessPiece piece2 = null;
		try {
			piece2 = board.getPiece("d6");
		} catch(IllegalPositionException e) {
			e.printStackTrace();
		}
		assertEquals(true, piece2 instanceof King);
		assertEquals("d6", piece2.getPosition());
	}
	
	@Test
	void testInitialBoardWhite() {
		board.initialize();
		assertEquals(false, board.isCheckMate(Color.WHITE));
	}
	
	@Test
	void testInitialBoardBlack() {
		board.initialize();
		assertEquals(false, board.isCheckMate(Color.BLACK));
	}
	
	@Test
	void testAnastasiasMate() {
		board.placePiece(whiteKing, "h7");
		board.placePiece(new Pawn(board, ChessPiece.Color.WHITE), "g7");
		board.placePiece(new Knight(board, ChessPiece.Color.BLACK), "e7");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "h5");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.WHITE));
	}
	
	@Test
	void testAnderssensMate () {
		board.placePiece(new King(board, ChessPiece.Color.WHITE), "f6");
		board.placePiece(new Pawn(board, ChessPiece.Color.WHITE), "g7");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "h8");
		board.placePiece(blackKing, "g8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testArabianMate() {
		board.placePiece(new Knight(board, ChessPiece.Color.WHITE), "f6");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "h7");
		board.placePiece(blackKing, "h8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBackRateMate() {
		board.placePiece(new Pawn(board, ChessPiece.Color.BLACK), "f7");
		board.placePiece(new Pawn(board, ChessPiece.Color.BLACK), "g7");
		board.placePiece(new Pawn(board, ChessPiece.Color.BLACK), "h7");
		board.placePiece(blackKing, "g8");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "d8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBishopKnightKingMate() {
		board.placePiece(new King(board, ChessPiece.Color.WHITE), "g6");
		board.placePiece(new Knight(board, ChessPiece.Color.WHITE), "h6");
		board.placePiece(new Bishop(board, ChessPiece.Color.WHITE), "f6");
		board.placePiece(blackKing, "h8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBlackburnesMate () {
		board.placePiece(new Bishop(board, ChessPiece.Color.WHITE), "b2");
		board.placePiece(new Knight(board, ChessPiece.Color.WHITE), "g5");
		board.placePiece(new Bishop(board, ChessPiece.Color.WHITE), "h7");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "f8");
		board.placePiece(blackKing, "g8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBlindSwineMate() {
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "g7");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "h7");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "f8");
		board.placePiece(blackKing, "g8");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBodensMate() {
		board.placePiece(blackKing, "c8");
		board.placePiece(new Pawn(board, ChessPiece.Color.BLACK), "d7");
		board.placePiece(new Rook(board, ChessPiece.Color.BLACK), "d8");
		board.placePiece(new Bishop(board, ChessPiece.Color.WHITE), "a6");
		board.placePiece(new Bishop(board, ChessPiece.Color.WHITE), "f4");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}
	
	@Test
	void testBoxMate() {
		board.placePiece(blackKing, "d8");
		board.placePiece(new Rook(board, ChessPiece.Color.WHITE), "a8");
		board.placePiece(new King(board, ChessPiece.Color.WHITE), "d6");
		assertEquals(true, board.isCheckMate(ChessPiece.Color.BLACK));
	}

}