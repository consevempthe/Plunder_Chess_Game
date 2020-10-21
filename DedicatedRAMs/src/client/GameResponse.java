package client;

import client.Player.Color;

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
			System.out.println("Could not find game.");
			return;
		}

		String id = responseContent[4];
		user.createGame(id);
		client.game = user.getGame(id);
		client.game.setGameId(id);
		Player p = new Player(Color.WHITE, responseContent[2], true);
		Player o = new Player(Color.BLACK, responseContent[3], false);
		
		client.game.setPlayers(p, 0);
		client.game.setPlayers(o, 1);
		
		client.game.getGameBoard().initialize();
		System.out.println("Entering game no. " + responseContent[4] + ".");
	}

}