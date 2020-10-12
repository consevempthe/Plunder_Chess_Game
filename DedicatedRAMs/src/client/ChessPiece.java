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
	protected ArrayList<Class<?>> plunderableTypes;

	public ChessPiece (ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
		this.plunderableTypes = new ArrayList<Class<?>>();
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
	
	public boolean isPositionTakable(String position) {
		try {
			ChessPiece piece = board.getPiece(position);
			if(piece != null && piece.color.equals(this.color))
				return false;
		} catch (IllegalPositionException e) {
			return false;
		}
		return true;
	}
	
	public ArrayList<String> illegalMovesDueToCheck(ArrayList<String> potentialMoves){
		ArrayList<String> removeMoves = new ArrayList<>();
		Vest currentVest = vest;
		for(String position: potentialMoves) {
			String previous = getPosition();
			board.simulateMove(this, previous, position);
			if(board.isCheck(this.color))
				removeMoves.add(position);
			board.simulateMove(this, position, previous);
			if(board.getHistory().getLastMove() != null && board.getHistory().getLastMove().getCaptured() != null)
				board.placePiece(board.getHistory().getMoveHistory().get(board.getHistory().getMoveHistory().size()-1).getCaptured(), position);
			vest = currentVest;
			board.getHistory().removeEnd();
		}
		return removeMoves;
	}

	
	abstract public String toString();
	abstract public ArrayList<String> legalMoves(boolean includeVest, boolean turn);
	
}