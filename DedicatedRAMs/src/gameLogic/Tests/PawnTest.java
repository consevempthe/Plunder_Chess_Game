package gameLogic.Tests;


import gameLogic.*;
import exceptions.*;
import gameLogic.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {
    ChessBoard board;
    Pawn w_1, w_2, w_3;
    Pawn b_1, b_2, b_3;

    @BeforeEach
    void setup() {
		board  = new ChessBoard();
        w_1 = new Pawn(board, Color.WHITE);
        b_1 = new Pawn(board, Color.BLACK);

        w_2 = new Pawn(board, Color.WHITE);
        b_2 = new Pawn(board, Color.BLACK);

        w_3 = new Pawn(board, Color.WHITE);
        b_3 = new Pawn(board, Color.BLACK);
    }
    
    @Test
    void testPawnPromotionWhiteBishop () {
    	board.placePiece(w_1, "g8");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("BISHOP");
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Bishop && pawn.getColor() == Color.WHITE);
    }
    
    @Test
    void testPawnPromotionWhiteQueen () {
    	board.placePiece(w_1, "g8");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("QUEEN");
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Queen && pawn.getColor() == Color.WHITE);
    }
    
    @Test
    void testPawnPromotionWhiteKnight () {
    	board.placePiece(w_1, "g8");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("KNIGHT");
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Knight && pawn.getColor() == Color.WHITE);
    }
    
    @Test
    void testPawnPromotionWhiteRook () {
    	board.placePiece(w_1, "g8");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("ROOK");
    	try {
			pawn = board.getPiece("g8");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Rook && pawn.getColor() == Color.WHITE);
    }
    
    @Test
    void testPawnPromotionBlackBishop () {
    	board.placePiece(b_1, "a1");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("BISHOP");
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Bishop && pawn.getColor() == Color.BLACK);
    }
    
    @Test
    void testPawnPromotionBlackQueen () {
    	board.placePiece(b_1, "a1");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("QUEEN");
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Queen && pawn.getColor() == Color.BLACK);
    }
    
    @Test
    void testPawnPromotionBlackKnight () {
    	board.placePiece(b_1, "a1");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("KNIGHT");
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Knight && pawn.getColor() == Color.BLACK);
    }
    
    @Test
    void testPawnPromotionBlackRook () {
    	board.placePiece(b_1, "a1");
    	ChessPiece pawn = null;
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertNotNull(pawn);
    	assertTrue(pawn instanceof Pawn);
    	((Pawn)pawn).promote("ROOK");
    	try {
			pawn = board.getPiece("a1");
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
    	assertTrue(pawn instanceof Rook && pawn.getColor() == Color.BLACK);
    }

    @Test
    void testMoveTwice() {
        board.placePiece(w_1, "a2");
        assertEquals(2, w_1.legalMoves(true, true).size());
        assertTrue(w_1.legalMoves(true, true).contains("a3"));
        assertTrue(w_1.legalMoves(true, true).contains("a4"));
        board.placePiece(w_2, "a4");
        assertEquals(1, w_1.legalMoves(true, true).size());
        board.placePiece(w_2, "a3");
        assertEquals(0, w_1.legalMoves(true, true).size());

        board.placePiece(b_1, "h7");
        assertEquals(2, b_1.legalMoves(true, true).size());
        assertTrue(b_1.legalMoves(true, true).contains("h6"));
        assertTrue(b_1.legalMoves(true, true).contains("h5"));
        board.placePiece(b_2, "h5");
        assertEquals(1,b_1.legalMoves(true, true).size());
        board.placePiece(b_2, "h6");
        assertEquals(0, b_1.legalMoves(true, true).size());
    }

    @Test
    void testPawnMovesToCorner() {
        board.placePiece(w_1, "h7");
        assertTrue(w_1.legalMoves(true, true).contains("h8"));

        board.placePiece(w_2, "a7");
        assertEquals(1, w_2.legalMoves(true, true).size());

        board.placePiece(b_1, "h2");
        assertTrue(b_1.legalMoves(true, true).contains("h1"));

        board.placePiece(b_2, "a2");
        assertEquals(1, b_2.legalMoves(true, true).size());
    }

    @Test
    void testPawnCapture() {
    	board.initialize();
        board.placePiece(w_1, "d3");
        board.placePiece(b_1, "e4");
        board.placePiece(b_2, "c4");
        ArrayList<String> w_1_legal_moves = w_1.legalMoves(true, true);
        assertEquals(3, w_1_legal_moves.size());
        assertTrue(w_1_legal_moves.contains("e4"));
        assertTrue(w_1_legal_moves.contains("c4"));
        board.getMoveHistory().addMoveToMoveHistory(new Move(new Rook(null, Color.BLACK), "e2", "e2"));
        board.placePiece(w_2, "e4");
        assertFalse(w_1.legalMoves(true, true).contains("e4"));

        board.placePiece(b_1, "e6");
        board.placePiece(w_2, "f5");
        board.placePiece(w_3, "d5");

        ArrayList<String> b_1_legal_moves = b_1.legalMoves(true, true);
        assertEquals(3, b_1_legal_moves.size());
        assertTrue(b_1_legal_moves.contains("f5"));
        assertTrue(b_1_legal_moves.contains("d5"));

        board.placePiece(b_2, "f5");
        assertFalse(w_1.legalMoves(true, true).contains("f5"));
    }
    
    @Test
    void testEnPassantLegalMove() throws Exception {
    	board.initialize();
    	board.placePiece(w_1, "b5");
		board.setTurnWhite(false);
    	board.move("a7", "a5", "no");
		assertEquals(2, w_1.legalMoves(true, true).size());
        assertTrue(w_1.legalMoves(true, true).contains("b6"));
        assertTrue(w_1.legalMoves(true, true).contains("a6"));
    }

    @Test
    void testNoPromotion () {
    	board.placePiece(b_1, "g5");
    	ChessPiece piece;
    	try {
    		piece = board.getPiece("g5");
            assertTrue((piece instanceof Pawn));
    		board.setTurnWhite(false);
    		board.move("g5", "g4", "no");
    		piece = board.getPiece("g4");
            assertTrue((piece instanceof Pawn));
    		
    	} catch (Exception e) {
    		fail("An exception has caused the test to fail.");
    	}
    }
    
    @Test
    void testIllegalCheckMoves() throws IllegalPositionException {
    	board.initialize();
    	w_1 = (Pawn)board.getPiece("d2");
    	b_1 = (Pawn)board.getPiece("e7");
    	b_2 = (Pawn)board.getPiece("c7");
        board.placePiece(w_1, "d3");
        board.placePiece(b_1, "e4");
        board.placePiece(b_2, "c4");

    	ArrayList<String> removed;

    	removed = w_1.illegalMovesDueToCheck(new ArrayList<>());

        assertEquals(0, removed.size());
    	removed = w_1.illegalMovesDueToCheck(w_1.legalMoves(false, false));
        assertEquals(3, removed.size());
    	
    }

    @Test
	void testEnPassantMoves() throws IllegalMoveException, IllegalPositionException {
    	board.initialize();

    	board.setTurnWhite(true);
    	board.move("a2", "a4", "no");

    	board.setTurnWhite(false);
    	board.move("h7", "h5", "no");

		board.setTurnWhite(true);
		board.move("a4", "a5", "no");

		board.setTurnWhite(false);
		board.move("b7", "b5", "no");

		board.setTurnWhite(true);
		board.move("a5", "b6", "no");
		assertNull(board.getPiece("b7"));
		assertNull(board.getPiece("a5"));
	}
    
}