package client;

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
			Game game = new Game(this.responseContent[i], this.responseContent[i+1], this.responseContent[i+2]);
			this.client.startUI.addGame(game);
		}
	}

	public class Game {
		public String gameId;

		public String player1;

		public String player2;

		public Game(String gameId, String player1, String player2) {
			this.gameId = gameId;
			this.player1 = player2;
			this.player2 = player2;
		}
	}
}
