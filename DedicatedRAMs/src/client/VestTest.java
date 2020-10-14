package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;

public class VestTest {
	
	private ChessBoard board;
	
	@BeforeEach
	public void setUp() {
		board  = new ChessBoard();
	}
	
	@Test
	void testKnightVest() throws IllegalPositionException{
		ChessPiece piece = new Pawn(board, Color.WHITE);
		piece.setVest(new Knight(board, Color.WHITE));
		
		board.placePiece(piece, "d3");
		assertEquals(piece.getVest().getType().getPosition(), "d3");
		
		ArrayList<String> legalMoves = piece.legalMoves(true, true);
		
		assertTrue(legalMoves.contains("e5"), "Legal moves should contain e5");
		assertTrue(legalMoves.contains("c5"), "Legal moves should contain c5");
		assertTrue(legalMoves.contains("f4"), "Legal moves should contain f4");
		assertTrue(legalMoves.contains("f2"), "Legal moves should contain f2");
		assertTrue(legalMoves.contains("e1"), "Legal moves should contain e1");
		assertTrue(legalMoves.contains("c1"), "Legal moves should contain c1");
		assertTrue(legalMoves.contains("b2"), "Legal moves should contain b2");
		assertTrue(legalMoves.contains("b4"), "Legal moves should contain b4");
	}
	
	@Test
	void testBishopVest() throws IllegalPositionException{
		ChessPiece piece = new Pawn(board, Color.WHITE);
		piece.setVest(new Bishop(board, Color.WHITE));
		
		board.placePiece(piece, "e2");
		assertEquals(piece.getVest().getType().getPosition(), "e2");
		
		ArrayList<String> legalMoves = piece.legalMoves(true, true);
		
		assertTrue(legalMoves.contains("d1"), "Legal moves should contain d1");
		assertTrue(legalMoves.contains("f1"), "Legal moves should contain f1");
		assertTrue(legalMoves.contains("f3"), "Legal moves should contain f3");
		assertTrue(legalMoves.contains("g4"), "Legal moves should contain g4");
		assertTrue(legalMoves.contains("h5"), "Legal moves should contain h4");
		assertTrue(legalMoves.contains("d3"), "Legal moves should contain d3");
		assertTrue(legalMoves.contains("c4"), "Legal moves should contain c4");
		assertTrue(legalMoves.contains("b5"), "Legal moves should contain b5");
		assertTrue(legalMoves.contains("a6"), "Legal moves should contain a6");
	}
	
	@Test
	void testRookVest() throws IllegalPositionException{
		ChessPiece piece = new Pawn(board, Color.WHITE);
		piece.setVest(new Rook(board, Color.WHITE));
		
		board.placePiece(piece, "b3");
		assertEquals(piece.getVest().getType().getPosition(), "b3");
		
		ArrayList<String> legalMoves = piece.legalMoves(true, true);
		
		assertTrue(legalMoves.contains("b1"), "Legal moves should contain b1");
		assertTrue(legalMoves.contains("b2"), "Legal moves should contain b2");
		assertTrue(legalMoves.contains("b4"), "Legal moves should contain b4");
		assertTrue(legalMoves.contains("b5"), "Legal moves should contain b5");
		assertTrue(legalMoves.contains("b6"), "Legal moves should contain b6");
		assertTrue(legalMoves.contains("b7"), "Legal moves should contain b7");
		assertTrue(legalMoves.contains("b8"), "Legal moves should contain b8");
		assertTrue(legalMoves.contains("a3"), "Legal moves should contain a3");
		assertTrue(legalMoves.contains("c3"), "Legal moves should contain c3");
		assertTrue(legalMoves.contains("d3"), "Legal moves should contain d3");
		assertTrue(legalMoves.contains("e3"), "Legal moves should contain e3");
		assertTrue(legalMoves.contains("f3"), "Legal moves should contain f3");
		assertTrue(legalMoves.contains("g3"), "Legal moves should contain g3");
		assertTrue(legalMoves.contains("h3"), "Legal moves should contain h3");
	}
	
	@Test
	void testQueenVest() throws IllegalPositionException{
		ChessPiece piece = new Pawn(board, Color.WHITE);
		piece.setVest(new Queen(board, Color.WHITE));
		
		board.placePiece(piece, "b3");
		assertEquals(piece.getVest().getType().getPosition(), "b3");
		
		ArrayList<String> legalMoves = piece.legalMoves(true, true);
		
		assertTrue(legalMoves.contains("b1"), "Legal moves should contain b1");
		assertTrue(legalMoves.contains("b2"), "Legal moves should contain b2");
		assertTrue(legalMoves.contains("b4"), "Legal moves should contain b4");
		assertTrue(legalMoves.contains("b5"), "Legal moves should contain b5");
		assertTrue(legalMoves.contains("b6"), "Legal moves should contain b6");
		assertTrue(legalMoves.contains("b7"), "Legal moves should contain b7");
		assertTrue(legalMoves.contains("b8"), "Legal moves should contain b8");
		assertTrue(legalMoves.contains("a3"), "Legal moves should contain a3");
		assertTrue(legalMoves.contains("c3"), "Legal moves should contain c3");
		assertTrue(legalMoves.contains("d3"), "Legal moves should contain d3");
		assertTrue(legalMoves.contains("e3"), "Legal moves should contain e3");
		assertTrue(legalMoves.contains("f3"), "Legal moves should contain f3");
		assertTrue(legalMoves.contains("g3"), "Legal moves should contain g3");
		assertTrue(legalMoves.contains("h3"), "Legal moves should contain h3");
		
		assertTrue(legalMoves.contains("a2"), "Legal moves should contain a2");
		assertTrue(legalMoves.contains("c4"), "Legal moves should contain c4");
		assertTrue(legalMoves.contains("d5"), "Legal moves should contain d5");
		assertTrue(legalMoves.contains("e6"), "Legal moves should contain e6");
		assertTrue(legalMoves.contains("f7"), "Legal moves should contain f7");
		assertTrue(legalMoves.contains("g8"), "Legal moves should contain g8");
		assertTrue(legalMoves.contains("a4"), "Legal moves should contain a4");
		assertTrue(legalMoves.contains("c2"), "Legal moves should contain c2");
		assertTrue(legalMoves.contains("d1"), "Legal moves should contain d1");
	}
	
	@Test
	void testAddingAndUsingVest() throws IllegalMoveException, IllegalPositionException
	{
		ChessPiece piece = new Bishop(board, Color.WHITE);
		ChessPiece pieceToCapture = new Knight(board, Color.BLACK);
		
		board.placePiece(piece, "c3");
		board.placePiece(pieceToCapture, "e5");
		
		board.move("c3", "e5");
		assertEquals(piece.getVest().getType().getClass(), Knight.class, "Vest should be of type Knight");
		
		board.move("e5", "f7");
		assertEquals(piece.getVest(), null, "Vest move used, the value should be null");
	}
	
	@Test
	void testAddingAndNotUsingVest() throws IllegalMoveException, IllegalPositionException
	{
		ChessPiece piece = new Pawn(board, Color.BLACK);
		ChessPiece pieceToCapture = new Rook(board, Color.WHITE);
		
		board.placePiece(piece, "c7");
		board.placePiece(pieceToCapture, "d6");
		
		board.move("c7", "d6");
		assertEquals(piece.getVest().getType().getClass(), Rook.class, "Vest should be of type Rook");
		
		board.move("d6", "d5");
		assertEquals(piece.getVest().getType().getClass(), Rook.class, "Vest move not used, the value should be null");
	}
	
	@Test
	void testPlunderableTypes() throws IllegalMoveException, IllegalPositionException
	{
		ChessPiece piece = new Queen(board, Color.BLACK);
		ChessPiece pieceToCapture = new Bishop(board, Color.WHITE);
		
		board.placePiece(piece, "d4");
		board.placePiece(pieceToCapture, "f6");
		
		board.move("d4", "f6");
		
		assertEquals(piece.getVest(), null, "Vest should be null, Queens can't plunder a bishop");
	}
}
