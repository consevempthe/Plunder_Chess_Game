package client;

import client.Player.Color;
import clientUI.ChessBoardUI;

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
			client.startUI.responseLbl.setText("Could not start game. Try again later.");
			return;
		}

		client.startUI.responseLbl.setText("Starting game...");
		String id = responseContent[4];
		user.createGame(id);

		Player p = new Player(Color.WHITE, responseContent[2]);
		Player o = new Player(Color.BLACK, responseContent[3]);
		user.getGame(id).setPlayers(p, o);

		Runnable r = () -> {
			client.chessBoardUI = new ChessBoardUI(user.getGame(id), client);

			JFrame window = new JFrame("Plunder Chess - " + client.user.getNickname());
			window.add(client.chessBoardUI.getGui());
			window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			window.setLocationByPlatform(true);

			window.pack();

			window.setMinimumSize(window.getSize());
			window.setVisible(true);
			System.out.println(client.chessBoardUI.toString());
			client.startUI.clearFields();
		};
		SwingUtilities.invokeLater(r);
	}

}