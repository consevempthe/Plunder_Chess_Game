package server;

import java.util.ArrayList;

/***
 * The LoginRequest class is a class that is created upon a login request coming from a client. The request should follow this protocol: login [nickname] [password]. LoginRequest will take the Request and build a response of success or failure.
 * @author DedicatedRAMs Team
 *
 */
public class LoginRequest {
	
	private String nickname;
	private String password;
	
	/**
	 * LoginRequest constructor takes the String from the request and fills nickname and password with the second and third arguments of the request.
	 * @param request - the entire request String from a Client.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a LoginRequest. See class description.
	 */
	public LoginRequest(String request) throws IllegalRequestException  {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 3 || !requestSplit[0].equals("login"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
		this.password = requestSplit[2];
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor. It queries the database using SQL and checks to make sure that the nickname and password match the nickname and password gotten from the database.
	 * @return String - Either "login failed" if the nickname and password did not match exactly or the database was unaccessible, or "login success" if the nickname and password were found in the database.
	 */
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults = null;
		try {
			 queryResults = accessor.queryFromDatabase("select nickname, password from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return "login failed";
		}
		if(queryResults.size() == 2 && queryResults.get(0).equals(nickname) && queryResults.get(1).equals(password))
			return "login success";
		return "login failed";
	}
	
}
