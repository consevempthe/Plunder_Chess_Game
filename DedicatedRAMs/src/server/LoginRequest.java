package server;

import java.util.ArrayList;

/***
 * The LoginRequest class is a class that is created upon a login request coming from a client. The request should follow this protocol: login [nickname] [password]. LoginRequest will take the Request and build a response of success or failure.
 * @author DedicatedRAMs Team NF
 * Implements Request interface, which requires buildResponse() method.
 */
public class LoginRequest implements Request {
	
	private String nickname;
	private String password;
	private ServerWorker serverWorker;
	
	/**
	 * LoginRequest constructor takes the String from the request and fills nickname and password with the second and third arguments of the request.
	 * @param request - the entire request String from a Client.
	 * @param serverWorker - serverWorker to add nickname to upon login.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a LoginRequest. See class description.
	 */
	public LoginRequest(String request, ServerWorker serverWorker) throws IllegalRequestException  {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 3 || !requestSplit[0].equals("login"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
		this.password = requestSplit[2];
		this.serverWorker = serverWorker;
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor. It queries the database using SQL and checks to make sure that the nickname and password match the nickname and password gotten from the database.
	 * @return String - Either "login failed" if the nickname and password did not match exactly or the database was unaccessible, or "login success [nickname] [email] [password]" if the nickname and password were found in the database.
	 */
	@Override
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults = null;
		try {
			 queryResults = accessor.queryFromDatabase("select nickname, email, password from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return "login failed";
		}
		String email = new String();
		if(queryResults.size() == 3) {
			email = queryResults.get(1);
			serverWorker.setNickname(nickname);
			String response = "login success " + nickname + " " + email + " " + password;
			return response;
		}
		return "login failed";
	}
	
}
