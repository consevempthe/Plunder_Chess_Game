package client;

import clientUI.GameUI;
import gameLogic.*;

public class MoveResponse implements Response {
	private User user;
	private Game game;
	private GameUI gameUI;
	private String[] responseContent;
	private String to;
	private String from;
	private String plunderOption;
	private Client client;

	public MoveResponse(String response, User user, GameUI cb, Client client) {
		this.responseContent = response.split(" ");
		if(responseContent.length > 2) {
			this.to = responseContent[1];
			this.from = responseContent[2];
			game = user.getGame(responseContent[3]);
			this.plunderOption = responseContent[5];
		}
		this.user = user;
		this.gameUI = cb;
		this.client = client;
	}

	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			System.out.println("Could not find game.");
			return;
		}
		else if(responseContent[1].equals("success")) {
			System.out.println("Move Succeeded.");
			//this.client.startUI.getUserGames();
			return;
		}
		
		game.move(to, from, plunderOption);
		if(gameUI != null)
		{
			gameUI.updateGUI();
		}
		else
		{
			System.out.println("UI was null " + this.client.getUser().getNickname());
			gameUI = new GameUI(game, this.client);
		}
		user.setReady(true);			
//		this.client.startUI.getUserGames();
	}

}
