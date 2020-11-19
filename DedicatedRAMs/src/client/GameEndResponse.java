package client;

import gameLogic.Player.Color;
import gameLogic.*;
import clientUI.GameUI;

import javax.swing.*;

public class GameEndResponse implements Response {
	private String[] responseContent;
	private Client client;

	/**
	 * Constructor for GameResponse, responseContent contains the response but split
	 * up by spacing.
	 * 
	 * @param response - response from Server.
	 */
	public GameEndResponse(String response, Client c) {
		this.responseContent = response.split(" ");
		this.client = c;
	}

	/**
	 * handleResponse() is overridden from the interface. Response is either "game
	 * failed" or "games success [player1nickname] [player2nickname] [gameID]".
	 * Assigns players to the Client's game object and initializes the board.
	 */
	@Override
	public void handleResponse() {
		// end outcome opponent winningColor
		if (responseContent[1].equals("failed")) {
			client.startUI.responseLabel.setText("Could not start game. Try again later.");
			return;
		}

		String outcome = responseContent[1];
		if (outcome.equals("checkMate")) {
			this.client.gameUI.showCheckMateDialog(responseContent[4]);
		}
	}

}