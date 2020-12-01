package client;

import gameLogic.Player.Color;
import gameLogic.*;
import clientUI.GameUI;

import javax.swing.*;

public class GameResponse implements Response {
	private String[] responseContent;
	private User user;
	private Client client;
	
	/**
	 * Constructor for GameResponse, responseContent contains the response but split up by spacing.
	 * @param response - response from Server.
	 */
	public GameResponse(String response, User u, Client c) {
		this.responseContent = response.split(" ");
		this.user = u;
		this.client = c;
	}
	
	/**
	 * handleResponse() is overridden from the interface.
	 * Response is either "game failed" or 
	 * "games success [player1nickname] [player2nickname] [gameID]".
	 * Assigns players to the Client's game object and initializes the board.
	 */
	@Override
	public void handleResponse() {
		//game success player1name player2name gameId
		if(responseContent[1].equals("failed")) {
			client.startUI.responseLabel.setText("Could not start game. Try again later.");
			return;
		}

		client.startUI.responseLabel.setText("Starting game...");
		String id = responseContent[4];
		user.createGame(id);

		Player p = new Player(Color.WHITE, responseContent[2]);
		Player o = new Player(Color.BLACK, responseContent[3]);
		user.getGame(id).setPlayers(p, o);

		Runnable r = () -> {
			client.gameUI = new GameUI(user.getGame(id), client);

			client.window = new JFrame("Plunder Chess - " + client.user.getNickname());
			client.window.add(client.gameUI.getGui());
			client.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			client.window.setLocationByPlatform(true);

			client.window.pack();

			client.window.setMinimumSize(client.window.getSize());
			client.window.setVisible(true);
			System.out.println(client.gameUI.toString());
			client.startUI.clearFields();
		};
		SwingUtilities.invokeLater(r);
	}

}