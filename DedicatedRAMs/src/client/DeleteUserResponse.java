package client;

import clientUI.LoginUI;
import clientUI.StartUI;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Window;

/**
 * DeleteUserResponse is a class used to handle a deleteuser response. The
 * protocol for the response is either, "deleteuser failed", if the deleteuser
 * failed in any way or "deleteuser success [nickname] [email]" if the delete
 * succeeded.
 * 
 * @author DedicatedRAMs
 *
 */
public class DeleteUserResponse implements Response {

	private User user;
	private String[] responseContent;
	private Client client;

	/**
	 * The constructor for DeleteUserResponse takes the response and a user to
	 * instantiate. responseContent contains the response but split up by spacing.
	 * 
	 * @param response - response from Server.
	 * @param user     - User to instantiate
	 */
	public DeleteUserResponse(String response, User user, Client client) {
		this.responseContent = response.split(" ");
		this.user = user;
		this.client = client;
	}

	/**
	 * handleResponse() is overridden from the interface, Response. It acts on the
	 * deleteuser response created. If the response comes back with the "success"
	 * key response, it will the UI will display a message showing success.
	 */
	@Override
	public void handleResponse() {
		if (responseContent[1].equals("success")) {
			showMessageDialog(null, "Sorry to see you go.\n", "Delete User", JOptionPane.ERROR_MESSAGE);
			client.user = new User(null, null, null);
			client.game = null;
			client.gameUI = null;
			
			if(client.startUI != null) {
				client.startUI.removeDeleteUserFrame();
				client.startUI.frame.dispose();
				client.startUI = null;
			}
			
			if(client.window != null) {
				client.window.dispose();
			}
			
			if (client.loginUI != null) {
				client.loginUI.frame.setVisible(true);
			} else {
				client.loginUI = new LoginUI(client);
			}
			return;

		}
		// String nickname = responseContent[2];
		// String email = responseContent[3];
		showMessageDialog(null, "Something went wrong.\nPlease try again later.", "Delete User",
				JOptionPane.ERROR_MESSAGE);

	}

}
