package client;

import java.util.ArrayList;

public abstract class ChessPiece {
	
	public enum Color {WHITE, BLACK};
	protected ChessBoard board = null;
	protected int row;
	protected int column;
	protected Color color;
	protected boolean isCaptured = false;
	protected Vest vest = null;

	public ChessPiece (ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isCaptured() {
		return isCaptured;
	}

	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}
	
	public String getPosition() {
		char c = (char) ('a' + this.column);
		char r = (char)('1' + this.row);
		return new String(c + "" + r);
	}
	
	public void setPosition(String position) throws IllegalPositionException {
		if(!isPositionOnBoard(position))
			throw new IllegalPositionException("Tried to set ChessPiece position off of the board.");
		this.row = position.charAt(1) - '1';
		this.column = position.charAt(0) - 'a';
	}
	
	public boolean isPositionOnBoard(String position) {
		if(position.length() != 2 || position.charAt(0) < 'a' || position.charAt(0) > 'h' || position.charAt(1) < '1' || position.charAt(1) > '8')
			return false;
		return true;
	}
	
	abstract public String toString();
	abstract public ArrayList<String> legalMoves();
	
}