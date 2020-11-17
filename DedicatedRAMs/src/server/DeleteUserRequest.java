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
	 * or "deleteuser success [nickname] [email]" if the nickname and password were found in the database.
	 */
	@Override
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("select nickname, email from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return "deleteuser failed";
		}
		String email;
		if(queryResults.size() == 2) {
			email = queryResults.get(1);
			serverWorker.setNickname(null);
			return "deleteuser success " + nickname + " " + email;
		}
		return "deleteuser failed";
	}
	
}
