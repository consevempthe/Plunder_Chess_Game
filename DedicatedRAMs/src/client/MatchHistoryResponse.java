package client;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;

public class MatchHistoryResponse implements Response {

	private String[] responseContent;
	private Client client;

	/**
	 * The constructor for LoginResponse takes the response and a user to
	 * instantiate. responseContent contains the response but split up by spacing.
	 * 
	 * @param response - response from Server.
	 */
	public MatchHistoryResponse(String response, User user, Client client) {
		this.responseContent = response.split(" ");
		this.client = client;
	}

	/**
	 * handleResponse() is overridden from the interface, Response. It acts on the
	 * login response created. If the response comes back with the "failed" key
	 * response, it will the UI will display an error message box informing the user
	 * that the login was not excepted. Otherwise, the user's data will be set given
	 * the response information.
	 */
	@Override
	public void handleResponse() {
		if (responseContent[1].equals("failed")) {
			showMessageDialog(null, "Invalid nickname", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int cols = 6; // game_id, player1_nickname, player2_nickname, win, loss, draw
		int rows = (responseContent.length - 2) / cols;

		ArrayList<String> r = new ArrayList<String>();
		Object[][] match = new Object[rows][cols];
		int ri = 0;
		
		for (int i = 2; i <= responseContent.length - 6; i += 6) {
			r.add(responseContent[i]);
			r.add(responseContent[i + 1]);
			r.add(responseContent[i + 2]);
			r.add(responseContent[i + 3]);
			r.add(responseContent[i + 4]);
			r.add(responseContent[i + 5]);
			match[ri] = r.toArray();
			r.clear();
			ri += 1;
		}
		client.profileUI.setHistory(match);
	}
}
