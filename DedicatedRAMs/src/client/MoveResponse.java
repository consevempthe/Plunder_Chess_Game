package client;

import clientUI.ChessBoardUI;
import gameLogic.*;

public class MoveResponse implements Response {
	private User user;
	private Game game;
	private ChessBoardUI chessboardUI;
	private String[] responseContent;
	private String to;
	private String from;

	//move success a2 a3 gameID nickname
	public MoveResponse(String response, User user, ChessBoardUI cb) {
		this.responseContent = response.split(" ");
		if(responseContent.length > 2) {
		this.to = responseContent[1];
		this.from = responseContent[2];
		game = user.getGame(responseContent[3]);
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
		boolean moved = game.move(to, from);
		chessboardUI.updateGUI();
		user.setReady(true);
	}

}
