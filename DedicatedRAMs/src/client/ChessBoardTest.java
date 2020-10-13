package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;


class ChessBoardTest {

	private ChessBoard board;
	@BeforeEach
	public void setUp() {
		board  = new ChessBoard();
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
			assertEquals(true, instance);
			assertEquals("\u2659", piece.toString());
			board.replacePiece(new Queen(board, ChessPiece.Color.WHITE), "d5");
			piece = board.getPiece("d5");
			instance = piece instanceof Queen;
			assertEquals("\u2655", piece.toString());
			assertEquals(true, instance);
		} catch (IllegalPositionException e) {
			fail("testReplacePiece test failed");
		}
	}

}
