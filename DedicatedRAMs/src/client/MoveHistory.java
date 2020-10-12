package client;

import java.util.ArrayList;

/**
 * MoveHistory class is a class containing the moves of a game in an ArrayList. The moveHistory list should be added to as players make moves.
 * @author DedicatedRAMs Team
 *
 */
public class MoveHistory {

	private ArrayList<Move> moveHistory;
	
	/**
	 * Default Constructor for MoveHistory creates an empty list of Moves.
	 */
	public MoveHistory() {
		this.moveHistory = new ArrayList<Move>();
	}
	
	/**
	 * getMoveHistory() is the getter for the moveHistory arrayList so that we can view the lists content outside of the MoveHistory class.
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * setMoveHistory() is the setter for the moveHistory ArrayList so that we can set the list if the game is being resumed, potentially. Check later to see if necessary.
	 * @param moveHistory - the list of moves in a game.
	 */
	public void setMoveHistory(ArrayList<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	/**
	 * addMoveToMoveHistory() adds a Move to the end of the ArrayList as a game progesses.
	 * @param moveToAdd - the move to add to the move history.
	 */
	public void addMoveToMoveHistory(Move moveToAdd) {
		moveHistory.add(moveToAdd);
	}
	
	public void removeEnd() {
		moveHistory.remove(moveHistory.size() -1);
	}
	
	public void setCapturedPieceInMove(ChessPiece captured) {
		moveHistory.get(moveHistory.size() - 1).setCaptured(captured);
	}
	
	public Move getLastMove() {
		return (moveHistory.get(0) != null) ? moveHistory.get(moveHistory.size() - 1): null;
	}
	
}
