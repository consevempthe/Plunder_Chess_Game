package client;

public class MoveResponse implements Response {
	private User user;
	private Game game;
	private String[] responseContent;
	private String to;
	private String from;

	//move success a2 a3 gameID nickname
	public MoveResponse(String response, User user) {
		this.responseContent = response.split(" ");
		this.to = responseContent[1];
		this.from = responseContent[2];
		game = user.getGame(responseContent[3]);
		this.user = user;
	}

	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			System.out.println("Could not find game.");
			return;
		}
		boolean moved = game.move(to, from);
		if(moved)
			System.out.println(game.getGameBoard());
		user.setReady(true);
	}

}
