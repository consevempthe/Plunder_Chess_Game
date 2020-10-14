package server;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DatabaseAccessorTest {

	@Test
	void testQueryFromDatabaseCorrect() throws ClassNotFoundException, SQLException {
		DatabaseAccessor accessor = new DatabaseAccessor();
		accessor.queryFromDatabase("select nickname, password from registration where nickname='NStrike' and password='doyoureallywanttohackme';");
	}
	
	@Test
	void testQueryFromDatabaseIncorrect1() throws ClassNotFoundException {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> result = accessor.queryFromDatabase("select nickname, password from registration where ickname='NStrike' and password='doyoureallywanttohackme';");
		assertEquals(0, result.size());
	}
	
	@Test
	void testQueryFromDatabaseInsertDelete() throws ClassNotFoundException {
		DatabaseAccessor accessor = new DatabaseAccessor();
		boolean result = accessor.changeDatabase("insert into registration (nickname, email, password) values ('Sky_Breaker3', 'pretend', 'doyoureallywanttohackme');");
		boolean delete = accessor.changeDatabase("delete from registration where nickname='Sky_Breaker3';");
		assertTrue(result);
		assertTrue(delete);
	}
	
	@Test
	void testQueryFromDatabaseInsertIncorrect() throws ClassNotFoundException {
		DatabaseAccessor accessor = new DatabaseAccessor();
		boolean result = accessor.changeDatabase("insert into registration (nickname, email, password) values ('Sky_Breaker2', 'pretend', 'doyoureallywanttohackme');");
		assertFalse(result);
	}

}
