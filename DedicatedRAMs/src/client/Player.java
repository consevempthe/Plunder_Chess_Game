package client;

import client.ChessPiece.Color;

public class Player {

	public enum Color {
		WHITE, BLACK
	}
	private String nickname;
	private Color color;
	private boolean turn;
	
	/**
	 * Constructor. Player can be created with color enum, nickname string, and if it is player's turn. 
	 * @param color
	 * @param nickname
	 * @param turn
	 */

	public Player(Color color, String nickname, boolean turn) {
		this.color = color;
		this.nickname = nickname;
		this.turn = turn;
		
	}
	
	/**
	 * Getter method.
	 * @returns Player nickname.
	 */
	public String getNickname() {
		return this.nickname;
	}
	
	/**
	 * Getter method.
	 * @returns Player turn as true or false.
	 */
	public boolean getTurn() {
		return this.turn;
	}
	
	/**
	 * Getter method.
	 * @returns Player color, BLACK or WHITE.
	 */
	public Color getColor() {
		return this.color;
	}


}
