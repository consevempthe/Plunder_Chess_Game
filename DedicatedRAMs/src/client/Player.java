package client;

import client.ChessPiece.Color;

public class Player {
	/**
	 * 
	 */

	public enum Color {
		WHITE, BLACK
	}
	private String nickname;
	private Color color;
	private boolean turn;

	public Player(Color color, String nickname, boolean turn) {
		this.color = color;
		this.nickname = nickname;
		this.turn = turn;
		
	}


}
