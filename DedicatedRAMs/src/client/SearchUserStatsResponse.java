package client;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JOptionPane;

import clientUI.StartUI;

public class SearchUserStatsResponse implements Response {
	
	private User user;
	private String[] responseContent;
	private Client client;
	
	/**
	 * The constructor for LoginResponse takes the response and a user to instantiate. responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 * @param user - User to instantiate
	 */
	public SearchUserStatsResponse(String response, User user, Client client) {
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
			showMessageDialog(null, "Invalid nickname", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String win = responseContent[2];
		String loss = responseContent[3];
		String draw = responseContent[4];
		
		Object [][] stat = new Object[][] {{win, loss, draw}};
		client.user.setUserStats(stat); 
		
	}
	
}
