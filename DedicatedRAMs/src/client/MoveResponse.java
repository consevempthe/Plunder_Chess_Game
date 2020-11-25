package client;

import clientUI.GameUI;
import gameLogic.*;

public class MoveResponse implements Response {
	private User user;
	private Game game;
	private GameUI chessboardUI;
	private String[] responseContent;
	private String to;
	private String from;
	private String plunderOption;

	public MoveResponse(String response, User user, GameUI cb) {
		this.responseContent = response.split(" ");
		if(responseContent.length > 2) {
			this.to = responseContent[1];
			this.from = responseContent[2];
			game = user.getGame(responseContent[3]);
			this.plunderOption = responseContent[5];
		}
		this.user = user;
		this.chessboardUI = cb;
	}

	@Override
	public void handleResponse() {
		if(responseContent[1].equals("failed")) {
			System.out.println("Could not find game.");
			return;
		}
		else if(responseContent[1].equals("success")) {
			System.out.println("Move Succeeded.");
			return;
		}
		System.out.println("Here");
		game.move(to, from, plunderOption);
		chessboardUI.updateGUI();
		user.setReady(true);
	}

}
