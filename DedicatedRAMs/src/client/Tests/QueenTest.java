package client.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import client.ChessBoard;
import client.ChessPiece;
import client.IllegalPositionException;
import client.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

public class QueenTest {
	//I am utilizing the QueenTest class to test the shared methods from ChessPiece.
	private ChessBoard board;
	private Queen white;
	private Queen black;
	private ChessPiece piece;
	
	@BeforeEach
	void setUp() {
		String input = "n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		board  = new ChessBoard(in);
		piece = new Queen(board, Color.WHITE);
		white = new Queen(board, Color.WHITE);
		black = new Queen(board, Color.BLACK);
	}
	
	
	@Test
	void testInitialValues() {
		assertEquals(Color.WHITE, piece.getColor(), "Piece should be white.");
	}
	
	@Test
	void testSetPositionCorrect() throws IllegalPositionException {
		piece.setPosition("b1");
		assertEquals("b1", piece.getPosition());
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
	void testLegalMovesMax() {
		board.placePiece(white, "d4");
		ArrayList<String> wMoves = white.legalMoves(true, true);
		assertEquals(27, wMoves.size());
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
		ArrayList<String> wMoves = white.legalMoves(true, true);
		assertEquals(4, wMoves.size());
		assertTrue(wMoves.contains("c4"));
		assertTrue(wMoves.contains("d3"));
		assertTrue(wMoves.contains("d5"));
		assertTrue(wMoves.contains("e3"));
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
		ArrayList<String> wMoves = white.legalMoves(true, true);
		assertEquals(4, wMoves.size());
		assertTrue(wMoves.contains("c4"));
		assertTrue(wMoves.contains("d3"));
		assertTrue(wMoves.contains("e3"));
		assertTrue(wMoves.contains("c5"));
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
		ArrayList<String> wMoves = white.legalMoves(true, true);
		assertEquals(0, wMoves.size());
	}
}
