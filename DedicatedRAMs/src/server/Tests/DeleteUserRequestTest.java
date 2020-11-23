package server.Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.IllegalRequestException;
import server.DatabaseAccessor;
import server.DeleteUserRequest;
import server.RegistrationRequest;
import server.RemoteSSHConnector;
import server.ServerWorker;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserRequestTest {

	private DeleteUserRequest deleteUser;
	private ServerWorker worker;
	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
	
	@BeforeEach
	public void setup() {
		connector.connect();
		worker = new ServerWorker(null, null);
		worker.setName("somename");
	}
	
	@AfterEach
	public void tearDown() {
		connector.disconnect();
	}

	@Test
	void testBuildResponseSucceed() throws IllegalRequestException {
		RegistrationRequest register = new RegistrationRequest("register cat1 cat@c.com cat1");
		String response = register.buildResponse();

		deleteUser = new DeleteUserRequest("deleteuser cat1 cat1", worker);
		response = deleteUser.buildResponse();
		assertEquals("deleteuser success cat1", response);
		assertNull(worker.getNickname());
	}
	
	@Test
	void testDeleteDatabase() throws IllegalRequestException {
		deleteUser = new DeleteUserRequest("deleteuser test test", worker);
		DatabaseAccessor accessor = new DatabaseAccessor();
		String nickname = "cathytest";
		boolean gamesp1 = deleteUser.databaseDelete(accessor, "delete from games where player1_nickname ='"+ nickname +"';");
		assertTrue(gamesp1);
	}
	
	@Test
	void testBuildResponseException() {
		assertThrows(IllegalRequestException.class, () -> deleteUser = new DeleteUserRequest(" ", worker));
		assertThrows(IllegalRequestException.class, () -> deleteUser = new DeleteUserRequest("ll ll ll", worker));
	}
	
	@Test
	void testBuildResponseFail1() throws IllegalRequestException {
		deleteUser = new DeleteUserRequest("deleteuser test wrongpasswordhere", worker);
		String response = deleteUser.buildResponse();
		assertEquals("deleteuser failed", response);
	}	
	
	@Test
	void testBuildResponseFail2() throws IllegalRequestException {
		deleteUser = new DeleteUserRequest("deleteuser nouser nopassword", worker);
		String response = deleteUser.buildResponse();
		assertEquals("deleteuser failed", response);
	}

}
