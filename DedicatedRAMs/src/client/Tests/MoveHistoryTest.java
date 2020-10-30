package client.Tests;

import client.*;
import client.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveHistoryTest {

	private MoveHistory history;
	
	private ChessBoard board;

	@BeforeEach
	void setUp() {
		history = new MoveHistory();
		String input = "n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		board  = new ChessBoard(in);
	}

	@Test
	void testGetMoveHistory() {
		ArrayList<Move> moves = history.getMoveHistory();
		assertEquals(0, moves.size());
	}

	@Test
	void testSetMoveHistory() {
		ArrayList<Move> test = new ArrayList<>();
		test.add(new Move(new Pawn(board, Color.WHITE), "a1", "a2", null));
		history.setMoveHistory(test);
		assertEquals(test, history.getMoveHistory());
	}

	@Test
	void testAddMoveToMoveHistory() {
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.WHITE), "1", "a", null), false);
		assertEquals("1", history.getMoveHistory().get(0).getCurrentPos());
	}

	// test the draw conditions here
	@Test 
	void testFiftyMoveRule(){
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.WHITE), "1", "a", null), false);
		for (int i = 0; i <= 50; i++)
		{
			history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "1", "a", null), false);
		}
		
		assertTrue(this.history.checkFiftyMoveRule());
		
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.WHITE), "1", "a", null), false);
		
		assertFalse(this.history.checkFiftyMoveRule());
	}
	
	@Test 
	void testThreePieceRepeatRule(){
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "1", "a", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "1", "a", null), false);
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "a", "2", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "a", "2", null), false);
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "2", "a", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "2", "a", null), false);
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "a", "3", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "a", "3", null), false);
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "3", "a", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "3", "a", null), false);
			
		assertTrue(this.history.checkThreefoldRepetition());
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "a", "4", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "a", "4", null), false);
		
		history.addMoveToMoveHistory(new Move(new Bishop(board, Color.WHITE), "4", "b", null), false);
		history.addMoveToMoveHistory(new Move(new Pawn(board, Color.BLACK), "4", "b", null), false);
		
		assertFalse(this.history.checkThreefoldRepetition());
	}
}
