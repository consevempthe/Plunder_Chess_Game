package client;

import client.ChessPiece.Color;

public class Player {
	/**
	 * From CRC Cards - 10/5 Knows which color it is Has pieces and captured pieces
	 * Captures a piece Wins games
	 */

	private Color color;
	private String nickname;

	public Player(Color color, String nickname) {
		this.color = color;
		this.nickname = nickname;
	}


}
