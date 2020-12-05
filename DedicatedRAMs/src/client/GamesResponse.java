package client;

import java.io.IOException;
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
		this.client.startUI.clearGames();
		this.client.getUser().clearMatches();
		for(int i = 2; i <= this.responseContent.length - 3; i=i+3)
		{
			client.getUser().createGame(this.responseContent[i]);
			client.getUser().setGamePlayers(this.responseContent[i], this.responseContent[i+1], this.responseContent[i+2]);
			String loadRequest = "load " + this.responseContent[i] + "\n";
			try {
				this.client.request(loadRequest);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
