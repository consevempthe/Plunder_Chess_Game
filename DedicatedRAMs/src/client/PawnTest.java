package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {
    ChessBoard board = new ChessBoard();
    Pawn w_1, w_2, w_3;
    Pawn b_1, b_2, b_3;

    @BeforeEach
    void setup() {
        w_1 = new Pawn(board, ChessPiece.Color.WHITE);
        b_1 = new Pawn(board, ChessPiece.Color.BLACK);

        w_2 = new Pawn(board, ChessPiece.Color.WHITE);
        b_2 = new Pawn(board, ChessPiece.Color.BLACK);

        w_3 = new Pawn(board, ChessPiece.Color.WHITE);
        b_3 = new Pawn(board, ChessPiece.Color.BLACK);
    }

    @Test
    void testToString() {
        assertEquals("\u2659", w_1.toString());
        assertEquals("\u265F", b_1.toString());
    }

    @Test
    void testMoveTwice() {
        board.placePiece(w_1, "a2");
        assertEquals(2, w_1.legalMoves().size());
        assertTrue(w_1.legalMoves().contains("a3"));
        assertTrue(w_1.legalMoves().contains("a4"));
        board.placePiece(w_2, "a4");
        assertEquals(1, w_1.legalMoves().size());
        board.placePiece(w_2, "a3");
        assertEquals(0, w_1.legalMoves().size());

        board.placePiece(b_1, "h7");
        assertEquals(2, b_1.legalMoves().size());
        assertTrue(b_1.legalMoves().contains("h6"));
        assertTrue(b_1.legalMoves().contains("h5"));
        board.placePiece(b_2, "h5");
        assertEquals(1,b_1.legalMoves().size());
        board.placePiece(b_2, "h6");
        assertEquals(0, b_1.legalMoves().size());
    }

    @Test
    void testPawnMovesToCorner() {
        board.placePiece(w_1, "h7");
        assertTrue(w_1.legalMoves().contains("h8"));

        board.placePiece(w_2, "a7");
        assertEquals(1, w_2.legalMoves().size());

        board.placePiece(b_1, "h2");
        assertTrue(b_1.legalMoves().contains("h1"));

        board.placePiece(b_2, "a2");
        assertEquals(1, b_2.legalMoves().size());
    }

    @Test
    void testPawnCapture() {
        board.placePiece(w_1, "d3");
        board.placePiece(b_1, "e4");
        board.placePiece(b_2, "c4");

        assertEquals(3, w_1.legalMoves().size());
        assertTrue(w_1.legalMoves().contains("e4"));
        assertTrue(w_1.legalMoves().contains("c4"));

        board.placePiece(w_2, "e4");
        assertFalse(w_1.legalMoves().contains("e4"));

        board.placePiece(b_1, "e6");
        board.placePiece(w_2, "f5");
        board.placePiece(w_3, "d5");

        assertEquals(3, b_1.legalMoves().size());
        assertTrue(b_1.legalMoves().contains("f5"));
        assertTrue(b_1.legalMoves().contains("d5"));

        board.placePiece(b_2, "f5");
        assertFalse(w_1.legalMoves().contains("f5"));
    }
    
    @Test
    void testEnPassantLegalMove() throws Exception {
    	board.initialize();
    	board.placePiece(w_1, "b5");
    	board.move("a7", "a5");
    	assertEquals(2, w_1.legalMoves().size());
        assertTrue(w_1.legalMoves().contains("b6"));
        assertTrue(w_1.legalMoves().contains("a6"));
    }
    
    @Test
    void testPawnPromotionWhite () {
    	board.placePiece(w_1, "g7");
    	ChessPiece piece;
    	try {
			piece = board.getPiece("g7");
			assertEquals(true, (piece instanceof Pawn));
			board.move("g7", "g8");
			piece = board.getPiece("g8");
			// Note - piece instanceof Queen in the assertequals statement will need to change
			// later when the player can choose their pawn promotion.
			assertEquals(true, (piece instanceof Queen)); 
		} catch (Exception e) {
			fail("An exception has caused the test to fail.");
		}
    	
    }
    
    @Test
    void testPawnPromotionBlack () {
    	board.placePiece(b_1, "g2");
    	ChessPiece piece;
    	try {
			piece = board.getPiece("g2");
			assertEquals(true, (piece instanceof Pawn));
			board.move("g2", "g1");
			piece = board.getPiece("g1");
			// Note - piece instance of Queen in the assert equals statement will need to change
			// later when the player can choose their pawn promotion.
			assertEquals(true, (piece instanceof Queen)); 
		} catch (Exception e) {
			fail("An exception has caused the test to fail.");
		}
    	
    }
    
    @Test
    void testNoPromotion () {
    	board.placePiece(b_1, "g5");
    	ChessPiece piece;
    	try {
    		piece = board.getPiece("g5");
    		assertEquals(true, (piece instanceof Pawn));
    		board.move("g5", "g4");
    		piece = board.getPiece("g4");
    		assertEquals(true, (piece instanceof Pawn));
    		
    	} catch (Exception e) {
    		fail("An exception has caused the test to fail.");
    	}
    }
    
    
}