package server.Tests;

import client.Client;
import server.DatabaseAccessor;
import exceptions.IllegalRequestException;
import server.InviteRequest;
import server.LoginRequest;
import server.RemoteSSHConnector;
import server.Request;
import server.Server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

class InviteRequestTest {

	private String request1 = "invite add user1 user2 1";
	private String request2 = "invite remove user1 user2 1";
	private InviteRequest invite;
	private static int port = 1234;
	static Server server = new Server(port);
	private DatabaseAccessor accessor = new DatabaseAccessor();
	
	private RemoteSSHConnector connector = new RemoteSSHConnector(8088,3306, "faure.cs.colostate.edu", "concord.cs.colostate.edu");
	@BeforeEach
	public void setup() {
		connector.connect();
	}
	
	@AfterEach
	public void tearDown() {
		connector.disconnect();
	}
	
	@BeforeAll
	static void setupAll() throws IllegalRequestException, IOException {
    	server.start();
    	Client client = new Client("localhost", port);
    	Client client1 = new Client("localhost", port);
    	Client client2 = new Client("localhost", port);
    	client.connect();
    	client1.connect();
    	client2.connect();
    	
    	Request login1 = new LoginRequest("login test test", server.getWorkerList().get(0));
		login1.buildResponse();
		Request login2 = new LoginRequest("login user1 user1", server.getWorkerList().get(1));
		login2.buildResponse();
		Request login3 = new LoginRequest("login user2 user2", server.getWorkerList().get(2));
		login3.buildResponse();
	}
	
	@Test
	void testBadConstructor() {
		assertThrows(IllegalRequestException.class, () -> invite = new InviteRequest("invite add user1 user2 i i", server));
		assertThrows(IllegalRequestException.class, () -> invite = new InviteRequest("inv add user1 user1 i", server));
	}
	
	@Test
	void testBuildResponse1() throws IllegalRequestException, ClassNotFoundException {
		invite = new InviteRequest(request1, server);
		String response = invite.buildResponse();
		assertEquals("invite sent user2 user1 1", response);
		accessor.changeDatabase("delete from invitations where nicknameRx='user1';");
	}
	
	@Test
	void testBuildResponse2() throws IllegalRequestException {
		invite = new InviteRequest(request1, server);
		String response = invite.buildResponse();
		assertEquals("invite sent user2 user1 1", response);
		invite = new InviteRequest(request2, server);
		response = invite.buildResponse();
		assertEquals("invite remove user2 user1 1", response);
	}
	
	@Test
	void testBuildResponse3() throws IllegalRequestException {
		Request login1 = new LoginRequest("login test test", server.getWorkerList().get(0));
		login1.buildResponse();
		Request login2 = new LoginRequest("login user1 user1", server.getWorkerList().get(1));
		login2.buildResponse();
		Request login3 = new LoginRequest("login user2 user2", server.getWorkerList().get(2));
		login3.buildResponse();
		invite = new InviteRequest(request1, server);
		invite.buildResponse();
		assertEquals("test", server.getWorkerList().get(0).getNickname());
		assertEquals("user1", server.getWorkerList().get(1).getNickname());
		assertEquals("user2", server.getWorkerList().get(2).getNickname());
		invite = new InviteRequest(request2, server);
		invite.buildResponse();
	}
}
