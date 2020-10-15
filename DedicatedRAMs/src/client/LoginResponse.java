package client;
/**
 * LoginResponse is a class used to handle a login response. The protocol for the response is either, "login failed", if the login failed in any way or "login success [nickname] [email] [password]" if the login succeeded
 * where a User can be instantiated using the nickname, email, and password.
 * @author DedicatedRAMs NF 
 *
 */
public class LoginResponse implements Response {
	
	private User user;
	private String[] responseContent;
	
	/**
	 * The constructor for LoginResponse takes the response and a user to instantiate. responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 * @param user - User to instantiate
	 */
	public LoginResponse(String response, User user) {
		this.responseContent = response.split(" ");
		this.user = user;
	}
	
	
	/**
	 * handleResponse() is overridden from the interface, Response. It acts on the login response created.
	 */
	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			System.out.println("Your nickname or password were not recognized! Please try again.");
			return;
		}
		String nickname = responseContent[2];
		String email = responseContent[3];
		String password = responseContent[4];
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(password);
	    System.out.println("Welcome " + user.getNickname() + "!");
	}
	
}
