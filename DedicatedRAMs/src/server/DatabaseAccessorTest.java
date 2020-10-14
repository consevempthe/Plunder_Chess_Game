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

}
