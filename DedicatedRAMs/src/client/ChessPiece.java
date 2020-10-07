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
		if(!board.isPositionOnBoard(position))
			throw new IllegalPositionException("Tried to set ChessPiece position off of the board.");
		this.row = position.charAt(1) - '1';
		this.column = position.charAt(0) - 'a';
		
		// if the vest exists set the position to the same position as the parent piece
		if(this.vest != null)
		{
			this.vest.setVestPosition(position);
		}
	}
	
	public void setVest(ChessPiece type) throws IllegalPositionException //TODO add to CRC card/UML
	{
		this.vest = type != null ? new Vest(type) : null;
		
		// if the vest exists set the position to the same position as the parent piece
		if(this.vest != null)
		{
			this.vest.setVestPosition(this.getPosition());
		}
	}
	
	public Vest getVest() //TODO add to CRC card/UML
	{
		return this.vest;
	}
	
	abstract public String toString();
	abstract public ArrayList<String> legalMoves();
	
}