package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

public class QueenTest {
	//I am utilizing the QueenTest class to test the shared methods from ChessPiece.
	private ChessBoard board = new ChessBoard();
	private Queen white;
	private Queen black;
	private ChessPiece piece;
	
	@BeforeEach
	void setUp() {
		piece = new Queen(board, Color.WHITE);
		white = new Queen(board, Color.WHITE);
		black = new Queen(board, Color.BLACK);
	}
	
	
	@Test
	void testInitialValues() {
		assertEquals(board, piece.board, "Board should be created and not null");
		assertEquals(Color.WHITE, piece.color, "Piece should be white.");
	}
	
	@Test
	void testSetPositionCorrect() throws IllegalPositionException {
		piece.setPosition("b1");
		assertEquals(0, piece.row, "Expected row to be 1 but received " + piece.row);
		assertEquals(1, piece.column, "Expected column to be 2 but received " + piece.column);
	}
	
	@Test
	void testSetPositionPositionLength() {
		assertThrows(IllegalPositionException.class, ()->piece.setPosition("a1d"),
				"IllegelPositionException expected");
	}
	@Test
	void testSetPositionIllegalCharacter() {
		assertThrows(IllegalPositionException.class, ()->piece.setPosition("i1"),
				"IllegelPositionException expected");
		assertThrows(IllegalPositionException.class, ()->piece.setPosition("g9"),
				"IllegelPositionException expected");
		assertThrows(IllegalPositionException.class, ()->piece.setPosition("h0"),
				"IllegelPositionException expected");
		assertThrows(IllegalPositionException.class, ()->piece.setPosition((char) ('a'-1) + "5"),
				"IllegelPositionException expected");
	}
	
	@Test
	void testGetColor() {
		assertEquals(Color.WHITE, piece.getColor(), "Expected White Color of Piece.");
	}
	
	@Test
	void testGetPositionWithSetting() throws IllegalPositionException {
		piece.setPosition("h5");
		String pos = piece.getPosition();
		assertEquals("h5", pos, "Position should be h5.");
	}
	
	@Test
	void testToString() {
		assertEquals("\u2655", white.toString(), "Expect White Queen's Unicode to match the toString return.");
		assertEquals("\u265B", black.toString(), "Expect Black Queen's Unicode to match the toString return.");
	}
	
	@Test
	void testLegalMoves1() {
		board.placePiece(white, "d4");
		ArrayList<String> wMoves = white.legalMoves();
		assertEquals(27, wMoves.size());
		assertEquals(true, wMoves.contains("e4"));
		assertEquals(true, wMoves.contains("f4"));
		assertEquals(true, wMoves.contains("g4"));
		assertEquals(true, wMoves.contains("h4"));
		assertEquals(true, wMoves.contains("a4"));
		assertEquals(true, wMoves.contains("b4"));
		assertEquals(true, wMoves.contains("c4"));
		assertEquals(true, wMoves.contains("d1"));
		assertEquals(true, wMoves.contains("d2"));
		assertEquals(true, wMoves.contains("d3"));
		assertEquals(true, wMoves.contains("d5"));
		assertEquals(true, wMoves.contains("d6"));
		assertEquals(true, wMoves.contains("d7"));
		assertEquals(true, wMoves.contains("d8"));
		assertEquals(true, wMoves.contains("e5"));
		assertEquals(true, wMoves.contains("f6"));
		assertEquals(true, wMoves.contains("g7"));
		assertEquals(true, wMoves.contains("h8"));
		assertEquals(true, wMoves.contains("a7"));
		assertEquals(true, wMoves.contains("b6"));
		assertEquals(true, wMoves.contains("c5"));
		assertEquals(true, wMoves.contains("e3"));
		assertEquals(true, wMoves.contains("f2"));
		assertEquals(true, wMoves.contains("g1"));
		assertEquals(true, wMoves.contains("a1"));
		assertEquals(true, wMoves.contains("b2"));
		assertEquals(true, wMoves.contains("c3"));
	}
	
	@Test
	void testLegalMoves2() {
		board.placePiece(white, "d4");
		board.placePiece(new Queen(board, Color.WHITE), "e4");
		board.placePiece(new Queen(board, Color.BLACK), "c4");
		board.placePiece(new Queen(board, Color.WHITE), "e5");
		board.placePiece(new Queen(board, Color.BLACK), "d3");
		board.placePiece(new Queen(board, Color.WHITE), "c5");
		board.placePiece(new Queen(board, Color.BLACK), "d5");
		board.placePiece(new Queen(board, Color.WHITE), "c3");
		board.placePiece(new Queen(board, Color.BLACK), "e3");
		ArrayList<String> wMoves = white.legalMoves();
		assertEquals(4, wMoves.size());
		assertEquals(true, wMoves.contains("c4"));
		assertEquals(true, wMoves.contains("d3"));
		assertEquals(true, wMoves.contains("d5"));
		assertEquals(true, wMoves.contains("e3"));
	}
	
	@Test
	void testLegalMoves3() {
		board.placePiece(white, "d4");
		board.placePiece(new Queen(board, Color.WHITE), "e4");
		board.placePiece(new Queen(board, Color.BLACK), "c4");
		board.placePiece(new Queen(board, Color.WHITE), "e5");
		board.placePiece(new Queen(board, Color.BLACK), "d3");
		board.placePiece(new Queen(board, Color.WHITE), "b6");
		board.placePiece(new Queen(board, Color.WHITE), "d5");
		board.placePiece(new Queen(board, Color.WHITE), "c3");
		board.placePiece(new Queen(board, Color.BLACK), "e3");
		ArrayList<String> wMoves = white.legalMoves();
		assertEquals(4, wMoves.size());
		assertEquals(true, wMoves.contains("c4"));
		assertEquals(true, wMoves.contains("d3"));
		assertEquals(true, wMoves.contains("e3"));
		assertEquals(true, wMoves.contains("c5"));
	}
	@Test
	void testLegalMoves4() {
		board.placePiece(white, "d4");
		board.placePiece(new Queen(board, Color.WHITE), "e4");
		board.placePiece(new Queen(board, Color.WHITE), "c4");
		board.placePiece(new Queen(board, Color.WHITE), "e5");
		board.placePiece(new Queen(board, Color.WHITE), "d3");
		board.placePiece(new Queen(board, Color.WHITE), "c5");
		board.placePiece(new Queen(board, Color.WHITE), "d5");
		board.placePiece(new Queen(board, Color.WHITE), "c3");
		board.placePiece(new Queen(board, Color.WHITE), "e3");
		ArrayList<String> wMoves = white.legalMoves();
		assertEquals(0, wMoves.size());
	}
}
