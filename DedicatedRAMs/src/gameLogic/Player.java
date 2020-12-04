package gameLogic;

public class Player {

	public enum Color {
		BLACK, WHITE
	}
	private String nickname;
	private Color color;
	
	/**
	 * Constructor. Player can be created with color enum, nickname string, and if it is player's turn. 
	 * @param color - the color that the player is playing as
	 * @param nickname - the username of the player
	 */

	public Player(Color color, String nickname) {
		this.color = color;
		this.nickname = nickname;
	}
	
	/**
	 * Getter method.
	 * @return - Player nickname.
	 */
	public String getNickname() {
		return this.nickname;
	}
	
	/**
	 * Getter method.
	 * @return - Player color, BLACK or WHITE.
	 */
	public Color getColor() {
		return this.color;
	}


}
