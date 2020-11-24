package client;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JOptionPane;

public class StopGameResponse implements Response {
	private String[] responseContent;
	private String oppo = "";
	private Client client;

	public StopGameResponse(String response, Client client) {
		this.responseContent = response.split(" ");
		this.client = client;
		if (responseContent.length > 1) {
			this.oppo = responseContent[1];
		}
	}
	@Override
	public void handleResponse() {
		//if this client's game opponent equals the nickname of the deleted user, stop game
		if (client.gameUI.getOpponentNickname().equals(oppo)){
			showMessageDialog(null, "Player has left the game.", "Delete User",
					JOptionPane.ERROR_MESSAGE);
			client.window.dispose();
			client.gameUI = null;
			client.game = null;
		}
	}
}
