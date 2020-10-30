package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAccessorTest {
	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
	@BeforeEach
	public void setup() {
		connector.connect();
	}
	
	@AfterEach
	public void tearDown() {
		connector.disconnect();
	}
	
	@Test
	void testQueryFromDatabaseCorrect() throws ClassNotFoundException {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> result = accessor.queryFromDatabase("select nickname, password from registration where nickname='test' and password='test';");
		assertEquals(2, result.size());
		assertEquals("test", result.get(0));
		assertEquals("test", result.get(1));
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
