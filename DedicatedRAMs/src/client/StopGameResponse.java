package client;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class StopGameResponse implements Response {
	private String opponent = "";
	private Client client;

	public StopGameResponse(String response, Client client) {
		String[] responseContent = response.split(" ");
		this.client = client;
		if (responseContent.length > 1) {
			this.opponent = responseContent[1];
		}
	}
	@Override
	public void handleResponse() {
		//if this client's game opponent equals the nickname of the deleted user, stop game
		if (client.game.getOpponent().equals(opponent)){
			showMessageDialog(null, "Player has left the game.", "Delete User",
					JOptionPane.ERROR_MESSAGE);
			client.window.dispose();
			client.gameUI = null;
			client.game = null;
		}
	}
}
