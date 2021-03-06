package gameLogic.Tests;

import gameLogic.*;
import exceptions.*;
import gameLogic.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

	private ChessBoard board;
	private King white;
	private King black;

	@BeforeEach
	void setUp() {
		board  = new ChessBoard();
		white = new King(board, Color.WHITE);
		black = new King(board, Color.BLACK);
		board.setWhiteKing(white);
		board.setBlackKing(black);
	}
	
	
	@Test
	void testAllowedVests() {
		assertTrue(white.hasVestType(new Rook(board, Color.BLACK)));
		assertTrue(white.hasVestType(new Queen(board, Color.BLACK)));
		assertTrue(white.hasVestType(new Bishop(board, Color.BLACK)));
		assertTrue(white.hasVestType(new Knight(board, Color.BLACK)));
		assertTrue(white.hasVestType(new Pawn(board, Color.BLACK)));
	}
	
	@Test
	void testLegalMovesWhite1() {
		board.placePiece(white, "a1");
		ArrayList<String> moveW = white.legalMoves(false, true);
		assertEquals(3, moveW.size(), "Expect White King's number of moves.");
		assertTrue(moveW.contains("b1"), "Expect White King's move set.");
		assertTrue(moveW.contains("b2"), "Expect White King's move set.");
		assertTrue(moveW.contains("a2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesWhite2() throws IllegalPositionException {
		board.initialize();
		white = (King) board.getPiece("e1");
		ArrayList<String> moveW = white.legalMoves(true, true);
		assertEquals(0, moveW.size(), "Expect no possible moves.");
		board.getMoveHistory().addMoveToMoveHistory(new Move(new Pawn(board, Color.WHITE), "e2", "e2"));
		board.placePiece(new King(board, Color.BLACK), "e2");

		moveW = white.legalMoves(true, true);
		assertEquals(1,  moveW.size(), "Expect 1 possible move.");
		assertTrue(moveW.contains("e2"), "Expect White King's move set.");
	}
	
	@Test
	void testLegalMovesBlack1() {
		board.placePiece(black, "a1");
		ArrayList<String> moveB = black.legalMoves(true, false);
		assertEquals(3, moveB.size(), "Expect White King's number of moves.");
		assertTrue(moveB.contains("b1"), "Expect Black King's move set.");
		assertTrue(moveB.contains("b2"), "Expect Black King's move set.");
		assertTrue(moveB.contains("a2"), "Expect Black King's move set.");
	}

	@Test
	void testLegalMovesBlack2() {
		board.initialize();
		board.placePiece(black, "e3");
		ArrayList<String> moveB = black.legalMoves(false, true);
		assertEquals(3, moveB.size(), "Expect 3 possible moves.");
		board.getMoveHistory().addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "e2", "e2"));
		board.placePiece(new Queen(board, Color.BLACK), "e2");
		moveB = black.legalMoves(false, true);
		assertEquals(3,  moveB.size(), "Expect 7 possible move.");
	}
	
	@Test
	void testLegalMovesCheckSimulation() throws IllegalPositionException {
		board.initialize();
		white = (King) board.getPiece("e1");
		black = (King) board.getPiece("e8");
		board.placePiece(white, "e6");
		ArrayList<String> moves = white.legalMoves(true, true);
		assertEquals(3, moves.size());
	}
	
	@Test
	void testLegalMovesWithVest() throws IllegalPositionException {
		board.placePiece(black, "a1");
		black.setVest(new Rook(board, black.getColor()));
		ArrayList<String> moveB = black.legalMoves(true, true);
		assertEquals(17,  moveB.size(), "Expect 17 possible move, two are repeats.");
		assertTrue(moveB.contains("a2"));
		assertTrue(moveB.contains("a3"));
		assertTrue(moveB.contains("a4"));
		assertTrue(moveB.contains("a5"));
		assertTrue(moveB.contains("a6"));
		assertTrue(moveB.contains("a7"));
		assertTrue(moveB.contains("a8"));
		assertTrue(moveB.contains("b1"));
		assertTrue(moveB.contains("c1"));
		assertTrue(moveB.contains("d1"));
		assertTrue(moveB.contains("e1"));
		assertTrue(moveB.contains("f1"));
		assertTrue(moveB.contains("g1"));
		assertTrue(moveB.contains("h1"));
		assertTrue(moveB.contains("a2"));
	}
	
	@Test
	void testCastlingIsLegalMove() {
		board.placePiece(white, "e1");
		board.placePiece(new Rook(board, Color.WHITE), "a1");
		board.placePiece(new Rook(board, Color.WHITE), "h1");
		assertTrue(white.legalMoves(true, false).contains("c1"));
		assertTrue(white.legalMoves(true, false).contains("g1"));


		board.placePiece(black, "e8");
		board.placePiece(new Rook(board, Color.BLACK), "a8");
		board.placePiece(new Rook(board, Color.BLACK), "h8");
		board.placePiece(new Bishop(board, Color.BLACK), "g8");
		assertTrue(black.legalMoves(true, false).contains("c8"));
		assertFalse(black.legalMoves(true, false).contains("g8"));

	}

	@Test
	void testCastlingMoveLeft() throws IllegalMoveException, IllegalPositionException {
		board.initialize();
		board.move("d2", "d4", "no"); //moves pawn
		board.move("d1", "d3", "no"); // moves queen
		board.move("c1", "d2", "no"); // move bishop
		board.move("b1", "a3", "no"); //moves knight
		board.move("e1", "c1", "no");
		assertTrue(board.getPiece("c1") instanceof King);
		assertTrue(board.getPiece("c1").getHasMoved());
		assertTrue(board.getPiece("d1") instanceof Rook);
		assertTrue(board.getPiece("d1").getHasMoved());
		board.setTurnWhite(false);
		board.move("d7", "d5", "no");
		board.move("d8", "d6", "no");
		board.move("c8", "d7", "no");
		board.move("b8", "a6", "no");
		board.move("e8", "c8", "no");
		assertTrue(board.getPiece("c8") instanceof King);
		assertTrue(board.getPiece("d8") instanceof Rook);

	}

	@Test
	void testCastlingRight() throws IllegalPositionException, IllegalMoveException {
		board.initialize();
		board.move("e2", "e4", "no");
		board.move("f1", "d3", "no");
		board.move("g1", "h3", "no");
		board.move("e1", "g1", "no");
		assertTrue(board.getPiece("g1") instanceof King);
		assertTrue(board.getPiece("f1") instanceof Rook);
	}

	@Test
	void testCheckMate1() {
		board.placePiece(new Rook(board, Color.BLACK), "c6");
		board.placePiece(new Rook(board, Color.BLACK), "d6");
		board.placePiece(new Rook(board, Color.BLACK), "e6");
		board.placePiece(white, "d4");
		assertTrue(board.isCheckMate(Color.WHITE));
	}

	@Test
	void testCheckMate2() {
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
		board.placePiece(white, "h7");
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
		board.placePiece(black, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testArabianMate() {
		board.placePiece(new Knight(board, Color.WHITE), "f6");
		board.placePiece(new Rook(board, Color.WHITE), "h7");
		board.placePiece(black, "h8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBackRateMate() {
		board.placePiece(new Pawn(board, Color.BLACK), "f7");
		board.placePiece(new Pawn(board, Color.BLACK), "g7");
		board.placePiece(new Pawn(board, Color.BLACK), "h7");
		board.placePiece(black, "g8");
		board.placePiece(new Rook(board, Color.WHITE), "d8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBishopKnightKingMate() {
		board.placePiece(new King(board, Color.WHITE), "g6");
		board.placePiece(new Knight(board, Color.WHITE), "h6");
		board.placePiece(new Bishop(board, Color.WHITE), "f6");
		board.placePiece(black, "h8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBlackburnesMate () {
		board.placePiece(new Bishop(board, Color.WHITE), "b2");
		board.placePiece(new Knight(board, Color.WHITE), "g5");
		board.placePiece(new Bishop(board, Color.WHITE), "h7");
		board.placePiece(new Rook(board, Color.BLACK), "f8");
		board.placePiece(black, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBlindSwineMate() {
		board.placePiece(new Rook(board, Color.WHITE), "g7");
		board.placePiece(new Rook(board, Color.WHITE), "h7");
		board.placePiece(new Rook(board, Color.BLACK), "f8");
		board.placePiece(black, "g8");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBodensMate() {
		board.placePiece(black, "c8");
		board.placePiece(new Pawn(board, Color.BLACK), "d7");
		board.placePiece(new Rook(board, Color.BLACK), "d8");
		board.placePiece(new Bishop(board, Color.WHITE), "a6");
		board.placePiece(new Bishop(board, Color.WHITE), "f4");
		assertTrue(board.isCheckMate(Color.BLACK));
	}

	@Test
	void testBoxMate() {
		board.placePiece(black, "d8");
		board.placePiece(new Rook(board, Color.WHITE), "a8");
		board.placePiece(new King(board, Color.WHITE), "d6");
		assertTrue(board.isCheckMate(Color.BLACK));
	}



}