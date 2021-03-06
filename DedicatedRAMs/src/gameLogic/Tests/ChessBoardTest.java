package gameLogic.Tests;


import gameLogic.*;
import exceptions.*;
import gameLogic.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


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
		board  = new ChessBoard();
		board.placePiece(new Queen(board, Color.BLACK), "a1");
		board.getMoveHistory().addMoveToMoveHistory(new Move(new Rook(null, Color.BLACK), "e2", "e2"));
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
		board.move("c4", "c8", "no");
		assertNull(board.getPiece("c4"));
	}

	@Test
	void testMove() throws Exception {
		board.initialize();
		assertNull(board.getPiece("c4"));
		board.move("c2", "c4", "no");
		assertNull(board.getPiece("c2"));
		assertNotNull(board.getPiece("c4"));

		board.move("g1", "f3", "no");
		boolean instance = board.getPiece("f3") instanceof Knight;
		assertTrue(instance);
		assertEquals(Color.WHITE, board.getPiece("f3").getColor());

	}

	@Test
	void testIllegalMoves() {
		board.initialize();
		assertThrows(IllegalPositionException.class, () -> board.move("d(", "d4", "no"));
		assertThrows(IllegalMoveException.class, () -> board.move("d1", "d4", "no"));
		assertThrows(IllegalMoveException.class, () -> board.move("e1", "e2", "no"));
		assertThrows(IllegalMoveException.class, () -> board.move("d5", "d6", "no"));
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
		board.placePiece(new Pawn(board, Color.WHITE), "d5");
		try {
			ChessPiece piece = board.getPiece("d5");
			boolean instance = piece instanceof Pawn;
			assertTrue(instance);
			assertEquals("\u2659", piece.toString());
			board.replacePiece(new Queen(board, Color.WHITE), "d5");
			piece = board.getPiece("d5");
			instance = piece instanceof Queen;
			assertTrue(instance);
		} catch (IllegalPositionException e) {
			fail("testReplacePiece test failed");
		}
	}

	@Test
	void testCheckMate1() {
		board.setWhiteKing(whiteKing);
		board.placePiece(new Rook(board, Color.BLACK), "c6");
		board.placePiece(new Rook(board, Color.BLACK), "d6");
		board.placePiece(new Rook(board, Color.BLACK), "e6");
		board.placePiece(whiteKing, "d4");
		assertTrue(board.isCheckMate(Color.WHITE));

	}

	@Test
	void testCheckMate2() {
		board.setWhiteKing(whiteKing);
		board.placePiece(new King(board, Color.WHITE), "d6");
		assertFalse(board.isCheckMate(Color.WHITE));
	}

	@Test
	void testKingDoesNotMoveFromCheckmateAlgorithm() {
		ChessPiece piece = null;
		board.placePiece(new King(board, Color.WHITE), "d6");
		try {
			piece = board.getPiece("d6");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		assertTrue(piece instanceof King);
		assertFalse(board.isCheckMate(Color.WHITE));
		ChessPiece piece2 = null;
		try {
			piece2 = board.getPiece("d6");
		} catch(IllegalPositionException e) {
			e.printStackTrace();
		}
		assertTrue(piece2 instanceof King);
		assertEquals("d6", piece2.getPosition());
	}

	@Test
	void testInitialBoardWhite() {
		board.initialize();
		assertFalse(board.isCheckMate(Color.WHITE));
	}

	@Test
	void testInitialBoardBlack() {
		board.initialize();
		assertFalse(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testAnastasiasMate() {
		board.placePiece(whiteKing, "h7");
		board.placePiece(new Pawn(board, Color.WHITE), "g7");
		board.placePiece(new Knight(board, Color.BLACK), "e7");
		board.placePiece(new Rook(board, Color.BLACK), "h5");
		assertTrue(board.isCheckMate(Color.WHITE));
	}

	@Test
	void testAnderssensMate () {
		board.placePiece(new King(board, Color.WHITE), "f6");
		board.placePiece(new Pawn(board, Color.WHITE), "g7");
		board.placePiece(new Rook(board, Color.WHITE), "h8");
		board.placePiece(blackKing, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testArabianMate() {
		board.placePiece(new Knight(board, Color.WHITE), "f6");
		board.placePiece(new Rook(board, Color.WHITE), "h7");
		board.placePiece(blackKing, "h8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBackRateMate() {
		board.placePiece(new Pawn(board, Color.BLACK), "f7");
		board.placePiece(new Pawn(board, Color.BLACK), "g7");
		board.placePiece(new Pawn(board, Color.BLACK), "h7");
		board.placePiece(blackKing, "g8");
		board.placePiece(new Rook(board, Color.WHITE), "d8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBishopKnightKingMate() {
		board.placePiece(new King(board, Color.WHITE), "g6");
		board.placePiece(new Knight(board, Color.WHITE), "h6");
		board.placePiece(new Bishop(board, Color.WHITE), "f6");
		board.placePiece(blackKing, "h8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBlackburnesMate () {
		board.placePiece(new Bishop(board, Color.WHITE), "b2");
		board.placePiece(new Knight(board, Color.WHITE), "g5");
		board.placePiece(new Bishop(board, Color.WHITE), "h7");
		board.placePiece(new Rook(board, Color.BLACK), "f8");
		board.placePiece(blackKing, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBlindSwineMate() {
		board.placePiece(new Rook(board, Color.WHITE), "g7");
		board.placePiece(new Rook(board, Color.WHITE), "h7");
		board.placePiece(new Rook(board, Color.BLACK), "f8");
		board.placePiece(blackKing, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBodensMate() {
		board.placePiece(blackKing, "c8");
		board.placePiece(new Pawn(board, Color.BLACK), "d7");
		board.placePiece(new Rook(board, Color.BLACK), "d8");
		board.placePiece(new Bishop(board, Color.WHITE), "a6");
		board.placePiece(new Bishop(board, Color.WHITE), "f4");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBoxMate() {
		board.placePiece(blackKing, "d8");
		board.placePiece(new Rook(board, Color.WHITE), "a8");
		board.placePiece(new King(board, Color.WHITE), "d6");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

}