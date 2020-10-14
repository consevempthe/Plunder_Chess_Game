package server;


/**
 * The RegistrationRequest class is a class that is created upon a registration request from the client. The request should follow this protocol: register [nickname] [email] [password]. 
 * RegistrationRequest fills based on the request arguments and builds a response of success or failure.
 * @author DedicatedRAMs Team
 *
 */
public class RegistrationRequest {
	
	private String nickname;
	private String email;
	private String password;
	
	/**
	 * RegistrationRequest constructor takes the String from the request and fills nickname, email, and password with the second, third, and fourth arguments of the request.
	 * @param request - the entire request String from a Client.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a RegistrationRequest.
	 */
	public RegistrationRequest(String request) throws IllegalRequestException {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 4 || !requestSplit[0].equals("register"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
		this.email = requestSplit[2];
		this.password = requestSplit[3];
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor. It attempts to change the database using the insert SQL statement. If the SQL executes correctly, the changeDatabase() method returns true and therefore the registration was completed successfully.
	 * @return String - Either "register failed" if the nickname or email were already used or the database was unaccessible, or "register success" if the new user was inserted into the registration table.
	 */
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		boolean success = false;
		try {
			 success = accessor.changeDatabase("insert into registration (nickname, email, password) values ('"+ nickname + "', '" + email + "', '" + password + "');");
		} catch (ClassNotFoundException e) {
			return "register failed";
		}
		if(success)
			return "register success";
		return "register failed";
	}
	
}