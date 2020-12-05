package client;

import clientUI.ProfileUI;
import clientUI.StartUI;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.IOException;

/**
 * LoginResponse is a class used to handle a login response. The protocol for the response is either, "login failed", if the login failed in any way or "login success [nickname] [email] [password]" if the login succeeded
 * where a User can be instantiated using the nickname, email, and password.
 * @author DedicatedRAMs NF 
 *
 */
public class LoginResponse implements Response {
	
	private User user;
	private String[] responseContent;
	private Client client;
	
	/**
	 * The constructor for LoginResponse takes the response and a user to instantiate. responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 * @param user - User to instantiate
	 */
	public LoginResponse(String response, User user, Client client) {
		this.responseContent = response.split(" ");
		this.user = user;
		this.client = client;
	}
	
	
	/**
	 * handleResponse() is overridden from the interface, Response. It acts on the login response created.
	 * If the response comes back with the "failed" key response, it will the UI will display an error message box informing the user that the login was not excepted.
	 * Otherwise, the user's data will be set given the response information.
	 */
	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			showMessageDialog(null, "Invalid nickname or password.\nPlease make sure your login information was entered correctly!", "Invalid Login", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String nickname = responseContent[2];
		String email = responseContent[3];
		String password = responseContent[4];
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(password);
		client.startUI = new StartUI(client);
		try {
			this.client.request("searchuserstats " + nickname +"\n");
			this.client.request("matchhistory " + nickname +"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.loginUI.frame.setVisible(false);
	}
	
}
