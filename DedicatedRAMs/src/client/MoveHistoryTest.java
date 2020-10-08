package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveHistoryTest {

	private MoveHistory history;
	@BeforeEach
	void setUp() {
		history = new MoveHistory();
	}

	@Test
	void testGetMoveHistory() {
		ArrayList<Move> moves = history.getMoveHistory();
		assertEquals(0, moves.size());
	}

	@Test
	void testSetMoveHistory() {
		ArrayList<Move> test = new ArrayList<>();
		test.add(new Move(null, "a1", "a2"));
		history.setMoveHistory(test);
		assertEquals(test, history.getMoveHistory());
	}

	@Test
	void testAddMoveToMoveHistory() {
		history.addMoveToMoveHistory(new Move(null, "1", "a"));
		assertEquals("1", history.getMoveHistory().get(0).getFrom());
	}

}
