package client;

import client.Player.Color;

public class GameResponse implements Response {
	private String[] responseContent;
	private User user;
	private Client client;
	

	public GameResponse(String response, User u, Client c) {
		this.responseContent = response.split(" ");
		this.user = u;
		this.client = c;
	}

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
		System.out.println("The game no. " + responseContent[4] + " has started.");
	}

}