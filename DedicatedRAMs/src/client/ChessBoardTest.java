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
		assertEquals(true, placed, "Place Piece Failed When It Should Have Succeeded!");
	}
	
	@Test
	void testPlacePiecePositionLength() {
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1d");
		boolean placed2 = board.placePiece(new Queen(board, Color.WHITE), "a");
		assertEquals(false, placed, "Position should be too long!");
		assertEquals(false, placed2, "Position should be too short!");
	}
	@Test
	void testPlacePiecePositionIllegalCharacter() {
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "^1");
		boolean placed2 = board.placePiece(new Queen(board, Color.WHITE), "g*");
		assertEquals(false, placed, "Position ^1 should return false!");
		assertEquals(false, placed2, "Position g* should return false!");
	}
	@Test
	void testPlacePieceAlreadyFilledSameColor() {
		board.placePiece(new Queen(board, Color.WHITE), "a1");
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1");
		assertEquals(false, placed, "Position is already filled by White and should return false!");
	}
	@Test
	void testPlacePieceAlreadyFilledDifferentColor() {
		board.placePiece(new Queen(board, Color.BLACK), "a1");
		boolean placed = board.placePiece(new Queen(board, Color.WHITE), "a1");
		assertEquals(true, placed, "Piece is captured and should return true!");
	}
	
	
	@Test
	void testGetPieceNull() throws IllegalPositionException {
		ChessPiece piece = board.getPiece("a1");
		assertEquals(null, piece, "Position a1 should return null!");
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
					assertEquals(null, piece, "Position should be empty.");
			}
		}
	}

}
