package client;

import java.util.concurrent.ThreadLocalRandom;

import gameLogic.Player;

public class GamesResponse implements Response {

	private String[] responseContent;

	private Client client;

	/**
	 * Constructor for GamesResponse, responseContent contains the response but
	 * split up by spacing.
	 * 
	 * @param response - response from Server.
	 */
	public GamesResponse(String response, Client client) {
		this.responseContent = response.split(" ");
		this.client = client;
	}

	/**
	 * handleResponse() is overridden from the interface. Either "games failed" or
	 * "games success [game ids as strings separated by spaces]"
	 */
	@Override
	public void handleResponse() {
		if (!responseContent[1].equals("success")) {
			System.out.println("Unable to retrieve games at this time.");
			return;
		}
		// do something with the games returned, spaces between game_ids
		// the return string is gameId player1 player2 and so on
		for(int i = 2; i <= this.responseContent.length - 3; i=i+3)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(1, 9);
			Game game = new Game(this.responseContent[i], this.responseContent[i+1], this.responseContent[i+2], randomNum);
			this.client.startUI.addGame(game);
		}
	}

	public class Game {
		public String gameId;

		public String player1;

		public String player2;
		
		public Player.Color turnColor;
		
		public int turnNumber;

		public Game(String gameId, String player1, String player2, int turnNumber) {
			this.gameId = gameId;
			this.player1 = player2;
			this.player2 = player2;
			this.turnNumber = turnNumber;
			this.turnColor = Player.Color.values()[turnNumber % 2];
			
		}
	}
}
