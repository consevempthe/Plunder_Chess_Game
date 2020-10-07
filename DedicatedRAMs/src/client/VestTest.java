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
	void testQueenKnightVest() throws IllegalPositionException{
		ChessPiece piece = new Queen(board, Color.WHITE);
		piece.setVest(new Knight(board, Color.WHITE));
		
		board.placePiece(piece, "d3");
		assertEquals(piece.getVest().getPiece().getPosition(), "d3");
		
		ArrayList<String> legalMoves = piece.legalMoves();
		
		assertTrue(legalMoves.contains("e5"), "Legal moves should contain e5");
		assertTrue(legalMoves.contains("c5"), "Legal moves should contain c5");
		assertTrue(legalMoves.contains("f4"), "Legal moves should contain f4");
		assertTrue(legalMoves.contains("f2"), "Legal moves should contain f2");
		assertTrue(legalMoves.contains("e1"), "Legal moves should contain e1");
		assertTrue(legalMoves.contains("c1"), "Legal moves should contain c1");
		assertTrue(legalMoves.contains("b2"), "Legal moves should contain b2");
		assertTrue(legalMoves.contains("b4"), "Legal moves should contain b4");
	}
}
