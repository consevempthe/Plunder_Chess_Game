package client;

import client.Player.Color;

public class Vest {

	public enum VestColor {
		YELLOW, BLUE, ORANGE, RED, GREEN
	}

	private VestColor color;
	private ChessPiece type;

	public Vest(ChessPiece type) {
		this.type = type;
		this.setColor();
	}

	/**
	 * Gets the type of the vest piece
	 * 
	 * @return A chess piece representing the vest type
	 */
	public ChessPiece getType() {
		return this.type;
	}

	/**
	 * Get the name of the vest to be used in the UI
	 * 
	 * @return A user readable name
	 */
	public String getName() {
		String typeString = this.type.getClass().toString();
		return typeString.substring(typeString.lastIndexOf(".") + 1);
	}

	/**
	 * Checks if a move is legal based on the vest.
	 * 
	 * @param position : the position of the move
	 * @return a value indicating whether to not a move is legal
	 */
	public boolean moveIsLegal(String position) {
		return this.type.legalMoves(true, false).contains(position);
	}

	/**
	 * Gets the vest color to be used on the UI.
	 * 
	 * @return a java swing useable color, the default is magenta (if a the vest
	 *         doesn't have a color)
	 */
	public java.awt.Color getUiColor() {
		if (this.color == VestColor.YELLOW) {
			return java.awt.Color.yellow;
		} else if (this.color == VestColor.BLUE) {
			return java.awt.Color.blue;
		} else if (this.color == VestColor.ORANGE) {
			return java.awt.Color.orange;
		} else if (this.color == VestColor.RED) {
			return java.awt.Color.red;
		} else if (this.color == VestColor.GREEN) {
			return java.awt.Color.green;
		}

		return java.awt.Color.magenta;
	}

	/**
	 * Sets the position of the vest relative to the game board
	 */
	public void setVestPosition(String position) throws IllegalPositionException {
		this.type.setPosition(position);
	}
	
	/**
	 * Gets the position of the vest relative to the game board
	 */
	public String getVestPosition()
	{
		return this.type.getPosition()
;	}
	
	/**
	 * Setter method - to set the color of a given piece.
	 * @param the color
	 */	
	public void setVestPieceColor(Color color)
	{
		this.type.setColor(color);
	}

	/**
	 * Sets the color of the vest based on the type
	 */
	private void setColor() {
		if (this.type.getClass() == Queen.class) {
			this.color = VestColor.YELLOW;
		} else if (this.type.getClass() == Bishop.class) {
			this.color = VestColor.BLUE;
		} else if (this.type.getClass() == Knight.class) {
			this.color = VestColor.ORANGE;
		} else if (this.type.getClass() == Rook.class) {
			this.color = VestColor.RED;
		} else if (this.type.getClass() == Pawn.class) {
			this.color = VestColor.GREEN;
		}
	}
}
