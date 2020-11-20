package server;

import exceptions.IllegalRequestException;

import java.util.ArrayList;

/***
 * The DeleteUserRequest class is a class that is created upon a delete user request coming from a client. 
 * The request should follow this protocol: deleteuser [nickname] [password]. 
 * DeleteUserRequest will take the Request and build a response of success or failure.
 * @author DedicatedRAMs Team
 * Implements Request interface, which requires buildResponse() method.
 */
public class DeleteUserRequest implements Request {
	
	private String nickname;
	private String password;
	private ServerWorker serverWorker;
	
	/**
	 * DeleteUserRequest constructor takes the String from the request and fills nickname and password 
	 * with the second and third arguments of the request.
	 * @param request - the entire request String from a Client.
	 * @param serverWorker - serverWorker to remove nickname to upon deletion.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a DeleteUserRequest. 
	 */
	public DeleteUserRequest(String request, ServerWorker serverWorker) throws IllegalRequestException  {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 3 || !requestSplit[0].equals("deleteuser"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
		this.password = requestSplit[2];
		this.serverWorker = serverWorker;
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor. 
	 * It updates the database using SQL.
	 * @return String - Either "deleteuser failed" if the nickname and password did not match exactly or the database was unaccessible,
	 * or "deleteuser success [nickname]" if the nickname and password were found in the database.
	 */
	@Override
	public String buildResponse() {
		boolean c = checkUser();
		if (!c) {
			return "deleteuser failed";
		}
		boolean g = deleteGames();
		boolean i = deleteInvites();
		boolean r = deleteRegistration();
		if (r) {
			serverWorker.setNickname(null);
			String email = "";
			return "deleteuser success " + nickname + " " + email;
		} else {
			return "deleteuser failed";
		}
	}
	
	public boolean checkUser() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("select nickname, email, password from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		if(queryResults.size() == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteGames() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("delete from games where player1_nickname ='"+ nickname +"';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		try {
			 queryResults = accessor.queryFromDatabase("delete from games where player2_nickname ='"+ nickname +"';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	public boolean deleteInvites() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("delete from invitations where nicknameRx ='"+ nickname +"';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		try {
			 queryResults = accessor.queryFromDatabase("delete from invitations where nicknameTx ='"+ nickname +"';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	public boolean deleteRegistration() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("delete from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
}
